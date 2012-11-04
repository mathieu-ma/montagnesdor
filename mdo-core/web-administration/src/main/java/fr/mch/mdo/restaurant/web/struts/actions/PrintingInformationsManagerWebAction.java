package fr.mch.mdo.restaurant.web.struts.actions;

import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.dto.beans.IAdministrationManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.PrintingInformationDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;
import fr.mch.mdo.restaurant.exception.MdoBusinessException;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.ioc.spring.WebAdministrationBeanFactory;
import fr.mch.mdo.restaurant.services.business.managers.printings.IPrintingInformationsManager;
import fr.mch.mdo.restaurant.services.business.managers.restaurants.IRestaurantsManager;
import fr.mch.mdo.restaurant.ui.forms.IMdoAdministrationForm;
import fr.mch.mdo.restaurant.ui.forms.PrintingInformationsManagerForm;

public class PrintingInformationsManagerWebAction extends AdministrationManagerLabelsAction 
{
	/**
	 * Default Serial Version UID
     */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Restaurants Manager
	 */
	private IRestaurantsManager restaurantsManager;


	public PrintingInformationsManagerWebAction() {
		super(WebAdministrationBeanFactory.getInstance().getLogger(PrintingInformationsManagerWebAction.class.getName()), new PrintingInformationsManagerForm());
		administrationManager = WebAdministrationBeanFactory.getInstance().getPrintingInformationsManager();
		setRestaurantsManager(WebAdministrationBeanFactory.getInstance().getRestaurantsManager());
	}

	/**
	 * @param restaurantsManager the restaurantsManager to set
	 */
	public void setRestaurantsManager(IRestaurantsManager restaurantsManager) {
		this.restaurantsManager = restaurantsManager;
	}

	/**
	 * @return the restaurantsManager
	 */
	public IRestaurantsManager getRestaurantsManager() {
		return restaurantsManager;
	}

	public String listPrintingInformations() throws Exception {
		String result = Constants.ACTION_RESULT_AFTER_SUCCESS_FORM_LIST;
		PrintingInformationDto dtoBean = ((PrintingInformationDto) super.getForm().getDtoBean());
		if (dtoBean != null) {
			MdoUserContext userContext = (MdoUserContext) super.getForm().getUserContext();
			if (userContext != null) {
				IAdministrationManagerViewBean viewBean = ((IMdoAdministrationForm) super.getForm()).getViewBean();
				if (viewBean != null && dtoBean.getRestaurant() != null) {
					IPrintingInformationsManager manager = (IPrintingInformationsManager) administrationManager;
					viewBean.setList(manager.getList(dtoBean.getRestaurant().getId()));
				}
			}
		}
		return result;
	}
	
	@Override
	public String save() throws MdoBusinessException {
		super.save();
		// Reload the restaurant bean
		PrintingInformationDto dtoBean = ((PrintingInformationDto) super.getForm().getDtoBean());
		RestaurantDto restaurant = dtoBean.getRestaurant();
		try {
			restaurant = (RestaurantDto) restaurantsManager.findByPrimaryKey(dtoBean.getRestaurant().getId(), false);
		} catch (MdoException e) {
			super.addActionError(super.getText("error.action.technical", new String[] {this.getClass().getName(), "save"}));
		}
		dtoBean.setRestaurant(restaurant);
		// Return to the products list
		return Constants.ACTION_RESULT_AFTER_CUD_LIST_PRINTING_INFORMATIONS;
	}
	
}
