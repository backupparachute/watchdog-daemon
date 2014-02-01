<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %> 
<%@ taglib uri='http://www.springframework.org/security/tags' prefix='security' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="dog" tagdir="/WEB-INF/tags"%>

<dog:authenticated />

<html>
    <head>
        <title>WatchdogDaemon :: <decorator:title default=" " /></title>
        
        <meta HTTP-EQUIV="Pragma" content="no-cache" />
        <meta HTTP-EQUIV="Expires" content="-1" />
        <meta HTTP-EQUIV="Cache-Control" content="private,no-cache,no-store" />

		<link href='http://fonts.googleapis.com/css?family=Orbitron:regular,bold' rel='stylesheet' type='text/css'>
		<link href='http://fonts.googleapis.com/css?family=Nobile:regular,italic,bold' rel='stylesheet' type='text/css'>
		

        <link rel="StyleSheet" media="screen" href="/styles/screen.css" type="text/css" />
        <link rel="StyleSheet" media="handheld" href="/styles/handheld.css" type="text/css" />
        <link rel="icon" href="/images/favicon.ico" type="image/x-icon" />
        <link rel="shortcut icon" href="/images/favicon.ico" type="image/x-icon" />

	<c:if test="${authenticated}">
		<link rel="alternate" type="application/rss+xml" title="Site Statuses" href="/sites.xml" >
	</c:if>


		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>
		
        <decorator:head />

<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-374097-5']);
  _gaq.push(['_setDomainName', '.watchdogdaemon.com']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>

        
    </head>
    <body class="">


		<div class="wrapper">

			<div id="header">
				<%-- <a href="http://watchdogdaemon.com"><img src="/images/watchdogd-logo-small.png" /></a> --%>
				<c:choose>
					<c:when test="${authenticated}">
						<a href="/home"><img src="/images/watchdogd-logo-small.png" /></a>
				</c:when>
					<c:otherwise>
						<a href="/"><img src="/images/watchdogd-logo-small.png" /></a>
					</c:otherwise>
				</c:choose>
				
				<div id="user">
					<c:choose>
						<c:when test="${authenticated}">
							<span>Welcome  <a href="/account"><security:authentication property="principal.firstname"/></a> | <a id="menu-link" class="menu-link-unselected" href="#">menu</a> | <a href="/logout">logout</a></span>
					</c:when>
						<c:otherwise>
							<span><a href="/login">login</a></span>
						</c:otherwise>
					</c:choose>
				</div>
				
			</div>

			<div id="content" class="<decorator:getProperty property="body.class" default=" " writeEntireProperty="false" />">
				<decorator:body />
			</div>
			
		</div>	

		<div id="footer" class="wrapper">
			<hr />
				<div class="copyright">&#169; 2010-2012 <a class="bps" target="_blank" href="http://backupparachute.com">Backup Parachute Software LLC<img src="/images/bps-small.png"/></a></div>
				<!-- <div class=""><img src="/images/bps-logo-small.png"></img></div> -->

				<fieldset class="">
					<legend>Legal</legend>
					<ul>
						<li>
							<a href="/privacy">Privacy</a>
						</li>
						<li>
							<a href="/terms">Terms</a>
						</li>
					</ul>
				</fieldset>
				<fieldset>
				    <legend>Support</legend>
				    <ul>
				         <li>
				             <a href="/help">Help</a>
				         </li>
				        <li>
				            <a href="/help/faq">FAQ</a>
				         </li>
				         <li>
				             <a href="/contact">Contact</a>
				        </li>
				    </ul>
				</fieldset>
				<fieldset class="">
				    <!-- <legend>twitter</legend> -->
				    <ul>
				        <li>
								<a href="https://twitter.com/watchdogdaemon" class="twitter-follow-button" data-show-count="false" data-lang="en" data-show-screen-name="false" data-dnt="true">Follow</a>
								<script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0];if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src="//platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");</script>
				         </li>
				    </ul>
				</fieldset>
				<hr class="space" />
		</div>
		


		<div id="menu" style="display:none;">
				<ul>
					<li><a href="/home">Home</a></li>
					<li><a href="/account">Account</a></li>
					<li><a href="/applications">Applications</a></li>
					<li><a href="/contacts">Contacts</a></li>

					<li><a href="/help">Help</a></li>
					
					<security:authorize ifAnyGranted="ROLE_ADMIN_REQUESTS">
						<li><a href="/admin/requests">Requests</a></li>
					</security:authorize>
					<security:authorize ifAnyGranted="ROLE_ADMIN_INVITES">
						<li><a href="/admin/invites">Invites</a></li>
					</security:authorize>
					<security:authorize ifAnyGranted="ROLE_ADMIN_INVITE">
						<li class="highlight"><a href="/admin/invite">Send Invite</a></li>
					</security:authorize>
					<security:authorize ifAnyGranted="ROLE_ADMIN_CURRENT_USERS">
						<li><a href="/admin/users/current">Current Users</a></li>
					</security:authorize>
					
				</ul>
		</div>
		
		<script>
			function menuToggle() {
				var menu = $('#menu');
				menu.toggle();
				var link = $('#menu-link');
				var pos = link.position();
				menu.css('top',pos.top+27+'px');
				menu.css('left',pos.left+(-10)+'px');
				link.toggleClass("menu-link-unselected");
				link.toggleClass("menu-link-selected");
			}
			
			// $("#menu-link").hover(
			//   function () {
			//     menuToggle();
			//   },
			//   function () {
			//     menuToggle();
			//   }
			// );
			$('#menu-link').mouseenter(function() {
				var menu = $('#menu');
				menu.show();
				var link = $('#menu-link');
				var pos = link.position();
				menu.css('top',pos.top+27+'px');
				menu.css('left',pos.left+(-10)+'px');
				link.toggleClass("menu-link-unselected");
				link.toggleClass("menu-link-selected");
			});
			$('#menu').mouseleave(function() {
				var menu = $('#menu');
				var link = $('#menu-link');
				link.toggleClass("menu-link-unselected");
				link.toggleClass("menu-link-selected");
				menu.hide();
			});
		</script>
		
    </body>
</html>










