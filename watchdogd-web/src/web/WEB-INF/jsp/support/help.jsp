<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri='http://www.springframework.org/security/tags' prefix='security' %>
<%@ taglib prefix="dog" tagdir="/WEB-INF/tags"%>

<html>
	<head>
		<title>Help<c:if test="${not empty help.id}"> :: ${help.title}</c:if></title>
	</head>
	<body class="help">
		
		<ul id="crumbs">
			<li><a href="/home">Home</a></li>
			<li><a href="/help">Help</a></li>
				<c:if test="${not empty help.id}">
					<li>${help.title}</li>
				</c:if>
		</ul>

	<c:choose>
		<c:when test="${not empty help.id}">
		<security:authorize ifAnyGranted="ROLE_ADMIN_HELP"><a class="note" href="/admin/help/${_name}">edit</a></security:authorize>
			<div id="help-data">
				<dog:markdown body="${help.data}" />
			</div>
		</c:when>
		<c:otherwise>
			<h2 class="inline">${_name}</h2> <security:authorize ifAnyGranted="ROLE_ADMIN_HELP"><a class="note" href="/admin/help/${_name}">edit</a></security:authorize>
			<div class="warning">Oops, we don't have any information for this topic yet, please <a href="/contact?s=/help/${_name}">contact us</a> and we will get started right away.</div>
			<div>
				<a href="/help">Return to help</a></div>
			</div>
		</c:otherwise>
	</c:choose>
		

	</body>
</html>







