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

import javax.xml.soap.SOAPConstants;

/**
 * @author wasiq.bhamla
 * @since Mar 15, 2018 9:51:05 PM
 */
public enum SoapProtocol {
	/**
	 * Soap 1.1 protocol.
	 */
	SOAP_1_1 (SOAPConstants.SOAP_1_1_PROTOCOL),
	/**
	 * Soap 1.2 protocol.
	 */
	SOAP_1_2 (SOAPConstants.SOAP_1_2_PROTOCOL);

	private String protocol;

	private SoapProtocol (final String protocol) {
		this.protocol = protocol;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString () {
		return this.protocol;
	}
}