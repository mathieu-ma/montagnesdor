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

	<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/header.js"></script>			

	<div class="vspacer-left-100p">
		<div id="applets" class="applets">
			<!-- WARNING: Applet must not be inside the JQuery resizable plugin -->
			<!-- Default value for Internet Explorer -->
			<c:set var="classid" scope="request" value="clsid:8AD9C840-044E-11D1-B3E9-00805F499D93"/>
			<c:if test="${not fn:contains(header['user-agent'], 'MSIE')}">
				<c:set var="classid" scope="request" value="java:fr.mch.mdo.applets.DateTimeApplet.class"/>
			</c:if>
			<!-- For Google Chrome change object to applet and add code attribute -->
			<object id="objectDateTimeApplet" name="dateTimeApplet" classid="${classid}" width="1" height="1">
				<param name="code" value="fr.mch.mdo.applets.DateTimeApplet.class"/>
     			<param name="codebase" value="<c:out value="${pageContext.request.contextPath}"/>/jsp/commons" />
				<param name="type" value="application/x-java-applet;version=1.3" />
				<param name="archive" value="SDateTimeApplet.jar"/>							
				<param name='localeLanguage' value='<c:out value="${sessionScope.userSession.currentLocale.languageCode}"/>'></param>
				<param name='patternDate' value='<fmt:message key="applets.DateTimeApplet.param.patternDate.value"/>'></param>
				<param name='patternDateShort' value='<fmt:message key="applets.DateTimeApplet.param.patternDateShort.value"/>'></param>
				<param name='patternTime' value='<fmt:message key="applets.DateTimeApplet.param.patternTime.value"/>'></param>
				<param name='patternDateTime' value='<fmt:message key="applets.DateTimeApplet.param.patternDateTime.value"/>'></param>		
				<!--		
				<param NAME="MAYSCRIPT" VALUE="true"></param>
				<param name='startJavascriptFunction' value='timer'></param>
				-->
				<param name='debug' value='false'></param>
     			<comment>
         			<embed type="application/x-java-applet;version=1.3" width="1" height="1" name="dateTimeApplet" id="embedDateTimeApplet"
						codebase="<c:out value="${pageContext.request.contextPath}"/>/jsp/commons"
             			archive="SDateTimeApplet.jar"
             			code="fr.mch.mdo.applets.DateTimeApplet" 
             			localeLanguage='<c:out value="${sessionScope.userSession.currentLocale.languageCode}"/>'
             			patternDate='<fmt:message key="applets.DateTimeApplet.param.patternDate.value"/>'
             			patternDateShort='<fmt:message key="applets.DateTimeApplet.param.patternDateShort.value"/>'
             			patternTime='<fmt:message key="applets.DateTimeApplet.param.patternTime.value"/>'
             			patternDateTime='<fmt:message key="applets.DateTimeApplet.param.patternDateTime.value"/>'
             			pluginspage="http://javaweb.eng/plugin/plugin-install.html">
						<noembed>
				        	No JDK 1.3 support for APPLET!!
				        </noembed>
					</embed>
         		</comment>
			</object>
		</div>	
		<div class="hspacer-left-20p">
			<div id='header-date'>
				<fmt:message key="applets.message.wait.loading"/>
			</div>
			<img id="temp-image-flag" src="<c:out value="${pageContext.request.contextPath}"/>/images/flags/flag_ar.jpg"/>
			<c:if test="${empty userSession.userAuthentication.locales}">
				<img class="flag-selected" title='<c:out value="${userSession.systemAvailableLanguages[userSession.currentLocale.languageCode]}"/>' src="<c:out value="${pageContext.request.contextPath}"/>/images/flags/flag_<c:out value="${userSession.currentLocale.languageCode}"/>.jpg"/>
			</c:if>
			<s:iterator value="%{#session.userSession.userAuthentication.locales}" var="userLocale">
				<c:if test="${userSession.currentLocale.languageCode==userLocale.locale.languageCode}">
					<img class="flag-selected" alt='${userSession.systemAvailableLanguages[userLocale.locale.languageCode]}' title='${userSession.systemAvailableLanguages[userLocale.locale.languageCode]}' src="${pageContext.request.contextPath}/images/flags/flag_${userLocale.locale.languageCode}.jpg"/>
				</c:if>
				<c:if test="${userSession.currentLocale.languageCode!=userLocale.locale.languageCode}">
					<s:url var="url" includeParams="all" value="%{#session.userSession.currentURLWithParametersWithoutLocale}">
			    		<s:param name="request_locale" value="%{#userLocale.locale.languageCode}"/>
			    	</s:url>
			    	<a href="${url}" class="language" id="${userLocale.locale.languageCode}">
						<img class="flag-unselected" alt="${userSession.systemAvailableLanguages[userLocale.locale.languageCode]}" title="${session.userSession.systemAvailableLanguages[userLocale.locale.languageCode]}" 
						src="${pageContext.request.contextPath}/images/flags/flag_${userLocale.locale.languageCode}.jpg"/>
			    	</a>
				</c:if>
		 	</s:iterator>
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

		 	