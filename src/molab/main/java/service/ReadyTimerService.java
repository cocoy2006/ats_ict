package molab.main.java.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import molab.main.java.dao.ApplicationDao;
import molab.main.java.dao.ICtDeviceDao;
import molab.main.java.dao.ICtDispatcherDao;
import molab.main.java.dao.ICtRunnerDao;
import molab.main.java.dao.ICtScreenshotDao;
import molab.main.java.entity.Application;
import molab.main.java.entity.ICtDevice;
import molab.main.java.entity.ICtDispatcher;
import molab.main.java.entity.ICtRunner;
import molab.main.java.entity.ICtScreenshot;
import molab.main.java.util.HttpUtil;
import molab.main.java.util.Molab;
import molab.main.java.util.Path;
import molab.main.java.util.Status;
import molab.main.java.util.init.Idb;
import molab.main.java.util.shell.Result;

import org.libimobiledevice.ios.driver.binding.exceptions.SDKException;
import org.libimobiledevice.ios.driver.binding.services.DeviceService;
import org.libimobiledevice.ios.driver.binding.services.IOSDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ReadyTimerService {

	private static final Logger log = Logger.getLogger(ReadyTimerService.class.getName());
	
	@Autowired
	private ApplicationDao ad;
	
	@Autowired
	private ICtDeviceDao devDao;
	
	@Autowired
	private ICtDispatcherDao disDao;
	
	@Autowired
	private ICtRunnerDao rd;
	
	@Autowired
	private ICtScreenshotDao ssd;
	
	@Scheduled(cron = "0 0/1 * * * ?")
	public void run() throws IOException, SDKException {
		List<ICtRunner> runnerList = rd.findByState(Status.RunnerStatus.READY.getInt(), 
				Molab.getInstance().getProperty(Molab.CFG_SELF_SERVER), 
				Integer.parseInt(Molab.getInstance().getProperty(Molab.CFG_SELF_PORT)));
		if(runnerList != null && runnerList.size() > 0) {
			ThreadGroup tGroup = new ThreadGroup("icts");
			for(ICtRunner runner : runnerList) {
				ICtDevice device = devDao.findByRunnerId(runner.getId());
				IOSDevice iDevice = DeviceService.get(device.getSn());
				if(iDevice == null || !iDevice.isOnline()) {
					log.severe(String.format("Device %s is not ready.", device.getSn()));
					continue;
				} else {
					Molab.lock(iDevice);
					ICtDispatcher dispatcher = disDao.findById(runner.getDispatcherId());
					runner.setState(Status.RunnerStatus.RUNNING.getInt());
					rd.update(runner);
					Thread t = new Thread(tGroup, new Running(this, runner, dispatcher, iDevice, ad.findByRunnerId(runner.getId())), 
							"iCTS Thread " + runner.getId());
					t.start();
				}
			}
			while(tGroup.activeCount() > 0) {
				try {
					Thread.sleep(60000);
				} catch (InterruptedException e) {
					log.severe(e.getMessage());
				}
			}
			for(ICtRunner runner : runnerList) {
				rd.update(runner);
			}
			clear();
		}
	}
	
	class Running extends Thread {
		
		private ReadyTimerService service;
		private ICtRunner runner;
		private ICtDispatcher dispatcher;
		private IOSDevice iDevice;
		private Application app;
		private List<ICtScreenshot> ssList = new ArrayList<ICtScreenshot>();
		
		public Running(ReadyTimerService service, ICtRunner runner,
				ICtDispatcher dispatcher, IOSDevice iDevice, Application app) {
			this.service = service;
			this.runner = runner;
			this.dispatcher = dispatcher;
			this.iDevice = iDevice;
			this.app = app;
		}

		@Override
		public void run() {
			try {
				cts(); // Compatibility Test Suite
				runner.setState(Status.RunnerStatus.END.getInt());
				// update dispatcher if necessary
				ICtDispatcher dispatcher = service.disDao.findById(runner.getDispatcherId());
				// find dispatcher
				if(dispatcher != null && 
						dispatcher.getState() == Status.DispatcherStatus.START.getInt()) {
					dispatcher.setState(Status.DispatcherStatus.REPORTING.getInt());
					service.disDao.update(dispatcher);
					log.info(String.format("Dispatcher with id %d swift to REPORTING.", dispatcher.getId()));
				}
				// update screenshot
				for(ICtScreenshot ss : ssList) {
					service.ssd.save(ss);
				}
			} catch(Exception e) {
				// TODO maybe warning is needed
				runner.setState(Status.RunnerStatus.READY.getInt());
				log.severe(e.getMessage());
			} finally {
				try {
					Molab.release(iDevice);
				} catch (IOException e) {}
//				service.rd.update(runner);
			}
		}
		
		private void cts() throws IOException, InterruptedException {
			// installation
			boolean installSuccess = false;
			Result installResult = Idb.install(iDevice, Path.ipa(app.getAliasname()));
			if(installResult.getResult() != null) {
				installSuccess = true;
				runner.setInstallResult(installResult.getResult());
				runner.setInstallTime(installResult.getTime());
				log.info("Install success.");
			}
			
			// launch
			boolean loadSuccess = false;
			if(installSuccess) {
				Result loadResult = Idb.load(iDevice, app.getPackagename());
				if(loadResult.getResult() != null) {
					loadSuccess = true;
					runner.setLoadResult(loadResult.getResult());
					runner.setLoadTime(loadResult.getTime());
					log.info("Load success.");
				}
			}
			
			// just hold for a while
			if(loadSuccess) {
				hold();
				screenshot();
			}
			
			// uninstall
			if(installSuccess) {
				Result uninstallResult = Idb.uninstall(iDevice, app.getPackagename());
				if(uninstallResult.getResult() != null) {
					runner.setUninstallTime(uninstallResult.getTime());
					log.info("Uninstall success.");
				}
			}
		}
		
		private void hold() {
			if(dispatcher != null && dispatcher.getHoldTime() > 0) {
				try {
					Thread.sleep(dispatcher.getHoldTime() * 1000);
				} catch (InterruptedException e) {}
			}
		}
		
		private int ssCount = 1;
		private void screenshot() {
			String name = runner.getId() + "." + ssCount + ".png";
			String tmp = Path.temp(name);
			try {
				Idb.screenshot(iDevice, tmp);
				// post to file server
				String host = Molab.host() + "ictApi/file";
				HttpUtil.post(host, new File(tmp));
				// write into database
				ICtScreenshot ss = new ICtScreenshot();
				ss.setRunnerId(runner.getId());
				ss.setImage(name);
				ss.setOprTime(System.currentTimeMillis());
				ssList.add(ss);
				ssCount++;
			} catch (IOException e) {
				log.severe(e.getMessage());
			}
			
		}

	}
	
	private void clear() {
		try {
			File temp = new File(Path.temp());
			if(temp.exists() && temp.isDirectory()) {
				File[] files = temp.listFiles();
				for(File file : files) {
					file.delete();
				}
			}
		} catch(Exception e) {
			// TODO nothing
		}
	}
	
}
