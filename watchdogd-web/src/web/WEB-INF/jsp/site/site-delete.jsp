<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dog" tagdir="/WEB-INF/tags"%>

<html>
	<head>
		<title>Delete Site</title>
	</head>
	<body class="site-delete">
		
		<ul id="crumbs">
			<li><a href="/sites">Sites</a></li>
			<li><a href="/site/${site.id}">Site (${site.name})</a></li>
			<li>Delete</li>
		</ul>
		
		<div>
			
				<div class="warning">Are you sure you want to delete this Site and all associated information?</div>
			
			<fieldset class="data">
				<!-- <legend>Site</legend> -->
				
				<ol>
					<li>
						<label>Name</label>
                        <div>${site.name}</div>
					</li>
					<li>
						<label>URI</label>
						<div>${site.uri}</div>
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

