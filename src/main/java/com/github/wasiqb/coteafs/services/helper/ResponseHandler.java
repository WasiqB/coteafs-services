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
package com.github.wasiqb.coteafs.services.helper;

import java.util.concurrent.TimeUnit;

import com.github.wasiqb.coteafs.services.config.ServiceSetting;
import com.github.wasiqb.coteafs.services.config.ServiceType;
import com.github.wasiqb.coteafs.services.response.ResponseValueParser;
import com.github.wasiqb.coteafs.services.response.RestResponseValueParser;
import com.github.wasiqb.coteafs.services.response.SoapResponseValueParser;

import io.restassured.http.Headers;
import io.restassured.response.Response;

/**
 * @author wasiq.bhamla
 * @since Aug 25, 2017 3:56:47 PM
 */
public class ResponseHandler {
	private final String			name;
	private final Response			response;
	private final ServiceSetting	setting;

	/**
	 * @author wasiq.bhamla
	 * @param name
	 * @param response
	 * @param setting
	 * @since Aug 25, 2017 3:56:47 PM
	 */
	public ResponseHandler (final String name, final Response response, final ServiceSetting setting) {
		this.name = name;
		this.response = response;
		this.setting = setting;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 26, 2017 3:43:56 PM
	 * @return headers
	 */
	public Headers headers () {
		return this.response.headers ();
	}

	/**
	 * @author wasiq.bhamla
	 * @since Sep 5, 2017 11:36:14 AM
	 * @return status code.
	 */
	public int statusCode () {
		return this.response.statusCode ();
	}

	/**
	 * @author wasiq.bhamla
	 * @since Sep 5, 2017 11:35:47 AM
	 * @return status line.
	 */
	public String statusLine () {
		return this.response.statusLine ();
	}

	/**
	 * @author wasiq.bhamla
	 * @since Sep 5, 2017 11:34:48 AM
	 * @return response time in secs.
	 */
	public long time () {
		return this.response.timeIn (TimeUnit.SECONDS);
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 25, 2017 9:53:28 PM
	 * @param expression
	 * @return value
	 */
	public <T> T valueOf (final String expression) {
		ResponseValueParser parser = new SoapResponseValueParser (this.response);
		if (this.setting.getType () == ServiceType.REST) {
			parser = new RestResponseValueParser (this.response);
		}
		return parser.valueOf (this.name, expression);
	}
}