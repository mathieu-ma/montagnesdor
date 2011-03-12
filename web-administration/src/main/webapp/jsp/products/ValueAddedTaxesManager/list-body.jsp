<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<s:form action="ValueAddedTaxesManager" method="post">
	<s:hidden name="method:form"/>
	<table class="admin-list-body">
		<tr>
		    <th><fmt:message key="value.added.taxes.manager.value"/></th>
		    <th>
				<div style="padding: 7px; float: left;">
					<span class="mdo-ui-button ui-state-default ui-corner-all">
						<span class="ui-icon ui-icon-pencil"></span>
	    				<s:url id="url" action="ValueAddedTaxesManager" method="form" includeParams="none"/>
						<s:a href="%{url}"><fmt:message key="admin.manager.create"/></s:a>
					</span>
				</div>
		    </th>
	  	</tr>
		<s:iterator value="form.dtoBean.list" id="valueAddedTax">
	 		<tr>
	   			<td>
	   				<s:text name="format.number.percent">
						<s:param name="value" value="value"/>
	   				</s:text>
	   			</td>
			    <td>
	    			<div style="float: left; margin-right: 5px;">
						<span class="mdo-ui-button ui-state-default ui-corner-all">
							<span class="ui-icon ui-icon-pencil"></span>
	    					<s:url id="url" action="ValueAddedTaxesManager" method="form">
	    						<s:param name="form.dtoBean.daoBean.id" value="%{id}"/>
	    					</s:url>
	    					<s:a id="%{id}" href="%{url}"><fmt:message key="admin.manager.update"/></s:a>
						</span>
					</div>
	    			<div>
						<span class="mdo-ui-button ui-state-default ui-corner-all">
							<span class="ui-icon ui-icon-trash"></span>
	    					<s:url id="url" action="ValueAddedTaxesManager" method="delete">
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

