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

import java.util.HashMap;
import java.util.Map;

import com.github.wasiqb.coteafs.config.loader.ConfigLoader;
import com.github.wasiqb.coteafs.services.config.RequestMethod;
import com.github.wasiqb.coteafs.services.config.ServiceSetting;
import com.github.wasiqb.coteafs.services.config.ServicesSetting;
import com.github.wasiqb.coteafs.services.helper.RequestHandler;
import com.github.wasiqb.coteafs.services.helper.ResponseHandler;

/**
 * @author wasiq.bhamla
 * @since Aug 20, 2017 3:00:51 PM
 */
public abstract class AbstractRequest {
	private static ServicesSetting settings;

	static {
		settings = ConfigLoader.settings ()
			.withKey (SERVICE_CONFIG_KEY)
			.withDefault (SERVICE_CONFIG_DEFAULT_FILE_NAME)
			.load (ServicesSetting.class);
	}

	private final Map <String, Object>	formParams;
	private final Map <String, Object>	headers;
	private final Map <String, Object>	params;
	private final Map <String, Object>	pathParams;
	private final Map <String, Object>	queryParams;
	private final String				resourcePath;
	private final ServiceSetting		setting;

	/**
	 * @author wasiq.bhamla
	 * @param serviceName
	 * @param resourcePath
	 * @since Aug 20, 2017 3:00:51 PM
	 */
	public AbstractRequest (final String serviceName, final String resourcePath) {
		this.setting = settings.getService (serviceName);
		this.headers = new HashMap <> ();
		this.params = new HashMap <> ();
		this.pathParams = new HashMap <> ();
		this.formParams = new HashMap <> ();
		this.queryParams = new HashMap <> ();
		this.resourcePath = String.format ("%s%s", this.setting.getEndPointSuffix (), resourcePath);
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 25, 2017 10:14:51 PM
	 * @param method
	 * @param shouldWork
	 * @return response handler
	 */
	public ResponseHandler execute (final RequestMethod method, final boolean shouldWork) {
		RequestHandler handler = RequestHandler.build ()
			.setting (this.setting)
			.using ()
			.resource (this.resourcePath);
		if (method != RequestMethod.GET) {
			handler = handler.with (prepare ());
		}
		setHeaders (handler);
		setParams (handler);
		System.out.println (handler.url ());
		return handler.execute (method.getMethod (), shouldWork)
			.response ();
	}

	/**
	 * @author wasiq.bhamla
	 * @param name
	 * @param value
	 * @since Aug 25, 2017 10:15:07 PM
	 * @return instance
	 */
	public AbstractRequest withHeader (final String name, final Object value) {
		this.headers.put (name, value);
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @param name
	 * @param value
	 * @since Aug 25, 2017 10:15:12 PM
	 * @return instance
	 */
	public AbstractRequest withParameter (final String name, final Object value) {
		this.params.put (name, value);
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 25, 2017 10:19:45 PM
	 * @return request
	 */
	protected abstract RequestElement prepare ();

	/**
	 * @author wasiq.bhamla
	 * @since Sep 4, 2017 10:11:24 PM
	 * @param handler
	 */
	private void setHeaders (final RequestHandler handler) {
		handler.headers (this.headers);
	}

	/**
	 * @author wasiq.bhamla
	 * @since Sep 4, 2017 10:12:05 PM
	 * @param handler
	 */
	private void setParams (final RequestHandler handler) {
		handler.params (this.params);
	}
}