package org.adligo.i.smtp;

import java.io.IOException;

import org.adligo.i.adi.client.InvocationException;
import org.adligo.i.pool.I_PooledConnection;
import org.adligo.i.smtp.models.I_EMailMessage;

public interface I_SmtpConnection extends I_PooledConnection {
	/**
	 * sends part of a command (added to obvoid ram spikes in multiple attachments).
	 * sendCommand should always follow this.
	 * @param command
	 * @throws IOException
	 */
	public abstract void sendCommandPart(String command) throws IOException;
	/**
	 * send the smtp (aka telnet) command and wait a bit for the response
	 * @param command
	 * @return
	 * @throws IOException
	 */
	public abstract String[] sendCommand(String command) throws IOException;

	public abstract String[] getEhloResp();

	public abstract void send(I_EMailMessage message) throws IOException, InvocationException;
	
	public void reconnect() throws IOException;
	
	public String generateBoundry(int lenght);
}