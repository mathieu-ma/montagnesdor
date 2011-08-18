<%--
	When you use an absolute URI, you do not have to add the taglib element to web.xml; 
	the JSP container automatically locates the TLD inside the JSTL library implementation.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

	<fmt:message var="title" key="error.admin.manager.title"/>
	<tiles:insertDefinition name="main.layout">
		<tiles:putAttribute name="title" value="${title}"/>
		<tiles:putAttribute name="body" value="/jsp/errors/error500.jsp" />
	</tiles:insertDefinition>