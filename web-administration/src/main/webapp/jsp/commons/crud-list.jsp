<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<div id="<c:out value="${param.cssId}"/>">
	<input type="hidden" disabled="disabled" name="select-list-header-key" value="<fmt:message key="${param.selectListHeaderKey}"/>"/>
	<input type="hidden" disabled="disabled" name="data-add-empty" value="<fmt:message key="${param.dataAddEmpty}"/>"/>
	<input type="hidden" disabled="disabled" name="data-remove-empty" value="<fmt:message key="${param.dataRemoveEmpty}"/>"/>
	
	<div class="hspacer-left-50p">
		<c:out value="${param.selectedValueInList}"/>
		<s:if test="%{request.getParameter('selectListHeaderKey')==null || request.getParameter('selectListHeaderValue')==null}">
			<s:select
				label="%{getText(request.getParameter('labelSelectList'))}" 
				name="form.dtoBean.labels(0).key"
				list="%{#request[request.getParameter('selectList')]}"
				value="%{request.getParameter('selectedValueInSelectList')}"/>00
		</s:if>
		<s:else>
			<s:select
				headerValue="%{getText(request.getParameter('selectListHeaderValue'))}"
				headerKey="%{getText(request.getParameter('selectListHeaderKey'))}"
				label="%{getText(request.getParameter('labelSelectList'))}" 
				name="form.dtoBean.selectedList"
				list="%{#request[request.getParameter('selectList')]}"
				value="%{request.getParameter('selectedValueInSelectList')}"/>11
		</s:else>	
	</div>
	<div class="hspacer-left-50p">
		<div>
			<label class="label"><fmt:message key="${param.labelData}"/>:</label>
			<s:if test="%{request.getParameter('dataProperty')==''}">
				<input class="<c:out value="${param.cssDataProperty}"/>" type="text" name="form.dtoBean.labels(0).value"/>22
			</s:if>
			<s:else>
				<input class="<c:out value="${param.cssDataProperty}"/>" type="text" name="<c:out value="${param.iteratorList}"/>[0].<c:out value="${param.dataProperty}"/>"/>33
			</s:else>
		</div>
		<br/>
		<span class="mdo-ui-button ui-state-default ui-corner-all">
			<span class="ui-icon ui-icon-arrowthick-1-w"></span>
			<s:url id="url" action="%{request.getParameter('action')}" method="list" includeParams="none"/>
 			<s:a href="%{url}" cssClass="admin-manager-cancel"><fmt:message key="admin.manager.back"/></s:a>
		</span>
		&nbsp;
		<span class="mdo-ui-button ui-state-default ui-corner-all crud-list-reset">
			<span class="ui-icon ui-icon-closethick"></span>
			<fmt:message key="admin.manager.reset"/>
		</span>
		&nbsp;
		<span class="mdo-ui-button ui-state-default ui-corner-all crud-list-add">
			<span class="ui-icon ui-icon-plusthick"></span>
			<fmt:message key="admin.manager.add"/>
		</span>
		&nbsp;
		<span class="mdo-ui-button ui-state-default ui-corner-all crud-list-remove">
			<span class="ui-icon ui-icon-minusthick"></span>
			<fmt:message key="admin.manager.remove"/>
		</span>
		&nbsp;&nbsp;&nbsp;
	</div>
	<div class="crud-list">
		<s:iterator value="%{#request[request.getParameter('iteratorList')]}" status="status">
			<div class="clear">
				<br/>
			</div>
			<div class="hspacer-left-100p">
				<div class="hspacer-left-75p">
					<s:label label="%{getText(request.getParameter('labelSelectList'))}" value="%{#request[request.getParameter('selectList')][key]}"/>
					<s:if test="%{request.getParameter('dataProperty')==''}">
						<s:hidden name="%{request.getParameter('iteratorList')}[%{key}]" value="%{value}"/>
						<s:label label="%{getText(request.getParameter('labelData'))}" value="%{value}"/>
					</s:if>
					<s:else>
						<s:hidden name="%{request.getParameter('iteratorList')}[%{key}].%{request.getParameter('dataProperty')}" value="%{value[request.getParameter('dataProperty')]}"/>
						<s:label label="%{getText(request.getParameter('labelData'))}" value="%{value[request.getParameter('dataProperty')]}"/>
					</s:else>
				</div>
				<div class="hspacer-left-25p">
					<br/>
					<span class="mdo-ui-button ui-state-default ui-corner-all crud-list-edit">
						<s:hidden name="selectedId" disabled="true" value="%{key}"/>
						<span class="ui-icon ui-icon-tag"></span>
						<fmt:message key="admin.manager.edit"/>
					</span>
					&nbsp;
					<span class="mdo-ui-button ui-state-default ui-corner-all crud-list-remove">
						<s:hidden name="selectedId" disabled="true" value="%{key}"/>
						<span class="ui-icon ui-icon-minusthick"></span>
						<fmt:message key="admin.manager.remove"/>
					</span>
				</div>
			</div>
		</s:iterator>
	</div>
</div>
