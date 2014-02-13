package org.adligo.i.smtp.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.adligo.models.core.shared.EMailAddress;
import org.adligo.models.core.shared.I_Validateable;
import org.adligo.models.core.shared.InvalidParameterException;
import org.adligo.models.core.shared.ValidationException;

public class EMailMessageMutant implements I_EMailMessage, I_EMailMessageMutant {
	public static final String E_MAIL_MESSAGE_MUTANT_REQUIRES_AT_LEAST_ONE_RECIPIENT_IE_TO_CC_OR_BCC_EMAIL_ADDRESS = "EMailMessageMutant requires at least one recipient (ie to, cc, or bcc email address).";
	public static final String E_MAIL_ADDRESS_MUTANT_REQUIRES_NON_NULL_EMAIL_ATTACHMENTS = "EMailAddressMutant requires non null email attachments.";
	public static final String ADD_ATTACHMENT = "addAttachment";
	public static final String E_MAIL_MESSAGE_MUTANT_REQUIRES_A_NON_NULL_BODY = "EMailMessageMutant requires a non null body.";
	public static final String SET_BODY = "setBody";
	public static final String E_MAIL_MESSAGE_MUTANT_REQUIRES_A_NON_NULL_SUBJECT = "EMailMessageMutant requires a non null subject.";
	public static final String SET_SUBJECT = "setSubject";
	public static final String E_MAIL_MESSAGE_MUTANT_REQUIRES_NON_NULL_BCC_EMAIL_ADDRESSES = "EMailMessageMutant requires non null bcc email addresses.";
	public static final String ADD_BCC = "addBcc";
	public static final String E_MAIL_MESSAGE_MUTANT_REQUIRES_NON_NULL_CC_EMAIL_ADDRESSES = "EMailMessageMutant requires non null cc email addresses.";
	public static final String ADD_CC = "addCc";
	public static final String E_MAIL_MESSAGE_MUTANT_REQUIRES_NON_NULL_TO_EMAIL_ADDRESSES = "EMailMessageMutant requires non null to email addresses.";
	public static final String ADD_TO = "addTo";
	public static final String E_MAIL_MESSAGE_MUTANT_REQUIRES_A_NON_NULL_FROM_ADDRESS = "EMailMessageMutant requires a non null from address.";
	public static final String SET_FROM = "setFrom";
	private EMailAddress from;
	/** 
	 * if the email should show the recipients, 
	 * overrides discloseTos, discloseCcs, discloseBccs
	 */
	private boolean discloseRecipients = true;
	private List<EMailAddress> tos = new ArrayList<EMailAddress>();
	private boolean discloseTos = true;
	private List<EMailAddress> ccs = new ArrayList<EMailAddress>();
	private boolean discloseCcs = true;
	private List<EMailAddress> bccs = new ArrayList<EMailAddress>();
	private boolean discloseBccs = true;
	private String subject;
	private String body;
	private List<I_EMailAttachment> attachments = new ArrayList<I_EMailAttachment>();
	private boolean htmlBody = false;
	
	public EMailMessageMutant() {}
	
	public EMailMessageMutant(I_EMailMessage p) throws InvalidParameterException {
		String sub = p.getSubject();
		setSubject(sub);
		String bod = p.getBody();
		setBody(bod);
		EMailAddress frm = p.getFrom();
		setFrom(frm);
		List<EMailAddress> ts = p.getTos();
		setTos(ts);
		List<EMailAddress> cs = p.getCcs();
		setCcs(cs);
		List<EMailAddress> bs = p.getBccs();
		setBccs(bs);
		List<I_EMailAttachment> at = p.getAttachments();
		setAttachments(at);
		setHtmlBody(p.isHtmlBody());
		setDiscloseRecipients(p.isDiscloseRecipients());
		setDiscloseTos(p.isDiscloseTos());
		setDiscloseCcs(p.isDiscloseCcs());
		setDiscloseBccs(p.isDiscloseBccs());
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.i.smtp.models.I_EmailMessage#getFrom()
	 */
	@Override
	public EMailAddress getFrom() {
		return from;
	}
	/* (non-Javadoc)
	 * @see org.adligo.i.smtp.models.I_EMailMessageMutant#setFrom(org.adligo.models.core.client.EMailAddress)
	 */
	@Override
	public void setFrom(EMailAddress p) throws InvalidParameterException {
		if (p != null) {
			from = p;
		} else {
			throw new InvalidParameterException(E_MAIL_MESSAGE_MUTANT_REQUIRES_A_NON_NULL_FROM_ADDRESS, SET_FROM);
		}
	}
	/* (non-Javadoc)
	 * @see org.adligo.i.smtp.models.I_EmailMessage#getTo()
	 */
	@Override
	public List<EMailAddress> getTos() {
		return Collections.unmodifiableList(tos);
	}
	/* (non-Javadoc)
	 * @see org.adligo.i.smtp.models.I_EMailMessageMutant#setTos(java.util.List)
	 */
	@Override
	public void setTos(List<EMailAddress> p) throws InvalidParameterException {
		if (p != null) {
			tos.clear();
			for (EMailAddress e: p) {
				addTo(e);
			} 
		}
	}
	/* (non-Javadoc)
	 * @see org.adligo.i.smtp.models.I_EMailMessageMutant#addTo(org.adligo.models.core.client.EMailAddress)
	 */
	@Override
	public void addTo(EMailAddress p) throws InvalidParameterException {
		if (p != null) {
			tos.add(p);
		} else {
			throw new InvalidParameterException(E_MAIL_MESSAGE_MUTANT_REQUIRES_NON_NULL_TO_EMAIL_ADDRESSES, ADD_TO);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.i.smtp.models.I_EmailMessage#getCc()
	 */
	@Override
	public List<EMailAddress> getCcs() {
		return Collections.unmodifiableList(ccs);
	}
	/* (non-Javadoc)
	 * @see org.adligo.i.smtp.models.I_EMailMessageMutant#setCcs(java.util.List)
	 */
	@Override
	public void setCcs(List<EMailAddress> p) throws InvalidParameterException {
		if (p != null) {
			ccs.clear();
			for (EMailAddress e: p) {
				addCc(e);
			}
		}
	}
	/* (non-Javadoc)
	 * @see org.adligo.i.smtp.models.I_EMailMessageMutant#addCc(org.adligo.models.core.client.EMailAddress)
	 */
	@Override
	public void addCc(EMailAddress p) throws InvalidParameterException {
		if (p != null) {
			ccs.add(p);
		} else {
			throw new InvalidParameterException(E_MAIL_MESSAGE_MUTANT_REQUIRES_NON_NULL_CC_EMAIL_ADDRESSES,ADD_CC); 
		}
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.i.smtp.models.I_EmailMessage#getCc()
	 */
	@Override
	public List<EMailAddress> getBccs() {
		return Collections.unmodifiableList(bccs);
	}
	/* (non-Javadoc)
	 * @see org.adligo.i.smtp.models.I_EMailMessageMutant#setBccs(java.util.List)
	 */
	@Override
	public void setBccs(List<EMailAddress> p) throws InvalidParameterException {
		if (p != null) {
			bccs.clear();
			for (EMailAddress e: p) {
				addBcc(e);
			}
		}
	}
	/* (non-Javadoc)
	 * @see org.adligo.i.smtp.models.I_EMailMessageMutant#addBcc(org.adligo.models.core.client.EMailAddress)
	 */
	@Override
	public void addBcc(EMailAddress p) throws InvalidParameterException {
		if (p != null) {
			bccs.add(p);
		} else {
			throw new InvalidParameterException(E_MAIL_MESSAGE_MUTANT_REQUIRES_NON_NULL_BCC_EMAIL_ADDRESSES,ADD_BCC);
		}
	}
	/* (non-Javadoc)
	 * @see org.adligo.i.smtp.models.I_EmailMessage#getSubject()
	 */
	@Override
	public String getSubject() {
		if (subject == null) {
			return "";
		}
		return subject;
	}
	/* (non-Javadoc)
	 * @see org.adligo.i.smtp.models.I_EMailMessageMutant#setSubject(java.lang.String)
	 */
	@Override
	public void setSubject(String p) throws InvalidParameterException {
		if (p != null) {
			subject = p;
		} else {
			throw new InvalidParameterException(E_MAIL_MESSAGE_MUTANT_REQUIRES_A_NON_NULL_SUBJECT,SET_SUBJECT);
		}
	}
	/* (non-Javadoc)
	 * @see org.adligo.i.smtp.models.I_EmailMessage#getBody()
	 */
	@Override
	public String getBody() {
		if (body == null) {
			return "";
		}
		return body;
	}
	/* (non-Javadoc)
	 * @see org.adligo.i.smtp.models.I_EMailMessageMutant#setBody(java.lang.String)
	 */
	@Override
	public void setBody(String p) throws InvalidParameterException {
		if (p != null) {
			body = p;
		} else {
			throw new InvalidParameterException(E_MAIL_MESSAGE_MUTANT_REQUIRES_A_NON_NULL_BODY,SET_BODY);
		}
	}
	public List<I_EMailAttachment> getAttachments() {
		return Collections.unmodifiableList(attachments);
	}
	/* (non-Javadoc)
	 * @see org.adligo.i.smtp.models.I_EMailMessageMutant#setAttachments(java.util.List)
	 */
	@Override
	public void setAttachments(List<I_EMailAttachment> p) throws InvalidParameterException {
		if (p != null) {
			attachments.clear();
			for (I_EMailAttachment attachement: p) {
				addAttachment(attachement);
			}
		}
	}
	/* (non-Javadoc)
	 * @see org.adligo.i.smtp.models.I_EMailMessageMutant#addAttachment(org.adligo.i.smtp.models.I_EMailAttachment)
	 */
	@Override
	public void addAttachment(I_EMailAttachment p) throws InvalidParameterException {
		if (p != null) {
			attachments.add(p);
		} else {
			throw new InvalidParameterException (E_MAIL_ADDRESS_MUTANT_REQUIRES_NON_NULL_EMAIL_ATTACHMENTS,ADD_ATTACHMENT);
		}
	}
	public boolean isDiscloseRecipients() {
		return discloseRecipients;
	}
	/* (non-Javadoc)
	 * @see org.adligo.i.smtp.models.I_EMailMessageMutant#setDiscloseRecipients(boolean)
	 */
	@Override
	public void setDiscloseRecipients(boolean discloseRecipients) {
		this.discloseRecipients = discloseRecipients;
	}
	public boolean isDiscloseTos() {
		return discloseTos;
	}
	/* (non-Javadoc)
	 * @see org.adligo.i.smtp.models.I_EMailMessageMutant#setDiscloseTos(boolean)
	 */
	@Override
	public void setDiscloseTos(boolean discloseTos) {
		this.discloseTos = discloseTos;
	}
	public boolean isDiscloseCcs() {
		return discloseCcs;
	}
	/* (non-Javadoc)
	 * @see org.adligo.i.smtp.models.I_EMailMessageMutant#setDiscloseCcs(boolean)
	 */
	@Override
	public void setDiscloseCcs(boolean discloseCcs) {
		this.discloseCcs = discloseCcs;
	}
	public boolean isDiscloseBccs() {
		return discloseBccs;
	}
	/* (non-Javadoc)
	 * @see org.adligo.i.smtp.models.I_EMailMessageMutant#setDiscloseBccs(boolean)
	 */
	@Override
	public void setDiscloseBccs(boolean discloseBccs) {
		this.discloseBccs = discloseBccs;
	}
	public boolean isHtmlBody() {
		return htmlBody;
	}
	/* (non-Javadoc)
	 * @see org.adligo.i.smtp.models.I_EMailMessageMutant#setHtmlBody(boolean)
	 */
	@Override
	public void setHtmlBody(boolean htmlBody) {
		this.htmlBody = htmlBody;
	}
	
	public boolean isUsingMimeTypes() {
		if (htmlBody) {
			return true;
		}
		if (attachments.size() >= 1) {
			return true;
		}
		return false;
	}

	@Override
	public void isValid() throws ValidationException {
		try {
			new EMailMessageMutant(this);
			
			if (tos.size() == 0) {
				if (ccs.size() == 0) {
					if (bccs.size() == 0) {
						throw new ValidationException(
									E_MAIL_MESSAGE_MUTANT_REQUIRES_AT_LEAST_ONE_RECIPIENT_IE_TO_CC_OR_BCC_EMAIL_ADDRESS, 
									I_Validateable.IS_VALID);
					}
				}
			}
		} catch (InvalidParameterException ipe) {
			throw new ValidationException(ipe);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attachments == null) ? 0 : attachments.hashCode());
		result = prime * result + ((bccs == null) ? 0 : bccs.hashCode());
		result = prime * result + ((body == null) ? 0 : body.hashCode());
		result = prime * result + ((ccs == null) ? 0 : ccs.hashCode());
		result = prime * result + (discloseBccs ? 1231 : 1237);
		result = prime * result + (discloseCcs ? 1231 : 1237);
		result = prime * result + (discloseRecipients ? 1231 : 1237);
		result = prime * result + (discloseTos ? 1231 : 1237);
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + (htmlBody ? 1231 : 1237);
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		result = prime * result + ((tos == null) ? 0 : tos.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		try {
			I_EMailMessage other = (I_EMailMessage) obj;
			List<I_EMailAttachment> otherAttach = other.getAttachments();
			if (attachments.size() != otherAttach.size()) {
				return false;
			} else {
				if (!attachments.containsAll(otherAttach)) {
					return false;
				}
			}
			
			List<EMailAddress> otherBccs = other.getBccs();
			if (bccs.size() != otherBccs.size()) {
					return false;
			} else {
				if (!bccs.containsAll(otherBccs)) {
					return false;
				}
			}
			
			String otherBody = other.getBody();
			if (!getBody().equals(otherBody)) {
				return false;
			} 
			
			List<EMailAddress> otherCcs = other.getBccs();
			if (ccs.size() != otherCcs.size()) {
					return false;
			} else {
				if (!ccs.containsAll(otherCcs)) {
					return false;
				}
			}
			
			if (discloseBccs != other.isDiscloseBccs())
				return false;
			if (discloseCcs != other.isDiscloseCcs())
				return false;
			if (discloseRecipients != other.isDiscloseRecipients())
				return false;
			if (discloseTos != other.isDiscloseTos())
				return false;
			EMailAddress otherFrom = other.getFrom();
			if (from == null) {
				if (otherFrom != null)
					return false;
			} else if (!from.equals(otherFrom))
				return false;
			
			if (htmlBody != other.isHtmlBody())
				return false;
			
			String otherSubject = other.getSubject();
			if (!getSubject().equals(otherSubject)) {
				return false;
			} 
			
			List<EMailAddress> otherTos = other.getTos();
			if (tos.size() != otherTos.size()) {
					return false;
			} else {
				if (!tos.containsAll(otherTos)) {
					return false;
				}
			}
		} catch (ClassCastException x) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return toString(EMailMessageMutant.class);
	}
	
	public String toString(Class<?> c) {
		return "" + c.getName() + " [from=" + from + ", discloseRecipients="
				+ discloseRecipients + ", tos=" + tos + ", discloseTos="
				+ discloseTos + ", ccs=" + ccs + ", discloseCcs=" + discloseCcs
				+ ", bccs=" + bccs + ", discloseBccs=" + discloseBccs
				+ ", subject=" + subject + ", body=" + body + ", attachments="
				+ attachments + ", htmlBody=" + htmlBody + "]";
	}
}
