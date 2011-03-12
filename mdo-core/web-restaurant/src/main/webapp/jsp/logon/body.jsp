<%@ taglib uri="http://java.sun.com/jsp/jstl/core".com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"om/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/logon.js"></script>

<div class="logon-top">
	&nbsp;
</div>

<div class="logon-center-left">
	&nbsp;
</div>

<div class="logon-center">
	<s:form action="Logon" method="post" validate="true" cssClass="logon-center-height">
		<s:submit id="hidden-submit-form"/>
		<!-- A garder pour mémoire -->	
		<fmt:message var="accessKey" key="accesskey.access.admin"/>
	
		<s:hidden name="method:authenticate"/>
	
		<div class="mdo-upsidedown logon-center-height">
			<fieldset class="logon-center-height">
				<legend><fmt:message key="logon.title"/>&nbsp;</legend>
				<div class="logon-center-top">
					&nbsp;
				</div>
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
					<span class="mdo-ui-button ui-state-default ui-corner-all">
						<span class="ui-icon ui-icon-extlink"></span><fmt:message key="logon.access.orders"/>
					</span>
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
</div>

<div class="logon-center-right">
	&nbsp;
</div>

<div class="logon-bottom">
	&nbsp;
</div>
