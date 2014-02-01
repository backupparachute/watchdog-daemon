<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>

<% 
session.setAttribute("errex", request.getAttribute("javax.servlet.error.exception"));
%>

<c:redirect url="/error/500" />
</html>
