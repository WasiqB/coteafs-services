/**
 * Copyright 2017, Wasiq Bhamla.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.wasiqb.coteafs.services.config;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wasiq.bhamla
 * @since Aug 7, 2017 12:47:17 PM
 */
public class ServicesSetting {
	private Map <String, ServiceSetting> services;

	/**
	 * @author wasiq.bhamla
	 * @since Aug 7, 2017 12:49:38 PM
	 */
	public ServicesSetting () {
		this.services = new HashMap <> ();
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 7, 2017 12:49:06 PM
	 * @param name
	 * @return service setting.
	 */
	public ServiceSetting getService (final String name) {
		return this.services.get (name);
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 7, 2017 12:47:51 PM
	 * @return the services
	 */
	public Map <String, ServiceSetting> getServices () {
		return this.services;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 7, 2017 12:47:51 PM
	 * @param services
	 *            the services to set
	 */
	public void setServices (final Map <String, ServiceSetting> services) {
		this.services = services;
	}
}