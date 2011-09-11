<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

	<fmt:message var="title" key="users.manager.title"/>
	<%-- Get the called method of the action --%>
	<s:set name="method" value="%{#context['com.opensymphony.xwork2.ActionContext.actionInvocation'].proxy.method}"/>
	<s:set name="actionName" value="%{#context['com.opensymphony.xwork2.ActionContext.actionInvocation'].proxy.actionName}"/>
	<c:set var="actionNameSub" scope="request" value="${fn:replace(actionName, 'CUD', '')}" />
	<s:set name="actionName" value="%{#attr.actionNameSub}"/>
	<tiles:insertDefinition name="admin.manager">
		<tiles:putListAttribute name="javascript-files-default" inherit="true">
			<tiles:addAttribute type="string" value="/javascript/jquery/plugins/jquery.i18n.properties-min.js" />
			<tiles:addAttribute type="string" value="/javascript/jquery/plugins/jquery.tablesorter.min.js" />
			<tiles:addAttribute type="string" value="/javascript/jquery/plugins/jquery.validate.min.js" />
		</tiles:putListAttribute>
		<tiles:putAttribute name="title" value="${title}"/>
		<c:set var="bodyInclude">/jsp/users/<c:out value="${actionName}"/></c:set>
		<c:choose>
			<c:when test="${method=='form' || method=='addRestaurant' || method=='removeRestaurant' || method=='addLanguage' || method=='removeLanguage'}">
				<c:set var="bodyInclude"><c:out value="${bodyInclude}"/>/form-body.jsp</c:set>
			</c:when>
			<c:when test="${method=='save' && (not empty actionErrors || not empty fieldErrors)}">
				<c:set var="bodyInclude"><c:out value="${bodyInclude}"/>/form-body.jsp</c:set>
			</c:when>
			<c:otherwise>
				<c:set var="bodyInclude"><c:out value="${bodyInclude}"/>/list-body.jsp</c:set>
			</c:otherwise>
		</c:choose>
		<tiles:putAttribute name="body" value="${bodyInclude}"/>
	</tiles:insertDefinition>