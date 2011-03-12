<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<link rel="stylesheet" media="screen" type="text/css" href="<c:out value="${pageContext.request.contextPath}"/>/jquery-ui/theme/colorpicker.css" />
<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/jquery-ui/colorpicker.js"></script>
<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/products.js"></script>

<s:form action="ProductsManager" method="post" validate="true">
	<s:submit id="hidden-submit-form"/>
	<input type="hidden" id="method-labels" name="method:labels"/>
	<input type="hidden" id="method-save" name="method:save" disabled="disabled" />

	<s:if test="%{form.dtoBean.vats.size()>0}">
		<s:hidden name="form.selectedTab"/>
		<s:hidden name="form.dtoBean.daoBean.id"/>
		<input type="hidden" disabled="disabled" name="products-manager-warn-remove-product-one-label" value="<fmt:message key="products.manager.warn.remove.product.one.label"/>"/>
		<input type="hidden" disabled="disabled" name="products-manager-warn-remove-product-empty-label" value="<fmt:message key="products.manager.warn.remove.product.empty.label"/>"/>
		<input type="hidden" disabled="disabled" name="products-manager-warn-remove-product-label" value="<fmt:message key="products.manager.warn.remove.product.label"/>"/>
	
		<div id="tabs" class="ui-tabs ui-widget ui-widget-content ui-corner-all">
			<ul class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all">
				<li class="ui-state-default ui-corner-top"><a class="a-tabs" href="#tabs-1"><span><fmt:message key="products.manager.tab.product"/></span></a></li>
				<li class="ui-state-default ui-corner-top"><a class="a-tabs" href="#tabs-2"><span><fmt:message key="products.manager.tab.labels"/></span></a></li>
				<li class="ui-state-default ui-corner-top"><a class="a-tabs" href="#tabs-3"><span><fmt:message key="products.manager.tab.categories"/></span></a></li>
			</ul>
			<div id="tabs-1" class="ui-tabs-panel ui-widget-content ui-corner-bottom <s:property value='%{form.selectedTab==0?"":"ui-tabs-hide"}'/>">
				<div class="mdo-upsidedown-1">
					<div class="hspacer-left-100p">
						<input type="hidden" disabled="disabled" name="products-manager-warn-create-product-label" value="<fmt:message key="products.manager.warn.create.product.label"/>"/>
						<s:textfield label="%{getText('products.manager.code')}" name="form.dtoBean.daoBean.code" readonly='%{form.dtoBean.daoBean.id!=null}' required="true"/>
						<s:textfield cssClass="number-signed-float" label="%{getText('products.manager.price')}" name="form.dtoBean.daoBean.price" value="%{form.dtoBean.daoBean.price!=null?getText('format.number.decimal.3.2',{form.dtoBean.daoBean.price}):''}"/>
						<s:select label="%{getText('products.manager.vat')}" name="form.dtoBean.daoBean.vat.id" list="form.dtoBean.vats" listKey="id" listValue="%{getText('format.number.percent',{value})}" value="form.dtoBean.daoBean.vat.id" required="true"/>
						<s:select label="%{getText('products.manager.restaurant')}" name="form.dtoBean.daoBean.restaurant.id" list="form.dtoBean.restaurants" listKey="id" listValue="name" value="form.dtoBean.daoBean.restaurant.id" required="true"/>
						<div class="hspacer-left-100p">
							<label class="label"><fmt:message key="products.manager.color"/>:</label>
							<div class="hspacer-left-25p">&nbsp;</div>
							<div style="float: left; padding-right: 3px; line-height: 33px;">
								<input id="colorRGB" type="text" name="form.dtoBean.daoBean.colorRGB" value="<c:out value="${form.dtoBean.daoBean.colorRGB}"/>"/>
							</div>
							<div style="float: left; cursor: pointer;" id="colorSelector" class="ui-corner-all"></div>
						</div>
					</div>
					<div class="clear">
						<br/>
						<br/>
					</div>
					<div class="hspacer-left-100p">		
						<div class="hspacer-left-50p">
							<span id="submit" class="mdo-ui-button ui-state-default ui-corner-all">
								<span class="ui-icon ui-icon-disk"></span>
								<fmt:message key="admin.manager.save"/>
							</span>
						</div>
				   		<div class="hspacer-left-50p">
							<span class="mdo-ui-button ui-state-default ui-corner-all">
								<span class="ui-icon ui-icon-closethick"></span>
								<s:url id="url" action="ProductsManager" method="list" includeParams="none"/>
				  				<s:a cssClass="admin-manager-cancel" href="%{url}"><fmt:message key="admin.manager.cancel"/></s:a>
							</span>
				   		</div>
					</div>
					<div class="clear">
						<br/>
					</div>
				</div>
			</div>
			<div id="tabs-2" class="ui-tabs-panel ui-widget-content ui-corner-bottom <s:property value='%{form.selectedTab==1?"":"ui-tabs-hide"}'/>">
				<div class="mdo-upsidedown-2">
					<jsp:include page="../../commons/crud-list.jsp">
						<jsp:param name="cssId" value="languages"/>
						<jsp:param name="action" value="ProductsManager"/>
						<jsp:param name="dataAddEmpty" value="language.add.empty"/>
						<jsp:param name="dataRemoveEmpty" value="language.remove.empty"/>
						<jsp:param name="labelSelectList" value="products.manager.language"/>
						<jsp:param name="selectList" value="form.dtoBean.languages"/>
						<jsp:param name="selectListHeaderKey" value="products.manager.languages.select.list.header.key"/>
						<jsp:param name="selectListHeaderValue" value="products.manager.languages.select.list.header.value"/>
						<jsp:param name="selectedValueInSelectList" value="products.manager.languages.select.list.header.key"/>
						<jsp:param name="iteratorList" value="form.dtoBean.daoBean.labels"/>
						<jsp:param name="cssDataProperty" value=""/>
						<jsp:param name="dataProperty" value=""/>
						<jsp:param name="labelData" value="products.manager.label"/>
					</jsp:include>
					<div class="clear">
						<br/>
					</div>
				</div>
			</div>
			<div id="tabs-3" class="ui-tabs-panel ui-widget-content ui-corner-bottom <s:property value='%{form.selectedTab==2?"":"ui-tabs-hide"}'/>">
				<div class="mdo-upsidedown-3">
					<jsp:include page="../../commons/crud-list.jsp">
						<jsp:param name="cssId" value="categories"/>
						<jsp:param name="action" value="ProductsManager"/>
						<jsp:param name="dataAddEmpty" value="quantity.add.empty"/>
						<jsp:param name="dataRemoveEmpty" value="quantity.remove.empty"/>
						<jsp:param name="labelSelectList" value="products.manager.category"/>
						<jsp:param name="selectList" value="form.dtoBean.categoryLabels"/>
						<jsp:param name="selectListHeaderKey" value="products.manager.categories.select.list.header.key"/>
						<jsp:param name="selectListHeaderValue" value="products.manager.categories.select.list.header.value"/>
						<jsp:param name="selectedValueInSelectList" value="products.manager.categories.select.list.header.key"/>
						<jsp:param name="iteratorList" value="form.dtoBean.daoBean.categories"/>
						<jsp:param name="cssDataProperty" value="number-float"/>
						<jsp:param name="dataProperty" value="quantity"/>
						<jsp:param name="labelData" value="products.manager.category.quantity"/>
					</jsp:include>
					<div class="clear">
						<br/>
					</div>
				</div>
			</div>
		</div>
	</s:if>
	<s:else>

	</s:else>
		
</s:form>

<div class="error">
	<s:actionerror/>
</div>