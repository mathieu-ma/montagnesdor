<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<%-- START For testing --%>
<link rel="stylesheet" media="screen" type="text/css" href="<c:out value="${pageContext.request.contextPath}"/>/css/orders.css" />
<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/orders-body.js"></script>
<%-- END For testing --%>

<s:form action="TablesOrders" method="post">
	<s:hidden name="method:list"/>
	<div class="scroll-table-outer-body">
		<div class="scroll-table-inner-body">
			<table id="listsortable" class="sortable" border="1">
				<thead>
					<tr>
				    	<th><fmt:message key="tables.orders.table.number"/></th>
				    	<th><fmt:message key="tables.orders.customers.number"/></th>
				    	<th><fmt:message key="tables.orders.quantities"/></th>
				    	<th><fmt:message key="tables.orders.amount"/></th>
				    	<th><fmt:message key="tables.orders.registration.date"/></th>
				    	<th><fmt:message key="tables.orders.printing.date"/></th>
					    <th style="width: 21em;">
							<div style="float: left;">
								<span class="mdo-ui-button ui-state-default ui-corner-all">
									<span class="ui-icon ui-icon-pencil"></span>
				   					<s:url id="url" action="RestaurantsManager" method="form" includeParams="none" />
									<s:a href="%{url}"><fmt:message key="mdo.create"/></s:a>
								</span>
							</div>
							<div class="error">
								<s:actionerror cssClass="ui-helper-reset" />
							</div>
							<div class="error">
								<s:actionmessage cssClass="ui-helper-reset" />
							</div>
						</th>
				  	</tr>
				</thead>
				<tbody>
				<s:iterator value="form.list" id="table">
					<tr>
						<td class="mdo-code">
							<%-- Use displayDinnerTableById instead of displayDinnerTable --%>
							<s:url id="url" action="TablesOrders" method="displayDinnerTable">
	    						<s:param name="form.dtoBean.number" value="%{number}"/>
	    						<s:param name="form.dtoBean.customersNumber" value="%{customersNumber}"/>
	    					</s:url>
	    					<s:a id="%{id}" href="%{url}"><s:property value="number"/></s:a>
						</td>
						<td class="number"><s:property value="customersNumber"/></td>
						<td class="number"><s:property value="%{quantitiesSum!=null?getText('format.number.decimal.3.1',{quantitiesSum}):''}"/></td>
						<td class="number"><s:property value="%{amountPay!=null?getText('format.number.decimal.3.2',{amountPay}):''}"/></td>

						<td class="mdo-date mdo-code">
							<s:date name="registrationDate" format="EEEE dd MMMM yyyy HH:mm:ss"/>
							<!-- Used for javascript sortable -->
							<fmt:formatDate var="formattedDate" value="${table.registrationDate}" pattern="yyyy/MM/dd" />
							<input type="hidden" name="registrationDateHidden" value="${formattedDate}" />
						</td>
						<td class="mdo-date mdo-code">
							<s:date name="printingDate" format="EEEE dd MMMM yyyy"/>
							<!-- Used for javascript sortable -->
							<fmt:formatDate var="formattedDate" value="${table.printingDate}" pattern="yyyy/MM/dd" />
							<input type="hidden" name="printingDateHidden" value="${formattedDate}" />
						</td>
					    <td>
			    			<div>
								<span class="mdo-ui-button ui-state-default ui-corner-all">
									<span class="ui-icon ui-icon-trash"></span>
			    					<s:url id="url" action="TablesOrders" method="deleteTable">
			    						<s:param name="form.dtoBean.id" value="%{id}"/>
			    					</s:url>
			    					<s:a id="%{id}" href="%{url}"><fmt:message key="mdo.delete"/></s:a>
								</span>
				    		</div>
					    </td>
					</tr>		
				</s:iterator>
				</tbody>
			</table>
		</div>
	</div>		
</s:form>

<div class="error">
	<s:actionerror/>
</div>
