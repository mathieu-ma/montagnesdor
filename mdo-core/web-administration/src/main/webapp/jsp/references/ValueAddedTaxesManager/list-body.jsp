<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/value-added-taxes.js"></script>

<s:form action="ValueAddedTaxesManager" method="post">
	<s:hidden name="method:form"/>
	
	<div class="scroll-table-outer-body">
		<div class="scroll-table-inner-body">
			<table class="sortable">
				<thead>
					<tr>
				    	<th><fmt:message key="value.added.taxes.manager.code.name"/></th>
				    	<th><fmt:message key="value.added.taxes.manager.code.label.key"/></th>
					    <th class="sort-number"><fmt:message key="value.added.taxes.manager.rate"/></th>
					    <th>
					    	<c:if test="${not empty form.viewBean.codes}">
								<div style="padding: 12px; float: left;">
									<span class="mdo-ui-button ui-state-default ui-corner-all">
										<span class="ui-icon ui-icon-pencil"></span>
					    				<s:url id="url" action="ValueAddedTaxesManager" method="form" includeParams="none"/>
										<s:a href="%{url}"><fmt:message key="admin.manager.create"/></s:a>
									</span>
								</div>
							</c:if>
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
				<s:iterator value="form.viewBean.list" id="valueAddedTax">
			 		<tr>
			   			<td>
							<s:property value="code.name" />
			   			</td>
			   			<td>
			   				<s:property value="code.languageKeyLabel"/>
			   			</td>
			   			<td style="text-align: center;">
							<s:property value="%{getText('format.number.decimal.3.2',{rate})}" />
			   			</td>
					    <td>
			    			<div style="float: left; margin-right: 5px;">
								<span class="mdo-ui-button ui-state-default ui-corner-all">
									<span class="ui-icon ui-icon-pencil"></span>
			    					<s:url id="url" action="ValueAddedTaxesManager" method="form">
			    						<s:param name="form.dtoBean.id" value="%{id}"/>
			    					</s:url>
			    					<s:a id="%{id}" href="%{url}"><fmt:message key="admin.manager.update"/></s:a>
								</span>
							</div>
			    			<div>
								<span class="mdo-ui-button ui-state-default ui-corner-all">
									<span class="ui-icon ui-icon-trash"></span>
			    					<s:url id="url" action="ValueAddedTaxesManagerCUD" method="delete">
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
