<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/user-authentications.js"></script>

<s:form action="UserAuthenticationsManager" method="post">
	<s:hidden name="method:form"/>
	<div class="scroll-table-outer-body">
		<div class="scroll-table-inner-body">
			<table class="sortable">
				<thead>
					<tr>
						<th><fmt:message key="user.authentications.manager.login"/></th>
					    <th><fmt:message key="user.authentications.manager.user"/></th>
					    <th><fmt:message key="user.authentications.manager.role"/></th>
					    <th>
							<div style="padding: 7px; float: left;">
								<span class="mdo-ui-button ui-state-default ui-corner-all">
									<span class="ui-icon ui-icon-pencil"></span>
				    				<s:url id="url" action="UserAuthenticationsManager" method="form" includeParams="none"/>
									<s:a href="%{url}"><fmt:message key="admin.manager.create"/></s:a>
								</span>
							</div>
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
				<s:iterator value="form.viewBean.list" id="userAuthentication">
					<tr>
						<td><s:property value="login" /></td>
			   			<td><s:property value="user.name" /></td>
			   			<td>
			   				<s:property value="userRole.code.name" />
		  					(<s:property value="userRole.labels[#session.userSession.currentLocale.id]" />)
			   			</td>
					    <td>
			    			<div style="float: left; margin-right: 5px;">
								<span class="mdo-ui-button ui-state-default ui-corner-all">
									<span class="ui-icon ui-icon-pencil"></span>
			    					<s:url id="url" action="UserAuthenticationsManager" method="form">
			    						<s:param name="form.dtoBean.id" value="%{id}"/>
			    					</s:url>
			    					<s:a id="%{id}" href="%{url}"><fmt:message key="admin.manager.update"/></s:a>
								</span>
							</div>
					    	<s:if test="%{#session['userSession'].userAuthentication.id!=id}">
				    			<div>
									<span class="mdo-ui-button ui-state-default ui-corner-all">
										<span class="ui-icon ui-icon-trash"></span>
				    					<s:url id="url" action="UserAuthenticationsManagerCUD" method="delete">
				    						<s:param name="form.dtoBean.id" value="%{id}"/>
				    					</s:url>
				    					<s:a id="%{id}" href="%{url}"><fmt:message key="admin.manager.delete"/></s:a>
									</span>
								</div>
					    	</s:if>
					    </td>	    		
					</tr>
				</s:iterator>
				</tbody>
			</table>
		</div>
	</div>			 
</s:form>

<div class="error">
	<s:actionerror/>
</div>

