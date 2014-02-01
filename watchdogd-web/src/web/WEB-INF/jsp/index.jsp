<%@ taglib prefix="dog" tagdir="/WEB-INF/tags"%>
<%@ taglib uri='http://www.springframework.org/security/tags' prefix='security' %>

<html>
	<head>
		<title>Website, Server, API Monitor and Uptime service</title>

		<!-- <link rel="stylesheet" href="/styles/nivo-slider.css" type="text/css" media="screen" /> -->
		<script src="/scripts/jquery.nivo.slider.pack.js" type="text/javascript"></script>

	</head>
	<body class="marketing">
		
		<%--
	<security:authorize ifAnyGranted="ROLE_GREETING">
		<ul id="crumbs">
			<li><a href="#">Home</a></li>
		</ul>
	</security:authorize>
	--%>
		
		<div id="mkt-description" class="left">
			 <p><dog:name /> is a website and server monitoring service that supports multiple protocols, rich graphs, flexible notifications and the ability to monitor private resources with out installing a dedicated client.</p>
			
			<!-- <p>You configure everything through the easy to use web interface, and <dog:name /> will let you know if status of your site changes, it is that simple.
					</p> -->
			<!-- <h4>Sign Up for free today.</h4> -->
			<!-- <a href="#"><img src="/images/signup.png" /></a> -->
			
		</div>
		
		<div class="right">
			<a href="/signup"><img src="/images/signup.png" /></a>
		</div>
		
		<!-- <div id="beta-signup" class="box right">
			<h4 class="header">Private beta.</h4>
			<div class="para">
			    <form action="/contact" method="POST">
				<label>Enter your email for an invite:</label>
				<input type="text" name="_email" class="email"/>
				<input type="hidden" name="_subject" value="beta invite request" />
				<input type="image" name="_save" src="/images/signup.png" height="41" width="100" />
			    </form>
			</div>
		
			<div class="note">
			    You will be contacted when your account is ready.
			</div>
		</div> -->
		
		<hr class="space" />

		<div id="slider-wrapper ">
			<div id="slider" class="nivoSlider">
			    <img src="/images/response-time.png" alt="" />
			    <a href="http://watchdogdaemon.com"><img src="/images/uptime.png" alt="" title="#htmlcaption" /></a>
			    <!-- 
				<img src="/images/contact.png" alt="" title="This is an example of a caption" />
			    	<img src="/images/database.png" alt="" /> 
				-->
			</div>
			<div id="htmlcaption" class="nivo-html-caption">
			    Quick glance uptime report.
			</div>
		</div>
		
		<div id="features">
			
			<div class="feature">
				<div class="icon">
					<img src="/images/cog.png" />
				</div>
				<div class="description">
					<h4>Protocols</h4>
					<p>HTTP/S, TCP, REST API</p>
				</div>
			</div>
			
			<!-- <div class="feature">
				<div class="icon">
					<img src="/images/contact.png" />
				</div>
				<div class="description">
					<h4>Contacts</h4>
					<p>Flexible contact management</p>
				</div>
			</div> -->
			
			<div class="feature">
				<div class="icon">
					<img src="/images/mail.png" />
					<img src="/images/twitter.png"/>
					<img src="/images/rss.png" />
				</div>
				<div class="description">
					<h4>Notifications</h4>
					<p>Receive notifications via Twitter, email and RSS feed. Flexible contact management per site.</p>
				</div>
			</div>
			
			<div class="feature">
				<div class="icon">
					<img src="/images/chart-bar.png" />
				</div>
				<div class="description">
					<h4>Charts</h4>
					<p>Uptime, average response time charts, and custom charts for REST API.</p>
				</div>
			</div>
			
		</div>
		
		<!-- <hr /> -->
		
		<div class="clear">
			<div class="icon">
				<img src="/images/cog.png" />
				<img src="/images/lock.png" />
				<img src="/images/users.png" />
			</div>
			<div class="description">
				<h4>REST API</h4>
				
				<p><dog:name />'s REST API allows you to post status messages, which makes it possible to monitor virtually any hardware or software so you know exactly which piece of your system is having a problem.</p>
				
				<p>Typically you would either have to expose this server to the public internet to allow an external service acess, or install client software for each server and manage complicated internal monitoring software yourself.</p>
				
				<%-- 
				<p><a href="javascript:toggle('#mkt-example');">View Example.</a></p>
					
				<div id="mkt-example" style="display:none">
				
					<h4>Example:</h4>
								
					<p>With <dog:name /> you could setup a simple cron job on your server using curl to send an update every 2 minutes.  <dog:name /> will check to make sure it receives at least one update every 5 minutes; if an update is not received within that timeframe it will change the status and send any applicable notifications.</p>
				
					<code>
						curl -u user:pass -X POST http://watchdogdaemon.com/site/3/status/up
					</code>
				
					<p></p>
				
					<p>You can also set a custom threshold to monitor per site, and include a numeric value  with the status update.  If the value sent in the update exceeds the the threshold <dog:name /> will change the status and send any applicable notifications.</p>
				
					<p><dog:name /> also allows you to create a limited use account for each application to access the REST API, helping to keep your main account safe.</p>
				
					<p>This illustrates only one of the many monitoring solutions that the REST API makes possible, if you need any help setting up your custom solution please <a href="/contact">contact us</a>.</p>
				</div>
				--%>
				
			</div>
		</div>


		<!-- <hr /> -->

		<script>
			function toggle(id)
			{
				$(id).toggle();
			}
		</script>
		
<!--
		<div id="upcoming-features">
			<h3 class="heading">Upcoming Features</h3>
			<dl>
				<dt>Protocols</dt>
				<dd>DNS, FTP, SFTP, SMTP, and other popular protocols.</dd>

				<dt>Multiple Monitoring Locations</dt>
				<dd>We will have multiple monitoring locations across the US, and eventually expanding to other countries as well.</dd>


				<dt>REST API Examples and Ideas</dt>
				<dd>We look forward to hearing about the innovate ways you are using the REST API, as well as what different types of resources you are monitoring.  We will host a gallery of examples and ideas from ourselves as well as the community, hopefully covering most of the popular programming languages as well as systems.</dd>
				
				<dt>OEM Support</dt>
				<dd>Build support for the <dog:name /> REST API directly into your application, <a href="/contact">contact us</a> for more details.</dd>
				<dt>Public Statistics and Graphs</dt>
				<dd>You will be able to configure public statistics and graphs on a per site basis.</dd>
			</dl>
		</div>
		
		<a href="/signup"><img src="/images/signup.png" /></a>
	-->
		
		<script type="text/javascript">
		$(window).load(function() {
		    $('#slider').nivoSlider({effect:'fade',captionOpacity:'0.5'});
		});
		</script>
		
	</body>
</html>















