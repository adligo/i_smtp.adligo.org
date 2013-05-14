package org.adligo.i.smtp;

import org.adligo.i.pool.PoolConfiguration;

public class SmtpConnectionFactoryConfigMutant extends PoolConfiguration<SmtpConnection> implements I_SmtpConnectionFactoryConfig {
	private I_SmtpAuthenticator authenticator;
	private String host;
	private int port;
	private I_SmtpCredentials credentials;
	private int maxWait = 1000;
	
	public SmtpConnectionFactoryConfigMutant () {}
	
	public SmtpConnectionFactoryConfigMutant(I_SmtpConnectionFactoryConfig p) {
		authenticator = p.getAuthenticator();
		host = p.getHost();
		port = p.getPort();
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.i.smtp.I_SmtpConnectionFactoryConfig#getAuthenticator()
	 */
	@Override
	public I_SmtpAuthenticator getAuthenticator() {
		return authenticator;
	}
	public void setAuthenticator(I_SmtpAuthenticator authenticator) {
		this.authenticator = authenticator;
	}
	/* (non-Javadoc)
	 * @see org.adligo.i.smtp.I_SmtpConnectionFactoryConfig#getHost()
	 */
	@Override
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	/* (non-Javadoc)
	 * @see org.adligo.i.smtp.I_SmtpConnectionFactoryConfig#getPort()
	 */
	@Override
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}

	public I_SmtpCredentials getCredentials() {
		return credentials;
	}

	public void setCredentials(I_SmtpCredentials credentials) {
		this.credentials = credentials;
	}

	public int getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}
	
}
