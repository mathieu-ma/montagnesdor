<!--
	When you use an absolute URI, you do not have to add the taglib element to web.xml; 
	the JSP container automatically locates the TLD inside the JSTL library implementation.
-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>
<%@ taglib uri="/WEB-INF/mdo.tld" prefix="mdo" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

	<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/menu-top.js"></script>			

	<script type="text/javascript">
		var JS_CONTEXT_PATH='<c:out value="${pageContext.request.contextPath}"/>';
		$(function(){
			JS_CONTEXT_PATH='<c:out value="${pageContext.request.contextPath}"/>';
		});
	</script>
	<div class="vspacer-left-100p">
		<div class="hspacer-left-20p">
			<div id='header-date'>
				<fmt:message key="applets.message.wait.loading"/>
			</div>
			<img id="temp-image-flag" src="<c:out value="${pageContext.request.contextPath}"/>/images/flags/flag_ar.jpg"/>
			<c:if test="${empty userSession.userAuthentication.locales}">
				<img class="flag-selected" title='<c:out value="${userSession.systemAvailableLanguages[userSession.currentLocale.languageCode]}"/>' src="<c:out value="${pageContext.request.contextPath}"/>/images/flags/flag_<c:out value="${userSession.currentLocale.languageCode}"/>.jpg"/>
			</c:if>
			<c:forEach items="${userSession.userAuthentication.locales}" var="userLocale">
				<c:if test="${userSession.currentLocale.languageCode==userLocale.locale.languageCode}">
					<img class="flag-selected" alt='${userSession.systemAvailableLanguages[userLocale.locale.languageCode]}' title='${userSession.systemAvailableLanguages[userLocale.locale.languageCode]}' src="${pageContext.request.contextPath}/images/flags/flag_${userLocale.locale.languageCode}.jpg"/>
				</c:if>
				<c:if test="${userSession.currentLocale.languageCode!=userLocale.locale.languageCode}">
					<c:url var="url" value="${userSession.currentURLWithParameters}">
			    		<c:param name="request_locale" value="${userLocale.locale.languageCode}"/>
			    	</c:url>
			    	<a href="${url}" class="language" id="${userLocale.locale.languageCode}">
						<img class="flag-unselected" alt="${userSession.systemAvailableLanguages[userLocale.locale.languageCode]}" title="${session.userSession.systemAvailableLanguages[userLocale.locale.languageCode]}" 
						src="${pageContext.request.contextPath}/images/flags/flag_${locale.languageCode}.jpg"/>
			    	</a>
				</c:if>
		 	</c:forEach>
		 	<br/>
		 	<label><fmt:message key="menu.welcome"><fmt:param value="${userSession.userAuthentication.restaurant.name}"/></fmt:message></label>
		 	<img class="logo1" src="<c:out value="${pageContext.request.contextPath}"/>/images/logo1.gif"/>
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

	</div>

		 	