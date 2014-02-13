package org.adligo.i.smtp.models;

import java.util.List;

import org.adligo.models.core.shared.EMailAddress;
import org.adligo.models.core.shared.I_Validateable;

public interface I_EMailMessage extends I_Validateable {

	public abstract EMailAddress getFrom();

	public abstract List<EMailAddress> getTos();

	public abstract List<EMailAddress> getCcs();

	public abstract List<EMailAddress> getBccs();
	
	public abstract String getSubject();

	public abstract String getBody();

	public boolean isDiscloseRecipients();
	public boolean isDiscloseTos();
	public boolean isDiscloseCcs();
	public boolean isDiscloseBccs();
	public boolean isHtmlBody();
	public boolean isUsingMimeTypes();
	public List<I_EMailAttachment> getAttachments();
}