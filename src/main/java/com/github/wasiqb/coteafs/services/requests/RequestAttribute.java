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

/**
 * @author wasiq.bhamla
 * @since Aug 20, 2017 2:56:36 PM
 */
public class RequestAttribute {
	/**
	 * @author wasiq.bhamla
	 * @since Aug 20, 2017 2:56:41 PM
	 * @return instance
	 */
	public static RequestAttribute create () {
		return new RequestAttribute ();
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 20, 2017 2:56:46 PM
	 * @param prefix
	 * @param name
	 * @param value
	 * @return instance
	 */
	public static RequestAttribute create (final String prefix, final String name, final String value) {
		return new RequestAttribute (prefix, name, value);
	}

	private String	name;
	private String	prefix;
	private Object	value;

	/**
	 * @author wasiq.bhamla
	 * @since Aug 20, 2017 2:56:49 PM
	 */
	private RequestAttribute () {
		this (null, null, null);
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 20, 2017 2:56:56 PM
	 * @param prefix
	 * @param name
	 * @param value
	 */
	private <T> RequestAttribute (final String prefix, final String name, final T value) {
		this.prefix = prefix;
		this.name = name;
		this.value = value;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 20, 2017 2:57:01 PM
	 * @return name
	 */
	public String name () {
		return this.name;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 20, 2017 2:57:06 PM
	 * @param attrName
	 * @return instance
	 */
	public RequestAttribute name (final String attrName) {
		this.name = attrName;
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 20, 2017 2:57:10 PM
	 * @return prefix
	 */
	public String prefix () {
		return this.prefix;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 20, 2017 2:57:21 PM
	 * @param attrPrefix
	 * @return instance
	 */
	public RequestAttribute prefix (final String attrPrefix) {
		this.prefix = attrPrefix;
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 20, 2017 2:57:26 PM
	 * @return value
	 */
	@SuppressWarnings ("unchecked")
	public <T> T value () {
		return (T) this.value;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 20, 2017 2:57:30 PM
	 * @param attrValue
	 * @return instance
	 */
	public <T> RequestAttribute value (final T attrValue) {
		this.value = attrValue;
		return this;
	}
}