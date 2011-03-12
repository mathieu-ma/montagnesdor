<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/product-special-codes.js"></script>

<s:if test="hasActionErrors()">
	<div class="ui-widget">
		<div style="padding: 0pt 0.7em;" class="ui-state-error ui-corner-all">
			<p>
			<span style="float: left; margin-right: 0.3em;" class="ui-icon ui-icon-alert"></span> 
				<s:iterator value="actionErrors">
					<s:property escape="false"/>
				</s:iterator>
			</p>
		</div>
	</div>
</s:if>

<s:form action="ProductSpecialCodesManager" method="post" validate="true">
	<s:submit id="hidden-submit-form"/>
	<div class="mdo-upsidedown">
		<s:hidden name="method:labels"/>
		<s:hidden name="form.dtoBean.daoBean.id"/>
	
			<s:select id="restaurant" label="%{getText('product.special.codes.manager.restaurant')}" name="form.dtoBean.daoBean.restaurant.id" list="form.dtoBean.restaurants" listKey="id" listValue="name" value="form.dtoBean.daoBean.restaurant.id" required="true"/>
			<s:select id="psc_long_code" label="%{getText('product.special.codes.manager.long.code')}" name="form.dtoBean.daoBean.longCode" list="form.dtoBean.productSpecialCodes" listKey="name" listValue="name" value="form.dtoBean.daoBean.longCode" required="true"/>
		<div id="hidden_div_psc_short_code">
			<c:forEach var="psc" items="${form.dtoBean.productSpecialCodes}">
				<input type="hidden" disabled="disabled" name="${psc.name}" value="${psc.shortCode}"/>
			</c:forEach>
		</div>
		<s:textfield id="psc_short_code" label="%{getText('product.special.codes.manager.short.code')}" name="form.dtoBean.daoBean.shortCode" size="40" maxlength="1" required="true"/>

		<jsp:include page="../../commons/crud-list.jsp">
			<jsp:param name="cssId" value="languages"/>
			<jsp:param name="action" value="ProductSpecialCodesManager"/>
			<jsp:param name="dataAddEmpty" value="language.add.empty"/>
			<jsp:param name="dataRemoveEmpty" value="language.remove.empty"/>
			<jsp:param name="labelSelectList" value="product.special.codes.manager.language"/>
			<jsp:param name="selectList" value="form.dtoBean.languages"/>
			<jsp:param name="selectedValueInSelectList" value="${session.userSession.currentLocale.id}"/>
			<jsp:param name="iteratorList" value="form.dtoBean.daoBean.labels"/>
			<jsp:param name="cssDataProperty" value=""/>
			<jsp:param name="dataProperty" value=""/>
			<jsp:param name="labelData" value="product.special.codes.manager.label"/>
		</jsp:include>
		
		<div class="clear">
			<br/><br/>
		</div>
	</div>
</s:form>