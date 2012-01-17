<%@ taglib uri="/WEB-INF/mdo.tld" prefix="mdo" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<link rel="stylesheet" media="screen" type="text/css" href="<c:out value="${pageContext.request.contextPath}"/>/css/orders.css" />
<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/orders-body.js"></script>

<s:form action="TablesOrders!saveOrderLine" method="post">
 	<%-- 
 		Change Locale for Number Format in input text because of javascript
 		Use the change of locale instead of BigDecimal Converter because we want that user type "." instead of "," in case of french locale 
 	--%>
	<mdo:setLocale value="en"/>

	<s:hidden id="dinnerTableId" name="form.dtoBean.id" />
	<div class="scroll-table-outer-body">
		<div class="scroll-table-inner-body">
			<table id="table-orders" class="sortable" border="1">
				<thead>
					<tr>
						<th class="orders-quantity-header"><fmt:message key="tables.orders.label.quantity"/></th>
						<th class="orders-code-header"><fmt:message key="tables.orders.label.code"/></th>
						<th class="orders-label-header"><fmt:message key="tables.orders.label.description"/></th>
						<th class="orders-unit-price-header"><fmt:message key="tables.orders.label.unit.price"/></th>
						<th class="orders-amount-header"><fmt:message key="tables.orders.label.amount"/></th>
				  	</tr>
				</thead>
				<tfoot id="foot-orders">
					<tr>
						<td class="cell0 cell-center-align"><input class="text0" type="text" name="quantity" autocomplete="OFF"></td>
						<td class="cell1"></td>
						<td class="cell2"></td>
						<td class="cell3"></td>
						<td class="cell4"></td>
					</tr>		
				</tfoot>
				<tbody id="body-orders">
					<c:set var="index" value="0" />
					<s:iterator value="form.dtoBean.orders" id="order" status="status">
						<c:set var="cssStyleBgColor" value="" />
						<c:if test="${not empty order.product.colorRGB}"><c:set var="cssStyleBgColor">style="background-color: #${order.product.colorRGB}"</c:set></c:if>
						<c:set var="dataCode" value="" />
						<c:if test="${not empty order.dataCode}"><c:set var="dataCode">data-code=${order.code}</c:set></c:if>
						<tr id="${order.id}" <c:out value="${dataCode}" /> <c:out value="${cssStyleBgColor}" escapeXml="false"/>>
							<td class="cell0"><s:property value="%{quantity!=null?getText('format.number.decimal.3.2',{quantity}):''}" /></td>
							<td class="cell1"><s:property value="code"/></td>
							<td class="cell2"><s:property value="label"/></td>
							<td class="cell3"><s:property value="%{unitPrice!=null?getText('format.number.decimal.3.2',{unitPrice}):''}"/></td>
							<td class="cell4"><s:property value="%{amount!=null?getText('format.number.decimal.3.2',{amount}):''}"/></td>
						</tr>		
					</s:iterator>
				</tbody>
			</table>
		</div>
	</div>
 	<%-- Change back Locale --%>
	<mdo:setLocale value="${userSession.currentLocale.languageCode}"/>
</s:form>

<div class="error">
	<s:actionerror/>
</div>
