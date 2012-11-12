<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/products.js"></script>
<link rel="stylesheet" type="text/css" href="<c:out value="${pageContext.request.contextPath}"/>/css/products.css" />

<s:form action="ProductsManagerCUD" method="post" enctype="multipart/form-data" validate="false">
	<s:hidden name="form.dtoBean.restaurant.reference" />
	<s:hidden name="form.dtoBean.restaurant.id" />
	<div class="scroll-table-outer-body">
		<div class="scroll-table-inner-body">
			<table id="listproductssortable" class="sortable">
				<thead>
					<tr>
					    <th><fmt:message key="products.manager.restaurant"/> : <c:out value="${form.dtoBean.restaurant.name}"/> (<c:out value="${form.dtoBean.restaurant.reference}"/>)</th>
					    <th style="width: 186em;">
							<s:if test="%{form.restaurant.vats.size()==0}">
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
							<s:else>
								<div style="padding: 14px; float: left;">
									<span class="mdo-ui-button ui-state-default ui-corner-all">
										<span class="ui-icon ui-icon-pencil"></span>
				    					<s:url id="url" action="ProductsManager" method="form">
				    						<s:param name="form.dtoBean.restaurant.id" value="%{form.dtoBean.restaurant.id}"/>
				    						<s:param name="form.dtoBean.restaurant.reference" value="%{form.dtoBean.restaurant.reference}"/>
				    						<s:param name="form.dtoBean.restaurant.name" value="%{form.dtoBean.restaurant.name}"/>
				    					</s:url>
										<s:a href="%{url}"><fmt:message key="admin.manager.create"/></s:a>
									</span>
								</div>
								<div style="padding: 14px; float: left;">
									<span class="mdo-ui-button ui-state-default ui-corner-all">
										<span class="ui-icon ui-icon-arrowreturnthick-1-w"></span>
					    				<s:url id="url" action="ProductsManager" method="list" includeParams="none"/>
										<s:a href="%{url}"><fmt:message key="admin.manager.back"/></s:a>
									</span>
								</div>
								<div style="padding: 14px; float: left;">
									<span class="mdo-ui-button ui-state-default ui-corner-all">
										<span class="ui-icon ui-icon-folder-open"></span>
				    					<s:url id="url" action="ProductsManager" method="exportData">
				    						<s:param name="form.dtoBean.restaurant.id" value="%{form.dtoBean.restaurant.id}"/>
				    						<s:param name="form.dtoBean.restaurant.reference" value="%{form.dtoBean.restaurant.reference}"/>
				    						<s:param name="form.dtoBean.restaurant.name" value="%{form.dtoBean.restaurant.name}"/>
				    					</s:url>
										<s:a href="%{url}" cssClass="mdo-no-overlay"><fmt:message key="products.manager.export.products"/></s:a>
									</span>
								</div>
								<div id="fileinputs">
									<input type='file' id="file" name="form.importedFile" />
									<button id="importData" class="mdo-ui-button ui-state-default ui-corner-all" name="method:importData"><span class="ui-icon ui-icon-folder-collapsed"></span><fmt:message key="products.manager.import.products" /></button>	
								</div>
							</s:else>
					    </th>
				  	</tr>
				</thead>
				<tbody>		  	
				<s:iterator value="form.viewBean.list" id="product">
			 		<tr>
			   			<td style="background-color: #${product.colorRGB}">
							<s:property value="code"/>
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
			    					<s:url id="url" action="ProductsManager" method="form">
			    						<s:param name="form.dtoBean.id" value="%{id}"/>
			    					</s:url>
			    					<s:a href="%{url}"><fmt:message key="admin.manager.update"/></s:a>
								</span>
							</div>
							<div style="float: left; margin-right: 5px;">
								<span class="mdo-ui-button ui-state-default ui-corner-all">
									<span class="ui-icon ui-icon-trash"></span>
			    					<s:url id="url" action="ProductsManagerCUD" method="delete">
			    						<s:param name="form.dtoBean.id" value="%{id}"/>
			    						<s:param name="form.dtoBean.restaurant.id" value="%{restaurant.id}"/>
			    						<s:param name="form.dtoBean.restaurant.reference" value="%{restaurant.reference}"/>
			    						<s:param name="form.dtoBean.restaurant.name" value="%{restaurant.name}"/>
			    					</s:url>
			    					<s:a href="%{url}"><fmt:message key="admin.manager.delete"/></s:a>
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
	<s:actionerror />
	<s:actionmessage />
</div>

