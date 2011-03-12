<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"om/jsp/jstl/fmt" prefix="fmt" %>

	<fmt:message var="title" key="logon.title"/>
	<tiles:insertDefinition name="logon">
		<tiles:putAttribute name="title" value="${title}" />
	</tiles:insertDefinition>