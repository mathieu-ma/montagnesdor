<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

	<fmt:message var="title" key="restaurants.manager.title"/>
	<%-- Get the called method of the action --%>
	<s:set name="method" value="%{#context['com.opensymphony.xwork2.ActionContext.actionInvocation'].proxy.method}"/>
	<tiles:insertDefinition name="admin.manager">
		<tiles:putListAttribute name="javascript-files-default" inherit="true">
			<tiles:addAttribute type="string" value="/javascript/jquery/plugins/jquery.i18n.properties-min.js" />
			<tiles:addAttribute type="string" value="/javascript/jquery/plugins/jquery.tablesorter.min.js" />
			<tiles:addAttribute type="string" value="/javascript/jquery/plugins/jquery.validate.min.js" />
		</tiles:putListAttribute>
		<tiles:putAttribute name="title" value="${title}"/>
		<c:choose>
			<c:when test="${method=='form' || method=='addPrefix' || method=='removePrefix'}">
				<tiles:putAttribute name="body-css" value="body" />
				<tiles:putAttribute name="body" value="/jsp/restaurants/form-body.jsp" />
			</c:when>
			<c:when test="${method=='save' && (not empty actionErrors || not empty fieldErrors)}">
				<tiles:putAttribute name="body" value="/jsp/restaurants/form-body.jsp" />
			</c:when>
			<c:otherwise>
				<tiles:putAttribute name="body" value="/jsp/restaurants/list-body.jsp" />
			</c:otherwise>
		</c:choose>
	</tiles:insertDefinition>