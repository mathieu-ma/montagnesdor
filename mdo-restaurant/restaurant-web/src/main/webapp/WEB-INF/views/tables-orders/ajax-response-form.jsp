<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

	<tiles:insertDefinition name="ajax.main.layout">
		<tiles:putAttribute name="body-css" value="body" type="string" />
		<tiles:putAttribute name="menu" value="/jsp/tables-orders/menu.jsp" />
		<tiles:putAttribute name="body" value="/jsp/tables-orders/orders-body.jsp" />
		<tiles:putAttribute name="footer" value="/jsp/tables-orders/footer.jsp" />
	</tiles:insertDefinition>
			
