<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<html>
	<head>
		<title>Site Details :: ${site.name}</title>
	</head>
	<body class="site site-details">
		
		<ul id="crumbs">
			<li><a href="/home">Home</a></li>
			<li>${site.name}</li>
		</ul>
		
		<h2 class="inline">${site.name}</h2>  <a href="/site/${site.id}/edit" class="note" title="edit">edit</a>
		
		<div class="note">7 day snapshot: ${date}</div>
		
		<!-- <h3 class="inline note">7 day snapshot</h3> 

		<h3 class="inline note">${date}</h3> -->
		
		<hr class="space" />
		
		<div id="response" class="left">
			<fieldset>
				<legend>Avg Response Time</legend>
		    <c:choose>
		        <c:when test="${not empty responseTimeUrl}">
			        <img src="${responseTimeUrl}" />
		        </c:when>
			    <c:otherwise>
			        <div>
			            No results
	    		    </div>
			    </c:otherwise>
			</c:choose>
		</div>

		<div id="uptime" class="right">
			<fieldset>
				<legend><span class="label">Uptime:</span> ${percent}%</legend> 
				<c:choose>
					<c:when test="${not empty uptimeUrl}">
						<img src="${uptimeUrl}" />
			        </c:when>
				    <c:otherwise>
				        <div>
				            No results
		    		    </div>
				    </c:otherwise>
				</c:choose>
			</fieldset>
		</div>

		<hr class="space" />

		    <c:choose>
		        <c:when test="${not empty customValueUrl}">
				<div id="response" class="left">
					<fieldset>
						<legend>Custom Value</legend>
						<img src="${customValueUrl}" />
					</fieldset>
				</div>
		        </c:when>
			    <c:otherwise>
			    </c:otherwise>
			</c:choose>

		<hr class="space" />
		
		<h3>Recent Notifications</h3>

		<div>
			<display:table name="notifications" id="notification">
				<display:column property="type" title="Type"/>
				<display:column property="createdOn" title="Date"/>
				<display:column property="contact" title="Contact"/>
				<display:column property="subject" />
				<display:column property="success" />
				<display:column property="attempts" />
			</display:table>
		</div>
		
		<hr class="space" />
		
		<h3>Status Records</h3>

		<div class="">

			<display:table name="siteReports" id="report" requestURI="/site/${site.id}/reports/${_days}" excludedParams="*">
				<display:column title="Status">
					<c:choose>
						<c:when test="${report.type=='UP'}">
							<img src="/images/checkmark.png" />
						</c:when>
						<c:otherwise>
							<img src="/images/error.png" />
						</c:otherwise>
					</c:choose>
				</display:column>
				<display:column property="createdOn" title="Date" />
				<display:column property="responseTime" title="Response Time" />
<%--				<display:column property="value" title="Custom Value" /> --%>
				<display:column property="statusCode" title="Status Code" />
				<display:column property="message" title="Message" />
			</display:table>
		</div>
	</body>
</html>





