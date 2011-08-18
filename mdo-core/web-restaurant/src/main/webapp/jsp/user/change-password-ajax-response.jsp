<%--
	When you use an absolute URI, you do not have to add the taglib element to web.xml; 
	the JSP container automatically locates the TLD inside the JSTL library implementation.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>
<%@ taglib uri="/WEB-INF/mdo.tld" prefix="mdo" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<label id="change-password-ajax-response"><s:if test="hasActionErrors()">KO</s:if><s:else>OK</s:else></label>