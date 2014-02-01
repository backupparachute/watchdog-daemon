<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dog" tagdir="/WEB-INF/tags"%>

<html>
	<head>
		<title>Account</title>
	</head>
	<body>
		<ul id="crumbs">
			<li><a href="/home">Home</a></li>
			<li>Account</li>
		</ul>

		<div>
			<ul>
				<li>
					<a href="/applications">Applications</a>
				</li>
				<li>
					<a href="/account/payment">Payment</a>
				</li>
				<li>
					<a href="/contacts">Contacts</a>
				</li>
				<li>
					<a href="/user/recovery">Reset Password</a>
				</li>
			</ul>
		</div>
	</body>
</html>


