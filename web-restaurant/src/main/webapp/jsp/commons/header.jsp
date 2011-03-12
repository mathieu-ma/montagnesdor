<!--
	When you use an absolute URI, you do not have to add the taglib element to web.xml; 
	the JSP container automatically locates the TLD inside the JSTL library implementation.
-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core".com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"om/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/x.tld" prefix="x" %>
<%@ taglib uri="/WEB-INF/mdo.tld" prefix="mdo" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

	<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/header.js"></script>			
	<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/tables-orders.js"></script>

	<script type="text/javascript">
		var JS_CONTEXT_PATH='<c:out value="${pageContext.request.contextPath}"/>';
		$(function(){
			JS_CONTEXT_PATH='<c:out value="${pageContext.request.contextPath}"/>';
		});
	</script>
	<div class="vspacer-left-100p">
		<div id="header-welcome">
			<img id="temp-image-flag" src="<c:out value="${pageContext.request.contextPath}"/>/images/flags/flag_ar.jpg"/>
			<c:if test="${empty userSession.userAuthentication.locales}">
				<img class="flag-selected" title='<c:out value="${userSession.systemAvailableLanguages[userSession.currentLocale.language]}"/>' src="<c:out value="${pageContext.request.contextPath}"/>/images/flags/flag_<c:out value="${userSession.currentLocale.language}"/>.jpg" lang="<c:out value="${userSession.currentLocale.language}"/>"/>
			</c:if>
			<s:set name="method" value="%{#context['com.opensymphony.xwork2.ActionContext.actionInvocation'].proxy.method}"/>
			<s:set name="actionName" value="%{#context['com.opensymphony.xwork2.ActionContext.actionInvocation'].proxy.actionName}"/>
			<s:iterator value="%{#session.userSession.userAuthentication.locales}" id="locale">
				<s:if test="#session.userSession.currentLocale.language==language">
					<img class="flag-selected" alt='<s:property value="#session.userSession.systemAvailableLanguages[language]"/>' title='<s:property value="#session.userSession.systemAvailableLanguages[language]"/>' src="<c:out value="${pageContext.request.contextPath}"/>/images/flags/flag_<s:property value="language"/>.jpg"/ lang="<c:out value="${userSession.currentLocale.language}"/>">
				</s:if>
				<s:if test="#session.userSession.currentLocale.language!=language" >
					<s:url id="url" action="%{actionName}" method="%{method}">
			    		<s:param name="language" value="%{language}"/>
			    	</s:url>
			    	<s:a href="%{url}">
						<img class="flag-unselected" alt='<s:property value="#session.userSession.systemAvailableLanguages[language]"/>' title='<s:property value="#session.userSession.systemAvailableLanguages[language]"/>' src="<c:out value="${pageContext.request.contextPath}"/>/images/flags/flag_<s:property value="language"/>.jpg"/>
			    	</s:a>
				</s:if>
		 	</s:iterator>
		 	<label><fmt:message key="mdo.welcome.restaurant"><fmt:param value="${userSession.userAuthentication.restaurant.name}"/></fmt:message></label>
		 	<img class="logo1" src="<c:out value="${pageContext.request.contextPath}"/>/images/logo1.gif"/>
		</div> 	

		<div id="header-menu-top">
			<div class="menu-top-around">&nbsp;</div>
			<!-- Menu Top : Chargement des fichiers XML et XLS : -->
			<c:import url="/xml-xsl/mdo-menu-top.xml" varReader="menuTopXml">
				<c:import url="/xml-xsl/mdo-menu-top.xslt" var="menuTopXsl"/>
				<!-- Analyse du fichier XML chargé avec c:import -->
				<mdo:parseMenu doc="${menuTopXml}" selectedMenuItemId="${userSession.selectedMenuItemId}" var="modParsedDoc"/>
				
				<!-- Transformation XLS : -->
				<x:transform doc="${modParsedDoc}" xslt="${menuTopXsl}">
					<x:param name="selectedMenuItemId" value="selectedMenuItemId"/>
				</x:transform>
			</c:import>
			<div class="menu-top-around">&nbsp;</div>
		</div>

		<div id='header-order' class="mdo-upsidedown">
			<s:url id="tablesOrdersUrl" action="TablesOrders" method="form"/>
			<form action="<s:property value="%{tablesOrdersUrl}"/>" method="post" name="TablesOrders" id="TablesOrders">
				<s:url id="changeEntryDateUrl" action="ChangeEntryDate" method="form"/>
				<input type="hidden" name="changeEntryDateUrl" value="<s:property value="%{changeEntryDateUrl}"/>"/>
				<div id='header-date' class="anchor" title="<fmt:message key="header.change.date"/>">
					<fmt:message key="applets.message.wait.loading"/>
				</div>
				<div class='header-order-table-number'>
					<s:textfield cssClass="string uppercase" label="%{getText('header.table.number')}" name="form.dtoBean.dinnerTableDtoBean.tableNumber" id="input-header-order-table-number" maxLength="4" size="4"/>
					<s:hidden name="form.dtoBean.dinnerTableDtoBean.id"/>
				</div>
				<div class='header-order-takeaway'>
					<s:hidden id="prefixesTakeawayName" name="form.dtoBean.userContext.userAuthentication.restaurant.prefixesTakeawayName"/>
					<s:checkbox label="%{getText('header.table.takeaway')}" name="form.dtoBean.dinnerTableDtoBean.takeaway" id="checkbox-header-order-table-takeaway" fieldValue="false"/>
				</div>
				<div class='header-order-table-customer'>
					<s:textfield cssClass="integer" label="%{getText('header.table.customer')}" name="form.dtoBean.dinnerTableDtoBean.customersNumber" id="input-header-order-table-customer" maxLength="4" size="4"/>
				</div>
				<div class='header-order-auto-update'>
					<s:checkbox label="%{getText('header.auto.update.order.line')}" name="header.order.auto.update" value="true"/>
				</div>
			</form>
		</div>

	</div>

