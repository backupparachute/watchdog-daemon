<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dog" tagdir="/WEB-INF/tags"%>

<html>
	<head>
		<title>Help</title>
	</head>
	<body class="help">
		
		<ul id="crumbs">
			<li><a href="/home">Home</a></li>
			<li><a href="/help">Help</a></li>
				<c:if test="${not empty command.url}">
					<li>${command.title}</li>
				</c:if>
		</ul>
		
		<dog:messages />
		
		<div>
			<form:form>
				<fieldset class="form">
					<legend>Help Edit</legend>
					<ol>
						<li>
							<span class="note"><em class="mandatory">*</em> Mandatory</span>
						</li>
					    	<li>
							<form:checkbox path="enabled" />
							<label class="inline">Enabled</label>
						</li>
						<li>
							<label>Url</label>
							<input type="text" readonly="true" value="${command.url}" />
						</li>
						<li>
							<label>Title <em class="mandatory">*</em></label>
							<form:input path="title" />
						</li>
						<li>
							<label>Description <em class="mandatory">*</em></label>
							<form:input path="description" />
						</li>
						<li>
							<label>Weight <em class="mandatory">*</em></label>
							<form:input path="weight" />
						</li>
						<li>
							<label>Tags <em class="mandatory">*</em></label>
							<form:input path="tags" />
							<div class="note">space delimited</div>
						</li>
						<li>
							<label>Data <em class="mandatory">*</em></label>
							<form:textarea path="data" />
						</li>
						<li>
							<input type="submit" name="_save" value="Save" />
							<%--
							<c:if test="${not empty command.id}">
							    <a href="/site/${command.id}/delete">Delete</a>
							</c:if>
							--%>
						</li>
					</ol>
				</fieldset>
			</form:form>
		</div>
	</body>
</html>



