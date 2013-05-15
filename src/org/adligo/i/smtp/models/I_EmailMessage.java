package org.adligo.i.smtp.models;

import java.util.List;

import org.adligo.models.core.client.EMailAddress;

public interface I_EmailMessage {

	public abstract EMailAddress getFrom();

	public abstract List<EMailAddress> getTo();

	public abstract List<EMailAddress> getCc();

	public abstract List<EMailAddress> getBcc();
	
	public abstract String getSubject();

	public abstract String getBody();

	public boolean isDiscloseRecipients();
	public boolean isDiscloseTos();
	public boolean isDiscloseCcs();
	public boolean isDiscloseBccs();
	public boolean isHtmlBody();
	public boolean isHtmlBodyOrHasAttachments();
	public List<I_EMailAttachment> getAttachments();
}