package org.adligo.i.smtp.models;

import java.util.ArrayList;
import java.util.List;

import org.adligo.models.core.client.EMailAddress;

public class EMailMessageMutant implements I_EmailMessage {
	private EMailAddress from;
	private List<EMailAddress> to = new ArrayList<EMailAddress>();
	private List<EMailAddress> cc = new ArrayList<EMailAddress>();
	private String subject;
	private String body;
	private List<I_InputStreamFactory> attachments = new ArrayList<I_InputStreamFactory>();
	
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
	public List<I_InputStreamFactory> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<I_InputStreamFactory> p) {
		attachments.clear();
		attachments.addAll(p);
	}
	public void addAttachment(I_InputStreamFactory p) {
		attachments.add(p);
	}
}
