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

import com.sun.jna.Pointer;

import org.libimobiledevice.ios.driver.binding.raw.ImobiledeviceSdkLibrary;

public abstract class DeviceCallBack implements ImobiledeviceSdkLibrary.sdk_idevice_event_cb_t {

	private final String ADDED = "add";
	private final String REMOVED = "remove";

	@Override
	public void apply(String name, String uuid, Pointer user_data) {
		switch (name) {
		case ADDED:
			onDeviceAdded(uuid);
			break;
		case REMOVED:
			onDeviceRemoved(uuid);
			DeviceService.remove(uuid);
			break;
		default:
			throw new RuntimeException("event type " + name + "not recognized.");
		}
	}

	protected abstract void onDeviceAdded(String uuid);

	protected abstract void onDeviceRemoved(String uuid);

}
