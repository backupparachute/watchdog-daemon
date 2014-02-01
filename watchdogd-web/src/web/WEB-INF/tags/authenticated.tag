<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri='http://www.springframework.org/security/tags' prefix='security' %>

<security:authorize ifAnyGranted="ROLE_GREETING">
	<c:set var="authenticated" value="true" scope="request"/>
</security:authorize>

