<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dog" tagdir="/WEB-INF/tags"%>

<html>
	<head>
		<title>Application Edit</title>
	</head>
	<body class="app">
		
		<ul id="crumbs">
			<li><a href="/home">Home</a></li>
			<li><a href="/account">Account</a></li>
			<li><a href="/applications">Applications</a></li>
			<li>Application 
				<c:if test="${not empty command.id}">
					(${command.id})
				</c:if>
			</li>
		</ul>
		
		
		<div>
			<form:form>
				<fieldset class="form">
					<legend>Application</legend>
					<ol>
					    <li>
							<form:checkbox path="enabled" />
							<label class="inline">Enabled</label>
						</li>
						
						<c:if test="${not empty command.id}">
							<li>
								<label>User Token:</label>
								<input type="text" value="${command.username}" readonly="true" size="55"/>
								<a href="#">Change Password</a>
							</li>
				
						</c:if>

						<li>
							<label>Name</label>
							<form:input path="firstname" />
						</li>
						<li>
							<label>Description</label>
							<form:input path="lastname" />
						</li>

						<li>
						</li>

						<li>
							<input type="submit" name="_save" value="Save" />
							<a href="/application/${command.id}/delete">Delete</a>
						</li>
					</ol>
				</fieldset>
			</form:form>
		</div>
	</body>
</html>

