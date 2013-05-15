package org.adligo.i.smtp;

import org.adligo.i.log.client.Log;
import org.adligo.i.log.client.LogFactory;

public class SmtpHelper {
	private static final Log log = LogFactory.getLog(SmtpHelper.class);

	public static String toExceptionMessage(String [] p) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < p.length; i++) {
			sb.append(p[i]);
			sb.append("\n");
		}
		return sb.toString();
	}


	public static boolean contains(String p, String[] resps) {
		p = p.toLowerCase();
		for (int i = 0; i < resps.length; i++) {
			String line = resps[i];
			if (log.isDebugEnabled()) {
				log.debug(line);
			}
			line = line.toLowerCase();
			
			if (line.indexOf(p) != -1) {
				return true;
			}
		}
		return false;
	}

	public static boolean contains(String [] p, String[] resps) {
		for (int i = 0; i < p.length; i++) {
			p[i] = p[i].toLowerCase();
		}
		boolean foundAll = false;
		for (int i = 0; i < resps.length; i++) {
			String line = resps[i];
			if (log.isDebugEnabled()) {
				log.debug(line);
			}
			if (!foundAll) {
				line = line.toLowerCase();
				int lastIndex = 0;
				for (int j = 0; j < p.length; j++) {
					String searchText = p[j];
					lastIndex = line.indexOf(searchText, lastIndex);
					if (j + 1 == p.length && lastIndex >= 1) {
						foundAll = true;
					} else if (lastIndex == -1) {
						foundAll = false;
					}
				}
			}
		}
		return foundAll;
	}
}
