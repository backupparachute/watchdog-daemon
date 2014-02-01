package com.kylemiller.watchdogd.web.controller.paypal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Enumeration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kylemiller.watchdogd.protocol.http.HttpCallback;
import com.kylemiller.watchdogd.protocol.http.HttpClientHelper;
import com.kylemiller.watchdogd.protocol.http.HttpResponse;

@Controller
public class PaypalIpnController {

	protected final Logger log = LoggerFactory.getLogger(PaypalIpnController.class);
	@Resource
	private HttpClientHelper httpClient;
	
	public void handle(HttpServletRequest request) throws Exception {
		// read post from PayPal system and add 'cmd'
		Enumeration en = request.getParameterNames();
		String str = "cmd=_notify-validate";
		while(en.hasMoreElements()){
		String paramName = (String)en.nextElement();
		String paramValue = request.getParameter(paramName);
		str = str + "&" + paramName + "=" + URLEncoder.encode(paramValue);
		}

		// post back to PayPal system to validate
		// NOTE: change http: to https: in the following URL to verify using SSL (for increased security).
		// using HTTPS requires either Java 1.4 or greater, or Java Secure Socket Extension (JSSE)
		// and configured for older versions.
		URL u = new URL("https://www.paypal.com/cgi-bin/webscr");
		URLConnection uc = u.openConnection();
		uc.setDoOutput(true);
		uc.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		PrintWriter pw = new PrintWriter(uc.getOutputStream());
		pw.println(str);
		pw.close();

		BufferedReader in = new BufferedReader(
		new InputStreamReader(uc.getInputStream()));
		String res = in.readLine();
		in.close();

		// assign posted variables to local variables
		String itemName = request.getParameter("item_name");
		String itemNumber = request.getParameter("item_number");
		String paymentStatus = request.getParameter("payment_status");
		String paymentAmount = request.getParameter("mc_gross");
		String paymentCurrency = request.getParameter("mc_currency");
		String txnId = request.getParameter("txn_id");
		String receiverEmail = request.getParameter("receiver_email");
		String payerEmail = request.getParameter("payer_email");

		//check notification validation
		if(res.equals("VERIFIED")) {
		// check that paymentStatus=Completed
		// check that txnId has not been previously processed
		// check that receiverEmail is your Primary PayPal email
		// check that paymentAmount/paymentCurrency are correct
		// process payment
		}
		else if(res.equals("INVALID")) {
		// log for investigation
		}
		else {
		// error
		}
	}
	
	@RequestMapping(value="/paypal/ipn",method=RequestMethod.GET)
	public ModelAndView handle() {
		return new ModelAndView("paypal/paypal-ipn");
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/paypal/ipn",method=RequestMethod.POST)
	public void handleIpn(final HttpServletRequest request) throws Exception {
		
		//TODO: (Kyle) re-implment paypal ipn service with httpclient
		log.info("#### PAYPAL: REQUEST RECEIVED");
		String query = request.getQueryString();
//		PostMethod post = new PostMethod("https://www.sandbox.paypal.com/cgi-bin/webscr");
		getHttpClient().execute("https://www.sandbox.paypal.com/cgi-bin/webscr", new HttpCallback() {
			 
			@Override
			public void before(HttpURLConnection conn) {
				
				conn.setDoOutput(true);
				conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
				try {
					conn.setRequestMethod("POST");
				} catch (ProtocolException e) {
					log.warn("#### PAYPAL: couldn't set request method");
				}
				
			}
			
			
			@Override
			public void work(HttpURLConnection conn) {
				try {
					OutputStream out = conn.getOutputStream();
					
					Enumeration en = request.getParameterNames();
					StringBuilder sb = new StringBuilder();
					sb.append("cmd=_notify-validate");
					while(en.hasMoreElements()) {
						String key = en.nextElement().toString();
						sb.append("&").append(key).append("=").append(URLEncoder.encode(request.getParameter(key)));
					}
					
					log.info("#### PAYPAL Validate: "+sb.toString());
					
					PrintWriter pw = new PrintWriter(out);
					pw.println(sb.toString());
					pw.close();
					
					//out.write(sb.toString().getBytes());
				} catch (IOException e) {
					log.error("error writing body", e);
				}
			}
			
			@Override
			public void after(HttpResponse response) {
				
						String body = "";
						try {
							body = IOUtils.toString(response.getInputStream());
						} catch (IOException e) {
						}
				
				log.info("#### PAYPAL RESPONSE: "+body);
				if (StringUtils.equalsIgnoreCase("VERIFIED", body)) {
					log.info("##### PAYPAL: VERIFIED ");
				} else if(StringUtils.equalsIgnoreCase("INVALID", body)) {
					log.warn("##### PAYPAL: INVALID ");
				}
				else {
					log.error("##### PAYPAL: ERROR ");
				}
				
			}
		});
//		post.getParams().setCookiePolicy();
//		post.addParameter("cmd", "notify-validate");
//		for(Enumeration<String> e = request.getParameterNames(); e.hasMoreElements();) {
//			String key = e.nextElement();
//			log.info(String.format("#### PAYPAL: PARAM: KEY: %s, VALUE: %S",key,request.getParameter(key)));
//			post.addParameter(key, request.getParameter(key));
//		}
//		log.info("##### PAYPAL: QUERY: "+query);
//		//PostMethod post = new PostMethod("https://www.paypal.com/cgi-bin/webscr");
//		post.setQueryString(query);
//		HttpResponse response = getHttpClient().executeMethod(post);
//		
//		String body = IOUtils.toString(response.getInputStream());
//		
//		if (StringUtils.equalsIgnoreCase("VERIFIED", body)) {
//			
//			log.info("##### PAYPAL: VERIFIED ");
//		} else if(StringUtils.equalsIgnoreCase("INVALID", body)) {
//			log.warn("##### PAYPAL: INVALID ");
//		}
//		else {
//			log.error("##### PAYPAL: ERROR ");
//		}
		
		log.info("#### PAYPAL: REQUEST FINISHED");
	}


	public HttpClientHelper getHttpClient() {
		return httpClient;
	}


	public void setHttpClient(HttpClientHelper httpClient) {
		this.httpClient = httpClient;
	}
	
}
