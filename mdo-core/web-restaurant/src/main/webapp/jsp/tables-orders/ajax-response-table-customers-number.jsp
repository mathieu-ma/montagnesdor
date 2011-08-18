<%--
	When you use an absolute URI, you do not have to add the taglib element to web.xml; 
	the JSP container automatically locates the TLD inside the JSTL library implementation.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form><input type="hidden" value="<c:out value="${form.dtoBean.customersNumber}"/>" id="ajax-header_order_table_customer"/></form>