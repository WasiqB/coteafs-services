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
package com.github.wasiqb.coteafs.services.processor;

import java.util.Map;

import com.github.wasiqb.coteafs.services.requests.RequestElement;

import io.restassured.response.Response;

/**
 * @author wasiq.bhamla
 * @since Aug 20, 2017 3:37:40 PM
 */
public interface RequestProcessor {
	/**
	 * @author wasiq.bhamla
	 * @since Aug 20, 2017 3:41:18 PM
	 * @param url
	 */
	void changeBaseUrl (String url);

	/**
	 * @author wasiq.bhamla
	 * @since Aug 20, 2017 8:51:48 PM
	 * @param method
	 * @param body
	 * @param shouldWork
	 * @return response
	 */
	Response execute (RequestMethod method, RequestElement body, boolean shouldWork);

	/**
	 * @author wasiq.bhamla
	 * @since Aug 20, 2017 8:52:58 PM
	 * @param method
	 * @param body
	 * @param headers
	 * @param shouldWork
	 * @return response
	 */
	Response execute (RequestMethod method, RequestElement body, Map <String, String> headers, boolean shouldWork);

	/**
	 * @author wasiq.bhamla
	 * @since Aug 20, 2017 3:42:04 PM
	 * @return string response
	 */
	String response ();

	/**
	 * @author wasiq.bhamla
	 * @since Aug 20, 2017 3:42:38 PM
	 * @param path
	 * @return value
	 */
	<T> T valueOf (String path);
}