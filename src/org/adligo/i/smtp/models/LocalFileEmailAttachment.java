package org.adligo.i.smtp.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class LocalFileEmailAttachment implements I_EMailAttachment {
	private File file;
	private String mimeType;
	
	public LocalFileEmailAttachment(String path, String mimeType) {
		file = new File(path);
		this.mimeType = mimeType;
	}
	
	public LocalFileEmailAttachment(File p) {
		file = p;
	}
	@Override
	public InputStream create() throws IOException {
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException x) {
			throw new IOException(x);
		}
	}

	public String getMimeType() {
		return mimeType;
	}

	@Override
	public String getFileName() {
		return file.getName();
	}

}
