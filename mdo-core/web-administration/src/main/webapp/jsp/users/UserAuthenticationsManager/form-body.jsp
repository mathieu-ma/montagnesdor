<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/user-authentications.js"></script>

<s:form action="UserAuthenticationsManager" method="post" validate="true">
	<s:submit id="hidden-submit-form"/>
	<div class="mdo-upsidedown">
		<s:hidden name="method:save"/>
		<s:hidden name="form.dtoBean.daoBean.id"/>
		<s:textfield label="%{getText('user.authentications.manager.login')}" name="form.dtoBean.daoBean.login" readonly='%{form.dtoBean.daoBean.id!=null}' size="40" maxlength="20" required="true"/>
	
		<s:password label="%{getText('user.authentications.manager.password')}" name="form.dtoBean.daoBean.password" showPassword="true"/>
		<s:password label="%{getText('user.authentications.manager.levelPass1')}" name="form.dtoBean.daoBean.levelPass1" showPassword="true"/>
		<s:password label="%{getText('user.authentications.manager.levelPass2')}" name="form.dtoBean.daoBean.levelPass2" showPassword="true"/>
		<s:password label="%{getText('user.authentications.manager.levelPass3')}" name="form.dtoBean.daoBean.levelPass3" showPassword="true"/>
	
		<s:select label="%{getText('user.authentications.manager.role')}"
	        name="form.dtoBean.daoBean.userRole.id"
	        list="form.dtoBean.userRoles"
	        listKey = "id"
	        listValue="labelCode"
	        value="form.dtoBean.daoBean.userRole.id"
	        disabled="%{#disabled}"
	 	/>	 	
	 	
	 	<div class="hspacer-left-100p">
		 	<div class="hspacer-left-50p">
				<s:select label="%{getText('user.authentications.manager.user')}"
			        name="form.dtoBean.daoBean.user.id"
			        list="form.dtoBean.users"
			        listKey = "id"
			        listValue="name"
			        value="form.dtoBean.daoBean.user.id"
			 	/>	
		 	</div>
		 	<div class="hspacer-left-50p">
				<s:select label="%{getText('user.authentications.manager.restaurant')}"
			        name="form.dtoBean.daoBean.restaurant.id"
			        list="form.dtoBean.userRestaurants"
			        listKey = "id"
			        listValue="name"
			        value="form.dtoBean.daoBean.restaurant.id"
			 	/>
			 	<input type="hidden" id="form-dtoBean-daoBean-restaurant-name" name="form.dtoBean.daoBean.restaurant.name"/>
			 	<input type="hidden" id="saved-user-restaurant-id" name="saved-user-restaurant-id" value="<c:out value="${form.dtoBean.daoBean.restaurant.id}"/>" disabled="disabled"/>
			 </div>		
	 	</div>

	 	<div class="hspacer-left-100p">
		 	<div class="hspacer-left-50p">
		 		<label class="label"><fmt:message key="user.authentications.manager.locales"/>:</label>
			 	<div class="hspacer-left-50p" style="text-align: left;">
			 		<c:forEach items="${form.dtoBean.languages}" var="language" varStatus="statusLanguages">
						<c:set var="checked"></c:set>
						<c:set var="disabled">disabled="disabled"</c:set>
						<c:forEach items="${form.dtoBean.daoBean.locales}" var="locale">
							<c:if test="${locale.id==language.id}">
								<c:set var="checked">checked="checked"</c:set>
								<c:set var="disabled"></c:set>
							</c:if>
						</c:forEach>
						<input type="checkbox" id="form.dtoBean.userLocales.id.${language.id}" name="form.dtoBean.userLocales[${statusLanguages.index}].id" ${checked} value='${language.id}'/>
						<input type="hidden" id="form.dtoBean.userLocales.language.${language.id}" name="form.dtoBean.userLocales[${statusLanguages.index}].language" value='${language.languageCode}' ${disabled}/>
						<label id="checkboxLabel${language.id}" for="form.dtoBean.userLocales.id.${language.id}">${language.language}</label>
						<br/>
					</c:forEach>
				</div>
			</div>
		 	<div class="hspacer-left-50p">
		    	<s:select label="%{getText('user.authentications.manager.prefered.print.language')}" 
		    		name="form.dtoBean.daoBean.preferedLocale.id" 
		    		list="form.dtoBean.avalaibleLanguages" listKey="key" listValue="value"/>
			</div>
		</div>
	
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
					<s:url id="url" action="UserAuthenticationsManager" method="list" includeParams="none"/>
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