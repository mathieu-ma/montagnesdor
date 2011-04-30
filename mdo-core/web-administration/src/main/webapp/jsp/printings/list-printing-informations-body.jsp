<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/printing-informations.js"></script>

<s:form action="PrintingInformationsManager" method="post">
	<div class="scroll-table-outer-body" style="padding-top: 7em;">
		<div class="scroll-table-inner-body">
			<table class="sortable restaurant">
				<thead>
					<tr>      
					    <th colspan="3" style="width: 42em"><fmt:message key="printing.informations.manager.restaurant"/></th>
					    <th style="width: 42em">
							<c:out value="${form.dtoBean.restaurant.name}"/> (<c:out value="${form.dtoBean.restaurant.reference}"/>)					    
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
					    <th><fmt:message key="printing.informations.manager.alignment"/></th>
					    <th><fmt:message key="printing.informations.manager.size"/></th>
					    <th><fmt:message key="printing.informations.manager.part"/></th>
					    <th><fmt:message key="printing.informations.manager.order"/></th>
					    <th style="width:23em;">
							<div style="padding: 7px; float: left;">
								<span class="mdo-ui-button ui-state-default ui-corner-all">
									<span class="ui-icon ui-icon-pencil"></span>
			    					<s:url id="url" action="PrintingInformationsManager" method="form">
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
				    				<s:url id="url" action="PrintingInformationsManager" method="list" includeParams="none"/>
									<s:a href="%{url}"><fmt:message key="admin.manager.back"/></s:a>
								</span>
							</div>
					    </th>
				  	</tr>
				</thead>
				<tbody>		  	
				<s:iterator value="form.viewBean.list" id="printingInformation">
			 		<tr>
			   			<td>
							<s:property value="alignment.name"/>
			   			</td>
			   			<td>
							<s:property value="size.name"/>
			   			</td>
			   			<td>
							<s:property value="part.name"/>
			   			</td>
			   			<td>
							<s:property value="order"/>
			   			</td>
					    <td>
			    			<div style="float: left; margin-right: 5px;">
								<span class="mdo-ui-button ui-state-default ui-corner-all">
									<span class="ui-icon ui-icon-pencil"></span>
			    					<s:url id="url" action="PrintingInformationsManager" method="form">
			    						<s:param name="form.dtoBean.id" value="%{id}"/>
			    					</s:url>
			    					<s:a id="%{id}" href="%{url}"><fmt:message key="admin.manager.update"/></s:a>
								</span>
							</div>
							<div style="float: left; margin-right: 5px;">
								<span class="mdo-ui-button ui-state-default ui-corner-all">
									<span class="ui-icon ui-icon-trash"></span>
			    					<s:url id="url" action="PrintingInformationsManagerCUD" method="delete">
			    						<s:param name="form.dtoBean.id" value="%{id}"/>
			    						<s:param name="form.dtoBean.restaurant.id" value="%{restaurant.id}"/>
			    						<s:param name="form.dtoBean.restaurant.reference" value="%{restaurant.reference}"/>
			    						<s:param name="form.dtoBean.restaurant.name" value="%{restaurant.name}"/>
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

<div class="error">
	<s:actionerror/>
</div>

