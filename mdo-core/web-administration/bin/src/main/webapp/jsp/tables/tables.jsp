<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

	<fmt:message var="title" key="tables.manager.title"/>
	<%-- Get the called method of the action --%>
	<s:set name="method" value="%{#context['com.opensymphony.xwork2.ActionContext.actionInvocation'].proxy.method}"/>
	<s:set name="actionName" value="%{#context['com.opensymphony.xwork2.ActionContext.actionInvocation'].proxy.actionName}"/>
	<tiles:insertDefinition name="admin.manager">
		<tiles:putAttribute name="title" value="${title}"/>
		<c:set var="bodyInclude">/jsp/tables/<c:out value="${actionName}"/></c:set>
		<c:choose>
			<c:when test="${method=='form' || method=='labels'}">
				<c:set var="bodyInclude"><c:out value="${bodyInclude}"/>/form-body.jsp</c:set>
			</c:when>
			<c:otherwise>
				<c:set var="bodyInclude"><c:out value="${bodyInclude}"/>/list-body.jsp</c:set>
			</c:otherwise>
		</c:choose>
		<tiles:putAttribute name="body" value="${bodyInclude}"/>
	</tiles:insertDefinition>