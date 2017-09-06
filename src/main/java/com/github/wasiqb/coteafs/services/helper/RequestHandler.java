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

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.github.wasiqb.coteafs.services.config.LoggingSetting;
import com.github.wasiqb.coteafs.services.config.ServiceSetting;
import com.github.wasiqb.coteafs.services.parser.RequestFactory;
import com.github.wasiqb.coteafs.services.parser.RequestParser;
import com.github.wasiqb.coteafs.services.requests.RequestElement;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.response.ValidatableResponseLogSpec;
import io.restassured.specification.RequestLogSpecification;
import io.restassured.specification.RequestSpecification;

/**
 * <pre>
 * <code>
 * RequestHandler.build ()
 * 	.setting (serviceSetting)
 * 	.using ()
 * 	.headers (headerMap)
 * 	.params (paramsMap)
 * 	.resource ("/services")
 * 	.with (requestElement)
 * 	.execute (POST)
 * 	.response ();
 * </code>
 * </pre>
 *
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

	private String					name;
	private RequestSpecification	req;
	private String					resource;
	private Response				response;
	private ServiceSetting			setting;

	/**
	 * @author wasiq.bhamla
	 * @since Aug 25, 2017 2:49:52 PM
	 * @param method
	 * @param shouldWork
	 * @return instance
	 */
	public RequestHandler execute (final Method method, final boolean shouldWork) {
		final LoggingSetting logging = this.setting.getLogging ();
		logRequest (logging);
		if (!StringUtils.isEmpty (this.resource)) {
			this.req = this.req.basePath (this.resource);
		}
		this.response = this.req.request (method);
		logResponse (logging);
		if (shouldWork) {
			this.response.then ()
				.statusCode (200);
		}
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Sep 5, 2017 10:28:22 PM
	 * @param parameters
	 * @return instance
	 */
	public RequestHandler formParams (final Map <String, Object> parameters) {
		if (parameters != null && parameters.size () > 0) {
			this.req = this.req.formParams (parameters);
		}
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:40:21 PM
	 * @param headers
	 * @return instance
	 */
	public RequestHandler headers (final Map <String, Object> headers) {
		if (headers != null && headers.size () > 0) {
			this.req = this.req.headers (headers);
		}
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 25, 2017 2:48:12 PM
	 * @param parameters
	 * @return instance
	 */
	public RequestHandler params (final Map <String, Object> parameters) {
		if (parameters != null && parameters.size () > 0) {
			this.req = this.req.params (parameters);
		}
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Sep 5, 2017 10:16:01 PM
	 * @param parameters
	 * @return instance
	 */
	public RequestHandler pathParams (final Map <String, Object> parameters) {
		if (parameters != null && parameters.size () > 0) {
			this.req = this.req.pathParams (parameters);
		}
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Sep 5, 2017 10:28:55 PM
	 * @param parameters
	 * @return instance
	 */
	public RequestHandler queryParams (final Map <String, Object> parameters) {
		if (parameters != null && parameters.size () > 0) {
			this.req = this.req.queryParams (parameters);
		}
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 25, 2017 2:53:07 PM
	 * @param path
	 * @return instance
	 */
	public RequestHandler resource (final String path) {
		this.resource = path;
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:42:13 PM
	 * @return response
	 */
	public ResponseHandler response () {
		return new ResponseHandler (this.name, this.response, this.setting);
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 25, 2017 7:21:56 PM
	 * @param setting
	 * @return instance
	 */
	public RequestHandler setting (final ServiceSetting setting) {
		this.setting = setting;
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Sep 5, 2017 3:08:12 PM
	 * @return url
	 */
	public String url () {
		final StringBuilder path = new StringBuilder (this.setting.getEndPoint ());
		path.append (this.resource);
		return path.toString ();
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:42:25 PM
	 * @return instance
	 */
	public RequestHandler using () {
		this.req = RestAssured.given ()
			.baseUri (this.setting.getEndPoint ());
		if (this.setting.getPort () > 0) {
			this.req = this.req.port (this.setting.getPort ());
		}
		if (this.setting.getContentType () != null) {
			this.req = this.req.contentType (this.setting.getContentType ());
		}
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:42:47 PM
	 * @param request
	 * @return instance
	 */
	public RequestHandler with (final RequestElement request) {
		if (request != null) {
			this.name = request.name ();
			final RequestParser builder = RequestFactory.getParser (this.setting.getType ())
				.build (request);
			return with (builder.body ());
		}
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 25, 2017 9:26:07 PM
	 * @param logging
	 */
	private void logRequest (final LoggingSetting logging) {
		final RequestLogSpecification log = this.req.log ();
		if (logging.isLogHeaders ()) {
			log.headers ();
		}
		if (logging.isLogOnlyRequests ()) {
			log.body ();
		}
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 25, 2017 9:26:41 PM
	 * @param logging
	 */
	private void logResponse (final LoggingSetting logging) {
		final ValidatableResponseLogSpec <ValidatableResponse, Response> log = this.response.then ()
			.log ();
		if (logging.isLogHeaders ()) {
			log.headers ();
		}
		if (logging.isLogOnlyResponses ()) {
			log.body ();
		}
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:43:04 PM
	 * @param request
	 * @return instance
	 */
	private RequestHandler with (final String request) {
		this.req = this.req.body (request)
			.when ();
		return this;
	}
}