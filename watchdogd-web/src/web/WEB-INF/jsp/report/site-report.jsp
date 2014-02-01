<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<html>
	<head>
		<title>Site Report</title>
	</head>
	<body>
		
			<ul id="crumbs">
				<li><a href="/home">Home</a></li>
				<li><a href="/sites">Sites</a></li>
				<li><a href="/site/${_site}">Site (${site.name})</a></li>
				<li>Site Status Reports</li>
			</ul>
		
		<ul>
			<li><a href="/site/${_site}/reports/1">Today</a></li>
			<li><a href="/site/${_site}/reports/7">Week</a></li>
			<li><a href="/site/${_site}/reports/30">Month</a></li>
		</ul>
		<div>${date}</div>
		<display:table name="siteReports" id="report" requestURI="/site/${_site}/reports/${_days}" excludedParams="*">
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
			<display:column property="value" title="Custom Value" />
			<display:column property="statusCode" title="Status Code" />
			<display:column property="message" title="Message" />
		</display:table>
	</body>
</html>

