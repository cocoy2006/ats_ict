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

import org.libimobiledevice.ios.driver.binding.exceptions.SDKException;
import org.libimobiledevice.ios.driver.binding.raw.ImobiledeviceSdkLibrary.sdk_idevice_t;

public class IOSDevice {

	private final String uuid;
	// private final sdk_idevice_t sdk_handle;
	private SysLogService sysLogService;
	
	/** State of the device. */
    private DeviceState mState = null;

	IOSDevice(String uuid) throws SDKException {
		if (uuid == null) {
			throw new IllegalArgumentException("uuid cannot be null");
		}
		this.uuid = uuid;
		// PointerByReference ptr = new PointerByReference();
		// throwIfNeeded(sdk_idevice_new(ptr, uuid));
		// sdk_handle = new
		// ImobiledeviceSdkLibrary.sdk_idevice_t(ptr.getValue());
		setState(DeviceState.ONLINE);
	}

	sdk_idevice_t getSDKHandle() {
		// return sdk_handle;
		return null;
	}

	public void free() {
		DeviceService.remove(uuid);
		// sdk_idevice_free(sdk_handle);
	}

	public String getUUID() {
		return uuid;
	}

	public SysLogService getSysLogService() throws SDKException {
		if (sysLogService == null) {
			sysLogService = new SysLogService(this);
		}
		return sysLogService;
	}
	
	/**
     * The state of a device.
     */
    public enum DeviceState {
        BOOTLOADER("bootloader"), //$NON-NLS-1$
        OFFLINE("offline"), //$NON-NLS-1$
        ONLINE("device"), //$NON-NLS-1$
        RECOVERY("recovery"), //$NON-NLS-1$
        UNAUTHORIZED("unauthorized"), //$NON-NLS-1$
        BUSY("busy"); //$NON-NLS-1$

        private String mState;

        DeviceState(String state) {
            mState = state;
        }

        /**
         * Returns a {@link DeviceState} from the string returned by <code>adb devices</code>.
         *
         * @param state the device state.
         * @return a {@link DeviceState} object or <code>null</code> if the state is unknown.
         */
        public static DeviceState getState(String state) {
            for (DeviceState deviceState : values()) {
                if (deviceState.mState.equals(state)) {
                    return deviceState;
                }
            }
            return null;
        }
    }
    
    public boolean isOnline() {
        return mState == DeviceState.ONLINE;
    }
    
    /**
     * Changes the state of the device.
     */
    public void setState(DeviceState state) {
        mState = state;
    }
    
}
