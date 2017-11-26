package molab.main.java.util;

public class Status {

	public static enum Common {
		ERROR(0), SUCCESS(1);
		private int value;

		private Common(int value) {
			this.value = value;
		}

		public int getInt() {
			return value;
		}
	}
	
	public static enum DispatcherStatus {
		START(0), REPORTING(1), END(2);
		private int value;

		private DispatcherStatus(int value) {
			this.value = value;
		}

		public int getInt() {
			return value;
		}
	}

	public static enum RunnerStatus {
		START(0), DOWNLOAD(1), READY(2), RUNNING(3), INSTALL(4), LOAD(5), MONKEY(6), UNINSTALL(7), END(8);
		private int value;

		private RunnerStatus(int value) {
			this.value = value;
		}

		public int getInt() {
			return value;
		}
	}
	
	public static enum DeviceStatus {
		FREE(0), BUSY(1), RESERVED(2), UNACTIVED(3), PRELOCK(4), OFFLINE(5), UNAUTHORIZED(6), REMOVED(9);
		private int value;

		private DeviceStatus(int value) {
			this.value = value;
		}

		public int getInt() {
			return value;
		}
	}
	
	public static enum CommandType {
		LIST, MOUNT, INSTALL, LOAD, UNINSTALL, SCREENSHOT;
	}

}