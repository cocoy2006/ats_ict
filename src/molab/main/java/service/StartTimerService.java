package molab.main.java.service;

import java.util.List;
import java.util.logging.Logger;

import molab.main.java.dao.ApplicationDao;
import molab.main.java.dao.ICtRunnerDao;
import molab.main.java.entity.ICtRunner;
import molab.main.java.util.Molab;
import molab.main.java.util.Status;
import molab.main.java.util.Status.RunnerStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class StartTimerService {

	private static final Logger log = Logger.getLogger(StartTimerService.class.getName());

	@Autowired
	private ICtRunnerDao rd;
	
	@Autowired
	private ApplicationDao ad;
	
	@Scheduled(cron = "0 0/1 * * * ?")
	public void run() {
		List<ICtRunner> runnerList = rd.findByState(Status.RunnerStatus.START.getInt(), 
				Molab.getInstance().getProperty(Molab.CFG_SELF_SERVER), 
				Integer.parseInt(Molab.getInstance().getProperty(Molab.CFG_SELF_PORT)));
		if(runnerList != null && runnerList.size() > 0) {
			for(ICtRunner runner : runnerList) {
				if(Molab.isAppExist(ad.findByRunnerId(runner.getId()))) {
					log.info("IPA file found.");
					updateState(runner, Status.RunnerStatus.READY);
				} else {
					log.info("IPA file not found.");
					updateState(runner, Status.RunnerStatus.DOWNLOAD);
				}
			}
		}
	}
	
	private void updateState(ICtRunner runner, RunnerStatus state) {
		log.info(String.format("ICloud test runner(id=%d)'s state swift to %s.", runner.getId(), state.name()));
		if(runner.getState() != state.getInt()) {
			runner.setState(state.getInt());
			rd.update(runner);
		}
	}

}
