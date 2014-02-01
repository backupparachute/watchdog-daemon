<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>Response Time</title>
	</head>
	<body>
	    <c:choose>
	        <c:when test="${not empty reportUrl}">
		        <img src="${reportUrl}" />
	        </c:when>
		    <c:otherwise>
		        <div>
		            No results
    		    </div>
		    </c:otherwise>
		</c:choose>
	</body>
</html>