<%@ page isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<pre>
Sorry, but things didn't work out as planned. I've logged as much as
I know about the problem, so rest assured that my master will look
into what's wrong as soon as he's sober.
</pre>
<a href="#" id="details">Details</a>
<jsp:useBean id="now" class="java.util.Date" />

<div id="errors" style="display: none;">
	<div id="error-date"><fmt:formatDate value="${now}" pattern="EEEE dd MMMM yyyy" /></div>
	<pre>
	  -----
	  Request URI:  ${pageContext.errorData.requestURI}
	  Servlet Name: ${pageContext.errorData.servletName}
	  Status Code:  ${pageContext.errorData.statusCode}
	  Exception: ${pageContext.errorData.throwable}
	  Message:      ${pageContext.errorData.throwable.message}
	  Stack Trace:
	  <c:forEach var="st" items="${pageContext.errorData.throwable.stackTrace}">
	     ${st}
	  </c:forEach>
	  -----
	</pre>
</div>
<script>
$(document).ready(	
	function() {
		$("#details").toggle(
	      function () {
	    	  $("#errors").css("display", "block");
	      },
	      function () {
	    	  $("#errors").css("display", "none");
	    	  
	      }
	    );
	}
);
</script>
