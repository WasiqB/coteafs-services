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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wasiq.bhamla
 * @since 20-Aug-2017 3:21:47 PM
 */
public class RequestElement {
	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:22:36 PM
	 * @return instance
	 */
	public static RequestElement create () {
		return new RequestElement ();
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:22:51 PM
	 * @param name
	 * @return instance
	 */
	public static RequestElement create (final String name) {
		return new RequestElement (name);
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:22:56 PM
	 * @param prefix
	 * @param name
	 * @return instance
	 */
	public static RequestElement create (final String prefix, final String name) {
		return new RequestElement (prefix, name);
	}

	private final List <RequestAttribute>	attributes;
	private final List <RequestElement>		childs;
	private boolean							display;
	private final List <RequestElement>		list;
	private String							name;
	private final Map <String, String>		namespace;
	private RequestElement					parent;
	private String							prefix;
	private Object							value;

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:23:36 PM
	 */
	private RequestElement () {
		this (null, null);
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:23:40 PM
	 * @param name
	 */
	private RequestElement (final String name) {
		this (null, name);
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:23:45 PM
	 * @param prefix
	 * @param name
	 */
	private RequestElement (final String prefix, final String name) {
		this.childs = new ArrayList <RequestElement> ();
		this.list = new ArrayList <RequestElement> ();
		this.attributes = new ArrayList <RequestAttribute> ();
		this.display = true;
		this.prefix = prefix;
		this.name = name;
		this.namespace = new HashMap <String, String> ();
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:23:53 PM
	 * @param attribute
	 * @return instance
	 */
	public RequestElement addAttribute (final RequestAttribute attribute) {
		this.attributes.add (attribute);
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:27:32 PM
	 * @param element
	 * @return instance
	 */
	public RequestElement addElement (final RequestElement element) {
		this.list.add (element);
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:24:04 PM
	 * @return attributes
	 */
	public List <RequestAttribute> attributes () {
		return this.attributes;
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:24:17 PM
	 * @return childs
	 */
	public List <RequestElement> childs () {
		return this.childs;
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:24:27 PM
	 * @return should display
	 */
	public boolean display () {
		return this.display;
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:24:39 PM
	 * @param shouldDisplay
	 * @return instance
	 */
	public RequestElement display (final boolean shouldDisplay) {
		this.display = shouldDisplay;
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:24:49 PM
	 * @return array object
	 */
	public List <RequestElement> list () {
		return this.list;
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:25:40 PM
	 * @return name
	 */
	public String name () {
		return this.name;
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:25:46 PM
	 * @param elementName
	 * @return instance
	 */
	public RequestElement name (final String elementName) {
		this.name = elementName;
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:25:56 PM
	 * @param ns
	 * @param uri
	 * @return instance
	 */
	public RequestElement namespace (final String ns, final String uri) {
		if (!this.namespace.containsKey (ns)) {
			this.namespace.put (ns, uri);
		}
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:26:15 PM
	 * @return namespaces
	 */
	public Map <String, String> namespaces () {
		return this.namespace;
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:26:33 PM
	 * @return parent
	 */
	public RequestElement parent () {
		return this.parent;
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:26:46 PM
	 * @param elementParent
	 * @return instance
	 */
	public RequestElement parent (final RequestElement elementParent) {
		if (this.parent == null) {
			this.parent = elementParent;
		}
		if (!elementParent.childs ()
			.contains (this)) {
			elementParent.addChild (this);
		}
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:27:11 PM
	 * @return prefix
	 */
	public String prefix () {
		return this.prefix;
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:27:21 PM
	 * @param elementPrefix
	 * @return instance
	 */
	public RequestElement prefix (final String elementPrefix) {
		this.prefix = elementPrefix;
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:30:19 PM
	 * @return value
	 */
	@SuppressWarnings ("unchecked")
	public <T> T value () {
		return (T) this.value;
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:29:48 PM
	 * @param elementValue
	 * @return instance
	 */
	public <T> RequestElement value (final T elementValue) {
		this.value = elementValue;
		return display (this.value != null);
	}

	/**
	 * @author wasiq.bhamla
	 * @since 20-Aug-2017 3:29:39 PM
	 * @param child
	 * @return instance
	 */
	private RequestElement addChild (final RequestElement child) {
		this.childs.add (child);
		if (child.parent () == null || !child.parent ()
			.equals (this)) {
			child.parent (this);
		}
		return this;
	}
}