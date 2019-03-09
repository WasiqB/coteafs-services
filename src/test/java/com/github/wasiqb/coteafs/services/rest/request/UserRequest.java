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
package com.github.wasiqb.coteafs.services.rest.request;

import com.github.wasiqb.coteafs.services.requests.AbstractRequest;
import com.github.wasiqb.coteafs.services.requests.RequestElement;

/**
 * @author wasiq.bhamla
 * @since Feb 11, 2018 6:32:51 PM
 */
public class UserRequest extends AbstractRequest <UserRequest> {
	/**
	 * @author wasiq.bhamla
	 * @since Feb 11, 2018 6:44:12 PM
	 * @param serviceName
	 */
	public UserRequest (final String serviceName) {
		this (serviceName, 0);
	}

	/**
	 * @author wasiq.bhamla
	 * @since Feb 11, 2018 6:32:51 PM
	 * @param serviceName
	 * @param page
	 */
	public UserRequest (final String serviceName, final int page) {
		super (serviceName, "/users" + (page > 0 ? "/" + page : ""));
	}

	/*
	 * (non-Javadoc)
	 * @see com.github.wasiqb.coteafs.services.requests.AbstractRequest#buildRequest()
	 */
	@Override
	public RequestElement prepare () {
		final RequestElement user = RequestElement.create ();

		RequestElement.create ("name")
			.parent (user)
			.value (get ("name"));
		RequestElement.create ("job")
			.parent (user)
			.value (get ("job"));

		return user;
	}
}