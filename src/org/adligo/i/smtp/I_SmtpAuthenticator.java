package org.adligo.i.smtp;

import java.io.IOException;

import org.adligo.i.adi.shared.InvocationException;

/**
 * implementations need to to be thread-safe.
 * 
 * @author scott
 *
 */
public interface I_SmtpAuthenticator {

	/**
	 * 
	 * @param p
	 * @throws InvocationException on authentication error
	 * @throws IOException on issue with the socket connection
	 */
	public void authenticate(I_SmtpConnection p, I_SmtpCredentials credentials) throws InvocationException, IOException;
}
