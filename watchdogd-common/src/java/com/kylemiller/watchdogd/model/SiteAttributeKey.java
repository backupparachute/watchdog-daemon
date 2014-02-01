package com.kylemiller.watchdogd.model;

public enum SiteAttributeKey {
	
	KEYWORD("Keyword Search","regex", "", false)
	,HOST_KEY("Host Key","string","", true)
	,USERNAME("Username","string","", true)
	,PASSWORD("Password","string","", true)
	,FTP_USERNAME("Username","string","leave username and password blank for anonymous", false)
	,FTP_PASSWORD("Password","string","", false)
	,DEBUG("debug","string","", false)
	,IP_ADDRESS("IP Address", "string","", true)
	;
	
	private final String label;
	private final String type;
	private final String note;
	private final boolean mandatory;
	
	private SiteAttributeKey(String label, String type, String note, boolean mandatory) {
		this.label = label;
		this.type = type;
		this.note = note;
		this.mandatory = mandatory;
	}
	
	public String getLabel() {
		return label;
	}
	
	public String getType() {
		return type;
	}
	
	public boolean isMandatory() {
		return mandatory;
	}
	
	public String getNote() {
		return note;
	}

}
