<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/product-special-codes.js"></script>

<s:form action="ProductSpecialCodesManager" method="post" validate="false">
	<s:hidden name="method:form"/>
	<s:hidden name="form.dtoBean.restaurant.reference" />
	<s:hidden name="form.dtoBean.restaurant.id" />
	<div class="scroll-table-outer-body">
		<div class="scroll-table-inner-body">
			<table id="listsortable" class="sortable">
				<thead>
					<tr>
					    <th><fmt:message key="product.special.codes.manager.restaurant" /> : <c:out value="${form.dtoBean.restaurant.name}"/> (<c:out value="${form.dtoBean.restaurant.reference}"/>)</th>
					    <th style="width: 31em;">
							<div style="padding: 7px; float: left;">
								<span class="mdo-ui-button ui-state-default ui-corner-all">
									<span class="ui-icon ui-icon-pencil"></span>
			    					<s:url id="url" action="ProductSpecialCodesManager" method="form">
			    						<s:param name="form.dtoBean.restaurant.id" value="%{form.dtoBean.restaurant.id}"/>
			    						<s:param name="form.dtoBean.restaurant.reference" value="%{form.dtoBean.restaurant.reference}"/>
			    						<s:param name="form.dtoBean.restaurant.name" value="%{form.dtoBean.restaurant.name}"/>
			    					</s:url>
									<s:a href="%{url}"><fmt:message key="admin.manager.create"/></s:a>
								</span>
							</div>
							<div style="padding: 7px; float: left;">
								<span class="mdo-ui-button ui-state-default ui-corner-all">
									<span class="ui-icon ui-icon-arrowreturnthick-1-w"></span>
				    				<s:url id="url" action="ProductSpecialCodesManager" method="list" includeParams="none"/>
									<s:a href="%{url}"><fmt:message key="admin.manager.back"/></s:a>
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
					<s:iterator value="form.viewBean.list" id="productSpecialCode">
				 		<tr>
				   			<td>
				   				<s:property value="code.name"/>
				   				<c:if test="${not empty labels}">
					   				<c:if test="${empty labels[sessionScope.userSession.currentLocale.id]}">
					   					(<s:property value="labels.values().iterator().next()"/>)
					   				</c:if>
					   				<c:if test="${not empty labels[sessionScope.userSession.currentLocale.id]}">
					   					(<s:property value="labels[#session.userSession.currentLocale.id]"/>)
					   				</c:if>
				   				</c:if>
							</td>
						    <td>
				    			<div style="float: left; margin-right: 5px;">
									<span class="mdo-ui-button ui-state-default ui-corner-all">
										<span class="ui-icon ui-icon-pencil"></span>
				    					<s:url id="url" action="ProductSpecialCodesManager" method="form">
				    						<s:param name="form.dtoBean.id" value="%{id}"/>
				    					</s:url>
				    					<s:a id="%{id}" href="%{url}"><fmt:message key="admin.manager.update"/></s:a>
									</span>
								</div>
				    			<div>
									<span class="mdo-ui-button ui-state-default ui-corner-all">
										<span class="ui-icon ui-icon-trash"></span>
				    					<s:url id="url" action="ProductSpecialCodesManagerCUD" method="delete">
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

