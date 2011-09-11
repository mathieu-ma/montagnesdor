<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/users.js"></script>

<s:form action="UsersManager" method="post">
	<s:hidden name="method:form"/>
	<div class="scroll-table-outer-body">
		<div class="scroll-table-inner-body">
			<table id="listsortable" class="sortable">
				<thead>
					<tr>
					    <th><fmt:message key="users.manager.lastname"/></th>
					    <th><fmt:message key="users.manager.firstname1"/></th>
					    <th><fmt:message key="users.manager.firstname2"/></th>
					    <th><fmt:message key="users.manager.birthdate"/></th>
					    <th style="width: 21em;">
							<div style="padding: 7px; float: left;">
								<span class="mdo-ui-button ui-state-default ui-corner-all">
									<span class="ui-icon ui-icon-pencil"></span>
				    				<s:url id="url" action="UsersManager" method="form" includeParams="none"/>
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
				<s:iterator value="form.viewBean.list" id="user">
			 		<tr>
			   			<td><s:property value="name"/></td>
					   	<td><s:property value="forename1"/></td>
					   	<td><s:property value="forename2"/></td>
					   	<td class="mdo-date">
					   		<s:date name="birthdate" format="EEEE dd MMMM yyyy"/>
					   		<!-- Used for javascript sortable -->
							<fmt:formatDate var="birthdate" value="${user.birthdate}" pattern="yyyy/MM/dd" />
							<input type="hidden" value="${birthdate}" />
					   	</td>
					    <td>
			    			<div style="float: left; margin-right: 5px;">
								<span class="mdo-ui-button ui-state-default ui-corner-all">
									<span class="ui-icon ui-icon-pencil"></span>
			    					<s:url id="url" action="UsersManager" method="form">
			    						<s:param name="form.dtoBean.id" value="%{id}"/>
			    					</s:url>
			    					<s:a id="%{id}" href="%{url}"><fmt:message key="admin.manager.update"/></s:a>
								</span>
							</div>
					    	<s:if test="%{#session['userSession'].user.id!=id}">
				    			<div style="float: left; margin-right: 5px;">
									<span class="mdo-ui-button ui-state-default ui-corner-all">
										<span class="ui-icon ui-icon-trash"></span>
				    					<s:url id="url" action="UsersManagerCUD" method="delete">
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

