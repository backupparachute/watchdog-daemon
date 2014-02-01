<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<html>
	<head>
		<title>Requests</title>
	</head>
	<body>

		<ul id="crumbs">
			<li><a href="/home">Home</a></li>
			<li>Requests</li>
		</ul>

		<display:table name="requests" id="req">
			<display:column title="Email">
			    <a href="mailto:${req.email}">${req.email}</a>
			</display:column>
			<display:column property="subject" />
			<display:column property="message" />
			<display:column property="createdOn" title="Date" />
		</display:table>
	</body>
</html>
