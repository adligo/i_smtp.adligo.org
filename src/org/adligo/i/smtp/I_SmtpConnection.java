package org.adligo.i.smtp;

import java.io.IOException;

import org.adligo.i.pool.I_PooledConnection;
import org.adligo.i.smtp.models.I_EmailMessage;

public interface I_SmtpConnection extends I_PooledConnection {
	
	
	public abstract String[] sendCommand(String command) throws IOException;

	public abstract String[] getEhloResp();

	public abstract void send(I_EmailMessage message) throws IOException;
	
	public void reconnect() throws IOException;
	
	public String generateBoundry(int lenght);
}