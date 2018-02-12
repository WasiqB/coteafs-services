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
package com.github.wasiqb.coteafs.services.config;

/**
 * @author wasiq.bhamla
 * @since Feb 12, 2018 9:32:05 PM
 */
public enum MediaType {
	/**
	 * Any.
	 */
	ANY ("*/*"),
	/**
	 * <code>application/javascript</code>
	 */
	APP_JS ("application/javascript"),
	/**
	 * <code>application/json</code>
	 */
	APP_JSON ("application/json"),
	/**
	 * <code>application/xhtml+xml</code>
	 */
	APP_XHTML ("application/xhtml+xml"),
	/**
	 * <code>application/xml</code>
	 */
	APP_XML ("application/xml"),
	/**
	 * <code>application/octet-stream</code>
	 */
	BINARY ("application/octet-stream"),
	/**
	 * <code>text/html</code>
	 */
	HTML ("text/html"),
	/**
	 * <code>text/javascript</code>
	 */
	TEXT_JS ("text/javascript"),
	/**
	 * <code>text/json</code>
	 */
	TEXT_JSON ("text/json"),
	/**
	 * <code>text/plain</code>
	 */
	TEXT_PLAIN ("text/plain"),
	/**
	 * <code>text/xml</code>
	 */
	TEXT_XML ("text/xml"),
	/**
	 * <code>application/x-www-form-urlencoded</code>
	 */
	URLENC ("application/x-www-form-urlencoded");

	private final String type;

	private MediaType (final String type) {
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString () {
		return this.type;
	}
}