<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dog" tagdir="/WEB-INF/tags"%>

<html>
	<head>
		<title>Delete Application</title>
	</head>
	<body class="app">
		
		<ul id="crumbs">
			<li><a href="/applications">Applications</a></li>
			<li><a href="/application/${application.id}">Application (${application.firstname})</a></li>
			<li>Delete</li>
		</ul>
		
			<div class="warning">Are you sure you want to delete this Application?</div>
		
		<div>
			<fieldset class="data">
				<!-- <legend>Application</legend> -->
				<ol>
					<li>
						<label>Name</label>
                        <div>${application.firstname}</div>
					</li>
					<li>
						<label>Description</label>
						<div>${application.lastname}</div>
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

