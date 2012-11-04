<%@ taglib uri="/WEB-INF/mdo.tld" prefix="mdo" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<div class="scroll-table-outer-body">
	<div class="scroll-table-inner-body">

		<table class="sortable">
			<thead>
				<tr>
					<th>
						<div style="float: left; width: 50%;" class="label"><fmt:message key="restaurants.manager.vat"/></div>
						<s:select name="form.dtoBean.vatTableTypes.makeNew[0].vat.id"
							list="form.dtoBean.vats" listKey="vat.id" listValue="%{getText('format.number.decimal.3.2',{vat.rate})}" required="true"/>
					</th>
					<th>
						<div style="float: left; width: 50%;" class="label"><fmt:message key="restaurants.manager.table.type"/></div>
						<s:select name="form.dtoBean.vatTableTypes.makeNew[0].type.id" list="form.viewBean.vatTableTypes" listKey="id" listValue="code.name" />
						<s:hidden name="form.dtoBean.vatTableTypes.makeNew[0].restaurant.id" value="%{form.dtoBean.id}" />
					</th>
					<th>
						<c:if test="${not empty form.viewBean.vatTableTypes}">
							<button class="mdo-ui-button ui-state-default ui-corner-all" name="method:addVatTableType" style="float: left;">
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
				<s:iterator var="vatTableType" value="form.dtoBean.vatTableTypes" status="statusVatTableTypes">
					<tr>
						<td>	
							<s:property value="%{getText('format.number.decimal.3.2',{#vatTableType.vat.rate})}" />
							<s:hidden name="form.dtoBean.vatTableTypes(%{#vatTableType.id}).vat.id" value="%{#vatTableType.vat.id}" />
							<s:hidden name="form.dtoBean.vatTableTypes(%{#vatTableType.id}).vat.rate" value="%{#vatTableType.vat.rate}" />
							<s:hidden name="form.dtoBean.vatTableTypes(%{#vatTableType.id}).type.id" value="%{#vatTableType.type.id}" />
							<s:hidden name="form.dtoBean.vatTableTypes(%{#vatTableType.id}).type.code.name" value="%{#vatTableType.type.code.name}" />
							<s:hidden name="form.dtoBean.vatTableTypes(%{#vatTableType.id}).restaurant.id" value="%{form.dtoBean.id}" />
						</td>
						<td>
							<s:property value="%{#vatTableType.type.code.name}" />
						</td>
						<td>
							<button class="mdo-ui-button ui-state-default ui-corner-all" name="method:removeVatTableType" value="${vatTableType.id}">
								<span class="ui-icon ui-icon-trash"></span><fmt:message key="admin.manager.delete"/>
							</button>
						</td>		
					</tr>	
				</s:iterator>
			</tbody>
		</table>
	</div>
</div>