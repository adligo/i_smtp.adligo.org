package org.adligo.i.smtp.authenticators;

import java.io.IOException;

import org.adligo.i.adi.client.InvocationException;
import org.adligo.i.log.client.Log;
import org.adligo.i.log.client.LogFactory;
import org.adligo.i.smtp.I_SmtpAuthenticator;
import org.adligo.i.smtp.I_SmtpCredentials;
import org.adligo.i.smtp.SmtpConnection;
import org.adligo.models.params.client.Base64;

public class SmtpPlainAuthenticator implements I_SmtpAuthenticator {
	public static final String THE_SERVER_DOES_NOT_UNDERSTAND_THE_AUTH_PLAIN_COMMAND = "The server does NOT understand the AUTH PLAIN command.";
	private static final Log log = LogFactory.getLog(SmtpPlainAuthenticator.class);
	
	@Override
	public void authenticate(SmtpConnection con, I_SmtpCredentials credentials) throws InvocationException,
			IOException {
		
		String [] ehloTest = con.getEhloResp();
		if (!con.contains(new String[] {"AUTH","PLAIN"}, ehloTest)) {
			throw new InvocationException(THE_SERVER_DOES_NOT_UNDERSTAND_THE_AUTH_PLAIN_COMMAND);
		}
		con.sendCommand("AUTH PLAIN");
		String [] resps = con.getLastResponse();
		
		
		if (!con.contains("334", resps)) {
			throw new InvocationException("AUTH PLAIN did NOT return 334.\n" 
					+ con.toExceptionMessage(resps));
		}
		SmtpUserPassword userPass = (SmtpUserPassword) credentials;
		String user = userPass.getUser();
		String password = userPass.getPassword();
		String enc = Base64.encode("\000" + user + "\000" + password);
		con.sendCommand(enc);
		
		resps = con.getLastResponse();
		if (!con.contains("235", resps)) {
			throw new InvocationException("AUTH PLAIN did NOT return 235.\n"
					+ con.toExceptionMessage(resps));
		}
	}

}
