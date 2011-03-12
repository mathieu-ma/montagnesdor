<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<s:iterator var="userRestaurant" value="form.dtoBean.userRestaurants">
	<c:set var="checked"></c:set>
	<c:if test="${userRestaurant.id==form.dtoBean.daoBean.restaurant.id}">
		<c:set var="checked">selected="selected"</c:set>
	</c:if>
	<option value="<c:out value="${userRestaurant.id}"/>" <c:out value="${checked}"/>><c:out value="${userRestaurant.name}"/></option>
</s:iterator>
