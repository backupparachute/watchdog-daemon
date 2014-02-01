package com.kylemiller.watchdogd.protocol.http;

import java.net.HttpURLConnection;

public abstract class HttpCallback {

	public void before(HttpURLConnection conn) {
		
	}
	
	public abstract void after (HttpResponse response);

	public void work(HttpURLConnection http) {
		
	}
	
}
