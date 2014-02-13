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

import org.adligo.i.adi.shared.InvocationException;
import org.adligo.i.log.shared.Log;
import org.adligo.i.log.shared.LogFactory;
import org.adligo.i.pool.PooledConnection;
import org.adligo.i.smtp.models.I_EMailMessage;

public class SmtpConnection extends PooledConnection implements I_SmtpConnection {
	public static final String SMTP_LINEFEED = "\r\n";
	private static final Log log = LogFactory.getLog(SmtpConnection.class);
	private I_SmtpConnectionFactoryConfig config;
	private Socket socket;
	private OutputStream out;
	private InputStream in;
	private PrintWriter printer;
	private boolean ok = false;
	private String [] ehloResp;
	
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
		try {
			socket = new Socket(host, port);
		} catch (IOException x) {
			throw new IOException("Problem connecting to " + host + ":"  + port, x);
		}
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
		
		ehloResp = sendCommand("EHLO TEST");
		
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

	/* (non-Javadoc)
	 * @see org.adligo.i.smtp.I_SmtpConnection#sendCommandPart(java.lang.String)
	 */
	@Override
	public void sendCommandPart(String command) throws IOException {
		if (log.isDebugEnabled()) {
			log.debug("Sending command part;\n" + command);
		}
		printer.write(command);
		printer.flush();
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.i.smtp.I_SmtpConnection#sendCommand(java.lang.String)
	 */
	@Override
	public String[] sendCommand(String command) throws IOException {
		if (log.isDebugEnabled()) {
			log.debug("Sending command;\n" + command);
		}
		printer.write(command);
		printer.write(SMTP_LINEFEED);
		printer.flush();
		return readResponse();
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

	
	/* (non-Javadoc)
	 * @see org.adligo.i.smtp.I_SmtpConnection#getEhloResp()
	 */
	@Override
	public String[] getEhloResp() {
		return ehloResp;
	}

	@Override
	public void send(I_EMailMessage message) throws IOException, InvocationException {
		SmtpMailer.send(this,  message);
	}
	
	public String generateBoundry(int lenght) {
		return BoundryGenerator.gen(lenght);
	}
}
