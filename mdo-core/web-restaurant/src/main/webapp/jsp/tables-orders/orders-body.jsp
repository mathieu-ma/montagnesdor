<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/orders-body.js"></script>

<s:form action="TablesOrders" method="post">
	<s:hidden name="method:list"/>
	<div class="scroll-table-outer-body">
		<div class="scroll-table-inner-body">
			<table id="listsortable" class="sortable">
				<thead>
					<tr>
					    <th scope="col" class="th1"><label class="policeGray"><fmt:message key="tables.orders.label.quantity"/></label></th>
					    <th scope="col" class="th2"><label class="policeGray"><fmt:message key="tables.orders.label.code"/></label></th>
					    <th scope="col" class="th3"><label class="policeGray"><fmt:message key="tables.orders.label.description"/></label></th>
					    <th scope="col" class="th4"><label class="policeGray"><fmt:message key="tables.orders.label.unit.price"/></label></th>
					    <th scope="col" class="th5"><label class="policeGray"><fmt:message key="tables.orders.label.amount"/></label></th>
				  	</tr>
				</thead>
				<tbody id="orders-body">
					<tr id="hidden-order-line">
						<td><input class="quantity" type="text" name="quantity"></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>		
				<s:iterator value="form.dtoBean.orders" id="order">
					<tr>
						<td><s:property value="%{quantity!=null?getText('format.number.decimal.3.1',{quantity}):''}" /></td>
						<td><s:property value="code"/></td>
						<td><s:property value="label"/></td>
						<td><s:property value="%{unitPrice!=null?getText('format.number.decimal.3.2',{unitPrice}):''}"/></td>
						<td><s:property value="%{amount!=null?getText('format.number.decimal.3.2',{amount}):''}"/></td>
					</tr>		
				</s:iterator>
					<tr id="current-order-line">
						<td><input class="quantity" type="text" name="quantity"></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>		
				</tbody>
			</table>
		</div>
	</div>		
</s:form>

<div class="error">
	<s:actionerror/>
</div>
