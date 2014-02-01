<%@ attribute name="body" required="true" rtexprvalue="true" %>

<%@ tag import="com.petebevin.markdown.*,java.util.*" %>

<%
MarkdownProcessor proc = new MarkdownProcessor();
%>

<%= proc.markdown(body) %>