<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/restaurants.js"></script>

<s:form action="RestaurantsManager" method="post">
	<s:hidden name="method:form"/>
	<div class="scroll-table-outer-body">
		<div class="scroll-table-inner-body">
			<table id="listsortable" class="sortable">
				<thead>
					<tr>
				    	<th><fmt:message key="restaurants.manager.name"/></th>
				    	<th><fmt:message key="restaurants.manager.registration.date"/></th>
				    	<th><fmt:message key="restaurants.manager.reference"/></th>
				    	<th><fmt:message key="restaurants.manager.vat.reference"/></th>
				    	<th><fmt:message key="restaurants.manager.visa.reference"/></th>
					    <th style="width: 21em;">
							<div style="padding: 7px; float: left;">
								<span class="mdo-ui-button ui-state-default ui-corner-all">
									<span class="ui-icon ui-icon-pencil"></span>
				   					<s:url id="url" action="RestaurantsManager" method="form" includeParams="none" />
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
				<s:iterator value="form.viewBean.list" id="restaurant">
					<tr>
						<td><s:property value="name"/></td>
						<td class="mdo-date">
							<s:date name="registrationDate" format="EEEE dd MMMM yyyy"/>
							<!-- Used for javascript sortable -->
							<fmt:formatDate var="restaurantRegistrationDate" value="${restaurant.registrationDate}" pattern="yyyy/MM/dd" />
							<input type="hidden" value="${restaurantRegistrationDate}" />
						</td>
						<td><s:property value="reference"/></td>
						<td><s:property value="vatRef"/></td>
						<td><s:property value="visaRef"/></td>
					    <td>
			    			<div style="float: left; padding-right: 1em;">
								<span class="mdo-ui-button ui-state-default ui-corner-all">
									<span class="ui-icon ui-icon-pencil"></span>
			    					<s:url id="url" action="RestaurantsManager" method="form">
			    						<s:param name="form.dtoBean.id" value="%{id}"/>
			    					</s:url>
			    					<s:a id="%{id}" href="%{url}"><fmt:message key="admin.manager.update"/></s:a>
								</span>
							</div>
					    	<s:if test="%{#session['userSession'].userAuthentication.restaurant.id!=id}">
				    			<div>
									<span class="mdo-ui-button ui-state-default ui-corner-all">
										<span class="ui-icon ui-icon-trash"></span>
				    					<s:url id="url" action="RestaurantsManagerCUD" method="delete">
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

