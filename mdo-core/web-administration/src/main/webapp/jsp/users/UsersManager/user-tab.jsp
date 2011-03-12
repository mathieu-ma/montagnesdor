<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

	<div class="mdo-upsidedown">
		<s:hidden name="form.dtoBean.id"/>
	
		<div class="hspacer-left-100p">
			<s:textfield label="%{getText('users.manager.lastname')}" name="form.dtoBean.name" required="true" />
			<s:textfield label="%{getText('users.manager.firstname1')}" name="form.dtoBean.forename1" required="true" />
			<s:textfield label="%{getText('users.manager.firstname2')}" name="form.dtoBean.forename2"/>

			<s:select label="%{getText('users.manager.user.title')}"
		        name="form.dtoBean.title.id"
		        list="form.viewBean.titles"
		        listKey="id"
		        listValue="name"
		 	/>
			
			<s:set name="male" value="%{getText('users.manager.sex.male')}"/>
			<s:set name="female" value="%{getText('users.manager.sex.female')}"/>
			<s:select label="%{getText('users.manager.sex')}"
		        name="form.dtoBean.sex"
		        list="#{'false':#female, 'true':#male}"
		        value="form.dtoBean.sex"
		 	/>
		
			<div>
				<label class="label"><fmt:message key="users.manager.birthdate"/>:</label>
				<input class="label mdo-date" style="color: maroon" readonly="readonly" type="text" id="form-dtoBean-birthdate-datepicker" value="<s:date name="form.dtoBean.birthdate" format="EEEE dd MMMM yyyy"/>"/>
				<input type="hidden" id="form-dtoBean-birthdate" name="form.dtoBean.birthdate" value="<s:date name="form.dtoBean.birthdate" format="yyyy-MM-dd"/>T00:00:00"/>
			</div>

		</div>
	
		<div class="global-transparent-hidden" id="alert-no-restaurant-selected-label">
			<p>
				<fmt:message key="users.manager.alert.no.worked.restaurant.selected"/>
			</p>
			<p>
				<br/>
				<span class="mdo-ui-button ui-state-default ui-corner-all close-alert">
					<span class="ui-icon ui-icon-closethick"></span>
					<fmt:message key="admin.manager.ok"/>
				</span>
			</p>
		</div>		
	</div>	
