<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<html>
	<head>
		<title>Beta Invites</title>
	</head>
	<body class="beta-invites">
			
		<ul id="crumbs">
			<li><a href="/home">Home</a></li>
			<li>Invites</li>
		</ul>
			
		<a href="/admin/invite">New invite</a>
		
		<display:table name="invites" id="invites">
			<display:column property="email" />
			<display:column property="createdOn" />
			<display:column property="invitorAccountId" />
			<display:column property="inviteeAccountId" />
			<display:column property="uuid" />
		</display:table>
		
	</body>
</html>

