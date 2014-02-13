package org.adligo.i.smtp.models;

import java.util.List;

import org.adligo.models.core.shared.EMailAddress;
import org.adligo.models.core.shared.InvalidParameterException;

public interface I_EMailMessageMutant extends I_EMailMessage {

	public abstract void setFrom(EMailAddress p)
			throws InvalidParameterException;

	public abstract void setTos(List<EMailAddress> p)
			throws InvalidParameterException;

	public abstract void addTo(EMailAddress p) throws InvalidParameterException;

	public abstract void setCcs(List<EMailAddress> p)
			throws InvalidParameterException;

	public abstract void addCc(EMailAddress p) throws InvalidParameterException;

	public abstract void setBccs(List<EMailAddress> p)
			throws InvalidParameterException;

	public abstract void addBcc(EMailAddress p)
			throws InvalidParameterException;

	public abstract void setSubject(String p) throws InvalidParameterException;

	public abstract void setBody(String p) throws InvalidParameterException;

	public abstract void setAttachments(List<I_EMailAttachment> p)
			throws InvalidParameterException;

	public abstract void addAttachment(I_EMailAttachment p)
			throws InvalidParameterException;

	public abstract void setDiscloseRecipients(boolean discloseRecipients);

	public abstract void setDiscloseTos(boolean discloseTos);

	public abstract void setDiscloseCcs(boolean discloseCcs);

	public abstract void setDiscloseBccs(boolean discloseBccs);

	public abstract void setHtmlBody(boolean htmlBody);

}