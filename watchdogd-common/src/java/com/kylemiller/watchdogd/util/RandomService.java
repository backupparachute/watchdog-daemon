package com.kylemiller.watchdogd.util;

import javax.annotation.Resource;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.kylemiller.watchdogd.protocol.http.HttpClientHelper;
import com.kylemiller.watchdogd.protocol.http.StringResultHttpCallback;

@Service
public class RandomService {

	@Resource
	private HttpClientHelper httpClient;
	
	public HttpClientHelper getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(HttpClientHelper httpClient) {
		this.httpClient = httpClient;
	}

	public String generateString() {
		String[] s = generateStrings(1, 16);
		
		return (null != s && s.length > 0) ? s[0] : RandomStringUtils.randomAlphanumeric(16);
		
	}
	
	public String[] generateStrings(Integer num, Integer length) {
		try {
			
			StringResultHttpCallback callback = new StringResultHttpCallback();
			
			getHttpClient().execute("http://www.random.org/strings/?num=%s&len=%s&digits=on&upperalpha=on&loweralpha=on&unique=on&format=plain&rnd=new", callback);
			
			return (null == callback.getResult()) ? new String[] {} : StringUtils.split(callback.getResult());
		} catch (Exception e) {
			String[] sa = new String[num];
			
			for (int i = 0; i < num; i++) sa[i] = RandomStringUtils.randomAlphanumeric(length);
			
			return sa;
		} 
		
	}

}
