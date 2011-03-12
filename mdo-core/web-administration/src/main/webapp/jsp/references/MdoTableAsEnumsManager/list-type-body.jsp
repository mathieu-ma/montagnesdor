<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/mdo-table-as-enums.js"></script>

<s:form action="MdoTableAsEnumsManager" method="post">
	<s:hidden name="method:form"/>
	<div class="scroll-table-outer-body" style="padding-top: 7em;">
		<div class="scroll-table-inner-body">
			<table class="sortable type">
				<thead>
					<tr>      
					    <th colspan="3" style="width: 42em"><fmt:message key="enums.manager.type"/></th>
					    <th style="width: 42em">
					    	<c:out value="${form.dtoBean.type}"/>
					    </th>
					    <th style="width: 42em">
							<div class="error">
								<s:actionerror/>
							</div>
							<div class="error">
								<s:actionmessage/>
							</div>
					    </th>
					</tr>      
					<tr>      
					    <th><fmt:message key="enums.manager.name"/></th>
					    <th><fmt:message key="enums.manager.order"/></th>
					    <th><fmt:message key="enums.manager.default.label"/></th>
					    <th><fmt:message key="enums.manager.label.key"/></th>
					    <th style="width:23em;">
							<div style="padding: 7px; float: left;">
								<span class="mdo-ui-button ui-state-default ui-corner-all">
									<span class="ui-icon ui-icon-pencil"></span>
				    				<s:url id="url" action="MdoTableAsEnumsManager" method="form">
				    					<s:param name="form.dtoBean.type" value="%{form.dtoBean.type}"/>
									</s:url>
									<s:a href="%{url}"><fmt:message key="admin.manager.create"/></s:a>
								</span>
							</div>
							<div style="padding: 7px; float: left;">
								<span class="mdo-ui-button ui-state-default ui-corner-all">
									<span class="ui-icon ui-icon-arrowreturnthick-1-w"></span>
				    				<s:url id="url" action="MdoTableAsEnumsManager" method="list" includeParams="none"/>
									<s:a href="%{url}"><fmt:message key="admin.manager.back"/></s:a>
								</span>
							</div>
					    </th>
				  	</tr>
				</thead>
				<tbody>
				<s:iterator value="form.viewBean.list" id="tableAsEnum">
			 		<tr>
			   			<td>
			   				<s:property value="name"/>
			   			</td>
			   			<td>
			   				<s:property value="order"/>
			   			</td>
			   			<td>
			   				<s:property value="defaultLabel"/>
			   			</td>
			   			<td>
			   				<s:property value="languageKeyLabel"/>
			   			</td>
					    <td style="width:23em;">
			    			<div style="float: left; margin-right: 5px;">
								<span class="mdo-ui-button ui-state-default ui-corner-all">
									<span class="ui-icon ui-icon-pencil"></span>
			    					<s:url id="url" action="MdoTableAsEnumsManager" method="form">
			    						<s:param name="form.dtoBean.id" value="%{id}"/>
			    					</s:url>
			    					<s:a id="%{id}" href="%{url}"><fmt:message key="admin.manager.update"/></s:a>
								</span>
							</div>
							<s:set var="isDeleted" value="false" />
							<s:iterator value="%{form.viewBean.notDeletedBeanIds}" var="notDeletedBeanId" >
						    	<s:if test="%{#notDeletedBeanId==id}">
									<s:set var="isDeleted" value="true" />
						    	</s:if>
							</s:iterator>
					    	<s:if test="%{!#isDeleted}">
				    			<div style="float: left; margin-right: 5px;">
									<span class="mdo-ui-button ui-state-default ui-corner-all">
										<span class="ui-icon ui-icon-trash"></span>
				    					<s:url id="url" action="MdoTableAsEnumsManagerCUD" method="delete">
				    						<s:param name="form.dtoBean.id" value="%{id}"/>
				    						<s:param name="form.dtoBean.type" value="%{type}"/>
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

