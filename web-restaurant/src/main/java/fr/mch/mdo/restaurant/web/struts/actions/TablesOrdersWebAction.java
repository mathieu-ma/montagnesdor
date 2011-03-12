package fr.mch.mdo.restaurant.web.struts.actions;

import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.business.managers.tables.orders.ITablesOrdersManager;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.OrderLineDtoBean;
import fr.mch.mdo.restaurant.dto.beans.ProductDtoBean;
import fr.mch.mdo.restaurant.dto.beans.TablesOrdersDtoBean;
import fr.mch.mdo.restaurant.ioc.MdoBeanFactory;
import fr.mch.mdo.restaurant.ui.forms.TablesOrdersForm;

public final class TablesOrdersWebAction extends MdoAbstractWebAction
{
    /**
     * 
     */
    private static final long serialVersionUID = -1L;

    public TablesOrdersWebAction()
    {
	super(MdoBeanFactory.getInstance().getLogger(TablesOrdersWebAction.class.getName()), new TablesOrdersForm());
    }

    public String form() throws Exception
    {
	String result = "ajax-response-form";

	TablesOrdersDtoBean dtoBean = (TablesOrdersDtoBean) super.getForm().getDtoBean();
	
	//Table name
	String tableNumber = dtoBean.getDinnerTableDtoBean().getTableNumber();
	super.getLogger().debug("Table name : " + tableNumber);
	try
	{
	    ITablesOrdersManager iTablesOrdersManager = MdoBeanFactory.getInstance().getTablesOrdersManager();
	    dtoBean.setDinnerTableDtoBean(iTablesOrdersManager.getTableByTableNumber(dtoBean.getUserContext(), tableNumber));
	    MdoUserContext userContext = (MdoUserContext)super.getUserContext();
	    userContext.setCurrentTable(dtoBean.getDinnerTableDtoBean());
	}
	catch (Exception e)
	{
	    addActionError(getText("error.table.name"));
	}
	
	return result;
    }

    public String autoCompleteTablesNames() throws Exception
    {
	String forwardPage = "ajax-response-auto-complete-tables-names";

	TablesOrdersDtoBean dtoBean = (TablesOrdersDtoBean) super.getForm().getDtoBean();
	
	//Prefix table name
	String prefixTableNumber = dtoBean.getPrefixTableNumber();
	super.getLogger().debug("Prefix table name : " + prefixTableNumber);
	try
	{
	    ITablesOrdersManager iTableOrdersManager = MdoBeanFactory.getInstance().getTablesOrdersManager();
	    dtoBean.setTablesNames(iTableOrdersManager.lookupTablesNamesByPrefix(dtoBean.getUserContext(), prefixTableNumber));
	}
	catch (Exception e)
	{
	    addActionError(getText("error.auto.complete.tables.names"));
	}

	// Forward control to the specified success URI
	return forwardPage;
    }
    
    public String tableCustomersNumber() throws Exception
    {
	String forwardPage = "ajax-response-table-customers-number";

	TablesOrdersDtoBean dtoBean = (TablesOrdersDtoBean) super.getForm().getDtoBean();
	String tableNumber = dtoBean.getDinnerTableDtoBean().getTableNumber();
	super.getLogger().debug("Table name : " + tableNumber);
	try
	{
	    ITablesOrdersManager iTableOrdersManager = MdoBeanFactory.getInstance().getTablesOrdersManager();
	    dtoBean.getDinnerTableDtoBean().setCustomersNumber(iTableOrdersManager.getCustomersNumberByTableNumber(dtoBean.getUserContext(), tableNumber));
	}
	catch (Exception e)
	{
	    addActionError(getText("error.table.customers.number"));
	}
	
	return forwardPage;
    }
    
    public String autoCompleteProductsCodes() throws Exception
    {
	String forwardPage = "ajax-response-auto-complete-products-codes";

	TablesOrdersDtoBean dtoBean = (TablesOrdersDtoBean) super.getForm().getDtoBean();
	
	//Prefix product code
	String prefixProductCode = dtoBean.getPrefixProductCode();
	super.getLogger().debug("Prefix product code : " + prefixProductCode);
	try
	{
	    if(prefixProductCode!=null)
	    {
    	    	ITablesOrdersManager iTablesOrdersManager = MdoBeanFactory.getInstance().getTablesOrdersManager();
    	    	dtoBean.setProductsCodes(iTablesOrdersManager.lookupProductsCodesByPrefix(dtoBean.getUserContext(), prefixProductCode));
	    }
	}
	catch (Exception e)
	{
	    addActionError(getText("error.auto.complete.products.codes"));
	}

	// Forward control to the specified success URI
	return forwardPage;
    }
    
    public String saveOrderLine() throws Exception
    {
	String forwardPage = "ajax-response-save-order-line";

	TablesOrdersDtoBean dtoBean = (TablesOrdersDtoBean) super.getForm().getDtoBean();
	
	//Order line
	OrderLineDtoBean orderLine = dtoBean.getOrderLine();
	super.getLogger().debug("Product code : " + orderLine.getCode());
	try
	{
	    ITablesOrdersManager iTablesOrdersManager = MdoBeanFactory.getInstance().getTablesOrdersManager();
	    iTablesOrdersManager.saveOrderLine(dtoBean.getUserContext(), orderLine);
	}
	catch (Exception e)
	{
	    addActionError(getText("error.save.order.line.product"));
	}

	// Forward control to the specified success URI
	return forwardPage;
    }
    
}
