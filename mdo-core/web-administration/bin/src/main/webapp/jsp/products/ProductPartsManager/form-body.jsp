<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/product-parts.js"></script>

<s:form action="ProductPartsManagerCUD" method="post" validate="false">
	<s:submit id="hidden-submit-form"/>
	<div class="mdo-upsidedown">
		<s:hidden name="form.dtoBean.id"/>
	
		<div class="hspacer-left-100p">
			<s:if test="%{form.dtoBean.id==null}">
				<s:select label="%{getText('product.parts.manager.code')}" name="form.dtoBean.code.id" list="form.viewBean.codes" listKey="id" listValue="name" value="form.dtoBean.code.id" required="true"/>
			</s:if>
			<s:else>
				<s:label label="%{getText('product.parts.manager.code')}" value="%{form.dtoBean.code.name}" required="true" />
				<s:hidden key="form.dtoBean.code.id" />
			</s:else>
			<div style="height: 2em;">
				<div class="error">
					<s:actionerror cssClass="ui-helper-reset" />
				</div>
				<div class="error">
					<s:actionmessage cssClass="ui-helper-reset" />
				</div>
			</div>
		</div>
	
		<jsp:include page="../../commons/crud-list.jsp">
			<jsp:param name="cssId" value="languages"/>
			<jsp:param name="action" value="ProductPartsManager"/>
			<jsp:param name="labelSelectList" value="product.parts.manager.language"/>
			<jsp:param name="selectList" value="form.viewBean.languages"/>
			<jsp:param name="selectedValueInSelectList" value="${session.userSession.currentLocale.id}"/>
			<jsp:param name="iteratorList" value="form.dtoBean.labels"/>
			<jsp:param name="cssDataProperty" value=""/>
			<jsp:param name="dataProperty" value=""/>
			<jsp:param name="labelData" value="product.parts.manager.label"/>
		</jsp:include>

		<div class="clear">
			<br/><br/>
		</div>
	</div>
</s:form>