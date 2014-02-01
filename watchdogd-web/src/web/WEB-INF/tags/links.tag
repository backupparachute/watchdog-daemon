<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri='http://www.springframework.org/security/tags' prefix='security' %>

	
	<div>
		<fieldset id="links" class="">
			<legend>Menu</legend>
			<ul>
				<li><a href="/applications">Applications</a></li>
				<li><a href="/contacts">Contacts</a></li>
				<li><a href="/support">Support</a></li>
				<c:if test="${not empty invitesRemaining}">
					<li class="highlight"><a href="/invite">Send Invite</a></li>
				</c:if>
			</ul>
			
		<security:authorize ifAnyGranted="ROLE_ADMIN_REQUESTS,ROLE_ADMIN_INVITES">
			<h6>Admin</h6>
			<ul>
				<security:authorize ifAnyGranted="ROLE_ADMIN_REQUESTS">
					<li><a href="/admin/requests">Requests</a></li>
				</security:authorize>
				<security:authorize ifAnyGranted="ROLE_ADMIN_INVITES">
					<li><a href="/admin/invites">Invites</a></li>
				</security:authorize>
			</ul>
		</security:authorize>
		</fieldset>
	</div>
