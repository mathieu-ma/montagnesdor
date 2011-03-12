package fr.mch.mdo.restaurant.business.managers.tables.orders;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.business.managers.AbstractRestaurantManager;
import fr.mch.mdo.restaurant.dao.beans.DinnerTable;
import fr.mch.mdo.restaurant.dao.beans.OrderLine;
import fr.mch.mdo.restaurant.dao.beans.Product;
import fr.mch.mdo.restaurant.dao.beans.ProductSpecialCode;
import fr.mch.mdo.restaurant.dao.beans.UserAuthentication;

import fr.mch.mdo.restaurant.dao.beans.DinnerTableProperties;
import fr.mch.mdo.restaurant.dao.table.orders.ITablesOrdersDao;
import fr.mch.mdo.restaurant.dao.tables.orders.hibernate.DefaultTablesOrdersDao;
import fr.mch.mdo.restaurant.dto.beans.DinnerTableDtoBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.OrderLineDtoBean;
import fr.mch.mdo.restaurant.dto.beans.ProductDtoBean;
import fr.mch.mdo.restaurant.ioc.MdoBeanFactory;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;


public class DefaultTablesOrdersManager extends AbstractRestaurantManager implements ITablesOrdersManager
{
    public static enum ProductSpecialCodeCode 
    {
	NOTHING(""), 
	OFFERED_PRODUCT("#"), 
	REDUCED_ORDER("-"), 
	USER_ORDER("/") 
	{
	    public void updateOrderLineFromProduct(OrderLineDtoBean orderLine)
	    {
		orderLine.setUnitPrice(orderLine.getProduct().getPrice());
		if (orderLine.getLabel() == null)
		{
		    orderLine.setLabel(orderLine.getProduct().getLabel());
		}
		orderLine.setCode(this.getCode());
		orderLine.setProcessNextField(Boolean.TRUE);
	    }
	};
	private String code = "";
	ProductSpecialCodeCode(String code)
	{
	    this.code = code;
	}
	public static ProductSpecialCodeCode getProductSpecialCodeCode(String code)
	{
	    ProductSpecialCodeCode result = null;
	    
	    for (ProductSpecialCodeCode productSpecialCodeCode : ProductSpecialCodeCode.values())
	    {
		if(productSpecialCodeCode.getCode().equals(code))
		{
		    return productSpecialCodeCode;
		}
	    }
	    return result;
	}
	public void updateOrderLineFromProduct(OrderLineDtoBean orderLine)
	{
	    orderLine.setUnitPrice(orderLine.getProduct().getPrice());
	    orderLine.setLabel(orderLine.getProduct().getLabel());
	}
	public String getCode()
	{
	    return this.code;
	}
    }
    
    private static ITablesOrdersManager instance = null;

    public static ITablesOrdersManager getInstance()
    {
	if (instance == null)
	{
	    synchronized (DefaultTablesOrdersManager.class)
	    {
		if (instance == null)
		{
		    instance = new DefaultTablesOrdersManager();
		    instance.setLogger(LoggerServiceImpl.getInstance().getLogger(DefaultTablesOrdersManager.class.getName()));

		    instance.setDao(DefaultTablesOrdersDao.getInstance());
		}
	    }
	}

	return instance;
    }

    @Override
    public Map<Long, String> lookupTablesNamesByPrefix(IMdoBean userContext, String prefixTableNumber) throws Exception
    {
	Map<Long, String> result = new HashMap<Long, String>();
	MdoUserContext userContextX = (MdoUserContext)userContext;
	ITablesOrdersDao daoX = (ITablesOrdersDao)dao;
	List<DinnerTable> dinnerTableNames = daoX.findAllDinnerTablesByUser(userContextX.getUser().getId(), prefixTableNumber);
	for (Iterator<DinnerTable> iterator = dinnerTableNames.iterator(); iterator.hasNext();)
	{
	    DinnerTable dinnerTable = iterator.next();
	    result.put(dinnerTable.getId(), dinnerTable.getNumber());
	}

	return result;
    }

    @Override
    public DinnerTableDtoBean getTableByTableNumber(IMdoBean userContext, String tableNumber) throws Exception
    {
	DinnerTableDtoBean result = new DinnerTableDtoBean();
	MdoUserContext userContextX = (MdoUserContext)userContext;
	ITablesOrdersDao daoX = (ITablesOrdersDao)dao;
	//Get dinner table
	DinnerTable dinnerTable = daoX.findDinnerTableByTableNumber(userContextX.getUser().getId(), tableNumber);
	
	if(dinnerTable!=null)
	{
	    //Dinner Table
	    result.setId(dinnerTable.getId());
	    result.setTableNumber(dinnerTable.getNumber());
	    result.setCustomersNumber(dinnerTable.getCustomersNumber());
	    result.setTakeaway(dinnerTable.isTakeaway());
	    result.setRegistrationDate(dinnerTable.getRegistrationDate());
	    result.setPrintingDate(dinnerTable.getPrintingDate());
	    result.setCashingDate(dinnerTable.getCashingDate());
	    result.setQuantitiesSum(dinnerTable.getQuantitiesSum());
	    result.setReductionRatio(dinnerTable.getReductionRatio());
	    result.setReduction(dinnerTable.getReduction());
	    result.setAmountsSum(dinnerTable.getAmountsSum());
	    result.setAmountPay(dinnerTable.getAmountPay());
	    result.setReductionRatioManuallyChanged(dinnerTable.isReductionRatioManuallyChanged());
	    
	    //Get list of order line
	    List<OrderLineDtoBean> orders = new ArrayList<OrderLineDtoBean>();
	    if(dinnerTable.getOrders()!=null)
	    {
		for (Iterator<OrderLine> iterator = dinnerTable.getOrders().iterator(); iterator.hasNext();)
		{
		    OrderLine orderLine = iterator.next();
		    OrderLineDtoBean orderLineDtoBean = new OrderLineDtoBean();
		    orderLineDtoBean.setId(orderLine.getId());
		    orderLineDtoBean.setQuantity(orderLine.getQuantity());
		    orderLineDtoBean.setCode(orderLine.getCode());
		    orderLineDtoBean.setLabel(orderLine.getLabel());
		    orderLineDtoBean.setUnitPrice(orderLine.getUnitPrice());
		    orderLineDtoBean.setAmount(orderLine.getAmount());
		    orders.add(orderLineDtoBean);
		}
	    }
	    result.setOrders(orders);
	    //dinnerTable.
	}

	return result;
    }
    
    @Override
    public Integer getCustomersNumberByTableNumber(IMdoBean userContext, String tableNumber) throws Exception
    {
	// TODO Auto-generated method stub
	return null;
    }

    /**
     * @return
     */
    private BigDecimal getAmountPay(DinnerTable dinnerTable)
    {
	return dinnerTable.getAmountsSum().subtract(dinnerTable.getReduction()).subtract(dinnerTable.getCreditsAmount());
    }

    @Override
    public Map<Long, String> lookupProductsCodesByPrefix(IMdoBean userContext, String prefixProductCode) throws Exception
    {
	Map<Long, String> result = new HashMap<Long, String>();
	MdoUserContext userContextX = (MdoUserContext)userContext;
	ITablesOrdersDao daoX = (ITablesOrdersDao)dao;

    	Long restaurantId = userContextX.getUserAuthentication().getRestaurant().getId();
    	if(prefixProductCode!=null)
    	{
    	    ProductSpecialCodeCode productSpecialCode = getProductSpecialCode(prefixProductCode);
    	    //String productSpecialCodeCode = "";
	    if (productSpecialCode != null)
	    {
		prefixProductCode = prefixProductCode.substring(1);
		//productSpecialCodeCode = productSpecialCode.getCode();
	    }
	    if (prefixProductCode.length() > 0)
	    {
		List<Product> products = daoX.findAllProductsByRestaurant(restaurantId, prefixProductCode);
		for (Iterator<Product> iterator = products.iterator(); iterator.hasNext();)
		{
		    Product product = iterator.next();
		    result.put(product.getId(), productSpecialCode.getCode() + product.getCode());
		}
	    }
    	}
	return result;
    }

    /**
     * Check that the first character of the code is in the table t_product_special_code.
     * If this is the case then return the ProductSpecialCode object
     * @param restaurantId
     * @param code
     * @return
     */
    private ProductSpecialCodeCode getProductSpecialCode(String code)
    {
	ProductSpecialCodeCode result = null;
    	if (code.length() > 0)
	{
    	    //ITablesOrdersDao daoX = (ITablesOrdersDao)dao;
	    // Check if the First character belongs to a product special code
	    try
	    {
		//result = daoX.findProductSpecialCodesByRestaurant(restaurantId, code.substring(0, 1));
		result = ProductSpecialCodeCode.getProductSpecialCodeCode(code.substring(0, 1));
		
	    }
	    catch (Exception e)
	    {
		logger.error("message.error.business.find.productSpecialCode", e);
	    }
	}
	return result;
    }

    //@Override
    public void processOrderLine(IMdoBean userContext, OrderLineDtoBean orderLine) throws Exception
    {
	MdoUserContext userContextX = (MdoUserContext)userContext;
	ITablesOrdersDao daoX = (ITablesOrdersDao)dao;

    	Long restaurantId = userContextX.getUserAuthentication().getRestaurant().getId();
	String codeX = orderLine.getCode();
    	if(codeX!=null)
    	{
    	    //Try to know if the code begins by the a Product Special Code
    	    ProductSpecialCodeCode productSpecialCode = getProductSpecialCode(codeX);
	    if (productSpecialCode != null)
	    {
		//Remove the Product Special Code
		codeX = codeX.substring(1);
	    }
	    else
	    {
		productSpecialCode = ProductSpecialCodeCode.NOTHING;
	    }
	    if (codeX.length() > 0)
	    {
		//Try to get the Product
		Product product = daoX.findProductByRestaurant(restaurantId, codeX);
		if(product!=null)
		{
		    ProductDtoBean productDtoBean = new ProductDtoBean();
		    productDtoBean.setId(product.getId());
		    productDtoBean.setCode(product.getCode());
		    productDtoBean.setColorRGB(product.getColorRGB());
		    productDtoBean.setPrice(product.getPrice());
		    Map<Long, String> labels = product.getLabels();
		    productDtoBean.setLabel(labels.get(userContextX.getCurrentLocale().getId()));
		    orderLine.setProduct(productDtoBean);
		}
	    }
	    //Update unit price and label...
	    productSpecialCode.updateOrderLineFromProduct(orderLine);
	    if (orderLine.getUnitPrice() != null)
	    {
		// Amount == quantity * unit price
		orderLine.setAmount(orderLine.getUnitPrice().multiply(orderLine.getQuantity()));

		DinnerTable tableToBeSaved = new DinnerTable();
		DinnerTableDtoBean table = (DinnerTableDtoBean) userContextX.getCurrentTable();
		if (table == null)
		{
		    // Try to find in database
		    throw new Exception("table.not.init");
		}
		if (table.getId() == null)
		{
		    tableToBeSaved.setNumber(table.getTableNumber());
		    tableToBeSaved.setCustomersNumber(table.getCustomersNumber());
		    tableToBeSaved.setRegistrationDate(table.getRegistrationDate());
		    tableToBeSaved.setUser(userContextX.getUserAuthentication());
		    tableToBeSaved.setTakeaway(table.getTakeaway());
		    // Registration date
		    // tableToBeSaved.setRegistrationDate(date);
		    // Create new table
		    daoX.insert(tableToBeSaved);
		    table.setId(tableToBeSaved.getId());
		}
		else
		{
		    tableToBeSaved.setId(table.getId());
		    // Get table from database
		    daoX.load(tableToBeSaved);
		}
		// Save the order line
		OrderLine orderLineToBeSaved = new OrderLine();
		orderLineToBeSaved.setId(orderLine.getId());
		orderLineToBeSaved.setQuantity(orderLine.getQuantity());
		orderLineToBeSaved.setAmount(orderLine.getAmount());
		orderLineToBeSaved.setUnitPrice(orderLine.getUnitPrice());
		orderLineToBeSaved.setCode(orderLine.getCode());
		orderLineToBeSaved.setLabel(orderLine.getLabel());
		orderLineToBeSaved.setDinnerTable(tableToBeSaved);
		if (orderLine.getId() == null)
		{
		    daoX.insert(orderLineToBeSaved);
		}
		else
		{
		    // Load the order line from data base
		    OrderLine oldOrderLine = new OrderLine();
		    oldOrderLine.setId(orderLineToBeSaved.getId());
		    daoX.load(oldOrderLine);
		    // Subtract because this will be added later
		    tableToBeSaved.setAmountsSum(tableToBeSaved.getAmountsSum().subtract(oldOrderLine.getAmount()));
		    tableToBeSaved.setQuantitiesSum(tableToBeSaved.getQuantitiesSum().subtract(oldOrderLine.getQuantity()));
		    // Save new values
		    daoX.update(orderLineToBeSaved);
		}
		// Store the id get from database
		orderLine.setId(orderLineToBeSaved.getId());
		// Update the table amount/quantity after order line saved...
		tableToBeSaved.setQuantitiesSum(orderLine.getQuantity().add(tableToBeSaved.getQuantitiesSum()));
		tableToBeSaved.setAmountsSum(orderLine.getAmount().add(tableToBeSaved.getAmountsSum()));
		tableToBeSaved.setAmountPay(tableToBeSaved.getAmountsSum().multiply(new BigDecimal(1).subtract(tableToBeSaved.getReductionRatio().divide(new BigDecimal(100)))));
		daoX.update(tableToBeSaved);
	    }
    	}
    }
    
    public static void main(String[] args)
    {
	ProductSpecialCodeCode productSpecialCodeCodeX = ProductSpecialCodeCode.getProductSpecialCodeCode("#");
	System.out.println(productSpecialCodeCodeX.name());
	System.out.println(productSpecialCodeCodeX.ordinal());
    }
}
