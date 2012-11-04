<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

	<fmt:message var="title" key="user.manager.title"/>
	<tiles:insertDefinition name="main.layout">
		<tiles:putAttribute name="title" value="${title}"/>
		<tiles:putAttribute name="body" value="/jsp/user/body.jsp" />
	</tiles:insertDefinition>