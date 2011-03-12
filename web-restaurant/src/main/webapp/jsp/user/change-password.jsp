<%@ taglib uri="http://java.sun.com/jsp/jstl/core".com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"om/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/change-password.js"></script>

<s:form action="ChangePassword" method="post" validate="true">
	<s:hidden name="method:authenticate"/>
	<div class="mdo-upsidedown">

		<div class="hspacer-left-100p">		
			<s:password label='%{getText("user.current.password.level."+form.dtoBean.levelPassword)}' name="form.dtoBean.password" required="true"/>
		</div>
		
		<div class="clear">
			<br/>
		</div>
		<div class="hspacer-left-100p">		
			<s:password label='%{getText("user.new.password.level."+form.dtoBean.levelPassword)}' name="form.dtoBean.newPassword" required="true"/>
		</div>
		
		<div class="clear">
			<br/>
		</div>
	
		<div class="hspacer-left-100p">		
			<s:password label='%{getText("user.new.confirmed.password.level."+form.dtoBean.levelPassword)}' name="form.newPasswordConfirmed" required="true"/>
		</div>
		
		<div class="clear">
			<br/>
		</div>
		<s:hidden name="form.dtoBean.levelPassword"/>
	
		<div class="clear">
		</div>
	
		<div id="messages-box" class="ui-widget" style="visibility: hidden;">
			<div style="padding: 0pt 0.7em;" class="ui-state-error ui-corner-all">
				<p>
					<span style="float: left; margin-right: 0.3em;" class="ui-icon ui-icon-alert"></span> 
					<div id="messages" style="text-align: left; padding-left: 4.5em;">
						<ul id="displayed-messages">
							<li id="displayed-message-0" style="visibility: hidden;"><fmt:message key="error.current.password.level.required"/></li>
							<li id="displayed-message-1" style="visibility: hidden;"><fmt:message key="error.new.password.level.required"/></li>
							<li id="displayed-message-2" style="visibility: hidden;"><fmt:message key="error.new.confirmed.password.level.required"/></li>
						</ul>
						<ul id="hidden-messages" style="display: none;">
							<li id="hidden-message-0" style="visibility: hidden;"><fmt:message key="error.current.password.level.required"/></li>
							<li id="hidden-message-1" style="visibility: hidden;"><fmt:message key="error.new.password.level.required"/></li>
							<li id="hidden-message-2" style="visibility: hidden;"><fmt:message key="error.new.confirmed.password.level.required"/></li>
							<li id="hidden-message-3" style="visibility: hidden;"><fmt:message key="error.new.confirmed.password.level.different"/></li>
							<li id="hidden-message-4" style="visibility: hidden;"><fmt:message key="error.password.level.failed"/></li>
						</ul>
					</div>
				</p>
			</div>
		</div>
	
		<div class="clear">
			<br/><br/>
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

		<div class="clear">
		</div>
	
		<div id="ajax-response"></div>
	</div>
</s:form>
