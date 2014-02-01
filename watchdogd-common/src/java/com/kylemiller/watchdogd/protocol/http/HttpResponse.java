package com.kylemiller.watchdogd.protocol.http;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class HttpResponse
{
    private InputStream inputStream = null;
    private int statusCode;
    private String statusMessage = null;
    //private Cookie[] cookies = null;
    private Map<String, List<String>> headers;
    
    public HttpResponse(int statusCode, String statusMessage, Map<String, List<String>> headers,final InputStream inputStream) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.inputStream = inputStream;
        this.headers = headers;
    }
    
    public boolean hasError() {
    	return statusCode >= 300;
    }
    
    public InputStream getInputStream()
    {
        return this.inputStream;
    }
    public void setInputStream( InputStream inputStream )
    {
        this.inputStream = inputStream;
    }
    public int getStatusCode()
    {
        return this.statusCode;
    }
    public void setStatusCode( int statusCode )
    {
        this.statusCode = statusCode;
    }

    public String getStatusMessage()
    {
        return this.statusMessage;
    }

    public void setStatusMessage( String statusPhrase )
    {
        this.statusMessage = statusPhrase;
    }
    
    public Map<String, List<String>> getHeaders () {
    	return headers;
    }

//	public Header[] getHeaders() {
//		return headers;
//	}
//
//	public void setHeaders(Header[] headers) {
//		this.headers = headers;
//	}
//
//	public Cookie[] getCookies() {
//		return cookies;
//	}
//
//	public void setCookies(Cookie[] cookies) {
//		this.cookies = cookies;
//	}
//    
//    public Cookie findCookie(String name) {
//		for (Cookie cookie : getCookies()) {
//			if (StringUtils.equalsIgnoreCase(name, cookie.getName())) return cookie;
//		}
//		return null;
//	}
//    
//    public Header findHeader(String name) {
//    	for (Header header : getHeaders()) {
//    		if (StringUtils.equalsIgnoreCase(name, header.getName())) return header;
//    	}
//    	return null;
//    }
}
