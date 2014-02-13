package org.adligo.i.smtp.authenticators;

import java.io.IOException;

import org.adligo.i.adi.shared.InvocationException;
import org.adligo.i.smtp.I_SmtpAuthenticator;
import org.adligo.i.smtp.I_SmtpConnection;
import org.adligo.i.smtp.I_SmtpCredentials;
import org.adligo.i.smtp.SmtpHelper;
import org.adligo.models.params.shared.Base64;

public class SmtpPlainAuthenticator implements I_SmtpAuthenticator {
	public static final String ERROR_WITH_AUTH_PLAIN = "The User did not authenticate the AUTH PLAIN did NOT return 334.\n";
	public static final String THE_USER_DID_NOT_AUTHENTICATE = "The User did not authenticate the user password base 64 did NOT return 334.\n";
	public static final String THE_SERVER_DOES_NOT_UNDERSTAND_THE_AUTH_PLAIN_COMMAND = "The server does NOT understand the AUTH PLAIN command.";
	
	@Override
	public void authenticate(I_SmtpConnection con, I_SmtpCredentials credentials) throws InvocationException,
			IOException {
		
		String [] ehloTest = con.getEhloResp();
		if (!SmtpHelper.contains(new String[] {"AUTH","PLAIN"}, ehloTest)) {
			throw new InvocationException(THE_SERVER_DOES_NOT_UNDERSTAND_THE_AUTH_PLAIN_COMMAND);
		}
		String [] resps = con.sendCommand("AUTH PLAIN");
		
		
		if (!SmtpHelper.contains("334", resps)) {
			throw new InvocationException(ERROR_WITH_AUTH_PLAIN 
					+ SmtpHelper.toExceptionMessage(resps));
		}
		SmtpUserPassword userPass = (SmtpUserPassword) credentials;
		String user = userPass.getUser();
		String password = userPass.getPassword();
		String enc = Base64.encode("\000" + user + "\000" + password);
		resps = con.sendCommand(enc);
		
		if (!SmtpHelper.contains("235", resps)) {
			throw new InvocationException(THE_USER_DID_NOT_AUTHENTICATE
					+ SmtpHelper.toExceptionMessage(resps));
		}
	}

}
