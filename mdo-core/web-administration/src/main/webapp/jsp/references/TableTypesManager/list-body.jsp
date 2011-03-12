<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/table-types.js"></script>

<s:form action="TableTypesManagerCUD" method="post">
	<s:submit id="hidden-submit-form"/>
	<s:hidden name="method:save"/>
	<s:hidden name="form.dtoBean.id"/>
	<div class="scroll-table-outer-body scroll-table-outer-body-2-headers">
		<div class="scroll-table-inner-body">
			<table class="sortable">
				<thead>
					<tr>
						<th colspan="3" style="width: 52em;">			
							<s:select label="%{getText('table.types.manager.code.type')}" name="form.dtoBean.code.id" list="form.viewBean.codes" listKey="id" listValue="name" value="form.dtoBean.code.id" required="true"/>
						</th>
					    <th colspan="2" style="width: 52em;">
					    	<c:if test="${not empty form.viewBean.codes}">
								<div style="float: left;">
									<button id="submit" class="mdo-ui-button ui-state-default ui-corner-all">
										<span class="ui-icon ui-icon-extlink"></span><fmt:message key="admin.manager.create"/>
									</button>
								</div>
							</c:if>
							<div class="error" style="float: left;">
								<s:actionerror/>
							</div>
							<div class="error">
								<s:actionmessage/>
							</div>
						</th>
					</tr>
					<tr>
				    	<th><fmt:message key="table.types.manager.code.name"/></th>
				    	<th><fmt:message key="table.types.manager.code.order"/></th>
				    	<th><fmt:message key="table.types.manager.code.default.label"/></th>
				    	<th><fmt:message key="table.types.manager.code.label.key"/></th>
				    	<th>
				    	</th>
				  	</tr>
				</thead>
				<tbody>
					<s:iterator value="form.viewBean.list" id="tableType">
						<tr>
				   			<td>
				   				<s:property value="code.name"/>
				   			</td>
				   			<td>
				   				<s:property value="code.order"/>
				   			</td>
				   			<td>
				   				<s:property value="code.defaultLabel"/>
				   			</td>
				   			<td>
				   				<s:property value="code.languageKeyLabel"/>
				   			</td>
						    <td>
				    			<div>
									<span class="mdo-ui-button ui-state-default ui-corner-all">
										<span class="ui-icon ui-icon-trash"></span>
				    					<s:url id="url" action="TableTypesManagerCUD" method="delete">
				    						<s:param name="form.dtoBean.id" value="%{id}"/>
				    					</s:url>
				    					<s:a id="%{id}" href="%{url}"><fmt:message key="admin.manager.delete"/></s:a>
									</span>
					    		</div>
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

