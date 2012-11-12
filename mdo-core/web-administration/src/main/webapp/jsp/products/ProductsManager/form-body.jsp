<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<link rel="stylesheet" media="screen" type="text/css" href="<c:out value="${pageContext.request.contextPath}"/>/css/colorpicker.css" />
<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/jquery/plugins/colorpicker.js"></script>
<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/products.js"></script>

<s:form action="ProductsManagerCUD" method="post" validate="false">
	<s:if test="%{form.restaurant.vats.size()>0}">
		<s:hidden name="form.dtoBean.id"/>

		<div id="tabs" class="ui-tabs ui-widget ui-widget-content ui-corner-all">
			<ul class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all">
				<li class="ui-state-default ui-corner-top"><a class="a-tabs" href="#tabs-1"><span><fmt:message key="products.manager.tab.product"/></span></a></li>
				<li class="ui-state-default ui-corner-top"><a class="a-tabs" href="#tabs-2"><span><fmt:message key="products.manager.tab.labels"/></span></a></li>
				<li class="ui-state-default ui-corner-top"><a class="a-tabs" href="#tabs-3"><span><fmt:message key="products.manager.tab.categories"/></span></a></li>
			</ul>
		    <div class="tab-contents" style="height: 25em;">
				<div id="tabs-1" class="ui-tabs-panel ui-widget-content ui-corner-bottom">
					<div class="mdo-upsidedown-1">
						<jsp:include page="product-tab.jsp" />
						<s:submit id="hidden-submit-form"/>
					</div>	
				</div>
				<div id="tabs-2" class="ui-tabs-panel ui-widget-content ui-corner-bottom">
					<div class="mdo-upsidedown-2">
						<s:set var="backUrlMethod" value="%{'list'}" />
						<s:if test="%{form.dtoBean.restaurant.id!='' && form.viewBean.restaurants.contains(form.dtoBean.restaurant)}">
							<s:set name="backUrlMethod" value="%{'listProducts'}"/>
						</s:if>
						<s:set name="backUrlParam1" value="%{form.dtoBean.restaurant.id}"/>
						<s:set name="backUrlParam2" value="%{form.dtoBean.restaurant.reference}"/>
						<s:set name="backUrlParam3" value="%{form.dtoBean.restaurant.name}"/>
						<s:url id="backUrl" action="ProductsManager" method="%{backUrlMethod}"  escapeAmp='false'>
							<s:param name="form.dtoBean.restaurant.id" value="%{backUrlParam1}"/>
							<s:param name="form.dtoBean.restaurant.reference" value="%{backUrlParam2}"/>
							<s:param name="form.dtoBean.restaurant.name" value="%{backUrlParam3}"/>
						</s:url>
						<jsp:include page="../../commons/crud-list.jsp">
							<jsp:param name="cssId" value="languages"/>
							<jsp:param name="action" value="ProductsManager"/>
							<jsp:param name="labelSelectList" value="products.manager.language"/>
							<jsp:param name="selectList" value="form.viewBean.languages"/>
							<jsp:param name="selectedValueInSelectList" value="${session.userSession.currentLocale.id}"/>
							<jsp:param name="iteratorList" value="form.dtoBean.labels"/>
							<jsp:param name="cssDataProperty" value=""/>
							<jsp:param name="dataProperty" value=""/>
							<jsp:param name="labelData" value="products.manager.label"/>
							<jsp:param name="backUrl" value="${backUrl}" />
						</jsp:include>
						<div class="clear">
							<br/>
						</div>
					</div>
				</div>
				<div id="tabs-3" class="ui-tabs-panel ui-widget-content ui-corner-bottom <s:property value='%{form.selectedTab==2?"":"ui-tabs-hide"}'/>">
					<div class="mdo-upsidedown-3">
						<jsp:include page="category-tab.jsp" />
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