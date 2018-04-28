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
package com.github.wasiqb.coteafs.services.requests;

import static com.google.common.truth.Truth.assertThat;

import java.util.Optional;

import com.github.wasiqb.coteafs.services.helper.ResponseHandler;

/**
 * @author wasiq.bhamla
 * @since Apr 1, 2018 9:50:26 PM
 */
public class ResponseVerify {
	private final String			expression;
	private final ResponseHandler	response;

	/**
	 * @author wasiq.bhamla
	 * @param response
	 * @param expression
	 * @since Apr 1, 2018 9:50:26 PM
	 */
	public ResponseVerify (final ResponseHandler response, final String expression) {
		this.response = response;
		this.expression = expression;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Apr 1, 2018 9:56:24 PM
	 * @param actual
	 */
	public <T> void equalsTo (final T actual) {
		final Optional <Object> expected = this.response.valueOf (this.expression);
		assertThat (expected.isPresent ()).isTrue ();
		assertThat (actual).isEqualTo (expected.get ());
	}

	/**
	 * @author wasiq.bhamla
	 * @since Apr 1, 2018 10:12:14 PM
	 * @param actual
	 */
	public <T> void notEqualsTo (final T actual) {
		final Optional <Object> expected = this.response.valueOf (this.expression);
		assertThat (expected.isPresent ()).isTrue ();
		assertThat (actual).isNotEqualTo (expected.get ());
	}
}