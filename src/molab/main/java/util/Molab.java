package molab.main.java.util;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import org.libimobiledevice.ios.driver.binding.services.IOSDevice;
import org.libimobiledevice.ios.driver.binding.services.IOSDevice.DeviceState;

import molab.main.java.entity.Application;
import molab.main.java.util.init.Idb;

public class Molab {

	private static final Logger log = Logger.getLogger(Molab.class.getName());
	public static final String LAST_TIME = "opoL8fPcOoiJWZf594oE5Q=="; // 2016.04.29, useless but for atsgz
	public static final String EXPIRE_TIME = "GBDyfEFjcLaDp5Nv1+H1IQ=="; // 20170415
	public static final String CFG_SELF_SERVER = "self_server";
	public static final String CFG_SELF_PORT = "self_port";
	public static final String CFG_WEB_SERVER = "web_server";
	public static final String CFG_WEB_PORT = "web_port";
	public static final String CFG_WEB_NAME = "web_name";

	private static Molab molab = null;
	private static Properties props = null;

	public static Molab getInstance() {
		if (molab == null) {
			synchronized (Molab.class) {
				molab = new Molab();
				props = PropertiesUtil.loadProperties(Path.cfg());
			}
		}
		return molab;
	}
	
	public static boolean isAppExist(Application app) {
		if(app.getAliasname() != null) {
			String ipa = Path.ipa(app.getAliasname());
			File file = new File(ipa);
			return file.exists();
		}
		return false;
	}
	
	public String getProperty(String key) {
		if(props.containsKey(key)) {
			return props.getProperty(key);
		}
		return null;
	}
	
	public static Float setAccuracy(Float f, int scale) {
		int index = (int) Math.pow(10, scale);
		return (float) (Math.round(f * index)) / index;
	}
	
	public static String host() {
		return String.format(
				"http://%s:%s/%s/", 
				getInstance().getProperty(CFG_WEB_SERVER), 
				getInstance().getProperty(CFG_WEB_PORT), 
				getInstance().getProperty(CFG_WEB_NAME));
	}
	
	public static String getVersion(String udid) {
		String command = String.format("ideviceinfo -u %s -k ProductVersion", udid);
		String resp = Idb.execute(command);
		if(resp != null) {
			if(resp.contains("null")) {
				resp = resp.replace("null", "");
			}
			String[] nums = resp.split(".");
			if(nums.length == 1) {
				return resp + ".0";
			} else if(nums.length == 2) {
				return resp;
			} else if(nums.length >= 3) {
				return resp.substring(0, resp.lastIndexOf("."));
			}
		}
		return null;
	}
	
	public static void lock(IOSDevice iDevice) throws IOException {
		if(iDevice != null) {
			log.info("Device state swift to BUSY.");
			iDevice.setState(DeviceState.BUSY);
			// uninstall -3 apps
//			Idb.clear(iDevice);
		}
	}
	
	public static void release(IOSDevice iDevice) throws IOException {
		if(iDevice != null) {
			log.info("Device state swift to ONLINE.");
			iDevice.setState(DeviceState.ONLINE);
		}
	}

}
