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

import com.github.wasiqb.coteafs.services.requests.RequestElement;

/**
 * @author wasiq.bhamla
 * @since 27-Mar-2017 4:09:05 PM
 */
public interface RequestParser {
	/**
	 * @author wasiq.bhamla
	 * @since 28-Mar-2017 5:45:46 PM
	 * @return body string
	 */
	String body ();

	/**
	 * @author wasiq.bhamla
	 * @since 28-Mar-2017 5:45:58 PM
	 * @param element
	 * @return instance
	 */
	RequestParser build (final RequestElement element);
}