<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/user-roles.js1"></script>

<s:form action="UserRolesManager" method="post" validate="false">
	<s:submit id="hidden-submit-form"/>
	<div class="mdo-upsidedown">
		<s:hidden name="method:labels"/>
		<s:hidden name="form.dtoBean.id"/>
		<s:textfield label="%{getText('user.roles.manager.code')}" name="form.dtoBean.code.name" readonly='%{form.dtoBean.id!=null}' size="40" maxlength="20" required="true"/>

		<jsp:include page="../../commons/crud-list.jsp">
			<jsp:param name="cssId" value="languages"/>
			<jsp:param name="action" value="UserRolesManager"/>
			<jsp:param name="dataAddEmpty" value="language.add.empty"/>
			<jsp:param name="dataRemoveEmpty" value="language.remove.empty"/>
			<jsp:param name="labelSelectList" value="user.roles.manager.language"/>
			<jsp:param name="selectList" value="form.viewBean.languages"/>
			<jsp:param name="selectedValueInSelectList" value="${session.userSession.currentLocale.id}"/>
			<jsp:param name="iteratorList" value="form.dtoBean.labels"/>
			<jsp:param name="cssDataProperty" value=""/>
			<jsp:param name="dataProperty" value=""/>
			<jsp:param name="labelData" value="user.roles.manager.label"/>
		</jsp:include>

		<s:include value="/jsp/commons/form-actions.jsp">
			<s:url id="cancelUrl" action="UserRolesManager" method="list" />
			<s:param name="cancelUrl" value="%{cancelUrl}" />
		</s:include>

	</div>	
</s:form>

<div class="error">
	<s:actionerror/>
</div>