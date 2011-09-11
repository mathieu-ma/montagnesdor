<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/value-added-taxes2.js"></script>

<s:form action="ValueAddedTaxesManager" method="post" validate="true">
	<s:submit id="hidden-submit-form"/>
	<div class="mdo-upsidedown">
		<s:hidden name="method:save"/>
		<s:hidden name="form.dtoBean.daoBean.id"/>
	
		<s:textfield cssClass="number-float-percent" label="%{getText('value.added.taxes.manager.value')}" name="form.dtoBean.daoBean.value" value="%{getText('format.number.decimal.3.2',{form.dtoBean.daoBean.value})}"/>

		<s:select label="%{getText('value.added.taxes.manager.restaurant')}" name="form.dtoBean.daoBean.restaurant.id" list="form.dtoBean.restaurants" listKey="id" listValue="name" value="form.dtoBean.daoBean.restaurant.id" required="true"/>
		
		<div class="clear">
			<br/><br/>
		</div>
		<div class="hspacer-left-100p">		
			<div class="hspacer-left-50p">
				<span id="submit" class="mdo-ui-button ui-state-default ui-corner-all">
					<span class="ui-icon ui-icon-disk"></span>
					<fmt:message key="admin.manager.save"/>
				</span>
			</div>
	   		<div class="hspacer-left-50p">
				<span class="mdo-ui-button ui-state-default ui-corner-all">
					<span class="ui-icon ui-icon-closethick"></span>
					<s:url id="url" action="ValueAddedTaxesManager" method="list" includeParams="none"/>
	  				<s:a id="admin-manager-cancel" href="%{url}"><fmt:message key="admin.manager.cancel"/></s:a>
				</span>
	   		</div>
		</div>
		<div class="clear">
			<br/><br/>
		</div>
	</div>	
</s:form>

<div class="error">
	<s:actionerror/>
</div>