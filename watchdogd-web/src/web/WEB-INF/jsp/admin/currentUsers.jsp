<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="dog" tagdir="/WEB-INF/tags"%>

<html>
	<head>
		<title>Admin :: Current Users</title>
	</head>
	<body class="admin-current-users">
		
		<ul id="crumbs">
			<li><a href="/home">Home</a></li>
			<li>Current Users</li>
		</ul>

			<display:table name="currentUsers" id="cur">
				<display:column title="Current Users: ${fn:length(currentUsers)}">${cur}</display:column>
			</display:table>
			
	</body>
</html>



