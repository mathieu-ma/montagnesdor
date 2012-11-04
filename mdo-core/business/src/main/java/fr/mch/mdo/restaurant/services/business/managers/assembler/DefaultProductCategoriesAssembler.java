package fr.mch.mdo.restaurant.services.business.managers.assembler;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.logs.ILoggerBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.Category;
import fr.mch.mdo.restaurant.dao.beans.Product;
import fr.mch.mdo.restaurant.dao.beans.ProductCategory;
import fr.mch.mdo.restaurant.dto.beans.CategoryDto;
import fr.mch.mdo.restaurant.dto.beans.ProductCategoryDto;
import fr.mch.mdo.restaurant.dto.beans.ProductDto;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultProductCategoriesAssembler extends AbstractAssembler implements IManagerAssembler, ILoggerBean 
{
	private ILogger logger;

	private IManagerAssembler categoriesAssembler;

	private static class LazyHolder {
		private static IManagerAssembler instance = new DefaultProductCategoriesAssembler(
				LoggerServiceImpl.getInstance().getLogger(DefaultProductCategoriesAssembler.class.getName()));
	}

	private DefaultProductCategoriesAssembler(ILogger logger) {
		this.setLogger(logger);
		this.categoriesAssembler = DefaultCategoriesAssembler.getInstance();
	}

	public static IManagerAssembler getInstance() {
		return LazyHolder.instance;
	}

	public DefaultProductCategoriesAssembler() {
	}

	public IManagerAssembler getCategoriesAssembler() {
		return categoriesAssembler;
	}

	public void setCategoriesAssembler(IManagerAssembler categoriesAssembler) {
		this.categoriesAssembler = categoriesAssembler;
	}

	@Override
	public IMdoDtoBean marshal(IMdoDaoBean daoBean) {
		ProductCategoryDto dto = null;
		if (daoBean != null) {
			ProductCategory bean = (ProductCategory) daoBean;
			dto = new ProductCategoryDto();
			dto.setId(bean.getId());
			dto.setQuantity(bean.getQuantity());
			CategoryDto category = (CategoryDto) categoriesAssembler.marshal(bean.getCategory());
			dto.setCategory(category);
			ProductDto product = null;
			dto.setProduct(product);
		}
		return dto;
	}

	@Override
	public IMdoDaoBean unmarshal(IMdoDtoBean dtoBean, IMdoDaoBean... parents) {
		if (dtoBean == null) {
			return null;
		}
		ProductCategory bean = new ProductCategory();
		ProductCategoryDto dto = (ProductCategoryDto) dtoBean;
		bean.setId(dto.getId());
		bean.setQuantity(dto.getQuantity());
		if (parents != null && parents.length == 1) {
			bean.setProduct((Product) parents[0]);
		}
		Category category = (Category) categoriesAssembler.unmarshal(dto.getCategory());
		bean.setCategory(category);
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
