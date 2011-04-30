<%@ page language="java" isErrorPage="true"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		
		<link rel="shortcut icon" type="image/x-icon" href="<c:out value="${pageContext.request.contextPath}"/>/favicon.ico" />
		<link rel="shortcut icon" type="image/png" href="<c:out value="${pageContext.request.contextPath}"/>/favicon.png" />

		<tiles:useAttribute id="styles" name="css-files-default" classname="java.util.List"/>
 		<c:forEach items="${styles}" var="style">
 			<link rel="stylesheet" type="text/css" href="<c:out value="${pageContext.request.contextPath}"/>${style}"/>
    	</c:forEach>
		
		<tiles:useAttribute id="styles" name="css-files-specific" classname="java.util.List"/>
 		<c:forEach items="${styles}" var="style">
 			<link rel="stylesheet" type="text/css" href="<c:out value="${pageContext.request.contextPath}"/>${style}"/>
    	</c:forEach>

		<!-- s:head theme="css_xhtml" debug="false"/ -->

		<title><fmt:message key="montagnesdor.welcome"/> - <tiles:getAsString name="title"/></title>
		
		<tiles:useAttribute id="javascripts" name="javascript-files-default" classname="java.util.List"/>
 		<c:forEach items="${javascripts}" var="javascript">
 			<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>${javascript}"></script>
    	</c:forEach>
		<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/main-layout.js"></script>

		<script type="text/javascript">
			// This part is for the global javascript constants
			var CURRENT_DECIMAL_SEPARATOR = "${sessionScope.userSession.currentDecimalSeparator}";
			
			//This is used for dojo i18n
			//dojo.locale="<c:out value="${pageContext.request.locale.language}"/>";
			//<c:if test="${not empty (session.userSession.currentLocale.languageCode)}">
				//dojo.locale="<c:out value="${session.userSession.currentLocale.languageCode}"/>";
			//</c:if>
		</script>
	</head>
	<body>
		<div id="global-transparent" class="global-transparent-hidden">&nbsp;</div>
		<div class="applets">
			<!-- WARNING: Applet must not be inside the JQuery resizable plugin -->
			<!-- Default value for Internet Explorer -->
			<c:set var="classid" scope="request" value="clsid:8AD9C840-044E-11D1-B3E9-00805F499D93"/>
			<c:if test="${not fn:contains(header['user-agent'], 'MSIE')}">
				<c:set var="classid" scope="request" value="java:fr.mch.mdo.applets.DateTimeApplet.class"/>
			</c:if>
			<!-- For Google Chrome change object to applet and add code attribute -->
			<object id="dateTimeApplet" name="dateTimeApplet" type="application/x-java-applet" classid="${classid}" archive="SDateTimeApplet.jar" codebase="<c:out value="${pageContext.request.contextPath}"/>/jsp/commons" width="1" height="1">
				<param name="code" value="fr.mch.mdo.applets.DateTimeApplet"/>
				<param name="archive" value="SDateTimeApplet.jar"/>							
				<param name="codebase" value="<c:out value="${pageContext.request.contextPath}"/>/jsp/commons"/>
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
			</object>
		</div>	
		<tiles:useAttribute id="divPart" name="header" classname="java.lang.String"/>
		<c:if test="${divPart!='none'}">
			<div id="header">
				<div id="mdo-overlay" class="ui-widget-overlay mdo-overlay">
				</div>
				<div id="waiting-dialog">
				</div>
				<div id="header-resizable" class="mdo-resizable">
					<tiles:insertAttribute name="header"/>
			    </div>
		    </div>
	    </c:if>
		<tiles:useAttribute id="divPart" name="menu" classname="java.lang.String"/>
		<c:if test="${divPart!='none'}">
		    <div id="menu">
				<div id="menu-resizable" class="mdo-resizable">
			        <tiles:insertAttribute name="menu"/>
			    </div>
		    </div>
	    </c:if>
		<div id="<tiles:getAsString name="body-css"/>">
	    	<tiles:insertAttribute name="body"/>
	    </div>
		<tiles:useAttribute id="divPart" name="footer" classname="java.lang.String"/>
		<c:if test="${divPart!='none'}">
			<div id="footer">
				<div id="footer-resizable" class="mdo-resizable">
			    	<tiles:insertAttribute name="footer"/>
			    </div>
		    </div>
	    </c:if>
	</body>
</html>