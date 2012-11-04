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

	<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/orders-header.js"></script>

	<div id='header-order' class="mdo-upsidedown">
		<s:form action="TablesOrders!displayDinnerTable" method="post" id="HeaderTablesOrders" >
			<s:hidden id="user-entry-date" name="user-entry-date" value="%{#session.userSession.entryDate.time}" />
			<table style="width: 80%">
				<tbody>
					<tr>
						<td style="width: 5%">
							<div class='header-order-table-number'>
								<s:textfield cssClass="string uppercase" readonly="%{form.dtoBean.number!=null && form.dtoBean.number!=''}" labelposition="left" label="%{getText('header.table.number')}" name="form.dtoBean.number" id="input-header-order-table-number" maxLength="4" size="4"/>
								<s:hidden name="form.dtoBean.id"/>
							</div>
						</td>
						<td style="width: 5%">
							<c:set var="headerOrderTakeawayCssStyle" />
							<s:if test="#session.userSession.userAuthentication.restaurant.prefixTakeawayNames.length>0">
								<c:set var="headerOrderTakeawayCssStyle">style="visibility: hidden;"</c:set>
							</s:if>
							<div class='header-order-takeaway' <c:out value="${headerOrderTakeawayCssStyle}" escapeXml="false" /> >
								<s:hidden id="prefixesTakeawayName" value="#session.userSession.userAuthentication.restaurant.prefixTakeawayNames" />
								<s:checkbox labelposition="left" label="%{getText('header.table.takeaway')}" name="form.dtoBean.takeaway" id="checkbox-header-order-table-takeaway" fieldValue="false"/>
							</div>
						</td>
						<td style="width: 7%">
							<c:set var="headerOrderTableCustomerCssStyle">style="visibility: hidden;"</c:set>
							<s:if test="%{form.dtoBean.customersNumber!=null}">
								<c:set var="headerOrderTableCustomerCssStyle" />
							</s:if>
							<div class='header-order-table-customer' <c:out value="${headerOrderTableCustomerCssStyle}" escapeXml="false" /> >
								<s:textfield cssClass="integer" readonly="%{form.dtoBean.customersNumber!=null && form.dtoBean.customersNumber!=''}" labelposition="left" label="%{getText('header.table.customer')}" name="form.dtoBean.customersNumber" id="input-header-order-table-customer" maxLength="4" size="4"/>
							</div>
						</td>
						<td style="width: 10%">
							<div class='header-order-auto-update'>
								<s:checkbox id="header-order-auto-update" labelposition="left" label="%{getText('header.auto.update.order.line')}" name="header.order.auto.update" value="true"/>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</s:form>
	</div>
