<%@ taglib prefix="dog" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

	<li>
		<label>Keyword Search <dog:help keyword="keyword" /></label>
		<dog:attr-input list="${command.attributes}" attr="KEYWORD" name="attributes"/>
	</li>