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
package com.github.wasiqb.coteafs.services.parser;

import static com.github.wasiqb.coteafs.error.util.ErrorUtil.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import com.github.wasiqb.coteafs.services.config.SoapProtocol;
import com.github.wasiqb.coteafs.services.error.SoapParserInitError;
import com.github.wasiqb.coteafs.services.error.SoapRequestParsingFailedError;
import com.github.wasiqb.coteafs.services.requests.RequestAttribute;
import com.github.wasiqb.coteafs.services.requests.RequestElement;

/**
 * @author wasiq.bhamla
 * @since Aug 26, 2017 4:10:35 PM
 */
public class SoapRequestParser implements RequestParser {
	/**
	 * @author wasiq.bhamla
	 * @param soapProtocol
	 * @since Aug 26, 2017 4:10:40 PM
	 * @return parser
	 */
	public static RequestParser create (final SoapProtocol soapProtocol) {
		return new SoapRequestParser (soapProtocol);
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 26, 2017 4:10:49 PM
	 * @param parent
	 * @param e
	 * @throws SOAPException
	 */
	private static <T extends SOAPElement> void addAttributes (final T parent, final RequestElement e)
			throws SOAPException {
		if (!e.attributes ()
			.isEmpty ()) {
			for (final RequestAttribute attr : e.attributes ()) {
				if (null != attr.value ()) {
					final QName qName = parent.createQName (attr.name (), attr.prefix ());
					final String value = attr.value ();
					parent.addAttribute (qName, value);
				}
			}
		}
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 26, 2017 4:10:56 PM
	 * @param parent
	 * @param element
	 * @throws SOAPException
	 */
	private static <T extends SOAPElement> void addNamespaces (final T parent, final RequestElement element)
			throws SOAPException {
		if (!element.namespaces ()
			.isEmpty ()) {
			for (final String prefix : element.namespaces ()
				.keySet ()) {
				final String uri = element.namespaces ()
					.get (prefix);
				parent.addNamespaceDeclaration (prefix, uri);
			}
		}
	}

	private SOAPBodyElement	body;
	private SOAPBody		soapBody;
	private SOAPEnvelope	soapEnv;
	private SOAPMessage		soapMessage;

	/**
	 * @author wasiq.bhamla
	 * @param soapProtocol
	 * @since Aug 26, 2017 4:11:08 PM
	 */
	private SoapRequestParser (final SoapProtocol soapProtocol) {
		try {
			init (soapProtocol);
		}
		catch (final SOAPException e) {
			fail (SoapParserInitError.class, "Soap Parser Init failed.", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.github.wasiqb.coteafs.services.parser.RequestParser#body()
	 */
	@Override
	public String body () {
		try {
			final ByteArrayOutputStream bs = new ByteArrayOutputStream ();
			this.soapMessage.writeTo (bs);
			return new String (bs.toByteArray ());
		}
		catch (SOAPException | IOException e) {
			fail (SoapRequestParsingFailedError.class, "Soap Request Body Parsing failed:", e);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.github.wasiqb.coteafs.services.parser.RequestParser#build(com.github.wasiqb.coteafs.
	 * services.requests.RequestElement)
	 */
	@Override
	public RequestParser build (final RequestElement element) {
		if (element != null && this.body == null) {
			try {
				addNamespaces (this.soapEnv, element);
				final QName qName = this.soapBody.createQName (element.name (), element.prefix ());
				this.body = this.soapBody.addBodyElement (qName);
				addAttributes (this.body, element);
				addElement (this.body, element);
			}
			catch (final SOAPException e) {
				fail (SoapRequestParsingFailedError.class, "Soap Request Parsing failed:", e);
			}
		}
		return this;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Aug 26, 2017 4:11:33 PM
	 * @param parent
	 * @param element
	 * @throws SOAPException
	 */
	private <T extends SOAPElement> void addElement (final T parent, final RequestElement element)
			throws SOAPException {
		final List <RequestElement> elementList = element.childs ();
		for (int i = 0; i < elementList.size (); i++) {
			final T currentParent = parent;
			final RequestElement currentElement = elementList.get (i);
			SOAPElement child = null;

			if (currentElement.display ()) {
				if (currentElement.prefix () != null) {
					child = currentParent.addChildElement (currentElement.name (), currentElement.prefix ());
				}
				else {
					child = currentParent.addChildElement (currentElement.name ());
				}

				addNamespaces (child, currentElement);
				addAttributes (child, currentElement);

				final Object value = currentElement.value ();
				if (null != value) {
					child.addTextNode (value.toString ());
				}
				addElement (child, currentElement);
			}
		}
	}

	/**
	 * @author wasiq.bhamla
	 * @param soapProtocol
	 * @since Aug 26, 2017 4:11:43 PM
	 * @throws SOAPException
	 */
	private void init (final SoapProtocol soapProtocol) throws SOAPException {
		final MessageFactory factory = MessageFactory.newInstance (soapProtocol.toString ());
		this.soapMessage = factory.createMessage ();
		final SOAPPart soapPart = this.soapMessage.getSOAPPart ();
		this.soapEnv = soapPart.getEnvelope ();
		this.soapBody = this.soapEnv.getBody ();
		this.soapEnv.removeNamespaceDeclaration (this.soapEnv.getPrefix ());
		final String prefix = "soapenv";
		this.soapEnv.setPrefix (prefix);
		this.soapMessage.getSOAPHeader ()
			.setPrefix (prefix);
		this.soapMessage.getSOAPBody ()
			.setPrefix (prefix);
	}
}