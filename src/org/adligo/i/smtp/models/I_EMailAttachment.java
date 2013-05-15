package org.adligo.i.smtp.models;

import java.io.IOException;
import java.io.InputStream;

/**
 * this is for deferred binding of input streams to files
 * exc 
 * @author scott
 *
 */
public interface I_EMailAttachment {
	public String getMimeType();
	/**
	 * should create a new InputStream each time
	 * in case send burps, and the message is resent
	 * the send method will close all InputStreams it creates with this method
	 * @return
	 * @throws IOException
	 */
	public InputStream create() throws IOException;
	
	/**
	 * the file name to show to the recipient
	 * of the email
	 * @return
	 */
	public String getFileName();
}
