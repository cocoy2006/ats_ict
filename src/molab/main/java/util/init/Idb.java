package molab.main.java.util.init;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import molab.main.java.util.Molab;
import molab.main.java.util.Path;
import molab.main.java.util.Status.CommandType;
import molab.main.java.util.shell.Result;

import org.libimobiledevice.ios.driver.binding.services.IOSDevice;

public class Idb {
	
	private static final Logger log = Logger.getLogger(Idb.class.getName());
	private static Idb idb = null;
	
	private Idb() {
		
	}
	
	public static Idb getInstance() {
		if(idb == null) {
			synchronized(Idb.class) {
				idb = new Idb();
			}
		}
		return idb;
	}
	
	public static Result list() throws IOException {
		Result result = executeSync(null, CommandType.LIST, null);
		log.info(String.format("查询结束: %s, 耗时 %d 毫秒", result.getResult(), result.getTime()));
		return result;
	}
	
	public static Result mount(IOSDevice iDevice) throws IOException {
		Result result = executeSync(iDevice, CommandType.MOUNT, null);
		log.info(String.format("挂载结束: %s, 耗时 %d 毫秒", result.getResult(), result.getTime()));
		return result;
	}
	
	/**
	 * @param iDevice IOSDevice
	 * @param ipa full path of ipa file 
	 * @throws InterruptedException */
	public static Result install(IOSDevice iDevice, String ipa) throws IOException, InterruptedException {
		Result result = executeSync(iDevice, CommandType.INSTALL, ipa);
		log.info(String.format("安装结束: %s, 耗时 %d 毫秒", result.getResult(), result.getTime()));
		Thread.sleep(10000);
		return result;
	}
	
	/**
	 * @param iDevice IOSDevice
	 * @param packageName APPID 
	 * @throws InterruptedException */
	public static Result load(IOSDevice iDevice, String packageName) throws IOException, InterruptedException {
		Result result = executeSync(iDevice, CommandType.LOAD, packageName);
		if(result.getResult() == null) {
			result.setResult("LOAD_SUCCESS");
		}
		log.info(String.format("加载结束: %s, 耗时 %d 毫秒", result.getResult(), result.getTime()));
		Thread.sleep(5000);
		return result;
	}
	
	/**
	 * @param iDevice IOSDevice
	 * @param packageName APPID */
	public static Result uninstall(IOSDevice iDevice, String packageName) throws IOException {
		Result result = executeSync(iDevice, CommandType.UNINSTALL, packageName);
		log.info(String.format("卸载结束: %s, 耗时 %d 毫秒", result.getResult(), result.getTime()));
		return result;
	}
	
	/**
	 * @param iDevice IOSDevice
	 * @param filePath file's full path */
	public static Result screenshot(IOSDevice iDevice, String filePath) throws IOException {
		Result result = executeSync(iDevice, CommandType.SCREENSHOT, filePath);
		log.info(String.format("截图结束: %s, 耗时 %d 毫秒", result.getResult(), result.getTime()));
		return result;
	}
	
	public static Result executeSync(IOSDevice iDevice, CommandType cmdType, String cmd) throws IOException {
		synchronized(Idb.class) {
			long start = System.currentTimeMillis();
			Result result = null;
			try {
				String r = execute(iDevice, cmdType, cmd);
				long time = System.currentTimeMillis() - start;
				result = new Result(r, time);
			} catch (Exception e) {
				throw new IOException("Idb crash.");
			}
			return result;
		}
	}
	
	private static String execute(IOSDevice iDevice, CommandType cmdType, String param) throws IOException {
		String command = null;
		switch(cmdType) {
			case LIST:
				command = "idevice_id -l";
				break;
			case MOUNT:
				command = String.format("ideviceimagemounter -u %s %s", iDevice.getUUID(), 
						Path.diskimage(Molab.getVersion(iDevice.getUUID())));
				break;
			case INSTALL:
				command = String.format("ideviceinstaller -u %s -i %s", 
						iDevice.getUUID(), param);
				break;
			case LOAD:
				command = String.format("idevicedebug -u %s run %s", 
						iDevice.getUUID(), param);
				break;
			case UNINSTALL:
				command = String.format("ideviceinstaller -u %s -U %s", 
						iDevice.getUUID(), param);
				break;
			case SCREENSHOT:
				command = String.format("idevicescreenshot -u %s %s", 
						iDevice.getUUID(), param);
				break;
			default:
				throw new IOException("Command not found.");
		}
		return execute(command);
	}
	
	public static String execute(String command) {
		log.info("Executing " + command);
		BufferedReader br = null;
		Process process = null;
		String result = null;
		try {
			process = Runtime.getRuntime().exec(command);
			br = new BufferedReader(new InputStreamReader(process.getInputStream(), "utf-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				result += line + System.getProperty("line.separator");;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
}
