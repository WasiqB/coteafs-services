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
package com.github.wasiqb.coteafs.services.response;

import static com.github.wasiqb.coteafs.error.util.ErrorUtil.fail;
import static java.lang.String.format;

import org.apache.commons.lang3.StringUtils;

import com.github.wasiqb.coteafs.services.error.RestResponseParsingFailedError;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

/**
 * @author wasiq.bhamla
 * @since Aug 25, 2017 9:42:00 PM
 */
public class RestResponseValueParser implements ResponseValueParser {
	private final Response response;

	/**
	 * @author wasiq.bhamla
	 * @param response
	 * @since Aug 25, 2017 9:42:00 PM
	 */
	public RestResponseValueParser (final Response response) {
		this.response = response;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.github.wasiqb.coteafs.services.response.ResponseValueParser#valueOf(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public <T> T valueOf (final String name, final String path) {
		if (!StringUtils.isEmpty (name)) {
			this.response.then ()
				.root (name);
		}
		final JsonPath jsonPath = JsonPath.from (this.response.asString ());
		if (StringUtils.isBlank (path)) {
			return null;
		}
		try {
			return jsonPath.get (path);
		}
		catch (final Exception e) {
			final String message = "Response value parsing failed for [%s] expression.";
			fail (RestResponseParsingFailedError.class, format (message, path), e);
		}
		return null;
	}
}