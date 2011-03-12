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
				<div style="float: left; width: 50%;" class="label"><fmt:message key="restaurants.manager.prefix.table.name"/></div>
				<s:select name="form.dtoBean.prefixTableNames.makeNew[0].prefix.id" list="form.viewBean.prefixTableNames" listKey="id" listValue="name" />
			</th>
			<th>
				<div style="float: left; width: 50%;" class="label"><fmt:message key="restaurants.manager.table.type"/></div>
				<s:select name="form.dtoBean.prefixTableNames.makeNew[0].type.id" list="form.viewBean.tableTypes" listKey="id" listValue="code.name" />
				<s:hidden name="form.dtoBean.prefixTableNames.makeNew[0].restaurant.id" value="%{form.dtoBean.id}" />
			</th>
			<th>
				<c:if test="${not empty form.viewBean.prefixTableNames}">
					<button class="mdo-ui-button ui-state-default ui-corner-all" name="method:addPrefix" style="float: left;">
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
	<s:iterator var="prefixTableName" value="form.dtoBean.prefixTableNames" status="statusPrefixTableNames">
		<tr>
			<td>	
				<s:property value="%{#prefixTableName.prefix.name}" />
				<s:hidden name="form.dtoBean.prefixTableNames(%{#prefixTableName.id}).prefix.id" value="%{#prefixTableName.prefix.id}" />
				<s:hidden name="form.dtoBean.prefixTableNames(%{#prefixTableName.id}).prefix.name" value="%{#prefixTableName.prefix.name}" />
				<s:hidden name="form.dtoBean.prefixTableNames(%{#prefixTableName.id}).type.id" value="%{#prefixTableName.type.id}" />
				<s:hidden name="form.dtoBean.prefixTableNames(%{#prefixTableName.id}).type.code.name" value="%{#prefixTableName.type.code.name}" />
				<s:hidden name="form.dtoBean.prefixTableNames(%{#prefixTableName.id}).restaurant.id" value="%{form.dtoBean.id}" />
			</td>
			<td>
				<s:property value="%{#prefixTableName.type.code.name}" />
			</td>
			<td>
				<button class="mdo-ui-button ui-state-default ui-corner-all" name="method:removePrefix" value="${prefixTableName.id}">
					<span class="ui-icon ui-icon-trash"></span><fmt:message key="admin.manager.delete"/>
				</button>
			</td>		
		</tr>	
	</s:iterator>
	</tbody>
</table>
	</div>
</div>