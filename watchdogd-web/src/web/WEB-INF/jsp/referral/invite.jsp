<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dog" tagdir="/WEB-INF/tags"%>

<html>
	<head>
		<title>Invite</title>
	</head>
	<body>
		<ul id="crumbs">
			<li><a href="/home">Home</a></li>
			<li>Invite</li>
		</ul>

		<div>
			<form:form>
				<fieldset class="form">
					<legend>Invite</legend>
					<ol>
						<li>
							<label>email</label>
							<form:input path="email" />
						</li>
						<li>
							<label>Personalized Message</label>
							<form:textarea path="message" />
						</li>
						<li>
							<input type="submit" name="_save" value="Save" />
						</li>
					</ol>
				</fieldset>
			</form:form>
		</div>
	</body>
</html>


