<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/mdo-table-as-enums.js"></script>

<s:form action="MdoTableAsEnumsManager" method="post">
	<s:hidden name="method:form"/>
	<div class="scroll-table-outer-body">
		<div class="scroll-table-inner-body">
			<table class="sortable">
				<thead>
					<tr>
					    <th><fmt:message key="enums.manager.type"/></th>
					    <th>
							<div style="padding: 12px; float: left;">
								<span class="mdo-ui-button ui-state-default ui-corner-all">
									<span class="ui-icon ui-icon-pencil"></span>
				    				<s:url id="url" action="MdoTableAsEnumsManager" method="form" includeParams="none"/>
									<s:a href="%{url}"><fmt:message key="admin.manager.create"/></s:a>
								</span>
							</div>
							<div class="error">
								<s:actionerror/>
							</div>
							<div class="error">
								<s:actionmessage/>
							</div>
					    </th>
				  	</tr>
			  	</thead>
			  	<tbody>
				<s:iterator value="form.viewBean.existingTypes" id="type">
			 		<tr>
			   			<td>
			   				<s:property value="type"/>
			   			</td>
					    <td>
			    			<div style="float: left; margin-right: 5px;">
								<span class="mdo-ui-button ui-state-default ui-corner-all">
									<span class="ui-icon ui-icon-folder-open"></span>
			    					<s:url id="url" action="MdoTableAsEnumsManager" method="listType">
			    						<s:param name="form.dtoBean.type" value="%{type}"/>
			    					</s:url>
			    					<s:a id="%{type}" href="%{url}"><fmt:message key="admin.manager.view"/></s:a>
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
