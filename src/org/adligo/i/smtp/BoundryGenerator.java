package org.adligo.i.smtp;

public class BoundryGenerator {
	private static final String CHARS = 
			new String("qwertyuiopasdfghjklzxcvbnmQWERTYUIOPLKJHGFDSAZXCVBNM1234567890");
	
	public static String gen(int lenght) {
		
		double charLen = CHARS.length();
		double weight = charLen / 100.00;
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < lenght; i++) {
			double d = Math.random();
			d = d * 100;
			d = d * weight;
			int j = (int) d;
			char c = CHARS.charAt(j);
			sb.append(c);
		}
		return sb.toString();
	}
}
