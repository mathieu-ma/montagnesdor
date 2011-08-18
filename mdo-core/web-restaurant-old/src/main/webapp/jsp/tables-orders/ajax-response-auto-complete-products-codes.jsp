<%@ taglib uri="/struts-tags" prefix="s" %>
<s:iterator value="form.dtoBean.productsCodes">
<s:property value="value"/>|<s:property value="key"/>
</s:iterator>
