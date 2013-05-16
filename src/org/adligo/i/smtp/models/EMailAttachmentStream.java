package org.adligo.i.smtp.models;

import java.io.IOException;

import org.adligo.i.log.client.Log;
import org.adligo.i.log.client.LogFactory;

public class EMailAttachmentStream implements I_EMailAttachment {
	private static final Log log = LogFactory.getLog(EMailAttachmentStream.class);
	private I_EMailAttachmentStream _stream;
	private String _mimeType;
	private String _fileName;
	
	public EMailAttachmentStream(String fileName, String mimeType, I_EMailAttachmentStream stream) {
		_stream = stream;
		_mimeType = mimeType;
		_fileName = fileName;
	}
	

	public String getMimeType() {
		return _mimeType;
	}

	@Override
	public String getFileName() {
		return _fileName;
	}


	@Override
	public void gettingData() throws IOException {
		_stream.gettingData();
	}

	@Override
	public boolean hasMoreData() throws IOException {
		return _stream.hasMoreData();
	}

	@Override
	public byte nextByte() throws IOException {
		return _stream.nextByte();
	}

	@Override
	public void finishedGettingData() throws IOException {
		_stream.finishedGettingData();
	}

}
