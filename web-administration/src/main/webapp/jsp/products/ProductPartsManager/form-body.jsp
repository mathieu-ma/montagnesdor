<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/product-parts.js"></script>

<s:form action="ProductPartsManager" method="post" validate="true">
	<s:submit id="hidden-submit-form"/>
	<div class="mdo-upsidedown">
		<s:hidden name="method:labels"/>
		<s:hidden name="form.dtoBean.daoBean.id"/>
	
		<s:textfield label="%{getText('product.parts.manager.code')}" name="form.dtoBean.daoBean.labelCode" readonly='%{form.dtoBean.daoBean.id!=null}' size="40" maxlength="20" required="true"/>
	
		<jsp:include page="../../commons/crud-list.jsp">
			<jsp:param name="cssId" value="languages"/>
			<jsp:param name="action" value="ProductPartsManager"/>
			<jsp:param name="dataAddEmpty" value="language.add.empty"/>
			<jsp:param name="dataRemoveEmpty" value="language.remove.empty"/>
			<jsp:param name="labelSelectList" value="product.parts.manager.language"/>
			<jsp:param name="selectList" value="form.dtoBean.languages"/>
			<jsp:param name="selectedValueInSelectList" value="${session.userSession.currentLocale.id}"/>
			<jsp:param name="iteratorList" value="form.dtoBean.daoBean.labels"/>
			<jsp:param name="cssDataProperty" value=""/>
			<jsp:param name="dataProperty" value=""/>
			<jsp:param name="labelData" value="product.parts.manager.label"/>
		</jsp:include>

		<div class="clear">
			<br/><br/>
		</div>
	</div>
</s:form>

<div class="error">
	<s:actionerror/>
</div>