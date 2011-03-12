<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<s:form action="ProductPartsManager" method="post">
	<s:hidden name="method:form"/>
	<table class="admin-list-body">
		<tr>
		    <th><fmt:message key="product.parts.manager.code"/></th>
		    <th>
				<div style="padding: 7px; float: left;">
					<span class="mdo-ui-button ui-state-default ui-corner-all">
						<span class="ui-icon ui-icon-pencil"></span>
	    				<s:url id="url" action="ProductPartsManager" method="form" includeParams="none"/>
						<s:a href="%{url}"><fmt:message key="admin.manager.create"/></s:a>
					</span>
				</div>
		    </th>
	  	</tr>
		<s:iterator value="form.dtoBean.list" id="productSpecialCode">
	 		<tr>
	   			<td>
	   				<s:property value="labelCode"/>
	   				<c:if test="${not empty labels}">
		   				<c:if test="${empty labels[sessionScope.userSession.currentLocale.id]}">
		   					(<s:property value="labels.values.iterator.next"/>)
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
	    					<s:url id="url" action="ProductPartsManager" method="form">
	    						<s:param name="form.dtoBean.daoBean.id" value="%{id}"/>
	    					</s:url>
	    					<s:a id="%{id}" href="%{url}"><fmt:message key="admin.manager.update"/></s:a>
						</span>
					</div>
	    			<div>
						<span class="mdo-ui-button ui-state-default ui-corner-all">
							<span class="ui-icon ui-icon-trash"></span>
	    					<s:url id="url" action="ProductPartsManager" method="delete">
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

