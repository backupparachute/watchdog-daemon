package com.kylemiller.watchdogd.protocol.http;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;

import com.kylemiller.watchdogd.model.StatusCode;

public class HttpClientHelper
{
    private Integer timeout = 12000;
    private boolean disableRetry = true;
    
    public void init() throws NoSuchAlgorithmException, KeyManagementException {
    	
    	TrustManager easyTrustManager = new X509TrustManager() {

    	    @Override
    	    public void checkClientTrusted(
    	            X509Certificate[] chain,
    	            String authType) throws CertificateException {
    	        // Oh, I am easy!
    	    }

    	    @Override
    	    public void checkServerTrusted(
    	            X509Certificate[] chain,
    	            String authType) throws CertificateException {
    	        // Oh, I am easy!
    	    }

    	    @Override
    	    public X509Certificate[] getAcceptedIssuers() {
    	        return null;
    	    }
    	    
    	};
    	
    	SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, new TrustManager[] { easyTrustManager }, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory( sc.getSocketFactory() );
		
    	HttpsURLConnection.setFollowRedirects(false);
    	HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
			
			@Override
			public boolean verify(String host, SSLSession session) {
				return true;
			}
		});
    }
    
    public void applyAuthCredentials(DefaultHttpClient httpClient, Credentials credentials) {
    	if (null == credentials) return;
    	
    	httpClient.getCredentialsProvider().setCredentials(AuthScope.ANY, credentials);
//        httpClient.getParams().setAuthenticationPreemptive(true);
//        httpClient.getState().setCredentials(AuthScope.ANY, credentials);
    	

    }
    
    
    public void execute(String url, HttpCallback callback) {
    	
    	URL u = null;
    	HttpResponse response = null;
    	
		try {
			u = new URL(url);
		} catch (MalformedURLException e) {
			 response = new HttpResponse(StatusCode.ERROR.getCode(), "Malformed URL", new HashMap(), new ByteArrayInputStream(e.getMessage().getBytes()));
			 callback.after(response);
			 return;
		}
    	
    	HttpURLConnection http = null;
    	
		try {
			http = (HttpURLConnection) u.openConnection();
		} catch (IOException e) {
			response = new HttpResponse(StatusCode.ERROR.getCode(), "Connection Error", new HashMap(), new ByteArrayInputStream(e.getMessage().getBytes()));
			callback.after(response);
			return;
		} finally {
			disconnect(http);
		}
    	http.setConnectTimeout(getTimeout());
    	http.setReadTimeout(getTimeout());
    	
    	callback.before(http);
    	
    	try {
    		http.connect();
    		callback.work(http);
    		response = new HttpResponse(http.getResponseCode(), http.getResponseMessage(), http.getHeaderFields(), http.getInputStream());
    	} catch (SocketTimeoutException e) {
			response =new HttpResponse(StatusCode.TIMEOUT.getCode(), "Timeout", safeGetResponseHeaders(http), new ByteArrayInputStream(e.getMessage().getBytes()));
		} catch (IOException e) {
			response = new HttpResponse(safeGetResponseCode(http), safeGetResponseMessage(http), safeGetResponseHeaders(http), http.getErrorStream());
		}
		
		callback.after(response);
		disconnect(http);
    }

	protected void disconnect(HttpURLConnection http) {
		if (null != http) http.disconnect();
	}
    
    protected String safeGetResponseMessage(HttpURLConnection http) {
    	try {
    		return http.getResponseMessage();
    	} catch (Exception e) {
    		return "Error Reading Response";
    	}
    }
    
    protected Integer safeGetResponseCode(HttpURLConnection http) {
    	try {
    		return http.getResponseCode();
    	} catch (Exception e) {
    		return StatusCode.ERROR.getCode();
    	}
    }
    
    protected Map safeGetResponseHeaders(HttpURLConnection http) {
    	JSONObject obj = new JSONObject();
    	try {
    		Map map = http.getHeaderFields();
    		if (null != map) {
    			obj.putAll(obj);
    		}
    	} catch (Exception e) {
    	}
    	return obj;
    }
	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}
	
	public boolean isDisableRetry() {
		return disableRetry;
	}

	public void setDisableRetry(boolean disableRetry) {
		this.disableRetry = disableRetry;
	}
}
