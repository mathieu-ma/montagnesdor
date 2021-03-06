<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/mdo-table-as-enums.js"></script>

<s:form action="MdoTableAsEnumsManagerCUD" method="post" validate="false">

	<s:submit id="hidden-submit-form"/>
	<div class="mdo-upsidedown">
		<s:hidden name="method:save"/>
		<s:hidden name="form.dtoBean.id"/>
	
		<br/>
		<div class="hspacer-left-100p">	
			<s:select label="%{getText('enums.manager.type')}" cssClass="required"
		        name="form.dtoBean.type"
		        list="form.viewBean.existingTypes"
				value="form.dtoBean.type"
				required="true"
				headerKey=""
				headerValue="%{getText('enums.manager.type.select.header.value')}"
		 	/>
		 	<s:textfield name="form.userEntryType" size="84" maxlength="80" />
		</div>
		<div class="hspacer-left-100p">
			<s:textfield label="%{getText('enums.manager.name')}" name="form.dtoBean.name" size="84" maxlength="80" required="true" cssClass="required" />
			<s:textfield label="%{getText('enums.manager.order')}" name="form.dtoBean.order" required="true" cssClass="required number" />
			<s:textfield label="%{getText('enums.manager.default.label')}" name="form.dtoBean.defaultLabel" size="84" maxlength="80" required="true" cssClass="required" />
			<s:textfield label="%{getText('enums.manager.label.key')}" name="form.dtoBean.languageKeyLabel" size="84" maxlength="80" readonly="true" />
		</div>

		<div class="error" style="clear:both;">
			<s:actionerror/>
		</div>

		<s:include value="/jsp/commons/form-actions.jsp">
			<s:set var="cancelUrlMethod" value="%{'list'}" />
			<s:set name="cancelUrlParam" value="%{form.dtoBean.type}"/>
			<s:if test="%{form.dtoBean.type!='' && form.viewBean.existingTypes.contains(form.dtoBean.type)}">
				<s:set name="cancelUrlMethod" value="%{'listType'}"/>
				<s:set name="cancelUrlParam" value="%{form.dtoBean.type}"/>
			</s:if>
			<s:url id="cancelUrl" action="MdoTableAsEnumsManager" method="%{cancelUrlMethod}">
				<s:param name="form.dtoBean.type" value="%{cancelUrlParam}"/>
			</s:url>
			<s:param name="cancelUrl" value="%{cancelUrl}" />
		</s:include>

	</div>
</s:form>

