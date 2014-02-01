<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<c:forEach items="${_uimessages}" var="entry">

	<div class="${entry.key}">
		<ul>
		<c:forEach items="${entry.value}" var="m">
			<li>
				${m.message}
			</li>
		</c:forEach>
		</ul>
	</div>
	
	<%
		java.util.Map map = (java.util.Map) session.getAttribute("_uimessages");
		map.remove(( (java.util.Map.Entry) getJspContext().findAttribute("entry")).getKey());
	%>

</c:forEach>

