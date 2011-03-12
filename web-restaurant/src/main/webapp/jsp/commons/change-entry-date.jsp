<%@ taglib uri="http://java.sun.com/jsp/jstl/core".com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"om/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/change-entry-date.js"></script>
<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/jquery-ui/i18n/ui.datepicker-<s:property value="#session.userSession.currentLocale.language"/>.js"></script>

	<s:form action="ChangeEntryDate" method="post" validate="true">
		<s:hidden name="method:authenticate"/>
	
		<div class="hspacer-left-100p">		
			<br/>
			<input type="text" id="form-dtoBean-entryDate-datepicker" class="label mdo-date" readonly="readonly"/>
			<input type="hidden" id="form-dtoBean-entryDate" name="form.dtoBean.entryDate"/>
		</div>
		
		<div class="clear">
			<br/>
		</div>
	
		<s:hidden name="form.dtoBean.levelPassword" value="1"/>
		<s:password label="%{getText('password.level.1')}" name="form.dtoBean.password" required="true"/>
	
		<div class="clear">
		</div>
	
		<div id="messages-box" class="ui-widget" style="visibility: hidden;">
			<div style="padding: 0pt 0.7em;" class="ui-state-error ui-corner-all">
				<p>
					<span style="float: left; margin-right: 0.3em;" class="ui-icon ui-icon-alert"></span> 
					<span id="messages">
						&nbsp;
						<label id="message-1" style="display: none;"><fmt:message key="error.password.level.1.required"/></label>
						<label id="message-2" style="display: none;"><fmt:message key="error.password.level.1.failed"/></label>
						&nbsp;
					</span>
				</p>
			</div>
		</div>
	
		<div class="clear">
			<br/>
		</div>
	
		<div class="hspacer-left-100p">		
			<div class="hspacer-left-50p">
				<span id="submit" class="mdo-ui-button ui-state-default ui-corner-all">
					<span class="ui-icon ui-icon-disk"></span>
					<fmt:message key="label.confirm"/>
				</span>
			</div>
	   		<div class="hspacer-left-50p">
				<span id="cancel" class="mdo-ui-button ui-state-default ui-corner-all">
					<span class="ui-icon ui-icon-closethick"></span>
					<fmt:message key="label.cancel"/>
				</span>
	   		</div>
		</div>
	
		<div id="ajax-response"></div>
	</s:form>
