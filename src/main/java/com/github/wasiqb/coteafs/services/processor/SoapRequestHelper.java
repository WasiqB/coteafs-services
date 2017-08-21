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

import java.util.HashMap;
import java.util.Map;
import java.util.logging.XMLFormatter;

import org.apache.commons.lang3.StringUtils;
import org.testng.Reporter;

import com.github.wasiqb.coteafs.services.helper.RequestHandler;
import com.jayway.restassured.path.xml.XmlPath;
import com.jayway.restassured.response.Response;

/**
 * @author wasiq.bhamla
 * @since 27-Mar-2017 4:12:31 PM
 */
public class SoapRequestHelper implements RequestProcessor {
	private String	baseUrl;
	private String	responseString;
	private XmlPath	xmlPath;

	/**
	 * @author wasiq.bhamla
	 * @since Apr 4, 2016 5:04:11 PM
	 */
	public SoapRequestHelper () {
		this.baseUrl = Flows.BASE_URL;
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
		final Map <String, String> headers = new HashMap <String, String> ();
		headers.put ("SOAPAction", "");
		headers.put ("Keep-Alive", "timeout=10, max=127");

		return post (body, service, headers, shouldWork);
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

		System.out.println (this.baseUrl + service);

		RequestHandler builder = RequestHandler.build ()
			.using (this.baseUrl)
			.headers (headers)
			.contentType ("text/xml")
			.with (body);

		builder = builder.post (service);

		final Response response = builder.response ();

		final long end = System.currentTimeMillis ();

		Reporter.log ("\n" + service + " time: " + (end - start) + " milliseconds \n");
		System.out.println ("Call Time: " + (end - start) + " milliseconds.");
		System.out.println ();

		soapResponseString (response.asString ());
		validateResponse (response);

		if (shouldLog ()) {
			XMLFormatter.printFormattedXml (body, service + " Request");
			XMLFormatter.printFormattedXml (this.responseString, service + " Response");
		}

		if (shouldWork) {
			return checkSoapSuccess (response, body, service);
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
	 * @see com.mozido.commons.RequestHelper#valueOf(java.lang.String)
	 */
	@Override
	public <T> T valueOf (final String path) {
		if (StringUtils.isBlank (path)) {
			return null;
		}

		try {
			final StringBuilder pathBuilder = new StringBuilder ("Envelope.Body.");
			pathBuilder.append (path);

			final T value = this.xmlPath.get (pathBuilder.toString ());
			return value;
		}
		catch (final Exception e) {
			e.printStackTrace ();
		}
		return null;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Mar 28, 2016 11:43:27 AM
	 * @param response
	 * @param xml
	 * @param service
	 * @return
	 */
	private Response checkSoapSuccess (final Response response, final String xml, final String service) {
		final String status = valueOf ("contextResponse.statusCode", false);
		if (response.getStatusCode () == 200) {
			if (status.equals ("SUCCESS")) {
				return response;
			}
			XMLFormatter.printFormattedXml (xml, service + " Request");
			XMLFormatter.printFormattedXmlReporter (xml, service);
			XMLFormatter.printFormattedXml (this.responseString, service + " Response");
			XMLFormatter.printFormattedXmlReporterResponse (this.responseString);
			Assert.fail (failSoapMessage ());
		}
		else {
			XMLFormatter.printFormattedXmlReporterResponse (this.responseString);
			XMLFormatter.printFormattedXml (this.responseString, "FailedResponse");
			Assert.fail (response.getStatusCode () + ": " + failSoapMessage ());
		}
		return null;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Mar 28, 2016 11:43:21 AM
	 * @return
	 */
	private String failSoapMessage () {
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
	 * @since Mar 28, 2016 11:43:31 AM
	 * @param response
	 */
	private synchronized void soapResponseString (final String response) {
		this.responseString = response;
		this.xmlPath = XmlPath.with (this.responseString);
	}

	/**
	 * @author wasiq.bhamla
	 * @since Oct 6, 2016 12:54:19 PM
	 * @param response
	 */
	private void validateResponse (final Response response) {
		Assert.assertEquals (response.statusCode (), 200, "Response Status" + response.statusLine ());
	}
}
