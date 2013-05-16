package org.adligo.i.smtp.models;

import java.io.IOException;

public interface I_EMailAttachmentStream {
	/**
	 * marks that the emailer is starting to read the bytes of the attachment
	 */
	public void gettingData() throws IOException;
	/**
	 * true if there are more bytes, false if there arn't
	 * @return
	 */
	public boolean hasMoreData() throws IOException;
	/**
	 * returns the next byte
	 * @return
	 */
	public byte nextByte() throws IOException;
	
	/**
	 * marks that the emailer is done reading the bytes of the attachment
	 */
	public void finishedGettingData() throws IOException;
}
