package molab.main.java.service;

import java.util.logging.Logger;

import molab.main.java.util.Path;
import molab.main.java.util.Security;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service
public class InitializingService implements InitializingBean {
	
	private static final Logger log = Logger.getLogger(InitializingService.class.getName());

	@Override
	public void afterPropertiesSet() throws Exception {
		// initialize configuration
		initAtsgz();
	}
	
	private void initAtsgz() {
		long lastTime = Security.getLastTime(Path.atsgz());
		if(lastTime == 0) {
			log.severe("System is out of time.");
			System.exit(0);
		} else {
			Security.setLastTime(Path.atsgz(), System.currentTimeMillis());
		}
	}
	
}
