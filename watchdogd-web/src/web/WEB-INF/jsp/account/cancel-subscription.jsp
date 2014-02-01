<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dog" tagdir="/WEB-INF/tags"%>

<html>
	<head>
		<title>Cancel Subscription</title>
	</head>
	<body class="">
		
		<ul id="crumbs">
			<li><a href="/account">Account</a></li>
			<li>Cancel Subscription</li>
		</ul>
		
		<div>
			
				<!-- <div class="warning">Are you sure you want to cancel this Subscription?</div> -->
			
			<fieldset class="data">
				<!-- <legend>Site</legend> -->
				
				<ol>
					<!-- <li>
						<label>Name</label>
                        <div>${site.name}</div>
					</li>
					<li>
						<label>URI</label>
						<div>${site.uri}</div>
					</li> -->
					<li class="warning">
						<strong>Are you sure you want to cancel this Subscription?</strong><div>Your account will still be active until your <a href="/account/payment">renewal date</a>.</div>
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

