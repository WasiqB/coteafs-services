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
import org.json.simple.JSONStreamAware;
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

	private JSONStreamAware obj;

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
	@SuppressWarnings ("unchecked")
	@Override
	public RequestParser build (final RequestElement element) {
		// Case when JSON object is pure JSON Array.
		if (element.name () == null && !element.list ()
			.isEmpty ()) {
			this.obj = new JSONArray ();
			for (int j = 0; j < element.list ()
				.size (); j++) {
				final RequestElement v = element.list ()
					.get (j);
				final JSONObject arrObj = new JSONObject ();
				build (arrObj, v);
				((JSONArray) this.obj).add (arrObj);
			}
		}
		else {
			this.obj = new JSONObject ();
			build ((JSONObject) this.obj, element);
		}
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @param parent
	 * @since Sep 21, 2017 7:27:44 PM
	 * @param currentElement
	 */
	@SuppressWarnings ("unchecked")
	private void addList (final JSONObject parent, final RequestElement currentElement) {
		if (!currentElement.list ()
			.isEmpty ()) {
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

	/**
	 * @author wasiq.bhamla
	 * @since Sep 13, 2017 9:08:20 PM
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
					parent.put (currentElement.name (), value);
				}
				else {
					final JSONObject child = new JSONObject ();
					build (child, currentElement);
					parent.put (currentElement.name (), child);
				}
			}
			addList (parent, currentElement);
		}
	}
}