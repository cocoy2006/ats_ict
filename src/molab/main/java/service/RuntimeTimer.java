package molab.main.java.service;

import java.util.logging.Logger;

import molab.main.java.util.Molab;
import molab.main.java.util.Path;
import molab.main.java.util.Security;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

@Component
public class RuntimeTimer {

	@Scheduled(cron = "0 0 0/1 * * ?")
    public void run() {
		if(isExpire()) {
			System.exit(0);
		}
    }
	
	private static final Logger log = Logger.getLogger(RuntimeTimer.class.getName());
	
	private boolean isExpire() {
		long now = System.currentTimeMillis();
		String atsgz = Path.atsgz();
		if((now > decodeExpireTime())
				|| Security.getLastTime(atsgz) == 0 
				|| now <= Security.getLastTime(atsgz)) {
			return true;
		} else {
			Security.setLastTime(atsgz, now);
			return false;
		}
	}
	
	private long decodeExpireTime() {
		long expireTime = 0;
		try {
			expireTime = Long.parseLong(new String(Security.decrypt(Base64.decode(Molab.EXPIRE_TIME)), "UTF8"));
		} catch (Exception e) {
			log.severe(e.getMessage());
		}
		return expireTime;
	}
	
}
