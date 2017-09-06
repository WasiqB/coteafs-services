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

import io.restassured.http.Method;

/**
 * @author wasiq.bhamla
 * @since Sep 4, 2017 8:52:13 PM
 */
public enum RequestMethod {
	/**
	 * Delete.
	 */
	DELETE (Method.DELETE),
	/**
	 * Get.
	 */
	GET (Method.GET),
	/**
	 * Head.
	 */
	HEAD (Method.HEAD),
	/**
	 * Options.
	 */
	OPTIONS (Method.OPTIONS),
	/**
	 * Patch.
	 */
	PATCH (Method.PATCH),
	/**
	 * Post.
	 */
	POST (Method.POST),
	/**
	 * Put.
	 */
	PUT (Method.PUT),
	/**
	 * Trace.
	 */
	TRACE (Method.TRACE);

	private final Method method;

	private RequestMethod (final Method method) {
		this.method = method;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Sep 4, 2017 8:57:07 PM
	 * @return the method
	 */
	public Method getMethod () {
		return this.method;
	}
}