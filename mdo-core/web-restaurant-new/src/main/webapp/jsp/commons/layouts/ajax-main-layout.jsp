<%@ page language="java" isErrorPage="true"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/struts-tags" prefix="s"%>

	<script type="text/javascript" src="/mdo-web-restaurant/javascript/ajax-main-layout.js"></script>

		<div id="main">
			<tiles:useAttribute id="divPart" name="menu" classname="java.lang.String"/>
			<c:if test="${divPart!='none'}">
			    <div id="menu">
					<div id="menu-resizable" class="mdo-resizable">
				        <tiles:insertAttribute name="menu"/>
				    </div>
			    </div>
		    </c:if>
			<div id="<tiles:getAsString name="body-css"/>">
		    	<tiles:insertAttribute name="body"/>
		    </div>
			<tiles:useAttribute id="divPart" name="footer" classname="java.lang.String"/>
			<c:if test="${divPart!='none'}">
				<div id="footer">
					<div id="footer-resizable" class="mdo-resizable">
				    	<tiles:insertAttribute name="footer"/>
				    </div>
			    </div>
		    </c:if>
		</div>
