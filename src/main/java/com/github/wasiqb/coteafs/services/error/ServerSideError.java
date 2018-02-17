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
package com.github.wasiqb.coteafs.services.error;

/**
 * @author wasiq.bhamla
 * @since Feb 17, 2018 5:48:52 PM
 */
public class ServerSideError extends CoteafsServicesError {
	private static final long serialVersionUID = -2098287378186086339L;

	/**
	 * @author wasiq.bhamla
	 * @since Feb 17, 2018 5:48:52 PM
	 * @param message
	 */
	public ServerSideError (final String message) {
		super (message);
	}

	/**
	 * @author wasiq.bhamla
	 * @since Feb 17, 2018 5:48:52 PM
	 * @param message
	 * @param cause
	 */
	public ServerSideError (final String message, final Throwable cause) {
		super (message, cause);
	}
}