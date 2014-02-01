<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dog" tagdir="/WEB-INF/tags"%>

<html>
	<head>
		<title>Password Reset</title>
	</head>
	<body>
		<div>
			<form method="POST">
				<fieldset class="form">
					<legend>Reset Password</legend>
					<ol>
						<li>
							<label>Email</label>
							<input type="text" name="email" />
						</li>
						<li>
							<input type="submit" name="_save" value="Save" />
						</li>
					</ol>
				</fieldset>
			</form>
		</div>
	</body>
</html>
