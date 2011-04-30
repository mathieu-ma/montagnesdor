<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<div class="scroll-table-outer-body hspacer-left-50p">
	<div class="scroll-table-inner-body">
		<table>
			<thead>
				<tr>
					<th style="width: 33em;">
						<div style="float: left; width: 34%;" class="label"><fmt:message key="user.authentications.manager.locales"/></div>
						<s:select name="form.dtoBean.locales.makeNew[0].locale.id" list="form.viewBean.languages" listKey="id" listValue="displayLanguage" />
						<s:hidden name="form.dtoBean.locales.makeNew[0].user.id" value="%{form.dtoBean.id}" />
					</th>
					<th>
						<c:if test="${not empty form.viewBean.languages}">
							<button class="mdo-ui-button ui-state-default ui-corner-all" name="method:addLanguage" style="float: left;">
								<span class="ui-icon ui-icon-extlink"></span><fmt:message key="admin.manager.add" />
							</button>
						</c:if>	
						<div class="error">
							<s:actionerror cssClass="ui-helper-reset" />
						</div>
						<div class="error">
							<s:actionmessage cssClass="ui-helper-reset" />
						</div>
					</th>
				</tr>
			</thead>
			<tbody>
			<s:iterator var="userLocale" value="form.dtoBean.locales" status="statusUserLocales">
				<tr>
					<td style="width: 33em;">	
						<s:property value="%{#userLocale.locale.displayLanguage}" />
						<s:hidden name="form.dtoBean.locales(%{#userLocale.id}).locale.id" value="%{#userLocale.locale.id}" />
						<s:hidden name="form.dtoBean.locales(%{#userLocale.id}).locale.displayLanguage" value="%{#userLocale.locale.displayLanguage}" />
						<s:hidden name="form.dtoBean.locales(%{#userLocale.id}).user.id" value="%{form.dtoBean.id}" />
					</td>
					<td>
						<s:if test="%{form.dtoBean.printingLocale.id!=#userLocale.locale.id}">
							<button class="mdo-ui-button ui-state-default ui-corner-all" name="method:removeLanguage" value="${userLocale.id}">
								<span class="ui-icon ui-icon-trash"></span><fmt:message key="admin.manager.delete"/>
							</button>
						</s:if>
					</td>		
				</tr>	
			</s:iterator>
			</tbody>
		</table>
	</div>
</div>
<div class="hspacer-left-50p">
	<c:if test="${empty form.dtoBean.locales}">
	   	<s:select cssClass="required" name="form.dtoBean.printingLocale.id" required="true" key="user.authentications.manager.prefered.print.language"
	   		list="form.viewBean.languages" listKey="id" listValue="displayLanguage" />
	</c:if>
	<c:if test="${not empty form.dtoBean.locales}">
	   	<s:select cssClass="required" name="form.dtoBean.printingLocale.id" required="true" key="user.authentications.manager.prefered.print.language"
	   		list="form.dtoBean.locales" listKey="locale.id" listValue="locale.displayLanguage" />
	</c:if>
</div>
