<%--
	When you use an absolute URI, you do not have to add the taglib element to web.xml; 
	the JSP container automatically locates the TLD inside the JSTL library implementation.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/user.js"></script>	
		
<s:form action="User" method="post" validate="false">
	<s:submit id="hidden-submit-form" />
	<div class="mdo-upsidedown">
		<s:hidden name="method:save" />

		<div class="hspacer-left-100p">
			<s:label label="%{getText('user.manager.lastname')}" name="form.userContext.name" labelposition="left" /> 
			<s:label label="%{getText('user.manager.firstname1')}" name="form.userContext.forename1" labelposition="left" /> 
			<s:label label="%{getText('user.manager.birthdate')}" name="form.userContext.user.birthdate" labelposition="left" /> 
			<s:label label="%{getText('user.manager.role')}"	name="form.userContext.role" labelposition="left" /> 
			<s:label label="%{getText('user.manager.login')}" name="form.userContext.login" labelposition="left" /> 
			<s:select
				label="%{getText('user.manager.prefered.print.language')}"
				name="form.userContext.userAuthentication.printingLocale.id"
				list="form.userContext.userAuthentication.locales" listKey="id"
				listValue="#session.userSession.systemAvailableLanguages[form.userContext.currentLocale.languageCode]" labelposition="left" />

			<s:iterator begin="0" end="3" status="status">
				<div class="wwgrp" title="<fmt:message key="user.manager.change.password.level.${status.index}"/>">
					<s:a id='change-password-level-%{#status.index}' action="ChangePassword" method="form">
						<fmt:message key="user.manager.change.password.level.${status.index}"/>
						<s:param name="form.dtoBean.levelPassword" value="#status.index" />
					</s:a>
				</div>
			</s:iterator>
		</div>
	</div>
</s:form>

<div class="error"><s:actionerror /></div>