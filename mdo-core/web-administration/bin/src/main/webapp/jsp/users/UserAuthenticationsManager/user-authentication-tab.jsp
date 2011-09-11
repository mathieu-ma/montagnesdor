<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<div class="mdo-upsidedown">
	<s:hidden name="form.dtoBean.id"/>
	<s:textfield cssClass="required" label="%{getText('user.authentications.manager.login')}" name="form.dtoBean.login" readonly='%{form.dtoBean.id!=null}' size="40" maxlength="20" required="true" />

	<s:password cssClass="required" label="%{getText('user.authentications.manager.password')}" name="form.dtoBean.password" showPassword="true" required="true" />
	<s:password label="%{getText('user.authentications.manager.levelPass1')}" name="form.dtoBean.levelPass1" showPassword="true"/>
	<s:password label="%{getText('user.authentications.manager.levelPass2')}" name="form.dtoBean.levelPass2" showPassword="true"/>
	<s:password label="%{getText('user.authentications.manager.levelPass3')}" name="form.dtoBean.levelPass3" showPassword="true"/>

	<s:select label="%{getText('user.authentications.manager.role')}"
        name="form.dtoBean.userRole.id"
        list="form.viewBean.userRoles"
        listKey = "id"
        listValue="code.name"
        value="form.dtoBean.userRole.id"
        disabled="%{#disabled}"
 	/>	 	
 	
 	<div class="hspacer-left-100p">
	 	<div class="hspacer-left-50p">
			<s:select label="%{getText('user.authentications.manager.user')}"
		        name="form.dtoBean.user.id"
		        list="form.viewBean.users"
		        listKey = "id"
		        listValue="name"
		        value="form.dtoBean.user.id"
		 	/>	
	 	</div>
	 	<div class="hspacer-left-50p">
			<s:select label="%{getText('user.authentications.manager.restaurant')}"
		        name="form.dtoBean.restaurant.id"
		        list="form.viewBean.userRestaurants"
		        listKey = "id"
		        listValue="name"
		        value="form.dtoBean.restaurant.id"
		 	/>
		 </div>		
 	</div>
</div>
