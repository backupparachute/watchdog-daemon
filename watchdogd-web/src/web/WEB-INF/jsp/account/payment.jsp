<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dog" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<html>
	<head>
		<title>Payment</title>
        <meta http-equiv="Content-type" content="text/html; charset=utf-8" />
    </head>
    <body>
		<ul id="crumbs">
			<li><a href="/home">Home</a></li>
			<li><a href="/account">Account</a></li>
			<li>Payment</li>
		</ul>
		<div class="left half-screen">
			<fieldset class="data-inline short">
				<legend>${card.type}</legend>
				<ol>	
					<li>
						<label>Name:</label>
						<span>${card.name}</span>
					</li>
					<li>
						<label>Number:</label>
						<code>************${card.last4}</code>
					</li>
					<li>
						<label>Exp:</label>
						<span>${card.expMonth}/${card.expYear}</span>
					</li>
				</ol>
			</fieldset>
			<hr class="space" />
			<form:form class="">
				<dog:credit-card />
			</form:form>
		</div>
		<div class="right half-screen">
			<fieldset class="data-inline short">
				<legend>Recent Charges</legend>
				<c:if test="${not empty charges}">
					<display:table name="charges" id="charge">
						<display:column title="Card">
							${charge.card.type}
						</display:column>
						<display:column title="Number">
						 ************${charge.card.last4}
						</display:column>
						<display:column title="Date">
							<c:set var="epoch" value="${charge.created*1000}" />
							<%
								java.lang.Long l = (java.lang.Long) pageContext.findAttribute("epoch");
								pageContext.setAttribute("tmpDate", new java.util.Date(l));
							%>
							<fmt:formatDate value="${tmpDate}" pattern="yyyy-MM-dd" />
						</display:column>
						<display:column title="Amount">
							$${charge.amount / 100}
						</display:column>
					</display:table>	
				</c:if>	
			</fieldset>
			<hr class="space" />
			<fieldset class="data-inline wide">
				<legend>Current Subscription</legend>
				<ol>
					<c:choose>
						<c:when test="${status == 'INACTIVE'}">
							<li class="error">
								<strong>Subscription Inactive:</strong>
								<div>update credit card info to signup for new subscription.</div>
							</li>
							<c:if test="${not empty plan}">
								<li class="warning">
							 		<strong>Subscription:</strong> $${plan.amount / 100} / per year
								</li>
							</c:if>
						</c:when>
						<c:when test="${status == 'CANCELED'}">
							<li class="warning">
								<strong>Subscription Canceled:</strong> ends ${nextCharge.date}
							</li>
						</c:when>
						<c:otherwise>
								<li>
									<label>Renew Date:</label>
									<span>${nextCharge.date}</span>
								</li>
								<li>
									<label>Renew Amount:</label>
									<span>$${nextCharge.amount / 100}</span>
								</li>
								<c:if test="${not empty customer.discount.coupon.percentOff}">
									<li>
										<label>Discount:</label>
										<span>${customer.discount.coupon.percentOff}%</span>
									</li>
								</c:if>

							<li class="success">
								<strong>Subscription active</strong>
							</li>
							<li>
								<a href="/account/cancel">Cancel Subscription</a>
							</li>
						</c:otherwise>
					</c:choose>
					</ol>
			</fieldset>


		</div>
		<hr class="space" />
	</body>
</html>

