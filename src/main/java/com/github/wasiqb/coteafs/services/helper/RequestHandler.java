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

import static com.github.wasiqb.coteafs.services.utils.ErrorUtils.fail;
import static com.google.common.truth.Truth.assertThat;
import static java.lang.String.format;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.wasiqb.coteafs.services.config.LoggingSetting;
import com.github.wasiqb.coteafs.services.config.MediaType;
import com.github.wasiqb.coteafs.services.config.ServiceSetting;
import com.github.wasiqb.coteafs.services.config.ServiceType;
import com.github.wasiqb.coteafs.services.error.ClientSideError;
import com.github.wasiqb.coteafs.services.error.RequestExecutionError;
import com.github.wasiqb.coteafs.services.error.RequestExecutionFailedError;
import com.github.wasiqb.coteafs.services.error.ServerSideError;
import com.github.wasiqb.coteafs.services.error.ServiceNotFoundError;
import com.github.wasiqb.coteafs.services.formatter.PayloadLoggerFactory;
import com.github.wasiqb.coteafs.services.formatter.PayloadType;
import com.github.wasiqb.coteafs.services.parser.RequestFactory;
import com.github.wasiqb.coteafs.services.parser.RequestParser;
import com.github.wasiqb.coteafs.services.requests.RequestElement;

import io.restassured.RestAssured;
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
	private static final String	LINE;
	private static final Logger	LOG;

	static {
		LOG = LogManager.getLogger (RequestHandler.class);
		LINE = StringUtils.repeat ("=", 80);
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:37:19 PM
	 * @return instance
	 */
	public static RequestHandler build () {
		return new RequestHandler ();
	}

	private static void logMap (final String mapName, final Map <String, Object> map) {
		if (!map.isEmpty ()) {
			LOG.info (LINE);
			LOG.info (String.format ("Following %s is used", mapName));
			LOG.info (LINE);
			for (final Entry <String, Object> key : map.entrySet ()) {
				LOG.info (format ("%s: %s", key.getKey (), key.getValue ()));
			}
		}
	}

	private static void validateResponse (final Response res) {
		final int status = res.statusCode ();
		if (status == 404) {
			fail (ServiceNotFoundError.class, "Service Not found.");
		}
		else if (status >= 400 && status < 500) {
			fail (ClientSideError.class, "Client side error.");
		}
		else if (status >= 500) {
			fail (ServerSideError.class, "Server side error,");
		}
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
		LOG.info (LINE);
		LOG.info (format ("Executing request with method [%s]...", method));
		LOG.info (LINE);
		try {
			setSoapHeaders ();
			final Response res = this.request.request (method);
			validateResponse (res);
			this.response = new ResponseHandler (this.name, res, this.setting);
			if (shouldWork) {
				assertThat (res.statusCode ()).isGreaterThan (199);
				assertThat (res.statusCode ()).isLessThan (300);
			}
		}
		catch (final RequestExecutionFailedError e) {
			fail (RequestExecutionError.class, "Execution failed", e);
		}
		catch (final Exception e) {
			fail (ServiceNotFoundError.class, "Service not found.", e);
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
			logMap ("Form Params", parameters);
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
			logMap ("Request Headers", headers);
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
			logMap ("Params", parameters);
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
			logMap ("Path Params", parameters);
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
			logMap ("Query Params", parameters);
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
			LOG.info (format ("End-point resource: %s", this.resource));
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
		final String suffix = this.setting.getEndPointSuffix ();
		final MediaType type = this.setting.getContentType ();

		this.request = RestAssured.given ()
			.baseUri (endPoint + suffix);
		LOG.info (LINE);
		LOG.info ("Preparing to execute request with following parameters:");
		LOG.info (LINE);
		LOG.info (format ("End-point url: %s", endPoint));
		if (port > 0) {
			LOG.info (format ("End-point port: %d", port));
			this.request = this.request.port (port);
		}
		if (type != null) {
			LOG.info (format ("End-point content-type: %s", type));
			this.request = this.request.contentType (type.toString ());
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
			final RequestParser builder = RequestFactory.getParser (this.setting)
				.build (requestElement);
			final String body = builder.body ();
			return with (body, this.setting.getLogging ());
		}
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Feb 12, 2018 9:27:45 PM
	 */
	private void setSoapHeaders () {
		if (this.setting.getType () == ServiceType.SOAP) {
			final Map <String, Object> headers = new HashMap <> ();
			headers.put ("Keep-Alive", "timeout=10, max=127");

			headers (headers);
		}
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