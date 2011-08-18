package fr.mch.mdo.restaurant.web.struts.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashSet;
import java.util.Set;

import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.dto.beans.IAdministrationManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.ProductCategoryDto;
import fr.mch.mdo.restaurant.dto.beans.ProductDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.ioc.spring.WebAdministrationBeanFactory;
import fr.mch.mdo.restaurant.services.business.managers.products.IProductsManager;
import fr.mch.mdo.restaurant.services.business.managers.restaurants.IRestaurantsManager;
import fr.mch.mdo.restaurant.ui.forms.IMdoAdministrationForm;
import fr.mch.mdo.restaurant.ui.forms.ProductsManagerForm;

public class ProductsManagerWebAction extends AdministrationManagerLabelsAction 
{
	/**
     * Default Serial Version UID
     */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Restaurants Manager
	 */
	private IRestaurantsManager restaurantsManager;
	
	public ProductsManagerWebAction() {
		super(WebAdministrationBeanFactory.getInstance().getLogger(ProductsManagerWebAction.class.getName()), new ProductsManagerForm());
		administrationManager = WebAdministrationBeanFactory.getInstance().getProductsManager();
		restaurantsManager = WebAdministrationBeanFactory.getInstance().getRestaurantsManager();
	}

	/**
	 * @return the restaurantsManager
	 */
	public IRestaurantsManager getRestaurantsManager() {
		return restaurantsManager;
	}

	/**
	 * @param restaurantsManager the restaurantsManager to set
	 */
	public void setRestaurantsManager(IRestaurantsManager restaurantsManager) {
		this.restaurantsManager = restaurantsManager;
	}

	public String listProducts() throws Exception {
		String result = Constants.ACTION_RESULT_AFTER_SUCCESS_FORM_LIST;
		ProductDto dtoBean = ((ProductDto) super.getForm().getDtoBean());
		if (dtoBean != null) {
			MdoUserContext userContext = (MdoUserContext) super.getForm().getUserContext();
			if (userContext != null) {
				IAdministrationManagerViewBean viewBean = ((IMdoAdministrationForm) super.getForm()).getViewBean();
				if (viewBean != null && dtoBean.getRestaurant() != null) {
					IProductsManager manager = (IProductsManager) administrationManager;
					viewBean.setList(manager.getList(dtoBean.getRestaurant().getId(), userContext));
				}
			}
		}
		return result;
	}
	
	public String removeCategory() throws Exception {
		String categoryIdToRemove = super.getRequest().getParameter("method:removeCategory");
		this.processSave(categoryIdToRemove);
		this.form();
		return Constants.ACTION_RESULT_AFTER_CUD_CATEGORY;
	}

	public String addCategory() throws Exception {
		this.processSave();
		this.form();
		return Constants.ACTION_RESULT_AFTER_CUD_CATEGORY;
	}
	
	@Override
	public String save() {
		this.processSave(new String[] {null});
		
		// Reload the restaurant bean
		ProductDto dtoBean = ((ProductDto) super.getForm().getDtoBean());
		RestaurantDto restaurant = dtoBean.getRestaurant();
		try {
			restaurant = (RestaurantDto) restaurantsManager.findByPrimaryKey(dtoBean.getRestaurant().getId(), (MdoUserContext) super.getForm().getUserContext(), false);
		} catch (MdoException e) {
			super.addActionError(super.getText("error.action.technical", new String[] {this.getClass().getName(), "save"}));
		}
		dtoBean.setRestaurant(restaurant);
		// Return to the products list
		return Constants.ACTION_RESULT_AFTER_CUD_LIST_PRODUCTS;

	}
	
	@Override
	public String delete() {
		super.delete();
		// Return to the products list
		return Constants.ACTION_RESULT_AFTER_CUD_LIST_PRODUCTS;
	}
	
	private void processSave(String... categoryIdToRemove) {
		
		if (categoryIdToRemove != null && categoryIdToRemove.length == 1) {
			removeCategoryBeforeSaving(categoryIdToRemove[0]);
		}
		
		super.save();
	}
	
	private void removeCategoryBeforeSaving(String categoryIdToRemove) {
		ProductDto product = ((ProductDto) super.getForm().getDtoBean());
		Set<ProductCategoryDto> productCategories = new HashSet<ProductCategoryDto>();
		// Try to remove from product.getCategories() list 2 elements of ProductCategoryDto
		// One of id null and one of id equals to categoryIdToRemove
		for (ProductCategoryDto productCategoryDto : product.getCategories()) {
			if (productCategoryDto.getId() != null && !productCategoryDto.getId().toString().equals(categoryIdToRemove)) {
				productCategories.add(productCategoryDto);
			}
		}
		product.setCategories(productCategories);
	}

	public String importData() throws Exception {
		ProductsManagerForm form = (ProductsManagerForm) super.getForm();
		if (form.getImportedFileFileName() != null) {
			ProductDto product = (ProductDto) form.getDtoBean();
			if (product != null && product.getRestaurant() != null && form.getImportedFileFileName().contains(product.getRestaurant().getReference())) {
				try {
					((IProductsManager) administrationManager).importData(form.getImportedFileFileName(), form.getImportedFile(), (MdoUserContext) super.getForm().getUserContext());
					super.addActionMessage(super.getText("products.manager.success.import.products"));
				} catch (Exception e) {
					super.addActionError(super.getText("error.action.technical", new String[] {this.getClass().getName(), "importData"}));
				}
			} else {
				super.addActionError(super.getText("products.manager.error.import.restautant.reference.not.match"));
			}
		} else {
			super.addActionError(super.getText("products.manager.error.import.no.file"));
		}
		// Return to the products list
		listProducts();
		
		return Constants.ACTION_RESULT_AFTER_CUD_LIST_PRODUCTS;
	}
	
	public String exportData() {
		ProductsManagerForm thisForm = (ProductsManagerForm) super.getForm();
		ProductDto dtoBean = ((ProductDto) thisForm.getDtoBean());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		String reference = null;
		if (dtoBean.getRestaurant() !=  null) {
			reference = dtoBean.getRestaurant().getReference();
		}

		String exportFileName = "exportFileName";
		String[] headers = {super.getText("products.manager.code"), super.getText("products.manager.label"), super.getText("products.manager.price"), 
				super.getText("products.manager.vat"), super.getText("products.manager.color")};
		try {
			exportFileName = ((IProductsManager) administrationManager).exportData(out, reference, headers, (MdoUserContext) thisForm.getUserContext());
		} catch (Exception e) {
			super.addActionError(super.getText("error.action.technical", new String[] {this.getClass().getName(), "exportData"}));
		}
		ByteArrayInputStream bis = new ByteArrayInputStream(out.toByteArray());
		thisForm.setExportInputStream(bis);
		thisForm.setExportFileName(exportFileName);
		// Return to the products list
		return Constants.ACTION_RESULT_AFTER_EXPORT_PRODUCTS_LIST;
	}
}
