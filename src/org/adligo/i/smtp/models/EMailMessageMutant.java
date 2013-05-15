package org.adligo.i.smtp.models;

import java.util.ArrayList;
import java.util.List;

import org.adligo.models.core.client.EMailAddress;

public class EMailMessageMutant implements I_EmailMessage {
	private EMailAddress from;
	/** 
	 * if the email should show the recipients, 
	 * overrides discloseTos, discloseCcs, discloseBccs
	 */
	private boolean discloseRecipients = true;
	private List<EMailAddress> to = new ArrayList<EMailAddress>();
	private boolean discloseTos = true;
	private List<EMailAddress> cc = new ArrayList<EMailAddress>();
	private boolean discloseCcs = true;
	private List<EMailAddress> bcc = new ArrayList<EMailAddress>();
	private boolean discloseBccs = true;
	private String subject;
	private String body;
	private List<I_EMailAttachment> attachments = new ArrayList<I_EMailAttachment>();
	private boolean htmlBody = false;
	
	/* (non-Javadoc)
	 * @see org.adligo.i.smtp.models.I_EmailMessage#getFrom()
	 */
	@Override
	public EMailAddress getFrom() {
		return from;
	}
	public void setFrom(EMailAddress from) {
		this.from = from;
	}
	/* (non-Javadoc)
	 * @see org.adligo.i.smtp.models.I_EmailMessage#getTo()
	 */
	@Override
	public List<EMailAddress> getTo() {
		return to;
	}
	public void setTo(List<EMailAddress> p) {
		to.clear();
		to.addAll(p);
	}
	public void addTo(EMailAddress p) {
		to.add(p);
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.i.smtp.models.I_EmailMessage#getCc()
	 */
	@Override
	public List<EMailAddress> getCc() {
		return cc;
	}
	public void setCc(List<EMailAddress> p) {
		cc.clear();
		cc.addAll(p);
	}
	public void addCc(EMailAddress p) {
		cc.add(p);
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.i.smtp.models.I_EmailMessage#getCc()
	 */
	@Override
	public List<EMailAddress> getBcc() {
		return bcc;
	}
	public void setBcc(List<EMailAddress> p) {
		bcc.clear();
		bcc.addAll(p);
	}
	public void addBcc(EMailAddress p) {
		bcc.add(p);
	}
	/* (non-Javadoc)
	 * @see org.adligo.i.smtp.models.I_EmailMessage#getSubject()
	 */
	@Override
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/* (non-Javadoc)
	 * @see org.adligo.i.smtp.models.I_EmailMessage#getBody()
	 */
	@Override
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public List<I_EMailAttachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<I_EMailAttachment> p) {
		attachments.clear();
		attachments.addAll(p);
	}
	public void addAttachment(I_EMailAttachment p) {
		attachments.add(p);
	}
	public boolean isDiscloseRecipients() {
		return discloseRecipients;
	}
	public void setDiscloseRecipients(boolean discloseRecipients) {
		this.discloseRecipients = discloseRecipients;
	}
	public boolean isDiscloseTos() {
		return discloseTos;
	}
	public void setDiscloseTos(boolean discloseTos) {
		this.discloseTos = discloseTos;
	}
	public boolean isDiscloseCcs() {
		return discloseCcs;
	}
	public void setDiscloseCcs(boolean discloseCcs) {
		this.discloseCcs = discloseCcs;
	}
	public boolean isDiscloseBccs() {
		return discloseBccs;
	}
	public void setDiscloseBccs(boolean discloseBccs) {
		this.discloseBccs = discloseBccs;
	}
	public boolean isHtmlBody() {
		return htmlBody;
	}
	public void setHtmlBody(boolean htmlBody) {
		this.htmlBody = htmlBody;
	}
	
	public boolean isHtmlBodyOrHasAttachments() {
		if (htmlBody) {
			return true;
		}
		if (attachments.size() >= 1) {
			return true;
		}
		return false;
	}
}
