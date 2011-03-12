<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<form>
	<s:select id="psc_long_code" label="%{getText('product.special.codes.manager.long.code')}" name="form.dtoBean.daoBean.longCode" list="form.dtoBean.productSpecialCodes" listKey="name" listValue="name" value="form.dtoBean.daoBean.longCode" required="true"/>

	<div id="hidden_div_psc_short_code">
		<c:forEach var="psc" items="${form.dtoBean.productSpecialCodes}">
			<input type="hidden" disabled="disabled" name="${psc.name}" value="${psc.shortCode}"/>
		</c:forEach>
	</div>
</form>
