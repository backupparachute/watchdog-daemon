package com.kylemiller.watchdogd.protocol.http;

import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.hamcrest.core.Is;
import org.junit.Test;


public class HttpClientHelper_UT {
	
	@Test
	public void testConnect() throws KeyManagementException, NoSuchAlgorithmException, IOException {
		HttpClientHelper http = new HttpClientHelper();
		http.init();
		
		http.execute("http://localhost:8080/test/response-gen/200", new HttpCallback() {
			
			@Override
			public void after(HttpResponse response) {
				assertThat(response.getStatusCode(), Is.is(200));
				print(response);
				
			}
		});
		
		http.execute("http://localhost:8080/test/response-gen/301", new HttpCallback() {
			
			@Override
			public void after(HttpResponse response) {
				assertThat(response.getStatusCode(), Is.is(301));
				print(response);
				
			}
		});
		
		 http.execute("http://localhost:8080/test/response-gen/302", new HttpCallback() {
			
			@Override
			public void after(HttpResponse response) {
				assertThat(response.getStatusCode(), Is.is(302));
				print(response);
			}
		});
		
		 http.execute("http://localhost:8080/test/response-gen/400", new HttpCallback() {
			
			@Override
			public void after(HttpResponse response) {
				assertThat(response.getStatusCode(), Is.is(400));
				print(response);
			}
		});
		
		 http.execute("http://localhost:8080/test/response-gen/404", new HttpCallback() {
			
			@Override
			public void after(HttpResponse response) {
				assertThat(response.getStatusCode(), Is.is(404));
				print(response);
			}
		});
		
		 http.execute("http://localhost:8080/test/response-gen/403", new HttpCallback() {
			
			@Override
			public void after(HttpResponse response) {
				assertThat(response.getStatusCode(), Is.is(403));
				print(response);
			}
		});
		
		 http.execute("http://localhost:8080/test/response-gen/500", new HttpCallback() {
			
			@Override
			public void after(HttpResponse response) {
				assertThat(response.getStatusCode(), Is.is(500));
				print(response);
			}
		});
		
	}
	
	protected void print(HttpResponse response) {
		try {
			System.out.printf("code=%s; msg=%s; body=%s; headers=%s\n",response.getStatusCode(), response.getStatusMessage(),IOUtils.toString(response.getInputStream()), response.getHeaders());
		} catch (IOException e) {
		}
	}
		
	@Test
	public void testTimeout() throws KeyManagementException, NoSuchAlgorithmException, IOException {
		HttpClientHelper http = new HttpClientHelper();
		http.setTimeout(1000);
		http.init();
		
		http.execute("http://localhost:8080/test/wait/1100", new HttpCallback() {
			
			@Override
			public void after(HttpResponse response) {
				print(response);
				assertThat(response.getStatusCode(), Is.is(601));
			}
		});
	}

}
