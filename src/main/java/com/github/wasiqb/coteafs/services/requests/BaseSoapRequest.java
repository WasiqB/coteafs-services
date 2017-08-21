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

import java.util.HashMap;
import java.util.Map;

import com.github.wasiqb.coteafs.services.parser.RequestParser;
import com.github.wasiqb.coteafs.services.parser.SoapRequestParser;
import com.github.wasiqb.coteafs.services.processor.RequestProcessor;
import com.github.wasiqb.coteafs.services.processor.SoapRequestHelper;
import com.jayway.restassured.response.Response;

/**
 * @author wasiq.bhamla
 * @since 27-Mar-2017 4:16:11 PM
 */
public class BaseSoapRequest {
	protected RequestProcessor				rest;
	protected StatusCode				statusCode;
	protected int						statusCodeNo;
	private final Map <String, Object>	contextFields;
	private final Map <String, String>	headers;
	private final Map <String, Object>	requestFields;
	private final String				service;

	/**
	 * @author wasiq.bhamla
	 * @since Mar 16, 2016 3:55:49 PM
	 */
	public BaseSoapRequest (final String service) {
		this.service = service;
		this.rest = new SoapRequestHelper ();
		this.headers = new HashMap <String, String> ();
		this.contextFields = new HashMap <String, Object> ();
		this.requestFields = new HashMap <String, Object> ();
		this.statusCodeNo = 0;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Apr 5, 2016 11:57:49 AM
	 * @param key
	 * @param value
	 */
	public <T> void addContextField (final String key, final T value) {
		addField (this.contextFields, key, value);
	}

	/**
	 * @author wasiq.bhamla
	 * @since Apr 5, 2016 12:00:11 PM
	 * @param key
	 * @param value
	 */
	public <T> void addField (final String key, final T value) {
		addField (this.requestFields, key, value);
	}

	/**
	 * @author wasiq.bhamla
	 * @since Mar 16, 2016 4:07:35 PM
	 * @return the additionalStatusCode
	 */
	public AdditionalStatusCode getAdditionalStatusCode () {
		return AdditionalStatusCode.valueOf (valueOf ("contextResponse.additionalStatusCode").toString ());
	}

	/**
	 * @author wasiq.bhamla
	 * @since Mar 16, 2016 4:07:35 PM
	 * @return the additionalStatusMessage
	 */
	public String getAdditionalStatusMessage () {
		return valueOf ("contextResponse.additionalStatusMessage");
	}

	/**
	 * @author wasiq.bhamla
	 * @since Mar 16, 2016 3:53:29 PM
	 * @return
	 */
	public StatusCode getStatusCode () {
		return StatusCode.valueOf (valueOf ("contextResponse.statusCode").toString ());
	}

	/**
	 * @author wasiq.bhamla
	 * @since Mar 7, 2016 12:01:06 PM
	 * @return
	 */
	public int getStatusCodeNo () {
		return this.statusCodeNo;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Mar 16, 2016 4:07:35 PM
	 * @return the statusMessage
	 */
	public String getStatusMessage () {
		return valueOf ("contextResponse.statusMessage");
	}

	/**
	 * @author wasiq.bhamla
	 * @since Mar 7, 2016 11:21:12 AM
	 * @param path
	 * @return
	 */
	public <T> T valueOf (final String path) {
		return this.rest.valueOf (path, false);
	}

	/**
	 * @author wasiq.bhamla
	 * @since Mar 7, 2016 11:59:10 AM
	 * @param key
	 * @param value
	 */
	protected void addHeader (final String key, final String value) {
		this.headers.put (key, value);
	}

	/**
	 * @author wasiq.bhamla
	 * @since Apr 5, 2016 12:49:36 PM
	 */
	protected void clear () {
		this.contextFields.clear ();
		this.requestFields.clear ();
	}

	/**
	 * @author wasiq.bhamla
	 * @since Mar 7, 2016 11:58:40 AM
	 */
	protected void clearHeaders () {
		this.headers.clear ();
	}

	/**
	 * @author wasiq.bhamla
	 * @since Mar 7, 2016 11:20:08 AM
	 * @param obj
	 * @param shouldWork
	 */
	protected void getResponse (final RequestElement obj, final boolean shouldWork) {
		final RequestParser builder = SoapRequestParser.create ()
			.build (obj);
		getResponse (builder.body (), shouldWork);
	}

	/**
	 * @author wasiq.bhamla
	 * @since Mar 14, 2016 11:50:16 AM
	 * @param xml
	 * @param shouldWork
	 * @return
	 */
	protected void getResponse (final String xml, final boolean shouldWork) {
		final Response response = this.rest.post (xml, this.service, shouldWork);
		this.statusCodeNo = response.getStatusCode ();
		this.statusCode = getStatusCode ();
	}

	/**
	 * @author wasiq.bhamla
	 * @since Apr 5, 2016 12:00:15 PM
	 * @param fields
	 * @param key
	 * @param value
	 */
	private <T> void addField (final Map <String, T> fields, final String key, final T value) {
		if (fields != null && !fields.containsKey (key)) {
			fields.put (key, value);
		}
	}
}
