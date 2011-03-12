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
						<div style="float: left; width: 34%;" class="label"><span class="required">*</span><fmt:message key="users.manager.worked.restaurants"/></div>
						<s:select name="form.dtoBean.restaurants.makeNew[0].restaurant.id" list="form.viewBean.restaurants" listKey="id" listValue="name" />
						<s:hidden name="form.dtoBean.restaurants.makeNew[0].user.id" value="%{form.dtoBean.id}" />
					</th>
					<th>
						<c:if test="${not empty form.viewBean.restaurants}">
							<button class="mdo-ui-button ui-state-default ui-corner-all" name="method:addRestaurant" style="float: left;">
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
			<s:iterator var="userRestaurant" value="form.dtoBean.restaurants" status="statusUserRestaurants">
				<tr>
					<td style="width: 33em;">	
						<s:property value="%{#userRestaurant.restaurant.name}" />
						<s:hidden name="form.dtoBean.restaurants(%{#userRestaurant.id}).restaurant.id" value="%{#userRestaurant.restaurant.id}" />
						<s:hidden name="form.dtoBean.restaurants(%{#userRestaurant.id}).restaurant.name" value="%{#userRestaurant.restaurant.name}" />
						<s:hidden name="form.dtoBean.restaurants(%{#userRestaurant.id}).user.id" value="%{form.dtoBean.id}" />
					</td>
					<td>
						<button class="mdo-ui-button ui-state-default ui-corner-all" name="method:removeRestaurant" value="${userRestaurant.id}">
							<span class="ui-icon ui-icon-trash"></span><fmt:message key="admin.manager.delete"/>
						</button>
					</td>		
				</tr>	
			</s:iterator>
			</tbody>
		</table>

	</div>
</div>