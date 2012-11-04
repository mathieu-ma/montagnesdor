<!--
	When you use an absolute URI, you do not have to add the taglib element to web.xml; 
	the JSP container automatically locates the TLD inside the JSTL library implementation.
-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>
<%@ taglib uri="/WEB-INF/mdo.tld" prefix="mdo" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

		<div id="applets" class="applets">
			<fmt:bundle basename="fr.mch.mdo.restaurant.resources.i18n.ApplicationApplets">
				<!-- WARNING: Applet must not be inside the JQuery resizable plugin -->
				<!-- Default value for Internet Explorer -->
				<c:set var="classid" scope="request" value="clsid:8AD9C840-044E-11D1-B3E9-00805F499D93"/>
				<c:if test="${not fn:contains(header['user-agent'], 'MSIE')}">
					<c:set var="classid" scope="request" value="java:fr.mch.mdo.applets.DateTimeApplet.class"/>
				</c:if>
				<!-- For Google Chrome change object to applet and add code attribute -->
				<object id="objectDateTimeApplet" name="dateTimeApplet" classid="${classid}" width="1" height="1">
					<param name="code" value="fr.mch.mdo.applets.DateTimeApplet.class"/>
	     			<param name="codebase" value="<c:out value="${pageContext.request.contextPath}"/>/jsp/commons" />
					<param name="type" value="application/x-java-applet;version=1.3" />
					<param name="archive" value="SDateTimeApplet.jar"/>							
					<param name='localeLanguage' value='<c:out value="${sessionScope.userSession.currentLocale.languageCode}"/>'></param>
					<param name='patternDate' value='<fmt:message key="applets.DateTimeApplet.param.patternDate.value"/>'></param>
					<param name='patternDateShort' value='<fmt:message key="applets.DateTimeApplet.param.patternDateShort.value"/>'></param>
					<param name='patternTime' value='<fmt:message key="applets.DateTimeApplet.param.patternTime.value"/>'></param>
					<param name='patternDateTime' value='<fmt:message key="applets.DateTimeApplet.param.patternDateTime.value"/>'></param>
					<param name='patternEntryDate' value='dd/MM/yyyy/HH/mm/ss'></param>
<!-- 					
					<param NAME="MAYSCRIPT" VALUE="true"></param>
					<param name='startJavascriptFunction' value='focus'></param>
 --> 				
 					<param name='initial_focus' value='false'></param>
 					<param name='debug' value='false'></param>
	     			<comment>
	         			<embed type="application/x-java-applet;version=1.3" width="1" height="1" name="dateTimeApplet" id="embedDateTimeApplet"
							codebase="<c:out value="${pageContext.request.contextPath}"/>/jsp/commons"
	             			archive="SDateTimeApplet.jar"
	             			code="fr.mch.mdo.applets.DateTimeApplet" 
	             			localeLanguage='<c:out value="${sessionScope.userSession.currentLocale.languageCode}"/>'
	             			patternDate='<fmt:message key="applets.DateTimeApplet.param.patternDate.value"/>'
	             			patternDateShort='<fmt:message key="applets.DateTimeApplet.param.patternDateShort.value"/>'
	             			patternTime='<fmt:message key="applets.DateTimeApplet.param.patternTime.value"/>'
	             			patternDateTime='<fmt:message key="applets.DateTimeApplet.param.patternDateTime.value"/>'
	             			initial_focus='false'
	             			pluginspage="http://javaweb.eng/plugin/plugin-install.html">
							<noembed>
					        	No JDK 1.3 support for APPLET!!
					        </noembed>
						</embed>
	         		</comment>
				</object>
				<c:set var="classid" scope="request" value="clsid:8AD9C840-044E-11D1-B3E9-00805F499D93"/>
				<c:if test="${not fn:contains(header['user-agent'], 'MSIE')}">
					<c:set var="classid" scope="request" value="java:fr.mch.mdo.applets.PrinterApplet.class"/>
				</c:if>
				<object id='printerApplet' name='printerApplet' type="application/x-java-applet" classid="${classid}" archive="SPrinterApplet.jar" codebase="<c:out value="${pageContext.request.contextPath}"/>/jsp/commons" width="1" height="1">
					<param name="code" value="fr.mch.mdo.applets.PrinterApplet"/>
					<param name="archive" value="SPrinterApplet.jar"/>							
					<param name="codebase" value="<c:out value="${pageContext.request.contextPath}"/>/jsp/commons"/>
					<param name='charset' value='<fmt:message key="applets.PrinterApplet.param.charset.value"/>'></param>
					<param name='linuxDriverName'  value='<fmt:message key="applets.PrinterApplet.param.linux.driver.name.value"/>'></param>
					<param name='linuxPortCom'  value='/dev/ttyS0'></param>
					<!-- param name='windowsDriverName'  value='<fmt:message key="applets.PrinterApplet.param.windows.driver.name.value"/>'></param-->
					<param name='windowsDriverName'  value='<fmt:message key="applets.PrinterApplet.param.linux.driver.name.value"/>'></param>
					<param name='windowsPortCom'  value='<fmt:message key="applets.PrinterApplet.param.windows.printer.portcom.value"/>'></param>
					<param name='serialportBauds'  value='<fmt:message key="applets.PrinterApplet.param.serialport.bauds.value"/>'></param>
					<param name='serialportBits'  value='<fmt:message key="applets.PrinterApplet.param.serialport.bits.value"/>'></param>
					<param name='serialportStopBits'  value='<fmt:message key="applets.PrinterApplet.param.serialport.stopbits.value"/>'></param>
					<param name='serialportParity'  value='<fmt:message key="applets.PrinterApplet.param.serialport.parity.value"/>'></param>
					<!--param name='packet'  value='<fmt:message key="applets.PrinterApplet.param.packet.value"/>'></param-->
					<!--param name='pause'  value='<fmt:message key="applets.PrinterApplet.param.pause.value"/>'></param-->
					<param name='packet'  value='40'></param>
					<param name='pause'  value='1500'></param>
					<param name='specialCharactersString'  value='<fmt:message key="applets.PrinterApplet.param.printer.specialCharacters.value"/>'></param>
					<param name='bindCharacteresSpeciauxString'  value='<fmt:message key="applets.PrinterApplet.param.printer.bindSpecialCharacters.value"/>'></param>
					<!--param name='debug' value='<fmt:message key="applets.PrinterApplet.param.debug.value"/>'></param-->
 					<param name='initial_focus' value='false'></param>
					<param name='debug' value='true'></param>
	     			<comment>
	         			<embed type="application/x-java-applet;version=1.3" width="1" height="1" name="printerApplet" id="embedPrinterApplet"
							codebase="<c:out value="${pageContext.request.contextPath}"/>/jsp/commons"
	             			archive="SPrinterApplet.jar"
	             			code="fr.mch.mdo.applets.PrinterApplet" 
	             			charset='<fmt:message key="applets.PrinterApplet.param.charset.value"/>'
	             			linuxDriverName='<fmt:message key="applets.PrinterApplet.param.linux.driver.name.value"/>'
	             			linuxPortCom='/dev/ttyUSB1'
	             			windowsDriverName='<fmt:message key="applets.PrinterApplet.param.linux.driver.name.value"/>'
	             			windowsPortCom='<fmt:message key="applets.PrinterApplet.param.windows.printer.portcom.value"/>'
	             			serialportBauds='<fmt:message key="applets.PrinterApplet.param.serialport.bauds.value"/>'
	             			serialportBits='<fmt:message key="applets.PrinterApplet.param.serialport.bits.value"/>'
	             			serialportStopBits='<fmt:message key="applets.DateTimeApplet.param.patternDateTime.value"/>'
	             			serialportParity='<fmt:message key="applets.PrinterApplet.param.serialport.parity.value"/>'
	             			packet='40'
	             			pause='1500'
	             			specialCharactersString='<fmt:message key="applets.PrinterApplet.param.printer.specialCharacters.value"/>'
	             			bindCharacteresSpeciauxString='<fmt:message key="applets.PrinterApplet.param.printer.bindSpecialCharacters.value"/>'
	             			debug='true'
	             			initial_focus='false'
	             			pluginspage="http://javaweb.eng/plugin/plugin-install.html">
							<noembed>
					        	No JDK 1.3 support for APPLET!!
					        </noembed>
						</embed>
	         		</comment>
				</object>
			</fmt:bundle>	
		</div>	
