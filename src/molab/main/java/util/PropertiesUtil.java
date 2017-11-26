package molab.main.java.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class PropertiesUtil {
	
	private static final Logger log = Logger.getLogger(PropertiesUtil.class.getName());

	public static Properties loadProperties(String file) {
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(file));
			return loadProperties(is);
		} catch (Exception e) {
			log.severe(e.getMessage());
			return null;
		}
	}

	public static Properties loadProperties(InputStream is) {
		Properties props = new Properties();
		try {
			props.load(is);
			return props;
		} catch (Exception e) {
			log.severe(e.getMessage());
			return null;
		}
	}
	
}
