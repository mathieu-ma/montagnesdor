<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

	<fmt:message var="title" key="error.admin.manager.title"/>
	<tiles:insertDefinition name="admin.manager">
		<tiles:putAttribute name="title" value="${title}"/>
		<tiles:putAttribute name="body" value="/jsp/errors/error500.jsp" />
	</tiles:insertDefinition>