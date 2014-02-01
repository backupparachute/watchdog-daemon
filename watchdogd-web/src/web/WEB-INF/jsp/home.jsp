 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri='http://www.springframework.org/security/tags' prefix='security' %>
<%@ taglib prefix="dog" tagdir="/WEB-INF/tags"%>

<html>
	<head>
		<title>Home</title>
		<script src="/scripts/jquery.simplemodal.1.4.1.min.js"></script>
		<script src="/scripts/popup.js"></script>
	</head>
	<body class="site">
			
		<ul id="crumbs">
			<li><a href="#">Home</a></li>
		</ul>
		
		<dog:messages />
		
		<%-- <dog:links /> --%>
		
		<div class="right">
			<ul class="">
				<li>
					<a href="/sites.xml"><img src="/images/rss.png" /></a>
				</li>
			</ul>
		</div>
		
		<a href="javascript:popup('#modal');" class="modal-link"><img src="/images/add_small.png" />Create New</a>
		
		<div style="display:none;" id="modal">
			<h3><img src="/images/add_small.png" /> Create Site</h3>
			<dl>
				<dt><a href="/site/http/new">HTTP</a> <dog:help keyword="http" /></dt>
				<!-- <dd class="note">keywords</dd> -->
				<dt><a href="/site/ssh/new">SSH / SFTP</a> <dog:help keyword="ssh" /></dt>
				<dt><a href="/site/ftp/new">FTP</a> <dog:help keyword="ftp" /></dt>
				<dt><a href="/site/dns/new">DNS</a> <dog:help keyword="dns" /></dt>
				<dt><a href="/site/tcp/new">TCP</a> <dog:help keyword="tcp" /></dt>
				<!-- <dd class="note">keywords</dd> -->
				<dt><a href="/site/rest/new">REST</a> <dog:help keyword="rest" /></dt>
				<!-- <dd>receive http posts to monitor anything</dd> -->
				
			</dl>
			<div class="note">press escape to close</div>
		</div>
		
		<!-- <a href="/site/http/new">http</a>
		<a href="/site/tcp/new">tcp</a>
		<a href="/site/rest/new">rest</a> -->
		
		<c:if test="${not empty sites}">
			<display:table name="sites" id="site">
				<display:column title="Status">
					<c:choose>
					    <c:when test="${not site.enabled}">
							<img src="/images/pause.png" />
					    </c:when>
						<c:when test="${site.status.status=='UP'}">
							<img src="/images/checkmark.png" />
						</c:when>
						<c:when test="${site.status.status=='DOWN'}">
							<img src="/images/error.png" />
						</c:when>
						<c:otherwise>
							<img src="/images/warning.png" />
						</c:otherwise>
					</c:choose>
				</display:column>
				<display:column title="Name">
					<a href="/site/${site.id}" title="${site.uri}">${site.name}</a>
				</display:column>
			<%--
				<display:column title="Type">
					${site.type}
				</display:column>
			--%>
				<display:column title="">
					<a class="note" href="/site/${site.id}/edit" title="edit">edit</a>
				</display:column>
			</display:table>
		</c:if>
		

		
	</body>
</html>















