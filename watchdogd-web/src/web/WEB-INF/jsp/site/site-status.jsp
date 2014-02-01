<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dog" tagdir="/WEB-INF/tags"%>

<html>
	<head>
		<title>Site Status</title>
	</head>
	<body class="site">
		
		<ul id="crumbs">
			<li><a href="/sites">Sites</a></li>
			<li><a href="/site/${id}">Site (${site.name})</a></li>
			<li>Status</li>
		</ul>
		
		
		<div>
			site status api docs go here...
		</div>
		
		<div>
			<form action="/site/${id}/status/up" method="POST">
				<span>response time</span> <input type="text" name="response-time" value="">
				<span>custom value</span> <input type="text" name="custom-value" value="">
				<input type="submit" name="_submit" value="UP"/>
			</form>

			<form action="/site/${id}/status/down" method="POST">
				<span>response time</span> <input type="text" name="response-time" value="">
				<span>custom value</span> <input type="text" name="custom-value" value="">
				<input type="submit" name="_submit" value="DOWN"/>
			</form>
		</div>
	</body>
</html>

