package com.github.wasiqb.coteafs.services.formatter;

/**
 * @author wasiq.bhamla
 * @since Aug 18, 2017 4:18:21 PM
 */
public interface PayloadLogger {
	/**
	 * @author wasiq.bhamla
	 * @since Sep 15, 2017 8:34:26 PM
	 * @param type
	 * @param body
	 * @return request
	 */
	String [] getPayload (PayloadType type, String body);
}