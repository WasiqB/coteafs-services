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

/**
 * @author wasiq.bhamla
 * @since 28-Mar-2017 5:47:21 PM
 */
public final class RequestFactory {
	/**
	 * @author wasiq.bhamla
	 * @since 28-Mar-2017 5:49:36 PM
	 * @param type
	 * @return parser
	 */
	public static RequestParser getParser (final RequestType type) {
		switch (type) {
			case REST:
				return RestRequestParser.create ();
			case SOAP:
				return SoapRequestParser.create ();
			default:
				return null;
		}
	}
}