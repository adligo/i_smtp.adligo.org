package org.adligo.i.smtp;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.adligo.i.adi.client.InvocationException;
import org.adligo.i.log.client.Log;
import org.adligo.i.log.client.LogFactory;
import org.adligo.i.pool.PooledConnection;
import org.adligo.i.smtp.models.I_EmailMessage;
import org.adligo.models.core.client.EMailAddress;

public class SmtpConnection extends PooledConnection {
	private static final String SMTP_LINEFEED = "\r\n";
	private static final Log log = LogFactory.getLog(SmtpConnection.class);
	private I_SmtpConnectionFactoryConfig config;
	private Socket socket;
	private OutputStream out;
	private InputStream in;
	private PrintWriter printer;
	private boolean ok = false;
	private String [] ehloResp;
	private String[] lastResponse;
	
	protected SmtpConnection(I_SmtpConnectionFactoryConfig config) {
		this.config = config;
	}
	
	public void reconnect() throws IOException {
		if (socket != null) {
			if (!socket.isClosed()) {
				return;
			}
		}
		String host = config.getHost();
		int port = config.getPort();
		socket = new Socket(host, port);
		int maxWait = config.getMaxWait();
		socket.setSoTimeout(maxWait);
		
		out = socket.getOutputStream();
		in = socket.getInputStream();
		printer = new PrintWriter(out);
		if (log.isDebugEnabled()) {
			String [] lines = readResponse();
			for (int i = 0; i < lines.length; i++) {
				log.debug(lines[i]);
			}
		}
		
		sendCommand("EHLO TEST");
		ehloResp = lastResponse;
		
		I_SmtpAuthenticator authenticator = config.getAuthenticator();
		if (authenticator != null) {
			I_SmtpCredentials credentials = config.getCredentials();
			try {
				authenticator.authenticate(this, credentials);
				ok = true;
			} catch (InvocationException x) {
				throw new IOException(x);
			}
		} else {
			ok = true;
		}
	}
	
	@Override
	public boolean isReadWrite() {
		//smtp is for write actions only
		return true;
	}

	@Override
	public boolean isOK() {
		return true;
	}

	@Override
	public void dispose() {
		try {
			sendCommand("QUIT");
		} catch (IOException x) {
			log.error(x.getMessage(), x);
		}
		printer.close();
		try {
			//also closes input and output
			socket.close();
		} catch (IOException x) {
			log.error(x.getMessage(), x);
		}
	}

	public void sendCommand(String command) throws IOException {
		if (log.isDebugEnabled()) {
			log.debug("Sending command;\n" + command);
		}
		printer.write(command);
		printer.write(SMTP_LINEFEED);
		printer.flush();
		lastResponse = readResponse();
	}
	
	private String[] readResponse() throws IOException {
		List<Byte> bytes = new ArrayList<Byte>();
		
		//block and wait for response
		int next = in.read();
		bytes.add(new Byte((byte) next)); 
		while (in.available() > 0) {
			next = in.read();
			bytes.add(new Byte((byte) next)); 
		}
		byte [] bs = new byte[bytes.size()];
		int counter = 0;
		for (Byte b: bytes) {
			bs[counter++] = b;
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(bs);
		
		List<String> lines = new ArrayList<String>();
		InputStreamReader isr = new InputStreamReader(bais);
		BufferedReader inputReader = new BufferedReader(isr);
		while (inputReader.ready()) {
			String line = inputReader.readLine();
			lines.add(line);
		}
		return lines.toArray(new String[lines.size()]);
	}

	public boolean responseContains(String p) throws IOException {
		String [] resps = readResponse();
		return contains(p, resps);
	}

	public boolean contains(String p, String[] resps) {
		p = p.toLowerCase();
		for (int i = 0; i < resps.length; i++) {
			String line = resps[i];
			if (log.isDebugEnabled()) {
				log.debug(line);
			}
			line = line.toLowerCase();
			
			if (line.indexOf(p) != -1) {
				return true;
			}
		}
		return false;
	}

	public boolean contains(String [] p, String[] resps) {
		for (int i = 0; i < p.length; i++) {
			p[i] = p[i].toLowerCase();
		}
		boolean foundAll = false;
		for (int i = 0; i < resps.length; i++) {
			String line = resps[i];
			if (log.isDebugEnabled()) {
				log.debug(line);
			}
			if (!foundAll) {
				line = line.toLowerCase();
				int lastIndex = 0;
				for (int j = 0; j < p.length; j++) {
					String searchText = p[j];
					lastIndex = line.indexOf(searchText, lastIndex);
					if (j + 1 == p.length && lastIndex >= 1) {
						foundAll = true;
					} else if (lastIndex == -1) {
						foundAll = false;
					}
				}
			}
		}
		return foundAll;
	}
	
	public String[] getEhloResp() {
		return ehloResp;
	}

	public String[] getLastResponse() {
		return lastResponse;
	}

	public void setLastResponse(String[] lastResponse) {
		this.lastResponse = lastResponse;
	}
	
	public String toExceptionMessage(String [] p) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < p.length; i++) {
			sb.append(p[i]);
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public void send(I_EmailMessage message) throws IOException {
		reconnect();
		
		EMailAddress from = message.getFrom();
		sendCommand("MAIL FROM:<" + from.getEMail() + ">");
		if (!contains("OK", lastResponse)) {
			throw new IOException("Problem with from address " + from.getEMail());
		}
		List<EMailAddress> tos = message.getTo();
		for (EMailAddress to: tos) {
			sendCommand("RCPT TO:<" + to.getEMail() + ">");
			if (!contains("OK", lastResponse)) {
				throw new IOException("Problem with to address " + to.getEMail());
			}
		}
		sendCommand("DATA");
		if (!contains("354", lastResponse)) {
			throw new IOException("Problem with to data command no 354.");
		}
		StringBuilder sb = new StringBuilder();
		String subject = message.getSubject();
		sb.append("SUBJECT: " + subject);
		sb.append(SMTP_LINEFEED);
		String body = message.getBody();
		sb.append(body);
		sb.append(SMTP_LINEFEED);
		sb.append(".");
		sb.append(SMTP_LINEFEED);
		
		String data = sb.toString();
		sendCommand(sb.toString());
		if (!contains("250", lastResponse)) {
			throw new IOException("Problem with to data\n" + data);
		}
	}
}
