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

import static com.github.wasiqb.coteafs.error.util.ErrorUtil.fail;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wasiqb.coteafs.services.error.JsonParseError;
import com.github.wasiqb.coteafs.services.error.RequestSerializationError;
import com.github.wasiqb.coteafs.services.parser.RequestParser;
import com.github.wasiqb.coteafs.services.parser.RestRequestParser;
import com.github.wasiqb.coteafs.services.parser.SoapRequestParser;
import com.github.wasiqb.coteafs.services.requests.RequestElement;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * @author wasiq.bhamla
 * @since 20-Aug-2017 3:48:38 PM
 */
public class RequestHandler {
	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:37:19 PM
	 * @return instance
	 */
	public static RequestHandler build () {
		return new RequestHandler ();
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:38:01 PM
	 * @param source
	 * @return Object to String.
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	private static String serialize (final Object source)
			throws JsonGenerationException, JsonMappingException, IOException {
		final StringWriter sw = new StringWriter ();
		final ObjectMapper map = new ObjectMapper ();
		map.writeValue (sw, source);
		return sw.toString ();
	}

	private String					contentType;
	private RequestSpecification	req;
	private Response				response;

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:38:43 PM
	 * @return object
	 */
	public JSONObject asObject () {
		try {
			return (JSONObject) new JSONParser ().parse (this.response.asString ());
		}
		catch (final ParseException e) {
			fail (JsonParseError.class, "Error while parsing", e);
		}
		return null;
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:39:02 PM
	 * @param type
	 * @return instance
	 */
	public RequestHandler contentType (final ContentType type) {
		this.contentType = type.toString ();
		this.req = this.req.contentType (type);
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:39:22 PM
	 * @param type
	 * @return instance
	 */
	public RequestHandler contentType (final String type) {
		this.contentType = type;
		this.req = this.req.contentType (this.contentType);
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:44:39 PM
	 * @param url
	 * @return instance
	 */
	public RequestHandler delete (final String url) {
		this.response = this.req.delete (url);
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:39:45 PM
	 * @param url
	 * @return instance
	 */
	public RequestHandler get (final String url) {
		this.response = this.req.get (url);
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:39:45 PM
	 * @param url
	 * @return instance
	 */
	public RequestHandler head (final String url) {
		this.response = this.req.head (url);
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:40:21 PM
	 * @param headers
	 * @return instance
	 */
	public RequestHandler headers (final Map <String, String> headers) {
		this.req = this.req.headers (headers);
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:46:07 PM
	 * @param url
	 * @return instance
	 */
	public RequestHandler options (final String url) {
		this.response = this.req.options (url);
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:46:50 PM
	 * @param url
	 * @return instance
	 */
	public RequestHandler patch (final String url) {
		this.response = this.req.patch (url);
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:41:08 PM
	 * @param url
	 * @return instance
	 */
	public RequestHandler post (final String url) {
		this.response = this.req.post (url);
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:39:34 PM
	 */
	public void printResponse () {
		this.response.getBody ()
			.prettyPrint ();
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:47:31 PM
	 * @param url
	 * @return instance
	 */
	public RequestHandler put (final String url) {
		this.response = this.req.put (url);
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:42:13 PM
	 * @return response
	 */
	public Response response () {
		return this.response;
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:42:25 PM
	 * @param url
	 * @return instance
	 */
	public RequestHandler using (final String url) {
		RestAssured.baseURI = url;
		this.req = RestAssured.given ();
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:42:47 PM
	 * @param request
	 * @return instance
	 */
	public RequestHandler with (final RequestElement request) {
		RequestParser builder = null;
		if (this.contentType.toLowerCase ()
			.contains ("xml")) {
			builder = SoapRequestParser.create ()
				.build (request);
		}
		else {
			builder = RestRequestParser.create ()
				.build (request);
		}
		return with (builder.body ());
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:43:04 PM
	 * @param request
	 * @return instance
	 */
	public RequestHandler with (final String request) {
		this.req = this.req.body (request)
			.when ();
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:43:18 PM
	 * @param request
	 * @return instance
	 */
	public <T> RequestHandler with (final T request) {
		return with (request, false);
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:43:35 PM
	 * @param request
	 * @param debug
	 * @return instance
	 */
	public <T> RequestHandler with (final T request, final boolean debug) {
		String requestString;
		try {
			requestString = serialize (request);
			if (debug) {
				System.out.println (requestString);
			}
			return with (requestString);
		}
		catch (final IOException e) {
			fail (RequestSerializationError.class, "Error while serializing request.", e);
		}
		return null;
	}
}