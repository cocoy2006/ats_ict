package molab.main.java.service;

import java.net.MalformedURLException;
import java.util.List;
import java.util.logging.Logger;

import molab.main.java.dao.ApplicationDao;
import molab.main.java.dao.ICtRunnerDao;
import molab.main.java.entity.Application;
import molab.main.java.entity.ICtRunner;
import molab.main.java.util.HttpUtil;
import molab.main.java.util.Molab;
import molab.main.java.util.Path;
import molab.main.java.util.Status;
import molab.main.java.util.Status.RunnerStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class DownloadTimerService {

	private static final Logger log = Logger.getLogger(DownloadTimerService.class.getName());
	
	@Autowired
	private ApplicationDao ad;
	
	@Autowired
	private ICtRunnerDao rd;
	
	@Scheduled(cron = "0 0/1 * * * ?")
	public void run() {
		List<ICtRunner> runnerList = rd.findByState(Status.RunnerStatus.DOWNLOAD.getInt(), 
				Molab.getInstance().getProperty(Molab.CFG_SELF_SERVER), 
				Integer.parseInt(Molab.getInstance().getProperty(Molab.CFG_SELF_PORT)));
		if(runnerList != null && runnerList.size() > 0) {
			for(ICtRunner runner : runnerList) {
				downloading(runner);
			}
		}
	}
	
	private void downloading(ICtRunner runner) {
		updateState(runner, Status.RunnerStatus.DOWNLOAD);
		if(Molab.isAppExist(ad.findByRunnerId(runner.getId())) || doDownload(runner)) {
			updateState(runner, Status.RunnerStatus.READY);
		}
	}
	
	private void updateState(ICtRunner runner, RunnerStatus state) {
		log.info(String.format("ICloud test runner(id=%d)'s state swift to %s.", runner.getId(), state.name()));
		if(runner.getState() != state.getInt()) {
			runner.setState(state.getInt());
			rd.update(runner);
		}
	}
	
	private boolean doDownload(ICtRunner runner) {
		// first, get alias name of application
		Application app = ad.findByRunnerId(runner.getId());
		if(app != null) {
			log.info("Downloading file " + app.getAliasname());
			String remote = String.format(
					"http://%s:%s/%s/upload/ipa/%s", 
							Molab.getInstance().getProperty(Molab.CFG_WEB_SERVER), 
							Molab.getInstance().getProperty(Molab.CFG_WEB_PORT), 
							Molab.getInstance().getProperty(Molab.CFG_WEB_NAME), 
							app.getAliasname());
			String local = Path.ipa(app.getAliasname());
			try {
				if(HttpUtil.download(remote, local)) {
					log.info("Download successful.");
					return true;
				}
			} catch (MalformedURLException e) {
				log.severe("Download failed.");
			}
		}
		return false;
	}

}
