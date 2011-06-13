<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<div id="<c:out value="${param.cssId}"/>" >
	<div class="hspacer-left-50p">
		<s:if test="%{request.getParameter('selectListHeaderKey')==null || request.getParameter('selectListHeaderValue')==null}">
			<s:select
				label="%{getText(request.getParameter('labelSelectList'))}" 
				name="form.updatingLanguage"
				list="%{#request[request.getParameter('selectList')]}"
				value="%{form.updatingLanguage}"/>
		</s:if>
		<s:else>
			<s:select
				headerValue="%{getText(request.getParameter('selectListHeaderValue'))}"
				headerKey="%{getText(request.getParameter('selectListHeaderKey'))}"
				label="%{getText(request.getParameter('labelSelectList'))}" 
				name="form.updatingLanguage"
				list="%{#request[request.getParameter('selectList')]}"
				value="%{request.getParameter('selectedValueInSelectList')}"/>
		</s:else>	
	</div>
	<div class="hspacer-left-50p">
		<div>
			<label class="label"><fmt:message key="${param.labelData}"/>:</label>
			<s:if test="%{request.getParameter('dataProperty')==''}">
				<input class="<c:out value="${param.cssDataProperty}"/>" type="text" name="form.updatingLabel" value="<c:out value="${form.updatingLabel}"/>" />
			</s:if>
			<s:else>
				<input class="<c:out value="${param.cssDataProperty}"/>" type="text" name="<c:out value="${param.iteratorList}"/>[0].<c:out value="${param.dataProperty}"/>"/>
			</s:else>
		</div>
	</div>
	<div class="clear">&nbsp;</div>
	
	<div class="crud-list scroll-table-outer-body">
		<div class="scroll-table-inner-body" style="height: 262px;">
			<table class="crud-list-sortable">
				<thead>
					<tr>
						<th><s:property value="%{getText(request.getParameter('labelSelectList'))}" /></th>
						<th><s:property value="%{getText(request.getParameter('labelData'))}" /></th>
						<th style="width: 40em; text-align: left">
							<div style="padding-top: 0.9em; float: left;">
								<span class="mdo-ui-button ui-state-default ui-corner-all">
									<span class="ui-icon ui-icon-arrowthick-1-w"></span>
									<s:set id="url" value="%{request.getParameter('backUrl')}"/>
									<c:if test="${empty url}">
										<s:url id="url" action="%{request.getParameter('action')}" method="list" includeParams="none"/>
									</c:if>
						 			<s:a href="%{url}" cssClass="admin-manager-cancel"><fmt:message key="admin.manager.back"/></s:a>
								</span>
							</div>
						
							<button class="mdo-ui-button ui-state-default ui-corner-all crud-list-reset" type="reset">
								<span class="ui-icon ui-icon-closethick"></span><fmt:message key="admin.manager.reset" />
							</button>
							<button class="mdo-ui-button ui-state-default ui-corner-all crud-list-add" name="method:labels">
								<span class="ui-icon ui-icon-plusthick"></span><fmt:message key="admin.manager.add" />
							</button>
							<button id="submit" class="mdo-ui-button ui-state-default ui-corner-all">
								<span class="ui-icon ui-icon-disk"></span>
								<fmt:message key="admin.manager.save"/>
							</button>
						</th>
				    </tr>
				</thead>
				<tbody class="data-container">
					<tr class="crud-list-row-template">
			   			<td>
			   			</td>
			   			<td>
			   				<label><s:property value="value" /></label>
							<s:if test="%{request.getParameter('dataProperty')==''}">
								<s:hidden name="%{request.getParameter('iteratorList')}[X]" value="" disabled="true" />
							</s:if>
							<s:else>
								<s:hidden name="%{request.getParameter('iteratorList')}[X].%{request.getParameter('dataProperty')}" value="" disabled="true" />
							</s:else>
			   			</td>
			   			<td style="width: 40em;">
							<div style="padding-top: 0.9em; float: left;">
								<span class="mdo-ui-button ui-state-default ui-corner-all crud-list-edit">
									<span class="ui-icon ui-icon-tag"></span>
									<s:a href="#"><fmt:message key="admin.manager.edit"/></s:a>
								</span>
							</div>
							<button class="mdo-ui-button ui-state-default ui-corner-all crud-list-remove" name="method:removeLabel">
								<span class="ui-icon ui-icon-minusthick"></span><fmt:message key="admin.manager.remove"/>
							</button>
			   			</td>
					</tr>
					<s:iterator value="%{#request[request.getParameter('iteratorList')]}" status="status">
					<tr id="${key}">
			   			<td>
			   				<s:property value="%{#request[request.getParameter('selectList')][key]}" />
			   			</td>
			   			<td>
			   				<label><s:property value="value" /></label>
							<s:if test="%{request.getParameter('dataProperty')==''}">
								<s:hidden name="%{request.getParameter('iteratorList')}[%{key}]" value="%{value}"/>
							</s:if>
							<s:else>
								<s:hidden name="%{request.getParameter('iteratorList')}[%{key}].%{request.getParameter('dataProperty')}" value="%{value[request.getParameter('dataProperty')]}"/>
							</s:else>
			   			</td>
			   			<td style="width: 40em;">
							<div style="padding-top: 0.9em; float: left;">
								<span class="mdo-ui-button ui-state-default ui-corner-all crud-list-edit">
									<s:url id="url" action="%{request.getParameter('action')}" method="form">
										<s:param name="form.dtoBean.id" value="%{form.dtoBean.id}" />
										<s:param name="form.updatingLanguage" value="%{key}"/>
										<s:param name="form.updatingLabel" value="%{value}"/>
									</s:url>
									<span class="ui-icon ui-icon-tag"></span>
									<s:a href="%{url}"><fmt:message key="admin.manager.edit"/></s:a>
								</span>
							</div>
							<button class="mdo-ui-button ui-state-default ui-corner-all crud-list-remove" name="method:removeLabel" value="${key}">
								<span class="ui-icon ui-icon-minusthick"></span><fmt:message key="admin.manager.remove"/>
							</button>
			   			</td>
					</tr>
			   		</s:iterator>	
				</tbody>
			</table>
		</div>
	</div>			
</div>
