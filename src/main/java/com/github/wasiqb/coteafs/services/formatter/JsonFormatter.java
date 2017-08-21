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
package com.github.wasiqb.coteafs.services.formatter;

import static com.github.wasiqb.coteafs.error.util.ErrorUtil.fail;

import com.github.wasiqb.coteafs.services.error.JsonFormatTransformerError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * @author wasiq.bhamla
 * @since Aug 20, 2017 2:23:25 PM
 */
public class JsonFormatter implements Formatter {
	/*
	 * (non-Javadoc)
	 * @see com.github.wasiqb.coteafs.services.formatter.Formatter#format(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String format (final String body) {
		try {
			final JsonParser parser = new JsonParser ();
			final Gson gson = new GsonBuilder ().setPrettyPrinting ()
				.create ();
			final JsonElement element = parser.parse (body);
			return gson.toJson (element);
		}
		catch (final Exception e) {
			fail (JsonFormatTransformerError.class, "Error while JSON Transformation.", e);
		}
		return null;
	}
}