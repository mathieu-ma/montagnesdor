package fr.mch.mdo.restaurant.web.struts.actions;

import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.dto.beans.IAdministrationManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.ProductSpecialCodeDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;
import fr.mch.mdo.restaurant.exception.MdoBusinessException;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.ioc.spring.WebAdministrationBeanFactory;
import fr.mch.mdo.restaurant.services.business.managers.products.IProductSpecialCodesManager;
import fr.mch.mdo.restaurant.services.business.managers.restaurants.IRestaurantsManager;
import fr.mch.mdo.restaurant.ui.forms.IMdoAdministrationForm;
import fr.mch.mdo.restaurant.ui.forms.ProductSpecialCodesManagerForm;

public class ProductSpecialCodesManagerWebAction extends AdministrationManagerLabelsAction 
{
	/**
	 * Default Serial Version UID
     */
	private static final long serialVersionUID = 1L;

	/**
	 * Restaurants Manager
	 */
	private IRestaurantsManager restaurantsManager;

	public ProductSpecialCodesManagerWebAction() {
		super(WebAdministrationBeanFactory.getInstance().getLogger(ProductSpecialCodesManagerWebAction.class.getName()), new ProductSpecialCodesManagerForm());
		administrationManager = WebAdministrationBeanFactory.getInstance().getProductSpecialCodesManager();
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

	@Override
	public String save() throws MdoBusinessException {
		super.save();
		
		// Reload the restaurant bean
		ProductSpecialCodeDto dtoBean = ((ProductSpecialCodeDto) super.getForm().getDtoBean());
		RestaurantDto restaurant = dtoBean.getRestaurant();
		try {
			restaurant = (RestaurantDto) restaurantsManager.findByPrimaryKey(dtoBean.getRestaurant().getId(), false);
		} catch (MdoException e) {
			super.addActionError(super.getText("error.action.technical", new String[] {this.getClass().getName(), "save"}));
		}
		dtoBean.setRestaurant(restaurant);
		// Return to the products list
		return Constants.ACTION_RESULT_AFTER_CUD_LIST_PRODUCT_SPECIAL_CODES;

	}
	
	public String listProductSpecialCodes() throws Exception {
		String result = Constants.ACTION_RESULT_AFTER_SUCCESS_FORM_LIST;
		ProductSpecialCodeDto dtoBean = ((ProductSpecialCodeDto) super.getForm().getDtoBean());
		if (dtoBean != null) {
			MdoUserContext userContext = (MdoUserContext) super.getForm().getUserContext();
			if (userContext != null) {
				IAdministrationManagerViewBean viewBean = ((IMdoAdministrationForm) super.getForm()).getViewBean();
				if (viewBean != null && dtoBean.getRestaurant() != null) {
					IProductSpecialCodesManager manager = (IProductSpecialCodesManager) administrationManager;
					viewBean.setList(manager.getList(dtoBean.getRestaurant().getId(), userContext));
				}
			}
		}
		return result;
	}

}
