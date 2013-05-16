package org.adligo.i.smtp;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.adligo.i.adi.client.InvocationException;
import org.adligo.i.log.client.Log;
import org.adligo.i.log.client.LogFactory;
import org.adligo.i.smtp.models.I_EMailAttachment;
import org.adligo.i.smtp.models.I_EMailMessage;
import org.adligo.models.core.client.EMailAddress;
import org.adligo.models.core.client.ValidationException;
import org.adligo.models.params.client.Base64;

public class SmtpMailer {
	public static final String ATTACHMENT_READ_RETURNED_NULL_FOR_ATTACHMENT = "Attachment.read() returned null for attachment ";
	private static final Log log = LogFactory.getLog(SmtpMailer.class);
	public static final String SMTP_LINEFEED = "\r\n";
	
	protected static void send(I_SmtpConnection con, I_EMailMessage message) throws IOException, InvocationException {
		try {
			message.isValid();
		} catch (ValidationException x) {
			throw new InvocationException(x.getMessage(), x);
		}
				
		con.reconnect();
		
		EMailAddress from = message.getFrom();
		String [] lastResponse = con.sendCommand("MAIL FROM:<" + from.getEMail() + ">");
		if (!SmtpHelper.contains("OK", lastResponse)) {
			throw new IOException("Problem with from address " + from.getEMail());
		}
		List<EMailAddress> tos = message.getTos();
		addRcpt(con, tos);
		List<EMailAddress> ccs = message.getCcs();
		addRcpt(con, ccs);
		List<EMailAddress> bccs = message.getBccs();
		addRcpt(con, bccs);
		
		lastResponse = con.sendCommand("DATA");
		if (!SmtpHelper.contains("354", lastResponse)) {
			throw new IOException("Problem with to data command no 354.");
		}
		StringBuilder sb = new StringBuilder();
		boolean addMime = message.isUsingMimeTypes();
		String boundry = null;
		if (addMime) {
			boundry = con.generateBoundry(16);
			sb.append("MIME-Version: 1.0");
			sb.append(SMTP_LINEFEED);
			sb.append("Content-Type: multipart/mixed; boundary=");
			sb.append(boundry);
			sb.append(SMTP_LINEFEED);
		}
		boolean discloseAny = message.isDiscloseRecipients();
		if (discloseAny) {
			boolean discloseTos = message.isDiscloseTos();
			if (discloseTos) {
				disclose("TO", tos, sb);
			}
			boolean discloseCcs = message.isDiscloseCcs();
			if (discloseCcs) {
				disclose("CC", ccs, sb);
			}
			boolean discloseBccs = message.isDiscloseBccs();
			if (discloseBccs) {
				disclose("BCC", bccs, sb);
			}
		}
		String subject = message.getSubject();
		sb.append("SUBJECT: " + subject);
		sb.append(SMTP_LINEFEED);
		if (addMime) {
			sb.append("--");
			sb.append(boundry);
			sb.append(SMTP_LINEFEED);
			boolean html = message.isHtmlBody();
			if (html) {
				sb.append("Content-Type: text/html; charset=UTF-8");
				sb.append(SMTP_LINEFEED);
				sb.append(SMTP_LINEFEED);
			} else {
				sb.append("Content-Type: text/plain");
				sb.append(SMTP_LINEFEED);
				sb.append(SMTP_LINEFEED);
			}
		}
		String body = message.getBody();
		sb.append(body);
		sb.append(SMTP_LINEFEED);
		
		List<I_EMailAttachment> attachments = message.getAttachments();
		if (attachments.size() >= 1) {
			con.sendCommandPart(sb.toString());
			sb = new StringBuilder();
		}
		for (I_EMailAttachment attachment: attachments) {
			addAttachment(con, boundry, attachment);
		}
		if (addMime) {
			sb.append(SMTP_LINEFEED);
			sb.append("--");
			sb.append(boundry);
			sb.append("--");
			sb.append(SMTP_LINEFEED);
		}
		sb.append(SMTP_LINEFEED);
		sb.append(".");
		sb.append(SMTP_LINEFEED);
		String data = sb.toString();
		lastResponse = con.sendCommand(sb.toString());
		if (!SmtpHelper.contains("250", lastResponse)) {
			throw new IOException("Problem with to data\n" + data);
		}
	}

	public static void addAttachment(I_SmtpConnection con, String boundry,
			I_EMailAttachment attachment) throws IOException, InvocationException {
		StringBuilder sb = new StringBuilder();
		sb.append("--");
		sb.append(boundry);
		sb.append(SMTP_LINEFEED);
		
		String mimeType = attachment.getMimeType();
		sb.append("Content-Type: ");
		sb.append(mimeType);
		sb.append(SMTP_LINEFEED);
		
		String fileName = attachment.getFileName();
		sb.append("Content-Disposition: attachment; filename=");
		sb.append(fileName);
		sb.append(";");
		sb.append(SMTP_LINEFEED);
		
		sb.append("Content-Transfer-Encoding: base64");
		sb.append(SMTP_LINEFEED);
		sb.append(SMTP_LINEFEED);
		
		con.sendCommandPart(sb.toString());
		attachment.gettingData();
		byte [] bytes = new byte[3];
		int counter = 0;
		while (attachment.hasMoreData()) {
			bytes[counter++] = attachment.nextByte();
			
			if (counter == 3) {
				String base64 = Base64.encode(bytes);
				con.sendCommandPart(base64);
				counter = 0;
			}
		}
		if (counter != 3 && counter != 0 ) {
			byte [] lastBytes = new byte[counter];
			for (int i = 0; i < counter; i++) {
				lastBytes[i] = bytes[i];
			}
			String base64 = Base64.encode(bytes);
			con.sendCommandPart(base64);
		}
		attachment.finishedGettingData();
		con.sendCommandPart(SMTP_LINEFEED);
		
	}

	private static void disclose(String header, List<EMailAddress> tos, StringBuilder sb) {
		for (EMailAddress to: tos) {
			sb.append(header);
			sb.append(":<");
			sb.append(to.getEMail());
			sb.append(">");
			sb.append(SMTP_LINEFEED);
		}
	}

	private static void addRcpt(I_SmtpConnection con, List<EMailAddress> tos) throws IOException {
		for (EMailAddress to: tos) {
			String [] lastResponse = con.sendCommand("RCPT TO:<" + to.getEMail() + ">");
			if (!SmtpHelper.contains("OK", lastResponse)) {
				throw new IOException("Problem with to address " + to.getEMail());
			}
		}
	}
}
