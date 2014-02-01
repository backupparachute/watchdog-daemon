<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Contacts</title>
				<script src="/scripts/jquery.simplemodal.1.4.1.min.js"></script>
				<script src="/scripts/popup.js"></script>
	</head>
	<body class="contact">
		
		<ul id="crumbs">
			<li><a href="/home">Home</a></li>
			<li><a href="/account">Account</a></li>
			<li>Contacts</li>
		</ul>
		
		<a href="javascript:popup('#modal');" class="modal-link"><img src="/images/add_small.png" />Create New</a>
		
		<div style="display:none;" id="modal">
			<h3><img src="/images/add_small.png" /> Create Contact</h3>
			<dl>
				<dt><a href="/contact/email/new">Email</a> <a href="/help/email" class="note">help</a></dt>
				<dt><a href="/contact/twitter/new">Twitter</a> <a href="/help/twitter" class="note">help</a></dt>
			</dl>
			<div class="note">press escape to close</div>
		</div>
		
		<display:table name="contacts" id="contact">
			<display:column title="Type">
				<c:choose>
					<c:when test="${contact.type == 'email'}"><img src="/images/mail.png" /></c:when>
					<c:when test="${contact.type == 'twitter'}"><img src="/images/twitter.png" /></c:when>
				</c:choose>
			</display:column>
			<display:column title="Name">
				<a href="/contact/${contact.id}">${contact.name}</a>
			</display:column>
			<display:column title="Value">
				${contact.value}
			</display:column>
		</display:table>
	</body>
</html>



