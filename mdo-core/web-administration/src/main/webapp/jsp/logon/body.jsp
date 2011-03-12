<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/logon.js"></script>
	
<s:form action="Logon" method="post" validate="false">
	<s:submit id="hidden-submit-form"/>
	<!-- A garder pour mémoire -->	
	<fmt:message var="accessKey" key="accesskey.access.admin"/>

	<s:hidden name="method:authenticate"/>

	<div class="logon-center mdo-upsidedown">
		<fieldset>
			<legend><fmt:message key="logon.title"/>&nbsp;</legend>
			<br/>
			<div class="row">
				<span class="label"><fmt:message key="logon.login"/> :</span>
		    	<span class="formw"><s:textfield name="form.dtoBean.login" maxlength="26"/></span>
		    </div>			
			<div class="row">
				<span class="label"><fmt:message key="logon.password"/> :</span>
		    	<span class="formw"><s:password name="form.dtoBean.password" maxlength="26"/></span>
		    </div>			
			<br/>
			<br/>
			<br/>
			<div id="administration" style="clear: both;">
				<button class="mdo-ui-button ui-state-default ui-corner-all">
					<span class="ui-icon ui-icon-extlink"></span><fmt:message key="logon.access.orders"/>
				</button>
			</div>
			<br/>
		</fieldset>
		
		<s:if test="hasActionErrors()">
			<div class="ui-widget">
				<div style="padding: 0pt 0.7em;" class="ui-state-error ui-corner-all">
					<p>
						<span style="float: left; margin-right: 0.3em;" class="ui-icon ui-icon-alert"></span> 
						<s:iterator value="actionErrors">
							<s:property escape="false"/>
						</s:iterator>
					</p>
				</div>
			</div>
		</s:if>
	</div>
</s:form>

