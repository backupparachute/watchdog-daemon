package com.kylemiller.watchdogd.protocol.http;

import java.io.IOException;

import org.apache.commons.io.IOUtils;

public class StringResultHttpCallback extends HttpCallback {
	
	private String result;

	@Override
	public void after(HttpResponse response) {
		result = handle(response);
	}
	
	public String handle(HttpResponse response) {
		try {
			return IOUtils.toString(response.getInputStream());
		} catch (IOException e) {
			return null;
		}
	}
	
	protected void setResult(String s) {
		this.result = s;
	}

	public String getResult() {
		return result;
	}

}
