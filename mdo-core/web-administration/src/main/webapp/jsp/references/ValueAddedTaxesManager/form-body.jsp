<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/mdo.tld" prefix="mdo" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/value-added-taxes.js"></script>

<s:form action="ValueAddedTaxesManagerCUD" method="post" validate="false">
	<s:submit id="hidden-submit-form"/>
	<div class="mdo-upsidedown">
		<s:hidden name="method:save"/>
		<s:hidden name="form.dtoBean.id"/>

		<div class="hspacer-left-100p">	
			<c:if test="${empty form.dtoBean.id}">
				<s:select label="%{getText('value.added.taxes.manager.code.name')}" name="form.dtoBean.code.id" list="form.viewBean.codes" listKey="id" listValue="name" value="form.dtoBean.code.id" required="true" />
			</c:if>
			<c:if test="${not empty form.dtoBean.id}">
				<s:hidden name="form.dtoBean.code.id" />
				<s:hidden name="form.dtoBean.code.name" />
				<s:hidden name="form.dtoBean.code.languageKeyLabel" />
				<s:label label="%{getText('value.added.taxes.manager.code.name')}" name="form.dtoBean.code.name" />
				<s:label label="%{getText('value.added.taxes.manager.code.label.key')}" name="form.dtoBean.code.languageKeyLabel" />
			</c:if>
			
		 	<%-- 
		 		Change Locale for Number Format in input text because of javascript
		 		Use the change of locale instead of BigDecimal Converter because we want that user type "." instead of "," in case of french locale 
		 	--%>
			<s:text name="value.added.taxes.manager.rate" var="tvaRateLabel" />
			<mdo:setLocale value="en"/>
			<s:textfield cssClass="number-float-percent required" label="%{tvaRateLabel}"
				name="form.dtoBean.rate" value="%{form.dtoBean.rate==null?'':getText('format.number.decimal.3.2',{form.dtoBean.rate})}" required="true" />
		 	<%-- Change back Locale --%>
			<mdo:setLocale value="${userSession.currentLocale.languageCode}"/>
		</div>

		<div class="error" style="clear:both;">
			<s:actionerror/>
		</div>
		<s:include value="/jsp/commons/form-actions.jsp">
			<s:url id="cancelUrl" action="ValueAddedTaxesManager" method="list" />
			<s:param name="cancelUrl" value="%{cancelUrl}" />
		</s:include>
	</div>	
</s:form>

<div class="error">
	<s:actionerror/>
</div>
