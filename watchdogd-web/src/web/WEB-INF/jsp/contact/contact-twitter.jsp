<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Twitter Setup</title>
	</head>
	<body class="contact">
		
		<ul id="crumbs">
			<li><a href="/home">Home</a></li>
			<li><a href="/account">Account</a></li>
			<li><a href="/contacts">Contacts</a></li>
			<li>Twitter</li>
		</ul>
		
		<div>
			<form:form>
				<fieldset class="form">
					<legend>Twitter Setup</legend>
					<ol>
						<li>
							<span class="note"><em class="mandatory">*</em> Mandatory</span>
						</li>
						<li>
							<form:checkbox path="enabled" />
							<label class="inline">Enabled</label>
						</li>
						<li>
							<label>Name <em class="mandatory">*</em></label>
							<form:input path="name" />
						</li>
						<li>
							<label>Twitter  <em class="mandatory">*</em></label>
							<form:input path="value" />
							<div class="note">you must follow @watchdogdaemon to receive notifications</div>
						</li>
						<li>
							<input type="submit" name="_save" value="Save" />
							<c:if test="${not empty command.id}">
							    <a href="/contact/${command.id}/delete">Delete</a>
							</c:if>
						</li>
					</ol>
				</fieldset>
			</form:form>
		</div>
	</body>
</html>


