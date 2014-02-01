<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dog" tagdir="/WEB-INF/tags"%>

<html>
	<head>
		<title>HTTP Setup</title>
	</head>
	<body class="site">
		
		
		<ul id="crumbs">
			<li><a href="/home">Home</a></li>
				<c:if test="${not empty command.id}">
					<li><a href="/site/${command.id}">${command.name}</a></li>
				</c:if>
			<li>HTTP</li>
		</ul>

		<dog:messages />

		<div>
			<form:form>
				<fieldset class="form">
					<legend>HTTP Setup</legend>
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
							<label>URI <em class="mandatory">*</em></label>
							<form:input path="uri" />
							<div class="note">include http:// or https://</div>
						</li>
						<dog:attr-input />	
						<li>
							<input type="submit" name="_save" value="Save" />
							<c:if test="${not empty command.id}">
							    <a href="/site/${command.id}/delete">Delete</a>
							</c:if>
						</li>
					</ol>
				</fieldset>
				<dog:contacts />
			</form:form>
		</div>
	</body>
</html>
