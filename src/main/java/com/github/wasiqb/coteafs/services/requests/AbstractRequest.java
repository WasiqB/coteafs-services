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

import static com.github.wasiqb.coteafs.services.config.ConfigConstants.SERVICE_CONFIG_DEFAULT_FILE_NAME;
import static com.github.wasiqb.coteafs.services.config.ConfigConstants.SERVICE_CONFIG_KEY;
import static com.google.common.truth.Truth.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.github.wasiqb.coteafs.config.loader.ConfigLoader;
import com.github.wasiqb.coteafs.services.config.RequestMethod;
import com.github.wasiqb.coteafs.services.config.ServiceSetting;
import com.github.wasiqb.coteafs.services.config.ServicesSetting;
import com.github.wasiqb.coteafs.services.helper.RequestHandler;
import com.github.wasiqb.coteafs.services.helper.ResponseHandler;
import com.google.common.truth.IntegerSubject;
import com.google.common.truth.StringSubject;

/**
 * @author wasiq.bhamla
 * @param <T>
 * @since Aug 20, 2017 3:00:51 PM
 */
public abstract class AbstractRequest <T extends AbstractRequest <T>> {
	private static ServicesSetting settings;

	static {
		settings = ConfigLoader.settings ()
			.withKey (SERVICE_CONFIG_KEY)
			.withDefault (SERVICE_CONFIG_DEFAULT_FILE_NAME)
			.load (ServicesSetting.class);
	}

	private final List <String>			files;
	private final Map <String, Object>	formParams;
	private final Map <String, Object>	headers;
	private final Map <String, Object>	params;
	private final Map <String, Object>	pathParams;
	private int							port;
	private final Map <String, Object>	queryParams;
	private final String				resourcePath;
	private ResponseHandler				response;
	private final ServiceSetting		setting;
	private final Map <String, Object>	values;

	/**
	 * @author wasiq.bhamla
	 * @since Mar 21, 2019 3:53:05 PM
	 * @param serviceName
	 */
	public AbstractRequest (final String serviceName) {
		this (serviceName, 0);
	}

	/**
	 * @author wasiq.bhamla
	 * @param serviceName
	 * @param port
	 * @since Feb 12, 2018 4:19:46 PM
	 */
	public AbstractRequest (final String serviceName, final int port) {
		this (serviceName, null, port);
	}

	/**
	 * @author wasiq.bhamla
	 * @since Mar 21, 2019 3:54:15 PM
	 * @param serviceName
	 * @param resourcePath
	 */
	public AbstractRequest (final String serviceName, final String resourcePath) {
		this (serviceName, resourcePath, 0);
	}

	/**
	 * @author wasiq.bhamla
	 * @param serviceName
	 * @param resourcePath
	 * @param port
	 * @since Aug 20, 2017 3:00:51 PM
	 */
	public AbstractRequest (final String serviceName, final String resourcePath, final int port) {
		this.setting = settings.getService (serviceName);
		this.headers = new HashMap <> ();
		this.params = new HashMap <> ();
		this.pathParams = new HashMap <> ();
		this.formParams = new HashMap <> ();
		this.queryParams = new HashMap <> ();
		this.values = new HashMap <> ();
		this.files = new ArrayList <> ();
		this.port = port;
		this.resourcePath = resourcePath == null ? "" : resourcePath;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Mar 19, 2018 10:13:32 PM
	 * @return response
	 */
	@SuppressWarnings ("unchecked")
	public T delete () {
		this.response = execute (RequestMethod.DELETE);
		return (T) this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Mar 19, 2018 10:13:24 PM
	 * @return response
	 */
	@SuppressWarnings ("unchecked")
	public T get () {
		this.response = execute (RequestMethod.GET);
		return (T) this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Mar 19, 2018 10:13:18 PM
	 * @return response
	 */
	@SuppressWarnings ("unchecked")
	public T head () {
		this.response = execute (RequestMethod.HEAD);
		return (T) this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Mar 19, 2018 10:13:11 PM
	 * @return response
	 */
	@SuppressWarnings ("unchecked")
	public T options () {
		this.response = execute (RequestMethod.OPTIONS);
		return (T) this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Mar 19, 2018 10:13:01 PM
	 * @return response
	 */
	@SuppressWarnings ("unchecked")
	public T patch () {
		this.response = execute (RequestMethod.PATCH);
		return (T) this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Mar 19, 2018 10:12:54 PM
	 * @return response
	 */
	@SuppressWarnings ("unchecked")
	public T post () {
		this.response = execute (RequestMethod.POST);
		return (T) this;
	}

	/**
	 * @author wasiq.bhamla
	 * @return request
	 * @since Sep 21, 2017 10:38:06 AM
	 */
	public abstract RequestElement prepare ();

	/**
	 * @author wasiq.bhamla
	 * @since Mar 19, 2018 10:12:48 PM
	 * @return response
	 */
	@SuppressWarnings ("unchecked")
	public T put () {
		this.response = execute (RequestMethod.PUT);
		return (T) this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Mar 19, 2018 10:12:30 PM
	 * @return response
	 */
	@SuppressWarnings ("unchecked")
	public T trace () {
		this.response = execute (RequestMethod.TRACE);
		return (T) this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Mar 17, 2019 11:29:33 PM
	 * @return subject
	 */
	public StringSubject verifyBody () {
		return assertThat (this.response.body ());
	}

	/**
	 * @author wasiq.bhamla
	 * @since Mar 17, 2019 11:30:28 PM
	 * @param expression
	 * @return subject
	 */
	public IntegerSubject verifyInt (final String expression) {
		final Optional <Integer> value = this.response.valueOf (expression);
		return assertThat (value.get ());
	}

	/**
	 * @author wasiq.bhamla
	 * @since Mar 17, 2019 11:23:31 PM
	 * @return subject
	 */
	public IntegerSubject verifyStatusCode () {
		return assertThat (this.response.statusCode ());
	}

	/**
	 * @author wasiq.bhamla
	 * @since Mar 17, 2019 11:28:38 PM
	 * @param expression
	 * @return subject
	 */
	public StringSubject verifyString (final String expression) {
		final Optional <String> value = this.response.valueOf (expression);
		return assertThat (value.get ());
	}

	/**
	 * @author wasiq.bhamla
	 * @since May 12, 2018 7:07:54 PM
	 * @param filePath
	 * @return instance
	 */
	@SuppressWarnings ("unchecked")
	public T withFile (final String filePath) {
		this.files.add (filePath);
		return (T) this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Sep 9, 2017 10:18:17 PM
	 * @param name
	 * @param value
	 * @return instance
	 */
	@SuppressWarnings ("unchecked")
	public T withFormParameter (final String name, final Object value) {
		this.formParams.put (name, value);
		return (T) this;
	}

	/**
	 * @author wasiq.bhamla
	 * @param name
	 * @param value
	 * @since Aug 25, 2017 10:15:07 PM
	 * @return instance
	 */
	@SuppressWarnings ("unchecked")
	public T withHeader (final String name, final Object value) {
		this.headers.put (name, value);
		return (T) this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Sep 21, 2017 11:27:56 AM
	 * @param fieldName
	 * @param value
	 * @return instance
	 */
	@SuppressWarnings ("unchecked")
	public T withInputValue (final String fieldName, final Object value) {
		this.values.put (fieldName, value);
		return (T) this;
	}

	/**
	 * @author wasiq.bhamla
	 * @param name
	 * @param value
	 * @since Aug 25, 2017 10:15:12 PM
	 * @return instance
	 */
	@SuppressWarnings ("unchecked")
	public T withParameter (final String name, final Object value) {
		this.params.put (name, value);
		return (T) this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Sep 9, 2017 10:18:30 PM
	 * @param name
	 * @param value
	 * @return instance
	 */
	@SuppressWarnings ("unchecked")
	public T withPathParameter (final String name, final Object value) {
		this.pathParams.put (name, value);
		return (T) this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Sep 9, 2017 10:18:37 PM
	 * @param name
	 * @param value
	 * @return instance
	 */
	@SuppressWarnings ("unchecked")
	public T withQueryParameter (final String name, final Object value) {
		this.queryParams.put (name, value);
		return (T) this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Sep 21, 2017 11:30:07 AM
	 * @param fieldName
	 * @return value
	 */
	@SuppressWarnings ("unchecked")
	protected <V> V get (final String fieldName) {
		return (V) this.values.get (fieldName);
	}

	private ResponseHandler execute (final RequestMethod method) {
		RequestHandler handler = RequestHandler.build ()
			.setting (this.setting)
			.using (this.port)
			.resource (this.resourcePath);
		if (method != RequestMethod.GET) {
			handler = handler.with (prepare ());
		}
		setHeaders (handler);
		setParams (handler);
		setFormParams (handler);
		setQueryParams (handler);
		setPathParams (handler);
		setFiles (handler);
		return handler.execute (method.getMethod ())
			.response ();
	}

	private void setFiles (final RequestHandler handler) {
		handler.multiPart (this.files);
	}

	private void setFormParams (final RequestHandler handler) {
		handler.formParams (this.params);
	}

	private void setHeaders (final RequestHandler handler) {
		handler.headers (this.headers);
	}

	private void setParams (final RequestHandler handler) {
		handler.params (this.params);
	}

	private void setPathParams (final RequestHandler handler) {
		handler.pathParams (this.pathParams);
	}

	private void setQueryParams (final RequestHandler handler) {
		handler.queryParams (this.queryParams);
	}
}