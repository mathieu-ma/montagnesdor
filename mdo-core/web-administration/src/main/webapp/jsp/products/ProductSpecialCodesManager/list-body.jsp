<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/products.js"></script>

<s:form action="ProductsManager" method="post" validate="false">
	<div class="scroll-table-outer-body">
		<div class="scroll-table-inner-body">
			<table id="listsortable" class="sortable">
				<thead>
					<tr>
					    <th><fmt:message key="products.manager.restaurant"/></th>
					    <th>&nbsp;</th>
				  	</tr>
				</thead>
				<tbody>		  	
				<s:iterator value="form.viewBean.restaurants" id="restaurant">
			 		<tr>
			   			<td>
							<s:property value="name"/>(<s:property value="reference"/>)
			   			</td>
					    <td>
			    			<div style="float: left; margin-right: 5px;">
								<span class="mdo-ui-button ui-state-default ui-corner-all">
									<span class="ui-icon ui-icon-pencil"></span>
			    					<s:url id="url" action="ProductSpecialCodesManager" method="listProductSpecialCodes">
			    						<s:param name="form.dtoBean.restaurant.id" value="%{id}"/>
			    						<s:param name="form.dtoBean.restaurant.name" value="%{name}"/>
			    						<s:param name="form.dtoBean.restaurant.reference" value="%{reference}"/>
			    					</s:url>
			    					<s:a id="%{id}" href="%{url}"><fmt:message key="admin.manager.view"/></s:a>
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

