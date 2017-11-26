package molab.main.java.util;

import java.io.File;
import java.util.logging.Logger;

public class Path {
	
	private static final Logger log = Logger.getLogger(Path.class.getName());
	
	public static String atsgz() {
		try {
			return getWebInf() + File.separator + "atsgz";
		} catch(NullPointerException e) {
			log.severe("Important files miss.");
			System.exit(0);
		}
		return null;
	}
	
	public static String cfg() {
		return getWebInf() + "/cfg.properties";
	}
	
	private static String getWebInf() {
		return new File(Path.class.getResource("/").getFile()).getParent();
	}
	
	/**
	 * path of DeveloperDiskImage */
	public static String diskimage(String version) {
		return getWebRoot() + "/resources/diskimage/" + version + "/DeveloperDiskImage.dmg";
	}
	
	/**
	 * call imobiledevice command, like idevice_id, idevicedebug, ideviceinstaller and so on */
//	public static String imobiledevice(String name) {
//		return getWebRoot() + "/resources/imobiledevice/" + name;
//	}
	
	public static String temp() {
		return getWebRoot() + "/temp/";
	}
	
	public static String temp(String name) {
		return getWebRoot() + "/temp/" + name;
	}
	
	public static String ipa(String name) {
		return getWebRoot() + "/upload/ipa/" + name;
	}
	
	private static String getWebRoot() {
		return new File(Path.class.getResource("/").getFile()).getParentFile().getParent();
	}

}
