<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/products.js"></script>

<s:form action="ProductsManager" method="post">
	<s:hidden name="method:form"/>

	<s:if test="%{form.dtoBean.vats.size()==0}">
		<div class="global-transparent-hidden" id="products-manager-warn-create-product-vat-label">
			<p>
				<fmt:message key="products.manager.warn.create.product.vat.label"/>
			</p>
			<p>
				<span class="mdo-ui-button ui-state-default ui-corner-all">
					<span class="ui-icon ui-icon-pencil"></span>
    				<s:url id="url" action="ValueAddedTaxesManager" method="form">
    					<s:param name="selectedMenuItemId">4_3</s:param>
    				</s:url>
					<s:a href="%{url}"><fmt:message key="admin.manager.create"/></s:a>
				</span>
			</p>
		</div>		
	</s:if>
	<table class="admin-list-body">
		<tr>
		    <th><fmt:message key="products.manager.code"/></th>
		    <th><fmt:message key="products.manager.label"/></th>
		    <th>
				<div style="padding: 7px; float: left;">
					<span id="create-product" class="mdo-ui-button ui-state-default ui-corner-all">
						<span class="ui-icon ui-icon-pencil"></span>
	    				<s:url id="url" action="ProductsManager" method="form" includeParams="none"/>
						<s:a href="%{url}"><fmt:message key="admin.manager.create"/></s:a>
					</span>
				</div>
		    </th>
	  	</tr>
		<s:iterator value="form.dtoBean.list" id="product">
	 		<tr style='background-color: #<s:property value="colorRGB"/>;'>
	   			<td>
					<s:property value="code"/>
	   			</td>
	   			<td>
	   				<c:if test="${not empty labels}">
		   				<c:if test="${empty labels[sessionScope.userSession.currentLocale.id]}">
		   					<s:property value="labels.values.iterator.next"/>
		   				</c:if>
		   				<c:if test="${not empty labels[sessionScope.userSession.currentLocale.id]}">
		   					<s:property value="labels[#session.userSession.currentLocale.id]"/>
		   				</c:if>
	   				</c:if>
	   			</td>
			    <td>
	    			<div style="float: left; margin-right: 5px;">
						<span class="mdo-ui-button ui-state-default ui-corner-all">
							<span class="ui-icon ui-icon-pencil"></span>
	    					<s:url id="url" action="ProductsManager" method="form">
	    						<s:param name="form.dtoBean.daoBean.id" value="%{id}"/>
	    					</s:url>
	    					<s:a id="%{id}" href="%{url}"><fmt:message key="admin.manager.update"/></s:a>
						</span>
					</div>
	    			<div>
						<span class="mdo-ui-button ui-state-default ui-corner-all">
							<span class="ui-icon ui-icon-trash"></span>
	    					<s:url id="url" action="ProductsManager" method="delete">
	    						<s:param name="form.dtoBean.daoBean.id" value="%{id}"/>
	    					</s:url>
	    					<s:a id="%{id}" href="%{url}"><fmt:message key="admin.manager.delete"/></s:a>
						</span>
					</div>
			    </td>	    		
			</tr>
		</s:iterator>
	</table>
</s:form>

<div class="error">
	<s:actionerror/>
</div>

