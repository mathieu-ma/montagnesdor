<%--
	When you use an absolute URI, you do not have to add the taglib element to web.xml; 
	the JSP container automatically locates the TLD inside the JSTL library implementation.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>
<%@ taglib uri="/WEB-INF/mdo.tld" prefix="mdo" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/change-entry-date.js"></script>
<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/jquery/jquery-ui/i18n/jquery.ui.datepicker-<s:property value="#session.userSession.currentLocale.languageCode"/>.js"></script>


	<s:form action="ChangeEntryDate" method="post" validate="true">
		<s:hidden name="method:authenticate"/>
	
		<div class="hspacer-left-100p">		
			<br/>
			<div class="wwgrp">
				<span class="wwlbl"><label class="label"><fmt:message key="header.entry.date" /></label></span>
				<span class="wwctrl">
					<input id="form-dtoBean-entryDate-datepicker" type="text" class="label mdo-date" style="width: 200px;color: maroon" readonly="readonly" value="<s:date name="form.dtoBean.entryDate" format="EEEE dd MMMM yyyy"/>"/>
					<input id="form-dtoBean-entryDate" type="text" name="form.dtoBean.entryDate" value="<s:date name="form.dtoBean.entryDate" format="dd/MM/yyyy"/>"/>
				</span>
			</div>
		</div>
		
		<div class="clear">
			<br/>
		</div>
	
		<div class="hspacer-left-100p">		
			<s:hidden name="form.dtoBean.levelPassword" value="PASSWORD_LEVEL_ONE"/>
			<s:password label="%{getText('password.level.1')}" name="form.dtoBean.password" required="true" labelposition="left"/>
		</div>
	
		<div class="clear">
			<br/>
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
