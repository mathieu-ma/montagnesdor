<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>

<menu>

	<sub-menu id="0" class-selected="toggle">
		<label><fmt:message key="menu.manage.locales"/></label>
		<action><c:out value="${pageContext.request.contextPath}"/>/LocalesManager.do?forwardKey=list</action>
	</sub-menu>
	<item id="1">
		<label><fmt:message key="menu.manage.restaurants"/></label>
		<action><c:out value="${pageContext.request.contextPath}"/>/RestaurantsManager.do?forwardKey=list</action>
	</item>
	<item id="2">
		<label><fmt:message key="menu.manage.users"/></label>
		<action><c:out value="${pageContext.request.contextPath}"/>/UsersManager.do?forwardKey=list</action>
	</item>
	
</menu>

