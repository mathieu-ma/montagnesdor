<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<s:form action="UserAuthenticationsManager" method="post">
	<s:hidden name="method:form"/>
	<table class="admin-list-body">
		<tr>
		    <th><fmt:message key="user.authentications.manager.login"/></th>
		    <th><fmt:message key="user.authentications.manager.user"/></th>
		    <th><fmt:message key="user.authentications.manager.role"/></th>
		    <th style="width: 30%">
				<div style="padding: 7px; float: left;">
					<span class="mdo-ui-button ui-state-default ui-corner-all">
						<span class="ui-icon ui-icon-pencil"></span>
	    				<s:url id="url" action="UserAuthenticationsManager" method="form" includeParams="none"/>
						<s:a href="%{url}"><fmt:message key="admin.manager.create"/></s:a>
					</span>
				</div>
		    </th>
	  	</tr>
		<s:iterator value="form.dtoBean.list" id="userAuthentication">
	 		<tr>
	   			<td><s:property value="login"/></td>
	   			<td><s:property value="user.name"/></td>
	   			<td><s:property value="userRole.labelCode"/></td>
			    <td>
	    			<div style="float: left; margin-right: 5px;">
						<span class="mdo-ui-button ui-state-default ui-corner-all">
							<span class="ui-icon ui-icon-pencil"></span>
	    					<s:url id="url" action="UserAuthenticationsManager" method="form">
	    						<s:param name="form.dtoBean.daoBean.id" value="%{id}"/>
	    					</s:url>
	    					<s:a id="%{id}" href="%{url}"><fmt:message key="admin.manager.update"/></s:a>
						</span>
					</div>
			    	<s:if test="%{#session['userSession'].userAuthentication.id!=id}">
		    			<div>
							<span class="mdo-ui-button ui-state-default ui-corner-all">
								<span class="ui-icon ui-icon-trash"></span>
		    					<s:url id="url" action="UserAuthenticationsManager" method="delete">
		    						<s:param name="form.dtoBean.daoBean.id" value="%{id}"/>
		    					</s:url>
		    					<s:a id="%{id}" href="%{url}"><fmt:message key="admin.manager.delete"/></s:a>
							</span>
						</div>
			    	</s:if>
			    </td>	    		
			</tr>
		</s:iterator>
	</table>
</s:form>

<div class="error">
	<s:actionerror/>
</div>

