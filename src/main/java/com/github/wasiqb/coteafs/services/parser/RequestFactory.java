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
package com.github.wasiqb.coteafs.services.parser;

import com.github.wasiqb.coteafs.services.config.ServiceType;

/**
 * @author wasiq.bhamla
 * @since Aug 26, 2017 3:47:22 PM
 */
public final class RequestFactory {
	/**
	 * @author wasiq.bhamla
	 * @since Aug 26, 2017 3:47:26 PM
	 * @param type
	 * @return parser
	 */
	public static RequestParser getParser (final ServiceType type) {
		switch (type) {
			case REST:
				return RestRequestParser.create ();
			case SOAP:
			default:
				return SoapRequestParser.create ();
		}
	}
}