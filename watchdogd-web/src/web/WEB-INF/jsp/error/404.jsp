<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<% 
session.setAttribute("errparam", request.getAttribute("javax.servlet.error.request_uri"));
%>

<c:redirect url="/error/404" />
<%-- 
		out.print("<h1>headers</h1>");
  java.util.Enumeration e = request.getHeaderNames();
    while (e.hasMoreElements()){ 
        String name = (String) e.nextElement();
        out.print("<b>" + name + ": </b>"); 
          out.print(request.getHeader(name) + "<br>");
      } 

		out.print("<h1>attrs</h1>");

	  e = request.getAttributeNames();
	    while (e.hasMoreElements()){ 
	        String name = (String) e.nextElement();
	        out.print("<b>" + name + ": </b>"); 
	          out.print(request.getAttribute(name) + "<br>");
	      }
  --%>

</html>

