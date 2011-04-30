<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/user-roles.js"></script>

<s:form action="UserRolesManager" method="post">
	<s:hidden name="method:form"/>
	<div class="scroll-table-outer-body">
		<div class="scroll-table-inner-body">
			<table class="sortable">
				<thead>
					<tr>
						<th style="width: 40em;"><fmt:message key="user.roles.manager.code"/></th>
					    <th>
							<div style="padding: 7px; float: left;">
								<span class="mdo-ui-button ui-state-default ui-corner-all">
									<span class="ui-icon ui-icon-pencil"></span>
				    				<s:url id="url" action="UserRolesManager" method="form" includeParams="none"/>
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
				<s:iterator value="form.viewBean.list" id="userRole">
					<tr>
			  			<td style="width: 40em;">
			  				<s:property value="code.name"/>
			  				<c:if test="${not empty form.viewBean.labels}">
			  					(<s:property value="form.viewBean.labels[#userRole.id]" />)
			  				</c:if>
			  			</td>
					    <td>
				   			<div style="float: left; margin-right: 5px;">
								<span class="mdo-ui-button ui-state-default ui-corner-all">
									<span class="ui-icon ui-icon-pencil"></span>
				   					<s:url id="url" action="UserRolesManager" method="form">
				   						<s:param name="form.dtoBean.id" value="%{id}"/>
				   					</s:url>
				   					<s:a id="%{id}" href="%{url}"><fmt:message key="admin.manager.update"/></s:a>
								</span>
							</div>
					    	<s:if test="%{#session['userSession'].userRole.id!=id}">
				    			<div>
									<span class="mdo-ui-button ui-state-default ui-corner-all">
										<span class="ui-icon ui-icon-trash"></span>
				    					<s:url id="url" action="UserRolesManagerCUD" method="delete">
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
