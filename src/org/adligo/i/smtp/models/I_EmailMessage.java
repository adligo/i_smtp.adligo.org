package org.adligo.i.smtp.models;

import java.util.List;

import org.adligo.models.core.client.EMailAddress;

public interface I_EmailMessage {

	public abstract EMailAddress getFrom();

	public abstract List<EMailAddress> getTo();

	public abstract List<EMailAddress> getCc();

	public abstract String getSubject();

	public abstract String getBody();

}