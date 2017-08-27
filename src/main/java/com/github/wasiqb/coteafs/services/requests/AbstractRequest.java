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

import java.util.Map;

import com.github.wasiqb.coteafs.config.loader.ConfigLoader;
import com.github.wasiqb.coteafs.services.config.ServiceSetting;
import com.github.wasiqb.coteafs.services.config.ServicesSetting;
import com.github.wasiqb.coteafs.services.helper.RequestHandler;
import com.github.wasiqb.coteafs.services.helper.ResponseHandler;

import io.restassured.http.Method;

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

	private final String			resourcePath;
	private final RequestHandler	request;
	private final ServiceSetting	setting;

	/**
	 * @author wasiq.bhamla
	 * @param serviceName
	 * @param resourcePath
	 * @since Aug 20, 2017 3:00:51 PM
	 */
	public AbstractRequest (final String serviceName, final String resourcePath) {
		this.resourcePath = resourcePath;
		this.setting = settings.getService (serviceName);
		this.request = RequestHandler.build ()
			.setting (this.setting)
			.using ()
			.resource (this.resourcePath);
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 25, 2017 10:14:51 PM
	 * @param method
	 * @param shouldWork
	 * @return response handler
	 */
	public ResponseHandler execute (final Method method, final boolean shouldWork) {
		return this.request.with (prepare ())
			.execute (method, shouldWork)
			.response ();
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 25, 2017 10:15:07 PM
	 * @param headers
	 * @return instance
	 */
	public AbstractRequest withHeaders (final Map <String, Object> headers) {
		this.request.headers (headers);
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 25, 2017 10:15:12 PM
	 * @param parameters
	 * @return instance
	 */
	public AbstractRequest withParameters (final Map <String, Object> parameters) {
		this.request.params (parameters);
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 25, 2017 10:19:45 PM
	 * @return
	 */
	protected abstract RequestElement prepare ();
}