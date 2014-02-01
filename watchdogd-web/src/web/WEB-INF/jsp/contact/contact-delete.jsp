<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dog" tagdir="/WEB-INF/tags"%>

<html>
	<head>
		<title>Delete Contact</title>
	</head>
	<body class="contact">
		
		<ul id="crumbs">
			<li><a href="/home">Home</a></li>
			<li><a href="/contacts">Contacts</a></li>
			<li><a href="/contact/${contact.id}">Contact (${contact.name})</a></li>
			<li>Delete</li>
		</ul>
		
		<div class="warning">Are you sure you want to delete this Contact?</div>
		
		<div>
			<fieldset class="data">
				<!-- <legend>Contact</legend> -->
				<ol>
					<li>
						<label>Name</label>
                        <div>${contact.name}</div>
					</li>
					<li>
						<label>Type</label>
						<div>${contact.type}</div>
					</li>
					<li>
						<label>Value</label>
						<div>${contact.value}</div>
					</li>
					<li>
					    <form:form>
            		        <input type="submit" name="_delete" value="Delete" />
            		        <input type="submit" name="_cancel" value="Cancel" />
            		    </form:form>
					</li>
				</ol>
			</fieldset>
		</div>
		
		<div>

		</div>
	</body>
</html>

