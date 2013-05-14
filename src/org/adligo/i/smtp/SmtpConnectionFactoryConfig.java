package org.adligo.i.smtp;

import org.adligo.i.pool.I_PooledConnectionFactory;
import org.adligo.i.pool.PoolConfiguration;

public class SmtpConnectionFactoryConfig  extends PoolConfiguration<SmtpConnection> implements I_SmtpConnectionFactoryConfig {
	private SmtpConnectionFactoryConfigMutant mutant;
	private SmtpConnectionFactory factory;
	
	public SmtpConnectionFactoryConfig(I_SmtpConnectionFactoryConfig p) {
		mutant = new SmtpConnectionFactoryConfigMutant(p);
		factory = new SmtpConnectionFactory(p);
	}

	public I_SmtpAuthenticator getAuthenticator() {
		return mutant.getAuthenticator();
	}

	public String getHost() {
		return mutant.getHost();
	}

	public int getPort() {
		return mutant.getPort();
	}

	@Override
	public I_PooledConnectionFactory<SmtpConnection> getFactory() {
		return factory;
	}

	public I_SmtpCredentials getCredentials() {
		return mutant.getCredentials();
	}

	public int getMaxWait() {
		return mutant.getMaxWait();
	}
	
}
