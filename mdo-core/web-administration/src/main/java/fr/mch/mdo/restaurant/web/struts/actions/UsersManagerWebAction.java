package fr.mch.mdo.restaurant.web.struts.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.UserDto;
import fr.mch.mdo.restaurant.dto.beans.UserRestaurantDto;
import fr.mch.mdo.restaurant.dto.beans.UsersManagerViewBean;
import fr.mch.mdo.restaurant.ioc.spring.WebAdministractionBeanFactory;
import fr.mch.mdo.restaurant.ui.forms.UsersManagerForm;

public class UsersManagerWebAction extends AdministrationManagerAction 
{
	/**
	 * Default Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	public UsersManagerWebAction() {
		super(WebAdministractionBeanFactory.getInstance().getLogger(UsersManagerWebAction.class.getName()), new UsersManagerForm());
		administrationManager = WebAdministractionBeanFactory.getInstance().getUsersManager();
	}
	
	@Override
	public String form() throws Exception {
		String result = super.form();

		UserDto dtoBean = (UserDto) super.getForm().getDtoBean();
		if (dtoBean != null) {
			if (dtoBean.getBirthdate() == null) {
				dtoBean.setBirthdate(new Date());
			}
		}
		
		processAvailableRestaurants();

		return result;
	}

	@Override
	public void validate() {
		super.validate();
		
		if (super.hasErrors()) {
			// In case of validation failed
			// Remove the Restaurant with null id
			removeRestaurantBeforeSaving(null);
			processAvailableRestaurants();
		}
	}

	@Override
	public String save() {
		
		this.processSave(new String[] {null});
		// Return to the list
		return Constants.ACTION_RESULT_AFTER_CUD;

	}

	/**
	 * Process restaurants list on element which is not already stored in database.
	 * Must be called after super.prepare because we have to use viewBean. 
	 */
	private void processAvailableRestaurants() {
		UserDto dtoBean = (UserDto) super.getForm().getDtoBean();
		UsersManagerViewBean viewBean = (UsersManagerViewBean) super.getForm().getViewBean();

		List<IMdoDtoBean> listAll = viewBean.getRestaurants();
		List<IMdoDtoBean> availableRestaurants = new ArrayList<IMdoDtoBean>(listAll);
		if (dtoBean != null && dtoBean.getRestaurants() != null) {
			for (IMdoDtoBean restaurant : listAll) {
				for (UserRestaurantDto exlcudedBean : dtoBean.getRestaurants()) {
					if (restaurant.getId() != null && exlcudedBean.getRestaurant() != null && restaurant.getId().equals(exlcudedBean.getRestaurant().getId())) {
						availableRestaurants.remove(restaurant);
					}
				}
			}
		}

		viewBean.setRestaurants(availableRestaurants);
	}

	private void processSave(String... restaurantIdToRemove) {
		if (restaurantIdToRemove != null && restaurantIdToRemove.length == 1) {
			removeRestaurantBeforeSaving(restaurantIdToRemove[0]);
		}
		
		super.save();
	}
	
	private void removeRestaurantBeforeSaving(String restaurantIdToRemove) {
		UsersManagerForm form = (UsersManagerForm) super.getForm();
		UserDto user = (UserDto) form.getDtoBean();
		Set<UserRestaurantDto> restaurants = new HashSet<UserRestaurantDto>();
		// Try to remove from user.getRestaurants() list 2 elements of UserRestaurantDto
		// One of id null and one of id equals to restaurantIdToRemove
		for (UserRestaurantDto userRestaurant : user.getRestaurants()) {
			if (userRestaurant.getId() != null && !userRestaurant.getId().toString().equals(restaurantIdToRemove)) {
				restaurants.add(userRestaurant);
			}
		}
		user.setRestaurants(restaurants);
	}
	
	public String removeRestaurant() throws Exception {
		String restaurantIdToRemove = super.getRequest().getParameter("method:removeRestaurant");
		this.processSave(restaurantIdToRemove);
		this.form();
		return Constants.ACTION_RESULT_AFTER_CUD_RESTAURANT;
	}

	public String addRestaurant() throws Exception {
		this.processSave();
		this.form();
		return Constants.ACTION_RESULT_AFTER_CUD_RESTAURANT;
	}
}
