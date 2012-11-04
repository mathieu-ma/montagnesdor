<%@ taglib uri="/WEB-INF/mdo.tld" prefix="mdo" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<div class="scroll-table-outer-body" style="padding: 6.5em 0 3em;">
	<div class="scroll-table-inner-body">
		<table class="sortable">
			<thead>
				<tr>
					<th>
						<s:select label="%{getText('restaurants.manager.table.type')}" name="form.dtoBean.reductionTables.makeNew[0].type.id" list="form.viewBean.reductionTableTypes" listKey="id" listValue="code.name" />
						<s:hidden name="form.dtoBean.reductionTables.makeNew[0].restaurant.id" value="%{form.dtoBean.id}" />
					</th>
					<th style="width: 50em;">
						<div class="mdo-upsidedown-reduction-table-tab">
						 	<%-- 
						 		Change Locale for Number Format in input text because of javascript
						 		Use the change of locale instead of BigDecimal Converter because we want that user type "." instead of "," in case of french locale 
						 	--%>
							<s:text name="restaurants.manager.min.amount.reduction" var="reductionMinAmountLabel" />
							<s:text name="restaurants.manager.basic.reduction" var="reductionValueLabel" />
							<mdo:setLocale value="en"/>
							<s:textfield cssClass="number-float" label="%{reductionMinAmountLabel}"  name="form.dtoBean.reductionTables.makeNew[0].minAmount" value="%{getText('format.number.decimal.3.2',{form.dtoBean.reductionTables.makeNew[0].minAmount})}" />
							<s:textfield cssClass="number-float-percent" label="%{reductionValueLabel}" name="form.dtoBean.reductionTables.makeNew[0].value" value="%{getText('format.number.decimal.3.2',{form.dtoBean.reductionTables.makeNew[0].value})}" />
						 	<%-- Change back Locale --%>
							<mdo:setLocale value="${userSession.currentLocale.languageCode}"/>
						</div>
					</th>
					<th>
						<c:if test="${not empty form.viewBean.reductionTableTypes}">
							<button class="mdo-ui-button ui-state-default ui-corner-all" name="method:addReduction" style="float: left;">
								<span class="ui-icon ui-icon-extlink"></span><fmt:message key="admin.manager.add" />
							</button>
						</c:if>
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
			<s:iterator var="reductionTable" value="form.dtoBean.reductionTables" status="statusReductionTables">
				<tr>
					<td>	
						<s:property value="%{#reductionTable.type.code.name}" />
						<s:hidden name="form.dtoBean.reductionTables(%{#reductionTable.id}).type.id" value="%{#reductionTable.type.id}" />
						<s:hidden name="form.dtoBean.reductionTables(%{#reductionTable.id}).restaurant.id" value="%{form.dtoBean.id}" />
					</td>
					<td style="width: 50em;">
					 	<%-- 
					 		Change Locale for Number Format in input text because of javascript
					 		Use the change of locale instead of BigDecimal Converter because we want that user type "." instead of "," in case of french locale 
					 	--%>
						<mdo:setLocale value="en"/>
						<s:textfield cssClass="number-float readonly" readonly="true" label="%{reductionMinAmountLabel}"  name="form.dtoBean.reductionTables(%{#reductionTable.id}).minAmount" value="%{getText('format.number.decimal.3.2', {#reductionTable.minAmount})}" />
						<s:textfield cssClass="number-float-percent readonly" readonly="true" label="%{reductionValueLabel}" name="form.dtoBean.reductionTables(%{#reductionTable.id}).value" value="%{getText('format.number.decimal.3.2', {#reductionTable.value})}" />
					 	<%-- Change back Locale --%>
						<mdo:setLocale value="${userSession.currentLocale.languageCode}"/>
					</td>
					<td>
						<button class="mdo-ui-button ui-state-default ui-corner-all" name="method:removeReduction" value="${reductionTable.id}">
							<span class="ui-icon ui-icon-trash"></span><fmt:message key="admin.manager.delete"/>
						</button>
					</td>		
				</tr>	
			</s:iterator>
			</tbody>
		</table>
	</div>
</div>