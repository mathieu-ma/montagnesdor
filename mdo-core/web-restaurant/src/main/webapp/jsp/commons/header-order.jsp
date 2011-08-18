<%--
	When you use an absolute URI, you do not have to add the taglib element to web.xml; 
	the JSP container automatically locates the TLD inside the JSTL library implementation.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>
<%@ taglib uri="/WEB-INF/mdo.tld" prefix="mdo" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

	<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/header-order.js"></script>

	<div id='header-order' class="mdo-upsidedown">
		<s:url id="tablesOrdersUrl" action="TablesOrders" method="form" />
		<s:form action="TablesOrders!form" method="post" id="TablesOrders" >
			<s:hidden id="user-entry-date" value="%{#session.userSession.entryDate.time}" />
			<table style="width: 80%">
				<tbody>
					<tr>
						<td style="width: 5%">
							<div class='header-order-table-number'>
								<s:textfield cssClass="string uppercase" labelposition="left" label="%{getText('header.table.number')}" name="form.dtoBean.dinnerTableDto.tableNumber" id="input-header-order-table-number" maxLength="4" size="4"/>
								<s:hidden name="form.dtoBean.dinnerTableDtoBean.id"/>
							</div>
						</td>
						<td style="width: 5%">
							<div class='header-order-takeaway'>
								<s:hidden id="prefixesTakeawayName" name="form.dtoBean.userContext.userAuthentication.restaurant.prefixesTakeawayName"/>
								<s:checkbox labelposition="left" label="%{getText('header.table.takeaway')}" name="form.dtoBean.dinnerTableDto.takeaway" id="checkbox-header-order-table-takeaway" fieldValue="false"/>
							</div>
						</td>
						<td style="width: 7%">
							<div class='header-order-table-customer'>
								<s:textfield cssClass="integer" labelposition="left" label="%{getText('header.table.customer')}" name="form.dtoBean.dinnerTableDto.customersNumber" id="input-header-order-table-customer" maxLength="4" size="4"/>
							</div>
						</td>
						<td style="width: 9%">
							<div class='header-order-auto-update'>
								<s:checkbox labelposition="left" label="%{getText('header.auto.update.order.line')}" name="header.order.auto.update" value="true"/>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</s:form>
	</div>
