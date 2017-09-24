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

import com.github.wasiqb.coteafs.error.CoteafsError;

/**
 * @author wasiq.bhamla
 * @since Sep 24, 2017 2:19:09 PM
 */
public class SoapParserInitError extends CoteafsError {
	private static final long serialVersionUID = -5599263226031650749L;

	/**
	 * @author wasiq.bhamla
	 * @since Sep 24, 2017 2:19:09 PM
	 * @param message
	 */
	public SoapParserInitError (final String message) {
		super (message);
	}

	/**
	 * @author wasiq.bhamla
	 * @since Sep 24, 2017 2:19:09 PM
	 * @param message
	 * @param cause
	 */
	public SoapParserInitError (final String message, final Throwable cause) {
		super (message, cause);
	}
}