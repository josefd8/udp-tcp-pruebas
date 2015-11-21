package com.java.josefd8.misc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class fieldsValidator {
	
	private Pattern pattern;
	private Matcher matcher;
	private String IPADDRESS_PATTERN = 
				"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
				"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
				"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
				"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	
	public fieldsValidator() {
		super();
		pattern = Pattern.compile(IPADDRESS_PATTERN);
	}

	
	public boolean validateIPFormat(String IP){
		 matcher = pattern.matcher(IP);
		 return matcher.matches();	  
	
	}
	
	public boolean validatePort(String port){
		try {
			int p = Integer.valueOf(port);
			
			if ((p < 1) || (p > 65556)) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			return false;
		}

	}
	
}
