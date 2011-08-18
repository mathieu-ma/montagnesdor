<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form>
	<input type="hidden" name="ajax-order-line-by-user" id="ajax-order-line-by-user" value="true"/>
	<table>
		<tr id="ajax-order-line">
			<td class="orl1"><input type="text" maxlength="5" size="10" class="number-float" value="1.1"/></td>
			<td class="orl2"><input type="text" maxlength="5" size="10" class="string-code uppercase" readonly="readonly" value="11"/></td>
			<td class="orl3"><input type="text" maxlength="5" size="10" class="string" readonly="readonly" value="Nems"/></td>
			<td class="orl4"><input type="text" maxlength="5" size="10" class="number-float" readonly="readonly" value="10"/></td>
			<td class="orl5"><input type="text" maxlength="5" size="10" class="number-float" readonly="readonly" value="10.1"/></td>
		</tr>
	</table>
</form>