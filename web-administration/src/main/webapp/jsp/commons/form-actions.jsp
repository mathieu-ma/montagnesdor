<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<div class="clear">
	<br/>
</div>
<div class="hspacer-left-100p">		

	<c:if test="${empty param.cancelUrl}">
		<s:url id="cancelUrl" method="list" includeParams="none" />
	</c:if>
	<c:if test="${not empty param.cancelUrl}">
		<s:set var="cancelUrl"><c:out value="${param.cancelUrl}" /></s:set>
	</c:if>

	<button id="submit" class="mdo-ui-button ui-state-default ui-corner-all">
		<span class="ui-icon ui-icon-disk"></span>
		<fmt:message key="admin.manager.save"/>
	</button>
	<span style="width: 25%; display: inline-block;">&nbsp;</span>
	<span class="mdo-ui-button ui-state-default ui-corner-all">
		<span class="ui-icon ui-icon-closethick"></span>
		<s:a cssClass="admin-manager-cancel" href="%{cancelUrl}"><fmt:message key="admin.manager.cancel"/></s:a>
	</span>

</div>
<div class="clear">
	<br/><br/>
</div>
