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
package com.github.wasiqb.coteafs.services.soap;

import org.testng.annotations.Test;

import com.github.wasiqb.coteafs.services.config.RequestMethod;
import com.github.wasiqb.coteafs.services.helper.ResponseHandler;
import com.github.wasiqb.coteafs.services.soap.request.CalculatorRequest;
import com.google.common.truth.Truth;

/**
 * @author wasiq.bhamla
 * @since Feb 12, 2018 4:30:26 PM
 */
public class SoapTest {
	/**
	 * @author wasiq.bhamla
	 * @since Feb 12, 2018 4:33:50 PM
	 */
	@Test
	public void testSingleUser () {
		final CalculatorRequest calc = new CalculatorRequest ("soap_api");
		calc.withValue ("intA", 10);
		calc.withValue ("intB", 10);
		final ResponseHandler res = calc.execute (RequestMethod.POST, true);

		final String result = res.valueOf ("AddResult");
		Truth.assertThat (result)
			.isEqualTo ("20");
	}
}