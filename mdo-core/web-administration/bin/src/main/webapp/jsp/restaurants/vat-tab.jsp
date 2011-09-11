<%@ taglib uri="/WEB-INF/mdo.tld" prefix="mdo" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<div class="scroll-table-outer-body">
	<div class="scroll-table-inner-body" style="height: 270px; width: 300px;">
		<table id="vatsortable" class="sortable">
			<thead>
				<tr>
					<th><fmt:message key="restaurants.manager.vat"/></th>
				</tr>
			</thead>
			<tbody>
			<s:iterator var="vat" value="form.viewBean.valueAddedTaxes" status="statusVat">
				<s:set var="doesRestaurantVatExist" value="false" />
				<s:set var="currentRestaurantVatId" value="" />
				<tr style="height: 3em;">
					<td>
		 				<div style="padding-left: 4em; float: left; width: 4em;"><s:property value="%{getText('format.number.decimal.3.2',{#vat.rate})}" /></div>
						<s:hidden name="form.dtoBean.vats(%{#statusVat.index}).vat.id" value="%{#vat.id}" />
						<s:hidden name="form.dtoBean.vats(%{#statusVat.index}).restaurant.id" value="%{form.dtoBean.id}" />
			    		<s:set var="checked" value="" />
			    		<s:if test="%{form.vats.containsKey(#vat.id) && form.vats[#vat.id]!='false'}">
				    		<s:set var="checked" value="checked='checked'" />
			    		</s:if>
			    		<input type="checkbox" name="form.vats[<s:property value="%{#vat.id}"/>]" value="<s:property value="%{form.vats[#vat.id]}"/>" <s:property value="%{checked}"/> />
					</td>
				</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
</div>