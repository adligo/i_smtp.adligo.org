package org.adligo.i.smtp.models;

import java.io.IOException;
import java.io.InputStream;

/**
 * this is for deferred binding of input streams to files
 * exc 
 * @author scott
 *
 */
public interface I_InputStreamFactory {
	public InputStream create() throws IOException;
}
