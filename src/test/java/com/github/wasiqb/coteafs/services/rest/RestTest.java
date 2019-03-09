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

import com.github.wasiqb.coteafs.services.error.ServiceNotFoundError;
import com.github.wasiqb.coteafs.services.rest.request.UserRequest;

/**
 * @author wasiq.bhamla
 * @since Feb 11, 2018 6:26:45 PM
 */
public class RestTest {
	/**
	 * @author wasiq.bhamla
	 * @since Feb 12, 2018 2:32:55 PM
	 */
	@Test
	public void testSingleUser () {
		final UserRequest user = new UserRequest ("first_api", 2);
		user.get (true);
		user.verifyThat ("data.first_name")
			.equalsTo ("Janet");
		user.verifyThat ("data.last_name")
			.equalsTo ("Weaver");
	}

	/**
	 * @author wasiq.bhamla
	 * @since Mar 21, 2018 8:48:16 PM
	 */
	@Test (expectedExceptions = ServiceNotFoundError.class)
	public void testSingleUserInvalidUrl () {
		final UserRequest user = new UserRequest ("invalid_rest_api", 2);
		user.get (true);
	}

	/**
	 * @author wasiq.bhamla
	 * @since Feb 12, 2018 2:47:57 PM
	 */
	@Test
	public void testUserCreate () {
		final UserRequest user = new UserRequest ("first_api");
		user.withValue ("name", "morpheus")
			.withValue ("job", "leader")
			.post (true);

		user.verifyThat ("name")
			.equalsTo ("morpheus");
		user.verifyThat ("job")
			.equalsTo ("leader");
	}

	/**
	 * @author wasiq.bhamla
	 * @since Feb 12, 2018 3:06:01 PM
	 */
	@Test
	public void testUserDelete () {
		final UserRequest user = new UserRequest ("first_api", 2);
		user.delete (true);
	}

	/**
	 * @author wasiq.bhamla
	 * @since Feb 11, 2018 6:48:30 PM
	 */
	@Test
	public void testUserList () {
		final UserRequest user = new UserRequest ("first_api");
		user.withQueryParameter ("page", 2)
			.get (true);

		user.verifyThat ("data.find { it.id == 4 }.first_name")
			.equalsTo ("Eve");
		user.verifyThat ("data.find { it.id == 4 }.last_name")
			.equalsTo ("Holt");
	}

	/**
	 * @author wasiq.bhamla
	 * @since Feb 12, 2018 3:02:55 PM
	 */
	@Test
	public void testUserPatch () {
		final UserRequest user = new UserRequest ("first_api", 2);
		user.withValue ("name", "morpheus")
			.withValue ("job", "zion resident")
			.patch (true);

		user.verifyThat ("name")
			.equalsTo ("morpheus");
		user.verifyThat ("job")
			.equalsTo ("zion resident");
	}

	/**
	 * @author wasiq.bhamla
	 * @since Feb 12, 2018 3:02:01 PM
	 */
	@Test
	public void testUserUpdate () {
		final UserRequest user = new UserRequest ("first_api", 2);
		user.withValue ("name", "morpheus")
			.withValue ("job", "zion resident")
			.put (true);

		user.verifyThat ("name")
			.equalsTo ("morpheus");
		user.verifyThat ("job")
			.equalsTo ("zion resident");
	}

	/**
	 * @author wasiq.bhamla
	 * @since Apr 28, 2018 9:28:11 PM
	 */
	@Test
	public void testWithQueryParam () {
		final UserRequest user = new UserRequest ("first_api");
		user.withQueryParameter ("delay", "3")
			.get (true);
		user.verifyThat ("page")
			.equalsTo (1);
		user.verifyThat ("total")
			.equalsTo (12);
	}
}