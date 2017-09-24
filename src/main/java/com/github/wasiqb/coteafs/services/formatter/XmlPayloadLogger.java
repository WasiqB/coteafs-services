package com.github.wasiqb.coteafs.services.formatter;

import static com.github.wasiqb.coteafs.error.util.ErrorUtil.fail;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.github.wasiqb.coteafs.services.error.XmlFormatTransformerError;

/**
 * @author wasiq.bhamla
 * @since Aug 18, 2017 4:20:56 PM
 */
class XmlPayloadLogger implements PayloadLogger {
	/*
	 * (non-Javadoc)
	 * @see com.github.wasiqb.coteafs.services.formatter.PayloadLogger#getPayload(com.github.wasiqb.
	 * coteafs.services.formatter.PayloadType, java.lang.String)
	 */
	@Override
	public String [] getPayload (final PayloadType type, final String body) {
		final Source input = new StreamSource (new StringReader (body));
		final StringWriter writer = new StringWriter ();
		final StreamResult output = new StreamResult (writer);
		final TransformerFactory transformerFactory = TransformerFactory.newInstance ();
		transformerFactory.setAttribute ("indent-number", 4);
		try {
			final Transformer transformer = transformerFactory.newTransformer ();
			transformer.setOutputProperty (OutputKeys.INDENT, "yes");
			transformer.transform (input, output);
			return output.getWriter ()
				.toString ()
				.split ("\n");
		}
		catch (final TransformerException e) {
			fail (XmlFormatTransformerError.class, "Error while Xml Transformation.", e);
		}
		return new String [] {};
	}
}