<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/product-special-codes.js"></script>

<s:form action="ProductSpecialCodesManagerCUD" method="post" validate="false">
	<s:submit id="hidden-submit-form"/>
	<div class="mdo-upsidedown">
		<s:hidden name="form.dtoBean.id"/>
		<div class="hspacer-left-100p">
			<s:label label="%{getText('product.special.codes.manager.restaurant')}" value="%{form.dtoBean.restaurant.name}" required="true" />
			<s:hidden name="form.dtoBean.restaurant.id" />
			<s:if test="%{form.dtoBean.id==null}">
				<s:select label="%{getText('product.special.codes.manager.long.code')}" name="form.dtoBean.code.id" list="form.viewBean.codes" listKey="id" listValue="name" value="form.dtoBean.code.id" required="true"/>
			</s:if>
			<s:else>
				<s:label label="%{getText('product.special.codes.manager.long.code')}" value="%{form.dtoBean.code.name}" required="true" />
				<s:hidden name="form.dtoBean.code.id" />
			</s:else>
			<s:textfield label="%{getText('product.special.codes.manager.short.code')}" name="form.dtoBean.shortCode" />
			<s:select label="%{getText('product.special.codes.manager.vat')}" name="form.dtoBean.vat.id" value="form.dtoBean.vat.id" emptyOption="true" 
				list="form.restaurant.vats" listKey="vat.id" listValue="%{getText('format.number.decimal.3.2',{vat.rate})}" required="false" />
			<div style="height: 2em;">
				<div class="error">
					<s:actionerror cssClass="ui-helper-reset" />
				</div>
				<div class="error">
					<s:actionmessage cssClass="ui-helper-reset" />
				</div>
			</div>
		</div>
		<s:set var="backUrlMethod" value="%{'list'}" />
		<s:if test="%{form.dtoBean.restaurant.id!='' && form.viewBean.restaurants.contains(form.dtoBean.restaurant)}">
			<s:set name="backUrlMethod" value="%{'listProductSpecialCodes'}"/>
		</s:if>
		<s:set name="backUrlParam1" value="%{form.dtoBean.restaurant.id}"/>
		<s:set name="backUrlParam2" value="%{form.dtoBean.restaurant.reference}"/>
		<s:set name="backUrlParam3" value="%{form.dtoBean.restaurant.name}"/>
		<s:url id="backUrl" action="ProductSpecialCodesManager" method="%{backUrlMethod}"  escapeAmp='false'>
			<s:param name="form.dtoBean.restaurant.id" value="%{backUrlParam1}"/>
			<s:param name="form.dtoBean.restaurant.reference" value="%{backUrlParam2}"/>
			<s:param name="form.dtoBean.restaurant.name" value="%{backUrlParam3}"/>
		</s:url>
		<jsp:include page="../../commons/crud-list.jsp">
			<jsp:param name="cssId" value="languages"/>
			<jsp:param name="action" value="ProductSpecialCodesManager"/>
			<jsp:param name="labelSelectList" value="product.special.codes.manager.language"/>
			<jsp:param name="selectList" value="form.viewBean.languages"/>
			<jsp:param name="selectedValueInSelectList" value="${session.userSession.currentLocale.id}"/>
			<jsp:param name="iteratorList" value="form.dtoBean.labels"/>
			<jsp:param name="cssDataProperty" value=""/>
			<jsp:param name="dataProperty" value=""/>
			<jsp:param name="labelData" value="product.special.codes.manager.label"/>
			<jsp:param name="backUrl" value="${backUrl}" />
		</jsp:include>
	</div>
</s:form>