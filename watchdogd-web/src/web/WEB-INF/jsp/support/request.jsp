<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri='http://www.springframework.org/security/tags' prefix='security' %>
<%@ taglib prefix="dog" tagdir="/WEB-INF/tags"%>

<html>
	<head>
		<title>Contact Us</title>
	</head>
	<body>
		<dog:authenticated />

		<ul id="crumbs">
			<li><a href="/home">Home</a></li>
			<li>Contact</li>
		</ul>
		
		<dog:messages />
		
		<form action="/contact" method="POST">
			<fieldset class="form">
				<legend>Contact Us</legend>
				<ol>
					<li>
						<span class="note"><em class="mandatory">*</em> Mandatory</span>
					</li>
					<li>
						<label>Email <em class="mandatory">*</em></label>
						<c:choose>
							<c:when test="${authenticated}">
								<input type="text" name="_email" class="email" readonly="readonly" value="<security:authentication property="principal.username"/>" />
							</c:when>
							<c:otherwise>
								<input type="text" name="_email" class="email" value="${e}"/>
							</c:otherwise>
						</c:choose>
					</li>
					<li>
						<label>Subject</label>
						<input type="text" name="_subject" class="subject" value="${s}"/>
					</li>
					<li>
						<label>Message <em class="mandatory">*</em></label>
						<textarea name="_message" class="message" rows="4" cols="50"></textarea>
					</li>
					<li>

						<c:choose>
							<c:when test="${authenticated}">
								<input type="hidden" name="_userId" value="<security:authentication property="principal.id"/>" />
							</c:when>
							<c:otherwise>
								<input type="hidden" name="_userId" value="" />
							</c:otherwise>
						</c:choose>
						
						<input type="submit" name="_save" value="Send" />
					</li>
				</ol>
			</fieldset>
		</form>

	</body>
</html>


