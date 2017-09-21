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

import static java.lang.String.format;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.wasiqb.coteafs.services.config.LoggingSetting;
import com.github.wasiqb.coteafs.services.config.ServiceSetting;
import com.github.wasiqb.coteafs.services.formatter.PayloadLoggerFactory;
import com.github.wasiqb.coteafs.services.formatter.PayloadType;
import com.github.wasiqb.coteafs.services.parser.RequestFactory;
import com.github.wasiqb.coteafs.services.parser.RequestParser;
import com.github.wasiqb.coteafs.services.requests.RequestElement;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
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
	private static final String	line;
	private static final Logger	log;

	static {
		log = LogManager.getLogger (RequestHandler.class);
		line = StringUtils.repeat ("=", 80);
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:37:19 PM
	 * @return instance
	 */
	public static RequestHandler build () {
		return new RequestHandler ();
	}

	private String					name;
	private RequestSpecification	request;
	private String					resource;
	private ResponseHandler			response;
	private ServiceSetting			setting;

	/**
	 * @author wasiq.bhamla
	 * @since Aug 25, 2017 2:49:52 PM
	 * @param method
	 * @param shouldWork
	 * @return instance
	 */
	public RequestHandler execute (final Method method, final boolean shouldWork) {
		log.info (line);
		log.info (String.format ("Executing request with method [%s]...", method));
		try {
			final Response res = this.request.request (method);
			this.response = new ResponseHandler (this.name, res, this.setting);
			if (shouldWork) {
				res.then ()
					.statusCode (200);
			}
		}
		catch (final Exception e) {
			e.printStackTrace ();
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
			this.request = this.request.formParams (parameters);
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
			this.request = this.request.headers (headers);
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
			this.request = this.request.params (parameters);
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
			this.request = this.request.pathParams (parameters);
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
			this.request = this.request.queryParams (parameters);
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
		if (!StringUtils.isEmpty (this.resource)) {
			log.info (format ("End-point resource: %s", this.resource));
			this.request = this.request.basePath (this.resource);
		}
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:42:13 PM
	 * @return response
	 */
	public ResponseHandler response () {
		return this.response;
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
		final String endPoint = this.setting.getEndPoint ();
		final int port = this.setting.getPort ();
		final ContentType type = this.setting.getContentType ();
		this.request = RestAssured.given ()
			.baseUri (endPoint);
		log.info (line);
		log.info ("Preparing to execute request with following parameters:");
		log.info (line);
		log.info (format ("End-point url: %s", endPoint));
		if (port > 0) {
			log.info (format ("End-point port: %d", port));
			this.request = this.request.port (port);
		}
		if (type != null) {
			log.info (format ("End-point content-tyoe: %s", type));
			this.request = this.request.contentType (type);
		}
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:42:47 PM
	 * @param requestElement
	 * @return instance
	 */
	public RequestHandler with (final RequestElement requestElement) {
		if (requestElement != null) {
			this.name = requestElement.name ();
			final RequestParser builder = RequestFactory.getParser (this.setting.getType ())
				.build (requestElement);
			final String body = builder.body ();
			return with (body, this.setting.getLogging ());
		}
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:43:04 PM
	 * @param requestBody
	 * @return instance
	 */
	private RequestHandler with (final String requestBody, final LoggingSetting logging) {
		if (logging.isLogOnlyRequests ()) {
			PayloadLoggerFactory.log (this.setting.getType (), PayloadType.REQUEST, requestBody);
		}
		this.request = this.request.body (requestBody)
			.when ();
		return this;
	}
}