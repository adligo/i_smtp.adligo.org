package org.adligo.i.smtp.models;


/**
 * this is for deferred binding of input streams to files
 * exc 
 * @author scott
 *
 */
public interface I_EMailAttachment extends I_EMailAttachmentStream {
	public String getMimeType();
	
	/**
	 * the file name to show to the recipient
	 * of the email
	 * @return
	 */
	public String getFileName();
}
