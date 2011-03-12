<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<script type="text/javascript" src="<c:out value="${pageContext.request.contextPath}"/>/javascript/locales.js"></script>

<s:form action="LocalesManagerCUD" method="post">
	<s:hidden name="method:save"/>
	<div class="scroll-table-outer-body scroll-table-outer-body-2-headers">
		<div class="scroll-table-inner-body">
			<table class="sortable">
				<thead>
					<tr>
				    	<th>
				    		<s:select label="%{getText('locales.manager.language')}" name="form.dtoBean.languageCode" list="form.viewBean.languages" listKey="key" listValue="value"/>
				    	</th>
				    	<th>
							<c:if test="${not empty form.viewBean.languages}">				    	
								<div>
									<button id="submit" class="mdo-ui-button ui-state-default ui-corner-all">
										<span class="ui-icon ui-icon-extlink"></span><fmt:message key="admin.manager.create"/>
									</button>
								</div>
							</c:if>
				    	</th>
				    </tr>	
					<tr>
					    <th><fmt:message key="locales.manager.language"/></th>
				    	<th>
							<div class="error">
								<s:actionerror/>
							</div>
							<div class="error">
								<s:actionmessage/>
							</div>
				    	</th>
				  	</tr>
				</thead>
				<tbody>
				<s:iterator value="form.viewBean.list" id="locale">
					<tr>
						<td><s:property value="languageCode"/>(<s:property value="displayLanguage"/>)</td>
					    <td>
					    	<s:if test="id != #session.userSession.currentLocale.id">
				    			<div>
									<span class="mdo-ui-button ui-state-default ui-corner-all">
										<span class="ui-icon ui-icon-trash"></span>
				    					<s:url id="url" action="LocalesManagerCUD" method="delete">
				    						<s:param name="form.dtoBean.id" value="%{id}"/>
				    					</s:url>
				    					<s:a id="%{id}" href="%{url}"><fmt:message key="admin.manager.delete"/></s:a>
									</span>
								</div>
					    	</s:if>
					    	<s:if test="id == #session.userSession.currentLocale.id">
				    			<div style="visibility: hidden;">
									<span class="mdo-ui-button ui-state-default ui-corner-all">
										<span class="ui-icon ui-icon-trash"></span>
				    					<s:url id="url" action="LocalesManagerCUD" method="delete">
				    						<s:param name="form.dtoBean.id" value="%{id}"/>
				    					</s:url>
				    					<s:a id="%{id}" href="%{url}"><fmt:message key="admin.manager.delete"/></s:a>
									</span>
								</div>
					    	</s:if>
					    </td>
					</tr>		
				</s:iterator>
				</tbody>
			</table>
		</div>
	</div>
</s:form>

