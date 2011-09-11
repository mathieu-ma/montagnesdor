<%@ taglib uri="/WEB-INF/mdo.tld" prefix="mdo" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<s:form action="TableTypesManagerCUD" method="post" validate="false">
	<s:submit id="hidden-submit-form"/>

	<div class="mdo-upsidedown">
		<s:hidden name="method:save"/>
		<s:hidden name="form.dtoBean.id"/>

		<br/>
	 	<div class="hspacer-left-100p">
			<s:select label="%{getText('restaurants.manager.specific.round')}" name="form.dtoBean.code.id" list="form.viewBean.tableTypes" listKey="id" listValue="name" value="form.dtoBean.code.id" required="true"/>
		</div>
	
		<s:include value="/jsp/commons/form-actions.jsp" />
	</div>
</s:form>

<div class="error">
	<s:actionerror/>
</div>

