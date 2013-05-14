package org.adligo.i.smtp;

import java.io.IOException;

import org.adligo.i.pool.I_PooledConnectionFactory;

public class SmtpConnectionFactory implements I_PooledConnectionFactory<SmtpConnection> {
	private I_SmtpConnectionFactoryConfig config;
	
	@SuppressWarnings("unchecked")
	public SmtpConnectionFactory(I_SmtpConnectionFactoryConfig config) {
		this.config = config;
	}

	@Override
	public SmtpConnection create() throws IOException {
		return new SmtpConnection(config);
	}
}
