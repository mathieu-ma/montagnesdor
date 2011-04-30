<%@ taglib uri="/WEB-INF/mdo.tld" prefix="mdo" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<div class="scroll-table-outer-body">
	<div class="scroll-table-inner-body">
		<table>
			<thead>
				<tr>
					<th style="width: 33em;">
						<div style="float: left; width: 34%;" class="label"><span class="required">*</span><fmt:message key="products.manager.category"/></div>
						<s:select name="form.dtoBean.categories.makeNew[0].category.id" list="form.viewBean.categories" listKey="id" listValue="code.name" />
						<s:hidden name="form.dtoBean.categories.makeNew[0].product.id" value="%{form.dtoBean.id}" />
					</th>
					<th>
						<c:if test="${not empty form.viewBean.categories}">
							<button class="mdo-ui-button ui-state-default ui-corner-all" name="method:addCategory" style="float: left;">
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
			<s:iterator var="productCategory" value="form.dtoBean.categories" status="statusProductCategories">
				<tr>
					<td style="width: 33em;">	
						<s:property value="%{#productCategory.category.code.name}" />
						<s:hidden name="form.dtoBean.categories(%{#productCategory.id}).category.id" value="%{#productCategory.category.id}" />
						<s:hidden name="form.dtoBean.categories(%{#productCategory.id}).category.name" value="%{#productCategory.category.name}" />
						<s:hidden name="form.dtoBean.categories(%{#productCategory.id}).product.id" value="%{form.dtoBean.id}" />
					</td>
					<td>
						<button class="mdo-ui-button ui-state-default ui-corner-all" name="method:removeCategory" value="${productCategory.id}">
							<span class="ui-icon ui-icon-trash"></span><fmt:message key="admin.manager.delete"/>
						</button>
					</td>		
				</tr>	
			</s:iterator>
			</tbody>
		</table>

	</div>
</div>