<!--
	When you use an absolute URI, you do not have to add the taglib element to web.xml; 
	the JSP container automatically locates the TLD inside the JSTL library implementation.
-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>
<%@ taglib uri="/WEB-INF/mdo.tld" prefix="mdo" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

	<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/jquery/jquery-ui/i18n/jquery.ui.datepicker-<s:property value="#session.userSession.currentLocale.languageCode"/>.js"></script>
	<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/header.js"></script>			
<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/change-entry-date.js"></script>

	<div class="vspacer-left-100p">
<%-- 		
		<jsp:include page="applets.jsp" />
 --%>
		<div class="hspacer-left-20p"> 
			<div class="anchor" title="<fmt:message key="header.change.date"/>">
				<s:a id='header-date' action="ChangeEntryDate" method="form">
					<fmt:message key="applets.message.wait.loading"/>
				</s:a>
				<a href="../jnlp/printer/printer.jnlp">PROG</a>
			</div>
		</div>
 		<div class="header-menu-top">
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
		</div>
		<div class="clear">&nbsp;</div>
		<div class="hspacer-left-20p" style="margin-top: -15px;">
		 	<label><fmt:message key="mdo.welcome.restaurant"><fmt:param value="${userSession.userAuthentication.restaurant.name}"/></fmt:message></label>
		 	<img class="logo1" src="<c:out value="${pageContext.request.contextPath}"/>/images/logo1.gif"/>
		 	<br/>
			<jsp:include page="languages.jsp" />
		</div> 	
		<jsp:include page="header-order.jsp" />		
	</div>