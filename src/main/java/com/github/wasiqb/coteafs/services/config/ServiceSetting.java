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
 * @since Aug 7, 2017 12:40:53 PM
 */
public class ServiceSetting {
	private String						endPoint;
	private String						endPointSuffix;
	private LoggingSetting				logging;
	private final Map <String, Object>	params;
	private ServiceType					type;

	/**
	 * @author wasiq.bhamla
	 * @since Aug 7, 2017 12:40:54 PM
	 */
	public ServiceSetting () {
		this.params = new HashMap <> ();
		this.type = ServiceType.SOAP;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 7, 2017 12:42:38 PM
	 * @return the endPoint
	 */
	public String getEndPoint () {
		return this.endPoint;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 19, 2017 6:14:13 PM
	 * @return the endPointSuffix
	 */
	public String getEndPointSuffix () {
		return this.endPointSuffix;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 7, 2017 12:45:40 PM
	 * @return the logging
	 */
	public LoggingSetting getLogging () {
		return this.logging;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 19, 2017 6:14:13 PM
	 * @return the params
	 */
	public Map <String, Object> getParams () {
		return this.params;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 8, 2017 3:40:01 PM
	 * @return the type
	 */
	public ServiceType getType () {
		return this.type;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 7, 2017 12:42:38 PM
	 * @param endPoint
	 *            the endPoint to set
	 */
	public void setEndPoint (final String endPoint) {
		this.endPoint = endPoint;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 19, 2017 6:14:13 PM
	 * @param endPointSuffix
	 *            the endPointSuffix to set
	 */
	public void setEndPointSuffix (final String endPointSuffix) {
		this.endPointSuffix = endPointSuffix;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 7, 2017 12:45:40 PM
	 * @param logging
	 *            the logging to set
	 */
	public void setLogging (final LoggingSetting logging) {
		this.logging = logging;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 8, 2017 3:40:01 PM
	 * @param type
	 *            the type to set
	 */
	public void setType (final ServiceType type) {
		this.type = type;
	}
}