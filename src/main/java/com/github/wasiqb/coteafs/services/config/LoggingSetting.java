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
	private boolean	logHeaders;
	private boolean	logOnlyRequests;
	private boolean	logOnlyResponses;
	private boolean	logPayloadOnError;

	/**
	 * @author wasiq.bhamla
	 * @since Aug 19, 2017 6:15:39 PM
	 */
	public LoggingSetting () {
		this.logHeaders = false;
		this.logPayloadOnError = true;
		this.logOnlyRequests = false;
		this.logOnlyResponses = false;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 25, 2017 9:22:04 PM
	 * @return the logHeaders
	 */
	public boolean isLogHeaders () {
		return this.logHeaders;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 25, 2017 6:34:20 PM
	 * @return the logOnlyRequests
	 */
	public boolean isLogOnlyRequests () {
		return this.logOnlyRequests;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 25, 2017 6:34:20 PM
	 * @return the logOnlyResponses
	 */
	public boolean isLogOnlyResponses () {
		return this.logOnlyResponses;
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
	 * @since Aug 25, 2017 9:22:04 PM
	 * @param logHeaders
	 *            the logHeaders to set
	 */
	public void setLogHeaders (final boolean logHeaders) {
		this.logHeaders = logHeaders;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 25, 2017 6:34:20 PM
	 * @param logOnlyRequests
	 *            the logOnlyRequests to set
	 */
	public void setLogOnlyRequests (final boolean logOnlyRequests) {
		this.logOnlyRequests = logOnlyRequests;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 25, 2017 6:34:20 PM
	 * @param logOnlyResponses
	 *            the logOnlyResponses to set
	 */
	public void setLogOnlyResponses (final boolean logOnlyResponses) {
		this.logOnlyResponses = logOnlyResponses;
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