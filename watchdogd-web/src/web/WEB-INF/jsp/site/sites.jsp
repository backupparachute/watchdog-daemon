<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="dog" tagdir="/WEB-INF/tags"%>

<html>
	<head>
		<title>Home</title>
	</head>
	<body class="site">
			
		<ul id="crumbs">
			<li><a href="/home">Home</a></li>
			<li>Sites</li>
		</ul>
			
		<a href="/site/http/new">http</a>
		<a href="/site/tcp/new">tcp</a>
		<a href="/site/rest/new">rest</a>

		<dog:messages />
		
		<display:table name="sites" id="site">
			<display:column title="Status">
				<c:choose>
				    <c:when test="${not site.enabled}">
						<img src="/images/pause.png" />
				    </c:when>
					<c:when test="${site.status.status=='UP'}">
						<img src="/images/checkmark.png" />
					</c:when>
					<c:when test="${site.status.status=='DOWN'}">
						<img src="/images/error.png" />
					</c:when>
					<c:otherwise>
						<img src="/images/warning.png" />
					</c:otherwise>
				</c:choose>
			</display:column>
			<display:column title="Name">
				<a href="/site/${site.id}" title="${site.uri}">${site.name}</a>
			</display:column>
			<display:column title="">
				<a href="/site/${site.id}/edit" title="edit">edit</a>
			</display:column>
		</display:table>
		
	</body>
</html>


