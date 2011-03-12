<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

	<fmt:message var="title" key="locales.manager.title"/>
	<tiles:insertDefinition name="admin.manager">
		<tiles:putListAttribute name="javascript-files-default" inherit="true">
			<tiles:addAttribute type="string" value="/javascript/jquery/plugins/jquery.tablesorter.min.js" />
		</tiles:putListAttribute>

		<tiles:putAttribute name="title" value="${title}"/>
		<tiles:putAttribute name="body" value="/jsp/references/LocalesManager/list-body.jsp" />
	</tiles:insertDefinition>