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
package com.github.wasiqb.coteafs.services.parser;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;

import com.github.wasiqb.coteafs.services.requests.RequestElement;

/**
 * @author wasiq.bhamla
 * @since Aug 26, 2017 4:06:36 PM
 */
public class RestRequestParser implements RequestParser {
	/**
	 * @author wasiq.bhamla
	 * @since Aug 26, 2017 4:06:43 PM
	 * @return parser
	 */
	public static RequestParser create () {
		return new RestRequestParser ();
	}

	private final JSONObject obj;

	private RestRequestParser () {
		this.obj = new JSONObject ();
	}

	/*
	 * (non-Javadoc)
	 * @see com.github.wasiqb.coteafs.services.parser.RequestParser#body()
	 */
	@Override
	public String body () {
		final StringWriter out = new StringWriter ();
		try {
			this.obj.writeJSONString (out);
		}
		catch (final IOException e) {
			Assert.fail ("Failed to parse JSON string", e);
		}
		return out.toString ();
	}

	/*
	 * (non-Javadoc)
	 * @see com.github.wasiqb.coteafs.services.parser.RequestParser#build(com.github.wasiqb.coteafs.
	 * services.requests.RequestElement)
	 */
	@Override
	public RequestParser build (final RequestElement element) {
		build (this.obj, element);
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 26, 2017 4:07:03 PM
	 * @param parent
	 * @param element
	 */
	@SuppressWarnings ("unchecked")
	private void build (final JSONObject parent, final RequestElement element) {
		final List <RequestElement> elementList = element.childs ();
		for (int i = 0; i < elementList.size (); i++) {
			final RequestElement currentElement = elementList.get (i);
			if (currentElement.display ()) {
				final Object value = currentElement.value ();
				if (null != value) {
					parent.put (currentElement.name (), currentElement.value ());
				}
				else {
					final JSONObject child = new JSONObject ();
					build (child, currentElement);
					parent.put (currentElement.name (), child);
				}
				if (currentElement.list ()
					.size () > 0) {
					final JSONArray list = new JSONArray ();
					for (int j = 0; j < currentElement.list ()
						.size (); j++) {
						final RequestElement v = currentElement.list ()
							.get (j);
						final JSONObject arrObj = new JSONObject ();
						build (arrObj, v);
						list.add (arrObj);
					}
					parent.put (currentElement.name (), list);
				}
			}
		}
	}
}