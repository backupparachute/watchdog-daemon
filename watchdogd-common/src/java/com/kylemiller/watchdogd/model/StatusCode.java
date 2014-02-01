package com.kylemiller.watchdogd.model;

public enum StatusCode {

	OK(200, "OK")
	,FORBIDDEN(403, "Forbidden, authentication failed")
	,NOT_FOUND(404, "Not Found")
	,ERROR(600, "Error")
	,TIMEOUT(601, "Network Timeout")//should maybe be a 599
	,NOT_UPDATED_TIMELY(602, "Site not updated in a timely fashion")
	,IMPROPER_SETUP(604, "Improper Setup")
	,KEYWORD_NOT_FOUND(605, "Keyword not found")
	,COULD_NOT_CONNECT(610, "Could not connect")
	;
	
	private final Integer code;
	private final String message;
	
	private StatusCode(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public Integer getCode() {
		return this.code;
	}
	
	public String getMessage() {
		return this.message;
	}
	
}
