<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dog" tagdir="/WEB-INF/tags"%>

<html>
	<head>
		<title>Application Setup</title>
	</head>
	<body class="app">
		
		<ul id="crumbs">
			<li><a href="/home">Home</a></li>
			<li><a href="/account">Account</a></li>
			<li><a href="/applications">Applications</a></li>
			<li>Application</li>
		</ul>
		
		
		<div>
			<form:form>
				<fieldset class="form">
					<legend>Application</legend>
					<ol>
						<li>
							<span class="note"><em class="mandatory">*</em> Mandatory</span>
						</li>
						
						<li>
							<label>Name <em class="mandatory">*</em></label>
							<form:input path="firstname" />
						</li>
						<li>
							<label>Description</label>
							<form:input path="lastname" />
						</li>

						<li>
							<label>Password <em class="mandatory">*</em></label>
							<form:input path="password" />
						</li>
						
						<li>
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

