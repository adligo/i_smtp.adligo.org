package org.adligo.i.smtp;

import org.adligo.i.pool.I_PoolConfiguration;

public interface I_SmtpConnectionFactoryConfig extends I_PoolConfiguration<SmtpConnection> {

	public abstract I_SmtpAuthenticator getAuthenticator();

	public abstract String getHost();

	public abstract int getPort();

	public I_SmtpCredentials getCredentials();
	
	/**
	 * the maximum number of milliseconds to wait after sending a command 
	 * for a response from the smtp server
	 * defaults to 1 second (1000).
	 * @return
	 */
	public int getMaxWait();
}