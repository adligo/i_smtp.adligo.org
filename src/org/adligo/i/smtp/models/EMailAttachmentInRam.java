package org.adligo.i.smtp.models;

import java.io.IOException;

import org.adligo.i.log.client.Log;
import org.adligo.i.log.client.LogFactory;

public class EMailAttachmentInRam implements I_EMailAttachment {
	private static final Log log = LogFactory.getLog(EMailAttachmentInRam.class);
	private byte [] _data;
	private String _mimeType;
	private String _fileName;
	private int byteCounter = 0;
	
	public EMailAttachmentInRam(String fileName, String mimeType, byte [] data) {
		_data = data;
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
	public void gettingData() {
		 byteCounter = 0;
	}

	@Override
	public boolean hasMoreData() {
		if (byteCounter < _data.length) {
			return true;
		}
		return false;
	}

	@Override
	public byte nextByte() {
		// TODO Auto-generated method stub
		return _data[byteCounter++];
	}


	@Override
	public void finishedGettingData() throws IOException {
		//do nothing
	}

}
