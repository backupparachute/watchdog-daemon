<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="dog" tagdir="/WEB-INF/tags"%>

<html>
	<head>
		<title>Error</title>
	</head>
	<body class="error-page">
		
		<ul id="crumbs">
			<li><a href="/home">Home</a></li>
			<li>Error</li>
		</ul>
		
		<h3>Oops!</h3>
		
		<%-- <h3>Error Code: ${err}</h3> --%>
		
		<div class="error-message">
			<fmt:message key="error.${err}">
				<fmt:param value="${errparam}" />
			</fmt:message>
		</div>

		<p>Please visit our <a href="/help">Support Center</a> for help or <a href="/contact">contact us</a>.</p>

		<%
			session.removeAttribute("err");
			session.removeAttribute("errparam");
		%>
		
	</body>
</html>



