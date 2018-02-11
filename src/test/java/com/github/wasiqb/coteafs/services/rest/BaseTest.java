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
package com.github.wasiqb.coteafs.services.rest;

import org.testng.annotations.Test;

import com.github.wasiqb.coteafs.services.config.RequestMethod;
import com.github.wasiqb.coteafs.services.helper.ResponseHandler;
import com.github.wasiqb.coteafs.services.rest.request.UserRequest;

/**
 * @author wasiq.bhamla
 * @since Feb 11, 2018 6:26:45 PM
 */
public class BaseTest {
	/**
	 * @author wasiq.bhamla
	 * @since Feb 11, 2018 6:48:30 PM
	 */
	@Test
	public void testUserList () {
		final UserRequest user = new UserRequest ("first_api");
		user.withQueryParameter ("page", 2);
		final ResponseHandler res = user.execute (RequestMethod.GET, true);

		System.out.println (res.valueOf ("data.find { it.id == 50 }.last_name")
			.toString ());
	}
}