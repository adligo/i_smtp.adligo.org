package org.adligo.i.smtp.authenticators;

import java.io.IOException;

import org.adligo.i.adi.client.InvocationException;
import org.adligo.i.log.client.Log;
import org.adligo.i.log.client.LogFactory;
import org.adligo.i.smtp.I_SmtpAuthenticator;
import org.adligo.i.smtp.I_SmtpConnection;
import org.adligo.i.smtp.I_SmtpCredentials;
import org.adligo.i.smtp.SmtpHelper;
import org.adligo.models.params.client.Base64;

public class SmtpLoginAuthenticator implements I_SmtpAuthenticator {
	public static final String PASSWORD_BASE64_DID_NOT_RETURN_235 = "User did not authenticate Password Base64 did NOT return 235.\n";
	public static final String USER_BASE64_DID_NOT_RETURN_334 = "User did not authenticate User Base64 did NOT return 334.\n";
	public static final String AUTH_PLAIN_DID_NOT_RETURN_334 = "User did not authenticate AUTH PLAIN did NOT return 334.\n";
	public static final String THE_SERVER_DOES_NOT_UNDERSTAND_THE_AUTH_LOGIN_COMMAND = "The smtp server does NOT understand the AUTH LOGIN command.";
	
	@Override
	public void authenticate(I_SmtpConnection con, I_SmtpCredentials credentials) throws InvocationException,
			IOException {
		
		String [] ehloTest = con.getEhloResp();
		if (!SmtpHelper.contains(new String[] {"AUTH","LOGIN"}, ehloTest)) {
			throw new InvocationException(THE_SERVER_DOES_NOT_UNDERSTAND_THE_AUTH_LOGIN_COMMAND);
		}
		String [] resps = con.sendCommand("AUTH LOGIN");
		
		if (!SmtpHelper.contains("334", resps)) {
			throw new InvocationException(AUTH_PLAIN_DID_NOT_RETURN_334 
					+ SmtpHelper.toExceptionMessage(resps));
		}
		SmtpUserPassword userPass = (SmtpUserPassword) credentials;
		String user = userPass.getUser();
		String enc = Base64.encode(user);
		resps = con.sendCommand(enc);
		if (!SmtpHelper.contains("334", resps)) {
			throw new InvocationException(USER_BASE64_DID_NOT_RETURN_334
					+ SmtpHelper.toExceptionMessage(resps));
		}
		
		String pass = userPass.getPassword();
		enc = Base64.encode(pass);
		resps = con.sendCommand(enc);
		if (!SmtpHelper.contains("235", resps)) {
			throw new InvocationException(PASSWORD_BASE64_DID_NOT_RETURN_235
					+ SmtpHelper.toExceptionMessage(resps));
		}
	}

}
