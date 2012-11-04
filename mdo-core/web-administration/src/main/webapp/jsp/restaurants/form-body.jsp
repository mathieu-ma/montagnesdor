<%@ taglib uri="/WEB-INF/mdo.tld" prefix="mdo" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/restaurants.js"></script>
<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/jquery/jquery-ui/i18n/jquery.ui.datepicker-<s:property value="#session.userSession.currentLocale.languageCode"/>.js"></script>
<s:form action="RestaurantsManagerCUD" method="post" validate="false">
	<div id="tabs" class="ui-tabs ui-widget ui-widget-content ui-corner-all">
		<ul class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all">
			<li class="ui-state-default ui-corner-top"><a class="a-tabs" href="#tabs-1"><span><fmt:message key="restaurants.manager.tab.restaurant"/></span></a></li>
			<li class="ui-state-default ui-corner-top"><a class="a-tabs" href="#tabs-2"><span><fmt:message key="restaurants.manager.tab.vat"/></span></a></li>
			<li class="ui-state-default ui-corner-top"><a class="a-tabs" href="#tabs-3"><span><fmt:message key="restaurants.manager.tab.vat.table.type"/></span></a></li>
			<li class="ui-state-default ui-corner-top"><a class="a-tabs" href="#tabs-4"><span><fmt:message key="restaurants.manager.tab.prefix.table.name"/></span></a></li>
			<li class="ui-state-default ui-corner-top"><a class="a-tabs" href="#tabs-5"><span><fmt:message key="restaurants.manager.tab.reduction.table"/></span></a></li>
 		</ul>
	    <div class="tab-contents" style="height: 25em;">
			<div id="tabs-1" class="ui-tabs-panel ui-widget-content ui-corner-bottom">
				<jsp:include page="restaurant-tab.jsp" />
				<s:submit id="hidden-submit-form" />
		    </div>
			<div id="tabs-2" class="ui-tabs-panel ui-widget-content ui-corner-bottom">
				<jsp:include page="vat-tab.jsp" />
		    </div>
			<div id="tabs-3" class="ui-tabs-panel ui-widget-content ui-corner-bottom">
				<jsp:include page="vat-table-type-tab.jsp" />
		    </div>
			<div id="tabs-4" class="ui-tabs-panel ui-widget-content ui-corner-bottom">
				<jsp:include page="prefix-table-name-tab.jsp" />
		    </div>
			<div id="tabs-5" class="ui-tabs-panel ui-widget-content ui-corner-bottom">
				<jsp:include page="reduction-table-tab.jsp" />
		    </div>
		</div>
	</div>
	
	<s:include value="/jsp/commons/form-actions.jsp">
		<s:url id="cancelUrl" action="RestaurantsManager" method="list" />
		<s:param name="cancelUrl" value="%{cancelUrl}" />
	</s:include>
		
</s:form>

<div class="error">
	<s:actionerror/>
</div>

