<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dog" tagdir="/WEB-INF/tags"%>

<html>
	<head>
		<title>Signup</title>
        <meta http-equiv="Content-type" content="text/html; charset=utf-8" />
    </head>
    <body>
		<div>
			<form:form>
				<fieldset class="form">
					<legend>Sign up</legend>
					<dog:messages />
				<div>
					We take your <a href="/privacy">privacy</a> seriously and will never allow anyone to access your contact information.
				</div>
					<ol>
						<li>
							<label>Name</label>
							<form:input path="firstname" />
						</li>
						<li>
							<label>Email</label>
							<form:input path="username" />
						</li>
						<li>
						</li>
						<li>
							<label>Password</label>
							<form:password path="password" />
						</li>
					</ol>
				</fieldset>
				
				<dog:credit-card />

			</form:form>
		</div>
	</body>
</html>



