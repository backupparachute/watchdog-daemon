import org.apache.commons.lang.StringUtils;
import org.junit.Test;



public class KeywordTest {

	@Test
	public void testKeyword() {
		
		String data = "\n" + 
				" \n" + 
				"\n" + 
				"\n" + 
				"\n" + 
				"\n" + 
				"\n" + 
				"\n" + 
				"<html>\n" + 
				"    <head>\n" + 
				"        <title>AutoTransMart :: Admin - Home</title>\n" + 
				"        \n" + 
				"        <meta HTTP-EQUIV=\"Pragma\" content=\"no-cache\"></meta>\n" + 
				"        <meta HTTP-EQUIV=\"Expires\" content=\"-1\"></meta>\n" + 
				"        <meta HTTP-EQUIV=\"Cache-Control\" content=\"private,no-cache,no-store\"></meta>\n" + 
				"		<link rel=\"StyleSheet\" media=\"screen\" href=\"/css/style.css\" type=\"text/css\" />\n" + 
				"		<link rel=\"StyleSheet\" media=\"screen\" href=\"/css/tabs.css\" type=\"text/css\" />\n" + 
				"		<link rel=\"StyleSheet\" media=\"screen\" href=\"/css/form.css\" type=\"text/css\" />\n" + 
				"		<link rel=\"StyleSheet\" media=\"print\" href=\"/css/print.css\" type=\"text/css\" />\n" + 
				"		<link rel=\"icon\" href=\"/images/favicon.ico\" type=\"image/x-icon\" />\n" + 
				"        <link rel=\"shortcut icon\" href=\"/images/favicon.ico\" type=\"image/x-icon\" />\n" + 
				"        <script src=\"/scripts/prototype-1.6.0.3.js\"></script>\n" + 
				"        \n" + 
				"		\n" + 
				"		<style>\n" + 
				"        	div#admin-home{\n" + 
				"   	     			width: 150px;\n" + 
				"   	     		}\n" + 
				"			div#current-users {\n" + 
				"				background: #eee;\n" + 
				"				border: 1px solid #ccc;\n" + 
				"				padding: 5px;\n" + 
				"				width: 130px;\n" + 
				"				position: absolute;\n" + 
				"				top: 150px;\n" + 
				"				right: 10px;\n" + 
				"			}\n" + 
				"			div#newLeads{\n" + 
				"				width: 800px;\n" + 
				"			}\n" + 
				"        </style>\n" + 
				"	\n" + 
				"    </head>\n" + 
				"    <body class=\"home\">\n" + 
				"		<div id=\"\" class=\"wrapper\">\n" + 
				"			\n" + 
				"			\n" + 
				"			\n" + 
				"				<form action=\"/app/secure/search\">\n" + 
				"					<div id=\"search\" class=\"fancyForm\">\n" + 
				"						<script>\n" + 
				"							function removeText(input) {\n" + 
				"								if (input.value='Search') {\n" + 
				"									input.value='';\n" + 
				"									input.style.color='#000';\n" + 
				"								}\n" + 
				"							}\n" + 
				"						</script>\n" + 
				"						<fieldset>\n" + 
				"							<legend>Search</legend>\n" + 
				"							<ol>\n" + 
				"								<li><input class=\"text\" type=\"text\" name=\"_searchTerm\" id=\"_searchTerm\" value=\"Search\" onfocus=\"removeText(this);\"/>&nbsp;<input type=\"submit\" value=\"Search\" /><input type=\"hidden\" name=\"_searchBox\" id=\"_searchBox\" value=\"_searchBox\"/></li>\n" + 
				"							</ol>\n" + 
				"						</fieldset>\n" + 
				"					</div>\n" + 
				"				</form>\n" + 
				"			\n" + 
				"			<div id=\"logo\">&nbsp;</div>\n" + 
				"			<div id=\"feedback\">\n" + 
				"				<a href=\"/app/secure/supportemail\">\n" + 
				"				</a>\n" + 
				"			</div>\n" + 
				"			<div id=\"server-time\">\n" + 
				"				\n" + 
				"			</div>\n" + 
				"			<div id=\"menu\">\n" + 
				"				<ul>\n" + 
				"					<li id=\"home\"><a href=\"/app/home\">Home</a></li>\n" + 
				"					\n" + 
				"						<li id=\"loads\"><a href=\"/app/admin/secure/loads\">Loads</a></li>\n" + 
				"					\n" + 
				"					\n" + 
				"						<li id=\"customer-leads\"><a href=\"/app/admin/secure/leads\">Leads</a></li>\n" + 
				"					\n" + 
				"					\n" + 
				"						<li id=\"carriers\"><a href=\"/app/admin/secure/carriers\">Carriers</a></li>\n" + 
				"					\n" + 
				"					\n" + 
				"						<li id=\"customers\"><a href=\"/app/admin/secure/customers\">Customers</a></li>\n" + 
				"					\n" + 
				"					\n" + 
				"						<li id=\"adminMaintain\"><a href=\"/app/admin/secure/users\">Users</a></li>\n" + 
				"					\n" + 
				"					\n" + 
				"						<li id=\"reports\"><a href=\"/app/secure/reports\">Reports</a></li>\n" + 
				"					\n" + 
				"					\n" + 
				"					\n" + 
				"					\n" + 
				"					\n" + 
				"					<li id=\"maintain\"><a href=\"/app/secure/account\">My Account</a></li>\n" + 
				"					<li id=\"Logout\"><a href=\"/logout\">Logout</a></li>\n" + 
				"					\n" + 
				"				</ul>\n" + 
				"			</div>\n" + 
				"			\n" + 
				"\n" + 
				"			<div id=\"user\">\n" + 
				"				Welcome <a href=\"/app/secure/account\">Kyle</a> <span class=\"seperator\"> | </span> <a href=\"/app/secure/notifications\"><span>Inbox </span><img style=\"\" src=\"/images/mail.png\" width=\"16px\" height=\"16px\"></a>\n" + 
				"			</div>\n" + 
				"		\n" + 
				"			<div id=\"print-title\">\n" + 
				"				AutoTransMart :: Admin - Home\n" + 
				"			</div>\n" + 
				"			\n" + 
				"			<div id=\"content\">\n" + 
				"				\n" + 
				"		\n" + 
				"\n" + 
				"\n" + 
				"\n" + 
				"\n" + 
				"\n" + 
				"\n" + 
				"\n" + 
				"\n" + 
				"\n" + 
				"		\n" + 
				"\n" + 
				"\n" + 
				"\n" + 
				"\n" + 
				"\n" + 
				"\n" + 
				"\n" + 
				"\n" + 
				"\n" + 
				"		<div id=\"admin-home\" class=\"fancyForm\">\n" + 
				"			<fieldset>\n" + 
				"				<legend>Admin Home</legend>\n" + 
				"				<ol>\n" + 
				"					\n" + 
				"						<li id=\"admin-reg\"><a href=\"/app/admin/secure/signup/admin\">Admin Signup</a></li>\n" + 
				"					\n" + 
				"					\n" + 
				"						<li id=\"\"><a href=\"/app/admin/secure/users/current\">Current Users</a></li>\n" + 
				"					\n" + 
				"					\n" + 
				"						<li><a href=\"/app/admin/secure/quote\">Quote</a></li>\n" + 
				"					\n" + 
				"					\n" + 
				"						<li><a href=\"/app/admin/secure/signup/shipper\">Customer Sign-Up</a></li>\n" + 
				"					\n" + 
				"					\n" + 
				"						<li><a href=\"/app/admin/secure/rates\">Rates</a></li>\n" + 
				"					\n" + 
				"					\n" + 
				"						<li><a href=\"/app/admin/secure/cleanup\">Cleanup DB</a></li>\n" + 
				"					\n" + 
				"					\n" + 
				"						<li><a href=\"/app/admin/secure/sudo\">Login As User</a></li>\n" + 
				"					\n" + 
				"					\n" + 
				"						<li><a href=\"/app/admin/secure/billing/carrier/payments\">Carrier Payments</a></li>\n" + 
				"					\n" + 
				"					\n" + 
				"						<li><a href=\"/app/admin/secure/shipper/payments\">Shipper Payments</a></li>\n" + 
				"					\n" + 
				"					\n" + 
				"						<li><a href=\"/app/admin/secure/vins\">Decode Vins</a></li>\n" + 
				"					\n" + 
				"					\n" + 
				"						<li><a href=\"/app/admin/secure/audits\">Audit Logs</a></li>\n" + 
				"					\n" + 
				"					\n" + 
				"						<li><a href=\"/app/admin/secure/faqs\">Faq Administration</a></li>\n" + 
				"					\n" + 
				"					\n" + 
				"						<li><a href=\"/app/admin/secure/loads/active\">Active Loads</a></li>\n" + 
				"					\n" + 
				"					\n" + 
				"						<li><a href=\"/app/admin/secure/notification\">Send Notification</a></li>\n" + 
				"					\n" + 
				"					\n" + 
				"						<li><a href=\"/app/admin/secure/reports/maintain\">Add/Edit Reports</a></li>\n" + 
				"					\n" + 
				"					\n" + 
				"						<li><a href=\"/app/admin/secure/tags\">Tags</a></li>\n" + 
				"					\n" + 
				"					\n" + 
				"						<li><a href=\"/app/admin/secure/groups\">Groups</a></li>\n" + 
				"					\n" + 
				"					\n" + 
				"						<li><a href=\"/app/admin/secure/merge/prime\">Merge Customers</a></li>\n" + 
				"					\n" + 
				"				</ol>\n" + 
				"			</fieldset>\n" + 
				"		</div>\n" + 
				"		\n" + 
				"			<div id=\"newLeads\" class=\"fancyForm\">\n" + 
				"				<fieldset>\n" + 
				"					<legend>New Leads</legend>\n" + 
				"					<ol>\n" + 
				"						\n" + 
				"<table id=\"lead\">\n" + 
				"<thead>\n" + 
				"<tr>\n" + 
				"<th>Lead Number</th>\n" + 
				"<th>Contact Name</th>\n" + 
				"<th>Company Name</th>\n" + 
				"<th>Contact E-Mail</th>\n" + 
				"<th>Category</th>\n" + 
				"<th>Status</th></tr></thead>\n" + 
				"<tbody>\n" + 
				"<tr class=\"odd\">\n" + 
				"<td>\n" + 
				"								<a href=\"/app/admin/secure/customerLead/1935\">1935</a>\n" + 
				"							</td>\n" + 
				"<td>\n" + 
				"								Michael Downey\n" + 
				"							</td>\n" + 
				"<td>\n" + 
				"								CarBiz Auto Credit\n" + 
				"							</td>\n" + 
				"<td>\n" + 
				"								mdowney@hotmail.com\n" + 
				"							</td>\n" + 
				"<td>\n" + 
				"								\n" + 
				"							</td>\n" + 
				"<td></td></tr>\n" + 
				"<tr class=\"even\">\n" + 
				"<td>\n" + 
				"								<a href=\"/app/admin/secure/customerLead/1934\">1934</a>\n" + 
				"							</td>\n" + 
				"<td>\n" + 
				"								Michael Ricciardi\n" + 
				"							</td>\n" + 
				"<td>\n" + 
				"								European Motorsports\n" + 
				"							</td>\n" + 
				"<td>\n" + 
				"								eur0sports@aol.com\n" + 
				"							</td>\n" + 
				"<td>\n" + 
				"								\n" + 
				"							</td>\n" + 
				"<td></td></tr>\n" + 
				"<tr class=\"odd\">\n" + 
				"<td>\n" + 
				"								<a href=\"/app/admin/secure/customerLead/1933\">1933</a>\n" + 
				"							</td>\n" + 
				"<td>\n" + 
				"								Sharon Gluschenko\n" + 
				"							</td>\n" + 
				"<td>\n" + 
				"								Automobile Consulting Services\n" + 
				"							</td>\n" + 
				"<td>\n" + 
				"								atobkr@aol.com\n" + 
				"							</td>\n" + 
				"<td>\n" + 
				"								DEALER\n" + 
				"							</td>\n" + 
				"<td>CUST - CUSTOMER</td></tr>\n" + 
				"<tr class=\"even\">\n" + 
				"<td>\n" + 
				"								<a href=\"/app/admin/secure/customerLead/1932\">1932</a>\n" + 
				"							</td>\n" + 
				"<td>\n" + 
				"								Kent Pettus\n" + 
				"							</td>\n" + 
				"<td>\n" + 
				"								Davidson Motor Company, Inc \n" + 
				"							</td>\n" + 
				"<td>\n" + 
				"								kent.pettus@sbcglobal.net\n" + 
				"							</td>\n" + 
				"<td>\n" + 
				"								DEALER\n" + 
				"							</td>\n" + 
				"<td>CUST - CUSTOMER</td></tr>\n" + 
				"<tr class=\"odd\">\n" + 
				"<td>\n" + 
				"								<a href=\"/app/admin/secure/customerLead/1931\">1931</a>\n" + 
				"							</td>\n" + 
				"<td>\n" + 
				"								David Aynehchi\n" + 
				"							</td>\n" + 
				"<td>\n" + 
				"								Cigmode Motoring\n" + 
				"							</td>\n" + 
				"<td>\n" + 
				"								david@bidmycar.net\n" + 
				"							</td>\n" + 
				"<td>\n" + 
				"								DEALER\n" + 
				"							</td>\n" + 
				"<td>CUST - CUSTOMER</td></tr></tbody></table>\n" + 
				"					</ol>\n" + 
				"				</fieldset>\n" + 
				"			</div>\n" + 
				"		\n" + 
				"	\n" + 
				"			</div>\n" + 
				"			<div class=\"push\"></div>\n" + 
				"				\n" + 
				"		</div>	\n" + 
				"		<div class=\"footer\">\n" + 
				"			<span class=\"poweredby\">&nbsp;11:00 PM | Powered By <a href=\"http://autotransmart.com\">VLE</a>, &#169; Copyright 2009.</span>\n" + 
				"			<span class=\"tocprivacy\"><a href=\"/app/secure/supportemail\">Contact Us</a> | <a href=\"/app/terms\">Terms and Conditions</a> | <a href=\"/app/privacy\">Privacy Policy</a> | <a href=\"/app/fees\">Fees</a> | <a href=\"/app/faq\">FAQ</a></span>\n" + 
				"		</div>\n" + 
				"    </body>\n" + 
				"</html>\n" + 
				"";
		
		String regex = "Admin Home";
		
		
		System.out.println(data.contains(regex));
	}
}
