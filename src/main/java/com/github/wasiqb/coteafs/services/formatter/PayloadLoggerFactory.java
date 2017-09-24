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

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.wasiqb.coteafs.services.config.ServiceType;

/**
 * @author wasiq.bhamla
 * @since Sep 10, 2017 9:24:57 PM
 */
public class PayloadLoggerFactory {
	private static final String	LINE;
	private static final Logger	LOG;

	static {
		LOG = LogManager.getLogger (PayloadLoggerFactory.class);
		LINE = StringUtils.repeat ("=", 80);
	}

	/**
	 * @author wasiq.bhamla
	 * @since Sep 10, 2017 9:27:05 PM
	 * @param serviceType
	 * @param payloadType
	 * @param body
	 */
	public static void log (final ServiceType serviceType, final PayloadType payloadType, final String body) {
		PayloadLogger logger = new JsonPayloadLogger ();
		if (serviceType == ServiceType.SOAP) {
			logger = new XmlPayloadLogger ();
		}

		LOG.info (LINE);
		LOG.info (payloadType.getType ());
		LOG.info (LINE);
		for (final String text : logger.getPayload (payloadType, body)) {
			LOG.info (text);
		}
	}

	/**
	 * @author wasiq.bhamla
	 * @since Sep 24, 2017 1:48:22 PM
	 */
	private PayloadLoggerFactory () {
		// Utility class.
	}
}