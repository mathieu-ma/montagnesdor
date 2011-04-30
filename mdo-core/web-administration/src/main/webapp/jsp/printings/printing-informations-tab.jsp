<%@ taglib uri="/WEB-INF/mdo.tld" prefix="mdo" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<div class="hspacer-left-100p">
	<s:select label="%{getText('printing.informations.manager.restaurant')}" name="form.dtoBean.restaurant.id" list="form.viewBean.restaurants" listKey="id" listValue="name" value="form.dtoBean.restaurant.id" required="true"/>
	<s:select label="%{getText('printing.informations.manager.alignment')}" name="form.dtoBean.alignment.id" list="form.viewBean.alignments" listKey="id" listValue="name" value="form.dtoBean.alignment.id" required="true"/>
	<s:select label="%{getText('printing.informations.manager.size')}" name="form.dtoBean.size.id" list="form.viewBean.sizes" listKey="id" listValue="name" value="form.dtoBean.size.id" required="true"/>
	<s:select label="%{getText('printing.informations.manager.part')}" name="form.dtoBean.part.id" list="form.viewBean.parts" listKey="id" listValue="name" value="form.dtoBean.part.id" required="true"/>
	<s:textfield label="%{getText('printing.informations.manager.order')}" name="form.dtoBean.order" />

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
		<s:set name="cancelUrlMethod" value="%{'listPrintingInformations'}"/>
	</s:if>
	<s:url id="cancelUrl" action="PrintingInformationsManager" method="%{cancelUrlMethod}" escapeAmp='false'>
		<s:param name="form.dtoBean.restaurant.id" value="%{cancelUrlParam1}"/>
		<s:param name="form.dtoBean.restaurant.reference" value="%{cancelUrlParam2}"/>
		<s:param name="form.dtoBean.restaurant.name" value="%{cancelUrlParam3}"/>
	</s:url>
	<s:param name="cancelUrl" value="%{cancelUrl}" />
</s:include>
