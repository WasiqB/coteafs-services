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

/**
 * @author wasiq.bhamla
 * @since Aug 7, 2017 12:43:09 PM
 */
public class LoggingSetting {
	private boolean	logAllPayload;
	private boolean	logPayloadOnError;

	/**
	 * @author wasiq.bhamla
	 * @since Aug 19, 2017 6:15:39 PM
	 */
	public LoggingSetting () {
		this.logAllPayload = false;
		this.logPayloadOnError = true;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 7, 2017 12:45:04 PM
	 * @return the logAllPayload
	 */
	public boolean isLogAllPayload () {
		return this.logAllPayload;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 7, 2017 12:45:04 PM
	 * @return the logPayloadOnError
	 */
	public boolean isLogPayloadOnError () {
		return this.logPayloadOnError;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 7, 2017 12:45:04 PM
	 * @param logAllPayload
	 *            the logAllPayload to set
	 */
	public void setLogAllPayload (final boolean logAllPayload) {
		this.logAllPayload = logAllPayload;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 7, 2017 12:45:04 PM
	 * @param logPayloadOnError
	 *            the logPayloadOnError to set
	 */
	public void setLogPayloadOnError (final boolean logPayloadOnError) {
		this.logPayloadOnError = logPayloadOnError;
	}
}