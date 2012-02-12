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

		<title><fmt:message key="mdo.welcome.restaurant"/> - <tiles:getAsString name="title"/></title>
	</head>
	<body>
	    <%-- Put all javascripts at the bottom page see http://developer.yahoo.com/performance/rules.html#js_bottom --%>
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
		<div id="global-transparent" class="global-transparent-hidden">&nbsp;</div>
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
		<div id="menu-body-footer">
		    <jsp:include page="menu-body-footer-main-layout.jsp" />
		</div>    
	</body>
</html>