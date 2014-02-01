<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

	
	<c:forEach items="${command.attributes}" var="a" varStatus="loop">
		<c:if test="${a.enabled}">
				<li>
					<label>${a.name.label} <c:if test="${a.name.mandatory}"><em class="mandatory">*</em></c:if></label>
					<form:input path="attributes[${loop.index}].value" />
					<c:if test="${not empty a.name.note}">
					<div class="note">${a.name.note}</div>
					</c:if>
				</li>
		</c:if>
	</c:forEach>
	

