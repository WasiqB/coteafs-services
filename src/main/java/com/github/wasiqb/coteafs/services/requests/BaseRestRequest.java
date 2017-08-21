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
package com.github.wasiqb.coteafs.services.requests;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.testng.Assert;

import com.github.wasiqb.coteafs.services.parser.RequestParser;
import com.github.wasiqb.coteafs.services.parser.RestRequestParser;
import com.github.wasiqb.coteafs.services.processor.RequestProcessor;
import com.github.wasiqb.coteafs.services.processor.RestRequestProcessor;
import com.jayway.restassured.response.Response;

/**
 * @author wasiq.bhamla
 * @since 27-Mar-2017 4:17:14 PM
 */
public class BaseRestRequest {
	private Map <String, String>	headers;
	protected RequestProcessor			rest;
	private final String			service;
	protected int					statusCodeNo;

	/**
	 * @author wasiq.bhamla
	 * @since Jun 20, 2016 3:21:40 PM
	 */
	public AbstractRestRequest (final String service) {
		this.service = service;
		this.statusCodeNo = 0;
		this.headers = new HashMap <String, String> ();
		this.rest = new RestRequestProcessor ();
	}

	/**
	 * @author wasiq.bhamla
	 * @since Jun 20, 2016 3:22:36 PM
	 * @param key
	 * @param value
	 */
	protected void addHeader (final String key, final String value) {
		this.headers.put (key, value);
	}

	/**
	 * @author wasiq.bhamla
	 * @since Jun 20, 2016 3:22:49 PM
	 */
	protected void clearHeaders () {
		this.headers.clear ();
	}

	/**
	 * @author wasiq.bhamla
	 * @since Jun 20, 2016 3:23:12 PM
	 * @return
	 */
	public abstract AdditionalStatusCode getAdditionalStatusCode ();

	/**
	 * @author wasiq.bhamla
	 * @since Jun 20, 2016 3:23:45 PM
	 * @return
	 */
	public abstract String getAdditionalStatusMessage ();

	/**
	 * @author wasiq.bhamla
	 * @since Jun 20, 2016 3:23:59 PM
	 * @param obj
	 * @param shouldWork
	 */
	protected void getResponse (final RequestElement obj, final boolean shouldWork) {
		final RequestParser builder = RestRequestParser.create ()
			.build (obj);
		getResponse (builder.body (), shouldWork);
	}

	/**
	 * @author wasiq.bhamla
	 * @since Jun 20, 2016 3:24:26 PM
	 * @param obj
	 * @param shouldWork
	 */
	protected void getResponse (final JSONObject obj, final boolean shouldWork) {
		final StringWriter out = new StringWriter ();

		try {
			obj.writeJSONString (out);
		}
		catch (final IOException e) {
			Assert.fail ("Failed to parse JSON string", e);
		}
		getResponse (out.toString (), shouldWork);
	}

	/**
	 * @author wasiq.bhamla
	 * @since Jun 20, 2016 3:24:11 PM
	 * @param json
	 * @param shouldWork
	 */
	protected void getResponse (final String json, final boolean shouldWork) {
		final Response response = this.rest.post (json, this.service, this.headers, shouldWork);
		this.statusCodeNo = response.getStatusCode ();
	}

	/**
	 * @author wasiq.bhamla
	 * @since Jun 20, 2016 3:24:47 PM
	 * @return
	 */
	public abstract StatusCode getStatusCode ();

	/**
	 * @author wasiq.bhamla
	 * @since Jun 20, 2016 3:24:59 PM
	 * @return
	 */
	public int getStatusCodeNo () {
		return this.statusCodeNo;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Jun 20, 2016 3:25:21 PM
	 * @return
	 */
	public abstract String getStatusMessage ();

	/**
	 * @author wasiq.bhamla
	 * @since Jun 20, 2016 3:25:41 PM
	 * @param path
	 * @return
	 */
	public <T> T valueOf (final String path, final boolean isRaw) {
		return this.rest.valueOf (path, isRaw);
	}
}
