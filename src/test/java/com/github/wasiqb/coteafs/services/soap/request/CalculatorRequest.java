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
package com.github.wasiqb.coteafs.services.soap.request;

import com.github.wasiqb.coteafs.services.requests.AbstractRequest;
import com.github.wasiqb.coteafs.services.requests.RequestElement;

/**
 * @author wasiq.bhamla
 * @since Feb 12, 2018 4:17:35 PM
 */
public class CalculatorRequest extends AbstractRequest <CalculatorRequest> {
	/**
	 * @author wasiq.bhamla
	 * @param serviceName
	 * @since Feb 12, 2018 4:17:35 PM
	 */
	public CalculatorRequest (final String serviceName) {
		super (serviceName);
	}

	/*
	 * (non-Javadoc)
	 * @see com.github.wasiqb.coteafs.services.requests.AbstractRequest#prepare()
	 */
	@Override
	public RequestElement prepare () {
		final RequestElement calc = RequestElement.create ("calc", "Add")
			.namespace ("calc", "http://tempuri.org/");

		RequestElement.create ("calc", "intA")
			.parent (calc)
			.value (get ("intA"));
		RequestElement.create ("calc", "intB")
			.parent (calc)
			.value (get ("intB"));

		return calc;
	}
}