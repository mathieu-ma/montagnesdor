<%@ taglib uri="/WEB-INF/mdo.tld" prefix="mdo" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<div class="hspacer-left-100p">
	<s:select label="%{getText('products.manager.restaurant')}" name="form.dtoBean.restaurant.id" list="form.viewBean.restaurants" listKey="id" listValue="name" value="form.dtoBean.restaurant.id" required="true"/>
	<s:textfield label="%{getText('products.manager.code')}" name="form.dtoBean.code" readonly='%{form.dtoBean.id!=null}' required="true"/>

 	<%-- 
 		Change Locale for Number Format in input text because of javascript
 		Use the change of locale instead of BigDecimal Converter because we want that user type "." instead of "," in case of french locale 
 	--%>
	<s:text name="products.manager.price" var="priceLabel" />
	<mdo:setLocale value="en"/>
	<s:textfield cssClass="number-signed-float" label="%{priceLabel}" name="form.dtoBean.price" value="%{form.dtoBean.price!=null?getText('format.number.decimal.3.2',{form.dtoBean.price}):''}"/>
 	<%-- Change back Locale --%>
	<mdo:setLocale value="${userSession.currentLocale.languageCode}"/>
	
	<s:select label="%{getText('products.manager.vat')}" name="form.dtoBean.vat.id" 
		list="form.dtoBean.restaurant.vats" listKey="vat.id" listValue="%{getText('format.number.decimal.3.2',{vat.rate})}" required="true"/>
	<div class="hspacer-left-100p">
		<label class="label"><fmt:message key="products.manager.color"/>:</label>
		<div class="hspacer-left-25p">&nbsp;</div>
		<div style="float: left; padding-right: 3px; line-height: 33px;">
			<input id="colorRGB" type="text" name="form.dtoBean.colorRGB" value="<s:property value="%{form.dtoBean.colorRGB.toUpperCase()}"/>"/>
		</div>
		<div style="float: left; cursor: pointer;" id="colorSelector" class="ui-corner-all"></div>
	</div>
</div>
<div class="clear">
	<br/>
	<br/>
</div>

<s:include value="/jsp/commons/form-actions.jsp">
	<s:set var="cancelUrlMethod" value="%{'list'}" />
	<s:set name="cancelUrlParam1" value="%{form.dtoBean.restaurant.id}"/>
	<s:set name="cancelUrlParam2" value="%{form.dtoBean.restaurant.reference}"/>
	<s:set name="cancelUrlParam3" value="%{form.dtoBean.restaurant.name}"/>
	<s:if test="%{form.dtoBean.restaurant.id!='' && form.viewBean.restaurants.contains(form.dtoBean.restaurant)}">
		<s:set name="cancelUrlMethod" value="%{'listProducts'}"/>
	</s:if>
	<s:url id="cancelUrl" action="ProductsManager" method="%{cancelUrlMethod}" escapeAmp='false'>
		<s:param name="form.dtoBean.restaurant.id" value="%{cancelUrlParam1}"/>
		<s:param name="form.dtoBean.restaurant.reference" value="%{cancelUrlParam2}"/>
		<s:param name="form.dtoBean.restaurant.name" value="%{cancelUrlParam3}"/>
	</s:url>
	<s:param name="cancelUrl" value="%{cancelUrl}" />
</s:include>
