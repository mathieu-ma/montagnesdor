<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

	<fmt:message var="title" key="references.manager.title"/>

	<tiles:insertDefinition name="admin.manager">
		<tiles:putListAttribute name="javascript-files-default" inherit="true">
			<tiles:addAttribute type="string" value="/javascript/jquery/plugins/jquery.i18n.properties-min.js" />
			<tiles:addAttribute type="string" value="/javascript/jquery/plugins/jquery.tablesorter.min.js" />
			<tiles:addAttribute type="string" value="/javascript/jquery/plugins/jquery.validate.min.js" />
		</tiles:putListAttribute>

		<tiles:putAttribute name="title" value="${title}"/>
		<tiles:putAttribute name="body" value="/jsp/references/TableTypesManager/list-body.jsp"/>
	</tiles:insertDefinition>