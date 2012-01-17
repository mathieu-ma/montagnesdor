<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<%
    response.setContentType("text/xml");
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("pragma","no-cache");
%>
<order-line>
	<id>${form.orderLine.id}</id>
	<dinnerTableId><c:if test="${not empty form.orderLine.dinnerTableId}">${form.orderLine.dinnerTableId}</c:if></dinnerTableId>
	<color><c:if test="${not empty form.orderLine.color}">${form.orderLine.color}</c:if></color>
	<quantity>${form.orderLine.quantity}</quantity>
	<code>${form.orderLine.code}</code>
	<label>${form.orderLine.label}</label>
	<price>${form.orderLine.price}</price>
	<amount>${form.orderLine.amount}</amount>
	<dataCode>${form.orderLine.dataCode}</dataCode>
	<quantitiesSum></quantitiesSum>
	<amountsSum></amountsSum>
</order-line>

<s:if test="hasActionErrors()">
<%
	// Simulate a NullPointerException.
	String nullable = null;
	// Here an exception will be thrown.
	nullable.isEmpty();
%>
</s:if>
