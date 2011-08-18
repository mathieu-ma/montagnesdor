<%@ taglib uri="http://java.sun.com/jsp/jstl/core".com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"om/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/user.js"></script>			
<s:form action="User" method="post" validate="true">
	<s:submit id="hidden-submit-form" />
	<div class="mdo-upsidedown">
		<s:hidden name="method:save" />

		<div class="hspacer-left-100p">
			<s:label label="%{getText('user.manager.lastname')}"
				name="form.dtoBean.userContext.name" /> 
			<s:label
				label="%{getText('user.manager.firstname1')}"
				name="form.dtoBean.userContext.forename1" /> 
			<s:label
				label="%{getText('user.manager.birthdate')}"
				name="form.dtoBean.userContext.user.birthdate" /> 
			<s:label
				label="%{getText('user.manager.role')}"
				name="form.dtoBean.userContext.role" /> 
			<s:label
				label="%{getText('user.manager.login')}"
				name="form.dtoBean.userContext.login" /> 
			<s:select
				label="%{getText('user.manager.prefered.print.language')}"
				name="form.dtoBean.userContext.userAuthentication.preferedLocale.id"
				list="form.dtoBean.userContext.userAuthentication.locales" listKey="id"
				listValue="#session.userSession.systemAvailableLanguages[language]" />
			<div id="change-password-level-0" class="anchor wwgrp" title="<fmt:message key="user.manager.change.password.level.0"/>"><fmt:message key="user.manager.change.password.level.0"/></div>
			<div id="change-password-level-1" class="anchor wwgrp" title="<fmt:message key="user.manager.change.password.level.1"/>"><fmt:message key="user.manager.change.password.level.1"/></div>
			<div id="change-password-level-2" class="anchor wwgrp" title="<fmt:message key="user.manager.change.password.level.2"/>"><fmt:message key="user.manager.change.password.level.2"/></div>
			<div id="change-password-level-3" class="anchor wwgrp" title="<fmt:message key="user.manager.change.password.level.3"/>"><fmt:message key="user.manager.change.password.level.3"/></div>


		</div>
	</div>


</s:form>

<div class="error"><s:actionerror /></div>