<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dog" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<html>
	<head>
		<title>Applications</title>
	</head>
	<body class="app">
		<ul id="crumbs">
			<li><a href="/home">Home</a></li>
			<li><a href="/account">Account</a></li>
			<li>Applications</li>
		</ul>
		
		<a href="/application/new" class="modal-link"><img src="/images/add_small.png" />Create New</a>
		
			<display:table name="applications" id="app">
				<display:column title="Name">
					<a href="/application/${app.id}/edit">${app.firstname}</a>
				</display:column>
				<display:column property="lastname" title="Description"/>
			</display:table>
	</body>
</html>


