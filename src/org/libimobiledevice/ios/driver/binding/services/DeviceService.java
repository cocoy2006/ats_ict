/*
 * Copyright 2012-2013 eBay Software Foundation and ios-driver committers
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.libimobiledevice.ios.driver.binding.services;

import static org.libimobiledevice.ios.driver.binding.exceptions.SDKErrorCode.throwIfNeeded;
import static org.libimobiledevice.ios.driver.binding.raw.ImobiledeviceSdkLibrary.sdk_idevice_event_subscribe;
import static org.libimobiledevice.ios.driver.binding.raw.ImobiledeviceSdkLibrary.sdk_idevice_event_unsubscribe;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import molab.main.java.util.init.Idb;
import molab.main.java.util.shell.Result;

import org.libimobiledevice.ios.driver.binding.exceptions.SDKException;

public class DeviceService {

	public static final DeviceService INSTANCE = new DeviceService();
	private final Map<String, IOSDevice> devices = new HashMap<String, IOSDevice>();

	public synchronized static IOSDevice get(String uuid) throws SDKException, IOException {
		if (uuid == null) {
			throw new IllegalArgumentException("device id cannot be null.");
		}
		if(!isOnline(uuid)) {
			return null;
		}
		IOSDevice res = INSTANCE.devices.get(uuid);
		if (res == null) {
			res = new IOSDevice(uuid);
			Idb.mount(res);
			INSTANCE.devices.put(uuid, res);
		}
		return res;
	}
	
	static boolean isOnline(String uuid) {
		try {
			Result result = Idb.list();
			return result.getResult() != null && result.getResult().contains(uuid);
		} catch (IOException e) {}
		return false;
	}

	public static void free() throws SDKException, IOException {
		for (String uuid : INSTANCE.devices.keySet()) {
			IOSDevice device = INSTANCE.get(uuid);
			device.free();
		}
		INSTANCE.devices.clear();
	}

	public static void remove(String uuid) {
		DeviceService.INSTANCE.devices.remove(uuid);
	}

	public void startDetection(DeviceCallBack cb) throws SDKException {
		if (cb == null) {
			cb = new DeviceCallBack() {
				@Override
				protected void onDeviceAdded(String uuid) {
					System.out.println("Added :" + uuid);
				}

				@Override
				protected void onDeviceRemoved(String uuid) {
					System.out.println("Removed: " + uuid);
				}
			};
		}
		throwIfNeeded(sdk_idevice_event_subscribe(cb, null));
	}

	public void stopDetection() throws SDKException {
		throwIfNeeded(sdk_idevice_event_unsubscribe());
	}
}
