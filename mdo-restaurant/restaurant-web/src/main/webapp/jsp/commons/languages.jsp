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

	<img id="temp-image-flag" src="<c:out value="${pageContext.request.contextPath}"/>/images/flags/flag_ar.jpg"/>
	<c:if test="${empty userSession.userAuthentication.locales}">
		<img class="flag-selected" title='<c:out value="${userSession.systemAvailableLanguages[userSession.currentLocale.languageCode]}"/>' src="<c:out value="${pageContext.request.contextPath}"/>/images/flags/flag_<c:out value="${userSession.currentLocale.languageCode}"/>.jpg"/>
	</c:if>
	<s:iterator value="%{#session.userSession.userAuthentication.locales}" var="userLocale">
		<c:if test="${userSession.currentLocale.languageCode==userLocale.locale.languageCode}">
			<img class="flag-selected" alt='${userSession.systemAvailableLanguages[userLocale.locale.languageCode]}' title='${userSession.systemAvailableLanguages[userLocale.locale.languageCode]}' src="${pageContext.request.contextPath}/images/flags/flag_${userLocale.locale.languageCode}.jpg"
			lang="${userLocale.locale.languageCode}" />
		</c:if>
		<c:if test="${userSession.currentLocale.languageCode!=userLocale.locale.languageCode}">
			<s:url var="url" includeParams="all" value="%{#session.userSession.currentURLWithParametersWithoutLocale}">
	    		<s:param name="request_locale" value="%{#userLocale.locale.languageCode}"/>
	    	</s:url>
	    	<a href="${url}" class="language" id="${userLocale.locale.languageCode}">
				<img class="flag-unselected" alt="${userSession.systemAvailableLanguages[userLocale.locale.languageCode]}" title="${session.userSession.systemAvailableLanguages[userLocale.locale.languageCode]}" 
				src="${pageContext.request.contextPath}/images/flags/flag_${userLocale.locale.languageCode}.jpg" 
				lang="${userLocale.locale.languageCode}" />
	    	</a>
		</c:if>
 	</s:iterator>
