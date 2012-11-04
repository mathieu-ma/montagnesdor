<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

	<fmt:message var="title" key="tables.orders.manager.title"/>
	<%-- Get the called method of the action --%>
	<s:set name="method" value="%{#context['com.opensymphony.xwork2.ActionContext.actionInvocation'].proxy.method}"/>
	<tiles:insertDefinition name="main.layout">
		<tiles:putListAttribute name="javascript-files-default" inherit="true">
			<tiles:addAttribute type="string" value="/javascript/jquery/plugins/jquery.i18n.properties-min.js" />
		</tiles:putListAttribute>
		<tiles:putAttribute name="title" value="${title}"/>
		<tiles:putAttribute name="menu" value="/jsp/tables-orders/menu.jsp" />
		<tiles:putAttribute name="body-css" value="body" type="string" />
		<c:choose>
			<c:when test="${method=='list'}">
				<tiles:putAttribute name="body" value="/jsp/tables-orders/list-body.jsp" />
			</c:when>
			<c:when test="${method=='displayDinnerTable'}">
				<tiles:putAttribute name="body" value="/jsp/tables-orders/orders-body.jsp" />
				<tiles:putAttribute name="footer" value="/jsp/tables-orders/orders-footer.jsp" />
			</c:when>
			<c:otherwise>
				<tiles:putAttribute name="body" value="/jsp/tables-orders/list-body.jsp" />
			</c:otherwise>
		</c:choose>
	</tiles:insertDefinition>	