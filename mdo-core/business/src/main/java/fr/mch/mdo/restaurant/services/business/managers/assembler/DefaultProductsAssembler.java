package fr.mch.mdo.restaurant.services.business.managers.assembler;

import java.util.HashSet;
import java.util.Set;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.logs.ILoggerBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.Product;
import fr.mch.mdo.restaurant.dao.beans.ProductCategory;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dao.beans.ValueAddedTax;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.ProductCategoryDto;
import fr.mch.mdo.restaurant.dto.beans.ProductDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;
import fr.mch.mdo.restaurant.dto.beans.ValueAddedTaxDto;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultProductsAssembler extends AbstractAssembler implements IManagerAssembler, ILoggerBean
{
	private ILogger logger;

	private IManagerAssembler productCategoriesAssembler;
	private IManagerAssembler restaurantsAssembler;
	private IManagerAssembler vatsAssembler;

	private static class LazyHolder {
		private static IManagerAssembler instance = new DefaultProductsAssembler(LoggerServiceImpl.getInstance().getLogger(DefaultProductsAssembler.class.getName()));
	}

	private DefaultProductsAssembler(ILogger logger) {
		this.setLogger(logger);
		this.productCategoriesAssembler = DefaultProductCategoriesAssembler.getInstance();
		this.restaurantsAssembler = DefaultRestaurantsAssembler.getInstance();
		this.vatsAssembler = DefaultValueAddedTaxesAssembler.getInstance();
	}

	public static IManagerAssembler getInstance() {
		return LazyHolder.instance;
	}

	public DefaultProductsAssembler() {
	}

	public IManagerAssembler getProductCategoriesAssembler() {
		return productCategoriesAssembler;
	}

	public void setProductCategoriesAssembler(IManagerAssembler productCategoriesAssembler) {
		this.productCategoriesAssembler = productCategoriesAssembler;
	}

	public IManagerAssembler getRestaurantsAssembler() {
		return restaurantsAssembler;
	}

	public void setRestaurantsAssembler(IManagerAssembler restaurantsAssembler) {
		this.restaurantsAssembler = restaurantsAssembler;
	}

	public IManagerAssembler getVatsAssembler() {
		return vatsAssembler;
	}

	public void setVatsAssembler(IManagerAssembler vatsAssembler) {
		this.vatsAssembler = vatsAssembler;
	}

	@SuppressWarnings("unchecked")
	@Override
	public IMdoDtoBean marshal(IMdoDaoBean daoBean, MdoUserContext userContext) {
		ProductDto dto = null;
		if (daoBean != null) {
			Product bean = (Product) daoBean;
			dto = new ProductDto();
			dto.setId(bean.getId());
			dto.setCode(bean.getCode());
			dto.setColorRGB(bean.getColorRGB());
			dto.setPrice(bean.getPrice());
			RestaurantDto restaurant = (RestaurantDto) restaurantsAssembler.marshal(bean.getRestaurant(), userContext);
			dto.setRestaurant(restaurant);
			ValueAddedTaxDto vat = (ValueAddedTaxDto) vatsAssembler.marshal(bean.getVat(), userContext); 
			dto.setVat(vat);
			@SuppressWarnings("rawtypes")
			Set<ProductCategoryDto> categories = (Set) productCategoriesAssembler.marshal(bean.getCategories(), userContext);
			dto.setCategories(categories);
			dto.setLabels(super.getLabels(bean.getLabels()));
		}
		return dto;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IMdoDaoBean unmarshal(IMdoDtoBean dtoBean, IMdoDaoBean... parents) {
		if (dtoBean == null) {
			return null;
		}
		Product bean = new Product();
		ProductDto dto = (ProductDto) dtoBean;
		bean.setId(dto.getId());
		bean.setCode(dto.getCode());
		bean.setColorRGB(dto.getColorRGB());
		bean.setPrice(dto.getPrice());
		bean.setLabels(dto.getLabels());
		Restaurant restaurant = (Restaurant) restaurantsAssembler.unmarshal(dto.getRestaurant());
		bean.setRestaurant(restaurant);
		ValueAddedTax vat = (ValueAddedTax) vatsAssembler.unmarshal(dto.getVat());
		bean.setVat(vat);
		Set<ProductCategory> categories = new HashSet<ProductCategory>();
		if (dto.getCategories() != null) {
			categories = (Set) productCategoriesAssembler.unmarshal(dto.getCategories(), bean);
		}
		bean.setCategories(categories);
		
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
