<%@ taglib uri="/WEB-INF/mdo.tld" prefix="mdo" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<div class="mdo-upsidedown">
	<s:hidden name="form.dtoBean.id"/>

	<br/>
	<div class="hspacer-left-100p">	
		<s:textfield cssClass="required" label="%{getText('restaurants.manager.name')}" name="form.dtoBean.name" size="84" maxlength="80" required="true" requiredposition="right" />
	</div>
	<div class="hspacer-left-100p">	
		<label class="label"><fmt:message key="restaurants.manager.registration.date"/>:</label>
		<input class="label mdo-date" style="color: maroon" readonly="readonly" type="text" id="form-dtoBean-registrationDate-datepicker" value="<s:date name="form.dtoBean.registrationDate" format="EEEE dd MMMM yyyy"/>"/>
		<input type="text" id="form-dtoBean-registrationDate" name="form.dtoBean.registrationDate" value="<s:date name="form.dtoBean.registrationDate" format="yyyy-MM-dd"/>T00:00:00"/>
	</div>
	<div class="hspacer-left-50p">
		<s:textfield label="%{getText('restaurants.manager.address.road')}" name="form.dtoBean.addressRoad"/>
		<s:textfield label="%{getText('restaurants.manager.address.zip')}" name="form.dtoBean.addressZip"/>
		<s:textfield label="%{getText('restaurants.manager.address.city')}" name="form.dtoBean.addressCity"/>
		<s:textfield label="%{getText('restaurants.manager.phone')}" name="form.dtoBean.phone"/>
	</div>
	<div class="hspacer-left-50p">
		<s:textfield label="%{getText('restaurants.manager.reference')}" name="form.dtoBean.reference" readonly="true" />
		<s:textfield label="%{getText('restaurants.manager.vat.reference')}" name="form.dtoBean.vatRef"/>
		<s:textfield label="%{getText('restaurants.manager.visa.reference')}" name="form.dtoBean.visaRef"/>
	</div>
	<div style="clear: both;"></div>
	<div class="hspacer-left-100p">
	 	<%-- 
	 		Change Locale for Number Format in input text because of javascript
	 		Use the change of locale instead of BigDecimal Converter because we want that user type "." instead of "," in case of french locale 
	 	--%>
		<mdo:setLocale value="en"/>
		<div class="hspacer-left-50p">
			<s:textfield cssClass="number-float-percent" label="%{getText('restaurants.manager.takeaway.basic.reduction')}" name="form.dtoBean.takeawayBasicReduction" value="%{getText('format.number.decimal.3.2',{form.dtoBean.takeawayBasicReduction})}" />
		</div>	
		<div class="hspacer-left-50p">
			<s:textfield cssClass="number-float" label="%{getText('restaurants.manager.takeaway.min.amount.reduction')}"  name="form.dtoBean.takeawayMinAmountReduction" value="%{getText('format.number.decimal.3.2',{form.dtoBean.takeawayMinAmountReduction})}" />
		</div>
	 	<%-- Change back Locale --%>
		<mdo:setLocale value="${userSession.currentLocale.languageCode}"/>
	</div>
	<div class="hspacer-left-100p" id="wwgrp_RestaurantsManager_form_dtoBean_daoBean_amount">
		<div class="hspacer-left-50p">
		<s:set name="mdoTrue" value="%{getText('mdo.true')}"/>
		<s:set name="mdoFalse" value="%{getText('mdo.false')}"/>
			<s:select label="%{getText('restaurants.manager.sum.amount.vat')}"
		        name="form.dtoBean.vatByTakeaway"
		        list="#{'false':#mdoFalse, 'true':#mdoTrue}"
		        value="form.dtoBean.vatByTakeaway"
		 	/>
	 	</div>
		<div class="hspacer-left-50p">
			<s:select label="%{getText('restaurants.manager.specific.round')}" name="form.dtoBean.specificRound.id" list="form.viewBean.specificRounds" listKey="id" listValue="name" value="form.dtoBean.specificRound.id" required="true"/>
		</div>
	</div>
	<div style="clear: both;"></div>
	<div class="hspacer-left-100p">
		<s:textfield label="%{getText('restaurants.manager.3des.key')}" name="form.dtoBean.tripleDESKey" readonly="true"/>
	</div>
</div>
