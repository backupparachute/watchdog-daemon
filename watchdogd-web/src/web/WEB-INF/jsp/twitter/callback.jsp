<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<html>
	<head>
		<title>Twitter</title>
	</head>
	<body>
		<ol>
			<li>
				<label>token</label>
				${token}
			</li>
			<li>
				<label>secret key</label>
				${secretKey}
			</li>
			<li>
				<label>Screen name</label>
				${screenName}
			</li>
			<li>
				<label>user id</label>
				${userId}
			</li>
		</ol>
	</body>
</html>