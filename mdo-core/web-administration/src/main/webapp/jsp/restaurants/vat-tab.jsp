<%@ taglib uri="/WEB-INF/mdo.tld" prefix="mdo" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<div class="scroll-table-outer-body hspacer-left-50p">
	<div class="scroll-table-inner-body" style="height: 270px; width: 300px;">
		<table id="vatsortable" class="sortable">
			<thead>
				<tr>
					<th><fmt:message key="restaurants.manager.managed.vats"/></th>
				</tr>
			</thead>
			<tbody>
			<s:iterator var="vat" value="form.viewBean.valueAddedTaxes" status="statusVat">
				<s:set var="doesRestaurantVatExist" value="false" />
				<s:set var="currentRestaurantVatId" value="" />
				<tr style="height: 3em;">
					<td>
		 				<div id="id.form.vats[<s:property value="%{#vat.id}"/>]" style="padding-left: 4em; float: left; width: 4em; text-align: right;"><s:property value="%{getText('format.number.decimal.3.2',{#vat.rate})}" /></div>
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
<div class="hspacer-left-50p">
	<s:select cssClass="required" cssStyle="text-align: right;" id="default-vat-custom-order-line" name="form.dtoBean.vat.id" required="true" key="restaurants.manager.default.vat.custom.order.line"
		list="form.dtoBean.vats" listKey="vat.id" listValue="%{getText('format.number.decimal.3.2',{vat.rate})}" />
</div>
