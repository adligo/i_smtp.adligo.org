package org.adligo.i.smtp.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class FileInputStreamFactory implements I_InputStreamFactory {
	private File file;
	
	public FileInputStreamFactory(String path) {
		file = new File(path);
	}
	
	public FileInputStreamFactory(File p) {
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

}
