<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="dog" tagdir="/WEB-INF/tags"%>

<html>
	<head>
		<title>Admin :: Webhooks Test</title>
	</head>
	<body class="admin-webhooks">
		
		<ul id="crumbs">
			<li><a href="/home">Home</a></li>
			<li>Webhooks</li>
		</ul>
		
		<div id="results"></div>
		
		<form id="webhook-test">
			<textarea id="json" rows="40" cols="100"></textarea>
			<input type="submit">
		</form>

		<script>
		$('#webhook-test').submit(function() {
			$('#results').html('sending...');
			$.post('/webhooks', $('#json').val(), function(data) {
			  $('#results').html('done...');
			});
		  return false;
		});

		</script>
			
	</body>
</html>



