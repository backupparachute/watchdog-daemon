<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix='security' uri='http://www.springframework.org/security/tags' %>
<%@ taglib prefix="dog" tagdir="/WEB-INF/tags"%>

<html>
	<head>
		<title>Login</title>
	</head>
	<body class="login">

		<c:if test="${not empty param.login_error}">
			<div class="error">Username or password is incorrect.</div>
		</c:if>
		
		<dog:messages />
		
		<div id="login" class="">
			<form name='f' action='j_spring_security_check' method='POST'>
			<fieldset class="form">
				<legend>Login</legend>

					<ol>
						<li>
							<label>Email:</label>
							<input type='text' name='j_username' value=''>
						</li>
						<li>
							<label>Password:</label>
							<input type='password' name='j_password' id="pass"/>
						</li>
						<li>
							<input id="remem" type='checkbox' name='_spring_security_remember_me'/>
							<label for="remem" class="inline">Remember me</label>
						</li>
						<li>
							<a href="/user/recovery">Forgot Password?</a>
						</li>
						<li>
							<input type="submit" value="Login"/>
							<!-- <input name="reset" type="reset" value="Cancel"/> -->
						</li>
					</ol>

			</fieldset>
			</form>
		</div>
		
	</body>
</html>


