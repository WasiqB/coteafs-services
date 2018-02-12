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

import static com.google.common.truth.Truth.assertThat;

import org.testng.annotations.Test;

import com.github.wasiqb.coteafs.services.config.RequestMethod;
import com.github.wasiqb.coteafs.services.helper.ResponseHandler;
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
		final ResponseHandler res = user.execute (RequestMethod.GET, true);

		final String firstName = res.valueOf ("data.first_name");
		assertThat (firstName).isEqualTo ("Janet");
		final String lastName = res.valueOf ("data.last_name");
		assertThat (lastName).isEqualTo ("Weaver");
	}

	/**
	 * @author wasiq.bhamla
	 * @since Feb 12, 2018 2:47:57 PM
	 */
	@Test
	public void testUserCreate () {
		final UserRequest user = new UserRequest ("first_api");
		user.withValue ("name", "morpheus");
		user.withValue ("job", "leader");
		final ResponseHandler res = user.execute (RequestMethod.POST, true);

		final String firstName = res.valueOf ("name");
		assertThat (firstName).isEqualTo ("morpheus");
		final String lastName = res.valueOf ("job");
		assertThat (lastName).isEqualTo ("leader");
	}

	/**
	 * @author wasiq.bhamla
	 * @since Feb 12, 2018 3:06:01 PM
	 */
	@Test
	public void testUserDelete () {
		final UserRequest user = new UserRequest ("first_api", 2);
		user.execute (RequestMethod.DELETE, true);
	}

	/**
	 * @author wasiq.bhamla
	 * @since Feb 11, 2018 6:48:30 PM
	 */
	@Test
	public void testUserList () {
		final UserRequest user = new UserRequest ("first_api");
		user.withQueryParameter ("page", 2);
		final ResponseHandler res = user.execute (RequestMethod.GET, true);

		final String firstName = res.valueOf ("data.find { it.id == 4 }.first_name");
		assertThat (firstName).isEqualTo ("Eve");
		final String lastName = res.valueOf ("data.find { it.id == 4 }.last_name");
		assertThat (lastName).isEqualTo ("Holt");
	}

	/**
	 * @author wasiq.bhamla
	 * @since Feb 12, 2018 3:02:55 PM
	 */
	@Test
	public void testUserPatch () {
		final UserRequest user = new UserRequest ("first_api", 2);
		user.withValue ("name", "morpheus");
		user.withValue ("job", "zion resident");
		final ResponseHandler res = user.execute (RequestMethod.PATCH, true);

		final String firstName = res.valueOf ("name");
		assertThat (firstName).isEqualTo ("morpheus");
		final String lastName = res.valueOf ("job");
		assertThat (lastName).isEqualTo ("zion resident");
	}

	/**
	 * @author wasiq.bhamla
	 * @since Feb 12, 2018 3:02:01 PM
	 */
	@Test
	public void testUserUpdate () {
		final UserRequest user = new UserRequest ("first_api", 2);
		user.withValue ("name", "morpheus");
		user.withValue ("job", "zion resident");
		final ResponseHandler res = user.execute (RequestMethod.PUT, true);

		final String firstName = res.valueOf ("name");
		assertThat (firstName).isEqualTo ("morpheus");
		final String lastName = res.valueOf ("job");
		assertThat (lastName).isEqualTo ("zion resident");
	}
}