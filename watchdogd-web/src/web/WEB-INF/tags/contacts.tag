<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fieldset class="form">
	<legend>Contacts</legend>
	
	<c:choose>
		<c:when test="${not empty contacts}">
			<ol>
				<c:forEach items="${contacts}" var="contact">
					<li>
						<c:choose>
							<c:when test="${selectedContacts[contact.id]}">
								<input type="checkbox" name="_contact" value="${contact.id}" checked="true" />
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="_contact" value="${contact.id}" />
							</c:otherwise>
						</c:choose>
						<label class="inline">${contact.name}</label>
						<span class="note">${contact.type}</span>
					</li>
				</c:forEach>
			</ol>
		</c:when>
		<c:otherwise>
			<div>
				<a href="/contacts">Add Contact</a>
			</div>
		</c:otherwise>
	</c:choose>
	
	
</fieldset>