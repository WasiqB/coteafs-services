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

import org.apache.commons.lang3.StringUtils;
import org.testng.Reporter;

import com.github.wasiqb.coteafs.services.helper.RequestHandler;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

/**
 * @author wasiq.bhamla
 * @since 27-Mar-2017 3:44:31 PM
 */
public class RestRequestProcessor implements RequestProcessor {
	private String		baseUrl;
	private JsonPath	jsonPath;
	private String		responseString;

	/**
	 * @author wasiq.bhamla
	 * @since 28-Mar-2017 5:51:20 PM
	 * @param baseUrl
	 */
	public RestRequestProcessor (final String baseUrl) {
		this.baseUrl = baseUrl;
	}

	/*
	 * (non-Javadoc)
	 * @see com.mozido.commons.RequestHelper#changeBaseUrl(java.lang.String)
	 */
	@Override
	public void changeBaseUrl (final String url) {
		this.baseUrl = url;
	}

	/*
	 * (non-Javadoc)
	 * @see com.mozido.commons.RequestHelper#post(java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public Response post (final String body, final String service, final boolean shouldWork) {
		return post (body, service, null, shouldWork);
	}

	/*
	 * (non-Javadoc)
	 * @see com.mozido.commons.RequestHelper#post(java.lang.String, java.lang.String, java.util.Map,
	 * boolean)
	 */
	@Override
	public Response post (final String body, final String service, final Map <String, String> headers,
			final boolean shouldWork) {
		final long start = System.currentTimeMillis ();

		RequestHandler builder = RequestHandler.build ()
			.using (this.baseUrl);
		System.out.println (this.baseUrl + service);

		if (headers != null && headers.size () > 0) {
			builder = builder.headers (headers);
		}

		builder = builder.contentType (ContentType.JSON)
			.with (body);

		builder = builder.post (service);
		final Response response = builder.response ();

		final long end = System.currentTimeMillis ();
		Reporter.log ("\n" + service + " time: " + (end - start) + " milliseconds \n");
		System.out.println ("Call Time: " + (end - start) + " milliseconds.");
		System.out.println ();

		if (response.statusCode () == 200 || response.statusCode () == 400 || response.statusCode () == 403) {
			jsonResponseString (response.asString ());

			if (shouldLog ()) {
				JsonFormatter.printFormattedJson (body, service + " Request");
				JsonFormatter.printFormattedJson (this.responseString, service + " Response");
			}

			if (shouldWork) {
				return checkRestSuccess (response, body, service);
			}
		}
		else {
			Assert.fail ("Response Status" + response.statusLine ());
		}
		return response;
	}

	/*
	 * (non-Javadoc)
	 * @see com.mozido.commons.RequestHelper#response()
	 */
	@Override
	public String response () {
		return this.responseString;
	}

	/*
	 * (non-Javadoc)
	 * @see com.mozido.commons.RequestHelper#valueOf(java.lang.String, boolean)
	 */
	@Override
	public <T> T valueOf (final String path) {
		if (StringUtils.isBlank (path)) {
			return null;
		}
		try {
			final T value = this.jsonPath.get (path);
			return value;
		}
		catch (final Exception e) {
			e.printStackTrace ();
		}
		return null;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Mar 28, 2016 11:52:58 AM
	 * @param response
	 * @param json
	 * @param service
	 * @return
	 */
	private Response checkRestSuccess (final Response response, final String json, final String service) {
		final String status = valueOf ("contextResponse.statusCode", false);
		if (response.getStatusCode () == 200) {
			if (status.equals ("SUCCESS")) {
				return response;
			}
			JsonFormatter.printFormattedJson (json, service + " Request");
			JsonFormatter.printFormattedJsonReporter (json, service);
			JsonFormatter.printFormattedJson (this.responseString, service + " Response");
			JsonFormatter.printFormattedJsonReporterResponse (this.responseString);
			Assert.fail (failJsonMessage ());
		}
		else {
			JsonFormatter.printFormattedJsonReporterResponse (this.responseString);
			JsonFormatter.printFormattedJson (this.responseString, "FailedResponse");
			Assert.fail (response.getStatusCode () + ": " + failJsonMessage ());
		}
		return null;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Mar 28, 2016 11:52:46 AM
	 * @return
	 */
	private String failJsonMessage () {
		final StringBuilder response = new StringBuilder ();
		final String statusCode = valueOf ("contextResponse.statusCode", false);
		final String statusMessage = valueOf ("contextResponse.statusMessage", false);
		final String additionalStatusCode = valueOf ("contextResponse.additionalStatusCode", false);
		final String additionalStatusMessage = valueOf ("contextResponse.additionalStatusMessage", false);
		if (StringUtils.isNotBlank (statusCode)) {
			response.append (statusCode);
		}
		response.append (" : ");
		if (StringUtils.isNotBlank (statusMessage)) {
			response.append (statusMessage);
		}
		response.append (" : ");
		if (StringUtils.isNotBlank (additionalStatusCode)) {
			response.append (additionalStatusCode);
		}
		response.append (" : ");
		if (StringUtils.isNotBlank (additionalStatusMessage)) {
			response.append (additionalStatusMessage);
		}
		return response.toString ()
			.trim ();
	}

	/**
	 * @author wasiq.bhamla
	 * @since Mar 28, 2016 11:52:42 AM
	 * @param response
	 */
	private void jsonResponseString (final String response) {
		this.responseString = response;
		this.jsonPath = JsonPath.from (this.responseString);
	}
}
