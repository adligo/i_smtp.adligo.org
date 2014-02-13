package org.adligo.i.smtp.models;

import java.util.List;

import org.adligo.i.util.shared.I_Immutable;
import org.adligo.models.core.shared.EMailAddress;
import org.adligo.models.core.shared.InvalidParameterException;
import org.adligo.models.core.shared.ValidationException;

public class EMailMessage implements I_EMailMessage, I_Immutable {
	private EMailMessageMutant mutant;
	
	public EMailMessage(I_EMailMessage p) throws InvalidParameterException {
		mutant = new EMailMessageMutant(p);
	}

	public EMailAddress getFrom() {
		return mutant.getFrom();
	}

	public List<EMailAddress> getTos() {
		return mutant.getTos();
	}

	public List<EMailAddress> getCcs() {
		return mutant.getCcs();
	}

	public List<EMailAddress> getBccs() {
		return mutant.getBccs();
	}

	public String getSubject() {
		return mutant.getSubject();
	}

	public String getBody() {
		return mutant.getBody();
	}

	public List<I_EMailAttachment> getAttachments() {
		return mutant.getAttachments();
	}

	public boolean isDiscloseRecipients() {
		return mutant.isDiscloseRecipients();
	}

	public boolean isDiscloseTos() {
		return mutant.isDiscloseTos();
	}

	public boolean isDiscloseCcs() {
		return mutant.isDiscloseCcs();
	}

	public boolean isDiscloseBccs() {
		return mutant.isDiscloseBccs();
	}

	public boolean isHtmlBody() {
		return mutant.isHtmlBody();
	}

	public boolean isUsingMimeTypes() {
		return mutant.isUsingMimeTypes();
	}

	public void isValid() throws ValidationException {
		mutant.isValid();
	}

	@Override
	public String getImmutableFieldName() {
		return "mutant";
	}
}
