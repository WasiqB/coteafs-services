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
import static java.lang.String.format;

import java.io.File;
import java.util.HashMap;
import java.util.List;
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
	private static final String	LINE	= StringUtils.repeat ("=", 80);
	private static final Logger	LOG		= LogManager.getLogger (RequestHandler.class);

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:37:19 PM
	 * @return instance
	 */
	public static RequestHandler build () {
		LOG.info (LINE);
		LOG.info ("Preparing to execute request:");
		LOG.info (LINE);
		return new RequestHandler ();
	}

	private static void logMap (final String mapName, final Map <String, Object> map) {
		if (!map.isEmpty ()) {
			LOG.info (LINE);
			LOG.info (format ("Following %s is used", mapName));
			LOG.info (LINE);
			for (final Entry <String, Object> key : map.entrySet ()) {
				LOG.info (format ("%s: %s", key.getKey (), key.getValue ()));
			}
		}
	}

	private static void logStatusDetails (final Response res) {
		LOG.info (LINE);
		LOG.info ("Response Status:");
		LOG.info (LINE);
		LOG.info (format ("Status Code: [%d]...", res.statusCode ()));
		LOG.info (format ("Status Message: [%s]...", res.statusLine ()));
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
	 * @return instance
	 */
	public RequestHandler execute (final Method method) {
		LOG.info (format ("Executing request on [%s] with method [%s]...", url (), method));
		try {
			setSoapHeaders ();
			final Response res = this.request.request (method);
			logStatusDetails (res);
			validateResponse (res);
			this.response = new ResponseHandler (this.name, res, this.setting);
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
	 * @since May 12, 2018 7:02:53 PM
	 * @param files
	 * @return instance
	 */
	public RequestHandler multiPart (final List <String> files) {
		LOG.info (LINE);
		LOG.info ("Following files is used");
		LOG.info (LINE);
		for (final String file : files) {
			LOG.info (format ("File: %s", file));
			this.request = this.request.multiPart (new File (file));
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
		if (StringUtils.isNotEmpty (this.resource)) {
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
		final int port = this.setting.getPort ();
		final StringBuilder path = new StringBuilder (this.setting.getEndPoint ());
		if (port > 0) {
			path.append (":")
				.append (port);
		}
		path.append (this.setting.getEndPointSuffix ())
			.append (this.resource);
		return path.toString ();
	}

	/**
	 * @author wasiq.bhamla
	 * @param port
	 * @since 20-Aug-2017 3:42:25 PM
	 * @return instance
	 */
	public RequestHandler using (final int port) {
		final String endPoint = this.setting.getEndPoint ();
		final String suffix = this.setting.getEndPointSuffix ();
		final MediaType type = this.setting.getContentType ();

		this.request = RestAssured.given ()
			.baseUri (endPoint + suffix);
		if (port > 0) {
			this.request.port (port);
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
	 * @param body
	 * @return instance
	 */
	public <T> RequestHandler with (final T body) {
		if (body != null) {
			if (body instanceof RequestElement) {
				final RequestElement requestElement = (RequestElement) body;
				this.name = requestElement.name ();
				final RequestParser builder = RequestFactory.getParser (this.setting)
					.build (requestElement);
				return with (builder.body (), this.setting.getLogging ());
			}
			this.request.body (body);
		}
		return this;
	}

	private void setSoapHeaders () {
		if (this.setting.getType () == ServiceType.SOAP) {
			final Map <String, Object> headers = new HashMap <> ();
			headers.put ("Keep-Alive", "timeout=10, max=127");

			headers (headers);
		}
	}

	private RequestHandler with (final String requestBody, final LoggingSetting logging) {
		if (logging.isLogOnlyRequests ()) {
			PayloadLoggerFactory.log (this.setting.getType (), PayloadType.REQUEST, requestBody);
		}
		this.request = this.request.body (requestBody)
			.when ();
		return this;
	}
}