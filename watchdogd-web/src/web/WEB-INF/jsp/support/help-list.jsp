<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dog" tagdir="/WEB-INF/tags"%>
<%@ taglib uri='http://www.springframework.org/security/tags' prefix='security' %>

<html>
	<head>
		<title>Help</title>
	</head>
	<body class="help">
		
		<ul id="crumbs">
			<li><a href="/home">Home</a></li>
			<li>Help</li>
		</ul>

		<dog:messages />

		<dl>
		<c:forEach items="${helpList}" var="help">
			<dt><a href="/help/${help.url}">${help.title}</a> <security:authorize ifAnyGranted="ROLE_ADMIN_HELP"><a class="note" href="/admin/help/${help.url}">edit</a></security:authorize></dt>
			<dd>
				<div>${help.description}</div>
				<div class="note">tags: ${help.tags}</div>
			</dd>
		</c:forEach>
		</dl>
	</body>
</html>




