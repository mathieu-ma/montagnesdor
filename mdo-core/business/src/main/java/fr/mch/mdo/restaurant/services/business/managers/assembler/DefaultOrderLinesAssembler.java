package fr.mch.mdo.restaurant.services.business.managers.assembler;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.logs.ILoggerBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.Credit;
import fr.mch.mdo.restaurant.dao.beans.DinnerTable;
import fr.mch.mdo.restaurant.dao.beans.OrderLine;
import fr.mch.mdo.restaurant.dao.beans.Product;
import fr.mch.mdo.restaurant.dao.beans.ProductPart;
import fr.mch.mdo.restaurant.dao.beans.ProductSpecialCode;
import fr.mch.mdo.restaurant.dto.beans.CreditDto;
import fr.mch.mdo.restaurant.dto.beans.DinnerTableDto;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.OrderLineDto;
import fr.mch.mdo.restaurant.dto.beans.ProductDto;
import fr.mch.mdo.restaurant.dto.beans.ProductPartDto;
import fr.mch.mdo.restaurant.dto.beans.ProductSpecialCodeDto;
import fr.mch.mdo.restaurant.services.business.managers.tables.ManagedProductSpecialCode;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultOrderLinesAssembler extends AbstractAssembler implements IManagerAssembler, ILoggerBean
{
	private ILogger logger;

	private IManagerAssembler productsAssembler; 
	private IManagerAssembler productPartsAssembler; 
	private IManagerAssembler productSpecialCodesAssembler;
	private IManagerAssembler creditsAssembler;
	
	private static class LazyHolder {
		private static IManagerAssembler instance = new DefaultOrderLinesAssembler(LoggerServiceImpl.getInstance().getLogger(DefaultOrderLinesAssembler.class.getName()));
	}

	private DefaultOrderLinesAssembler(ILogger logger) {
		this.setLogger(logger);
		this.productsAssembler = DefaultProductsAssembler.getInstance();
		this.productPartsAssembler = DefaultProductPartsAssembler.getInstance();
		this.productSpecialCodesAssembler = DefaultProductSpecialCodesAssembler.getInstance();
		this.creditsAssembler = DefaultCreditsAssembler.getInstance();
	}

	public static IManagerAssembler getInstance() {
		return LazyHolder.instance;
	}

	public DefaultOrderLinesAssembler() {
	}

	/**
	 * @return the productsAssembler
	 */
	public IManagerAssembler getProductsAssembler() {
		return productsAssembler;
	}

	/**
	 * @param productsAssembler the productsAssembler to set
	 */
	public void setProductsAssembler(IManagerAssembler productsAssembler) {
		this.productsAssembler = productsAssembler;
	}

	/**
	 * @return the productPartsAssembler
	 */
	public IManagerAssembler getProductPartsAssembler() {
		return productPartsAssembler;
	}

	/**
	 * @param productPartsAssembler the productPartsAssembler to set
	 */
	public void setProductPartsAssembler(IManagerAssembler productPartsAssembler) {
		this.productPartsAssembler = productPartsAssembler;
	}

	/**
	 * @return the productSpecialCodesAssembler
	 */
	public IManagerAssembler getProductSpecialCodesAssembler() {
		return productSpecialCodesAssembler;
	}

	/**
	 * @param productSpecialCodesAssembler the productSpecialCodesAssembler to set
	 */
	public void setProductSpecialCodesAssembler(IManagerAssembler productSpecialCodesAssembler) {
		this.productSpecialCodesAssembler = productSpecialCodesAssembler;
	}

	/**
	 * @return the creditsAssembler
	 */
	public IManagerAssembler getCreditsAssembler() {
		return creditsAssembler;
	}

	/**
	 * @param creditsAssembler the creditsAssembler to set
	 */
	public void setCreditsAssembler(IManagerAssembler creditsAssembler) {
		this.creditsAssembler = creditsAssembler;
	}

	@Override
	public IMdoDtoBean marshal(IMdoDaoBean daoBean, MdoUserContext userContext) {
		OrderLineDto dto = null;
		if (daoBean != null) {
			OrderLine bean = (OrderLine) daoBean;
			dto = new OrderLineDto();
			dto.setId(bean.getId());
			dto.setAmount(bean.getAmount());
			String code = "";
			if (bean.getProduct() != null) {
				code = bean.getProduct().getCode();
			}
			// Currently the dataCode is used for merging 2 rows
			String dataCode = code;
			if (bean.getProductSpecialCode() != null && bean.getProductSpecialCode().getShortCode() != null) {
				code = bean.getProductSpecialCode().getShortCode() + code;
				dataCode = code;
				if (ManagedProductSpecialCode.USER_ORDER.name().equals(bean.getProductSpecialCode().getCode().getName())) {
					dataCode = null;
				}
			}
			dto.setCode(code);
			dto.setDataCode(dataCode);
			CreditDto credit = (CreditDto) creditsAssembler.marshal(bean.getCredit(), userContext);
			dto.setCredit(credit);
			if (bean.getDinnerTable() != null) {
				DinnerTableDto dinnerTable = new DinnerTableDto();
				dinnerTable.setId(bean.getDinnerTable().getId());
				dto.setDinnerTable(dinnerTable);
			}
			dto.setLabel(bean.getLabel());
			ProductDto product = (ProductDto) productsAssembler.marshal(bean.getProduct(), userContext);
			dto.setProduct(product);
			ProductPartDto productPart = (ProductPartDto) productPartsAssembler.marshal(bean.getProductPart(), userContext);
			dto.setProductPart(productPart);
			ProductSpecialCodeDto productSpecialCode = (ProductSpecialCodeDto) productSpecialCodesAssembler.marshal(bean.getProductSpecialCode(), userContext); 
			dto.setProductSpecialCode(productSpecialCode);
			dto.setQuantity(bean.getQuantity());
			dto.setUnitPrice(bean.getUnitPrice());
		}
		return dto;
	}

	@Override
	public IMdoDaoBean unmarshal(IMdoDtoBean dtoBean, IMdoDaoBean... parents) {
		if (dtoBean == null) {
			return null;
		}
		OrderLine bean = new OrderLine();
		OrderLineDto dto = (OrderLineDto) dtoBean;

		bean.setId(dto.getId());
		bean.setAmount(dto.getAmount());
		//bean.setCode(code);
		Credit credit = (Credit) creditsAssembler.unmarshal(dto.getCredit());
		bean.setCredit(credit);
		DinnerTable dinnerTable = null;
		if (parents != null && parents.length == 1) {
			dinnerTable = (DinnerTable) parents[0];
		} 
		if (dinnerTable == null && dto.getDinnerTable() != null) {
			dto.getDinnerTable().setBills(null);
			dinnerTable = new DinnerTable();
			dinnerTable.setId(dto.getDinnerTable().getId());
		}
		bean.setDinnerTable(dinnerTable);
		bean.setLabel(dto.getLabel());
		Product product = (Product) productsAssembler.unmarshal(dto.getProduct());
		bean.setProduct(product);
		ProductPart productPart = (ProductPart) productPartsAssembler.unmarshal(dto.getProductPart());
		bean.setProductPart(productPart);
		ProductSpecialCode productSpecialCode = (ProductSpecialCode) productSpecialCodesAssembler.unmarshal(dto.getProductSpecialCode()); 
		bean.setProductSpecialCode(productSpecialCode);
		bean.setQuantity(dto.getQuantity());
		bean.setUnitPrice(dto.getUnitPrice());
		
		return bean;
	}

	@Override
	public ILogger getLogger() {
		return logger;
	}

	@Override
	public void setLogger(ILogger logger) {
		this.logger = logger;
	}
}
