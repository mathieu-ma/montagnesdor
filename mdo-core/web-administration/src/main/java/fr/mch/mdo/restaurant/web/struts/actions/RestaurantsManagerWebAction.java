package fr.mch.mdo.restaurant.web.struts.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.opensymphony.xwork2.util.LocalizedTextUtil;

import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantPrefixTableDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantValueAddedTaxDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantsManagerViewBean;
import fr.mch.mdo.restaurant.ioc.spring.WebAdministractionBeanFactory;
import fr.mch.mdo.restaurant.ui.forms.RestaurantsManagerForm;

public class RestaurantsManagerWebAction extends AdministrationManagerAction
{
	/**
     * Default Serial Version UID
     */
	private static final long serialVersionUID = 1L;

	public RestaurantsManagerWebAction() {
		super(WebAdministractionBeanFactory.getInstance().getLogger(RestaurantsManagerWebAction.class.getName()), new RestaurantsManagerForm());
		administrationManager = WebAdministractionBeanFactory.getInstance().getRestaurantsManager();
		// Load the RestaurantsResources
		LocalizedTextUtil.addDefaultResourceBundle("fr/mch/mdo/restaurant/resources/i18n/RestaurantsResources");
	}

	@Override
	public String form() throws Exception {
		String result = super.form();

		RestaurantDto dtoBean = (RestaurantDto) super.getForm().getDtoBean();
		if (dtoBean != null) {
			if (dtoBean.getRegistrationDate() == null) {
				dtoBean.setRegistrationDate(new Date());
			}
		}

		processSelectedVatsFromDatabase();
		processAvailablePrefixTableNames();

		return result;
	}

	@Override
	public void validate() {
		super.validate();
		
		if (super.hasErrors()) {
			// In case of validation failed
			processSelectedVatsFromUser();
			// Remove the prefix with null id
			removePrefixBeforeSaving(null);
			processAvailablePrefixTableNames();
		}
	}
	
	/**
	 * Process prefixTableNames list on element which is not already stored in database.
	 * Must be called after super.prepare because we have to use viewBean. 
	 */
	private void processAvailablePrefixTableNames() {
		RestaurantDto dtoBean = (RestaurantDto) super.getForm().getDtoBean();
		RestaurantsManagerViewBean viewBean = (RestaurantsManagerViewBean) super.getForm().getViewBean();

		List<IMdoDtoBean> listAll = viewBean.getPrefixTableNames();
		List<IMdoDtoBean> availablePrefixTableNames = new ArrayList<IMdoDtoBean>(listAll);
		if (dtoBean != null && dtoBean.getPrefixTableNames() != null) {
			for (IMdoDtoBean tableAsEnum : listAll) {
				for (RestaurantPrefixTableDto exlcudedBean : dtoBean.getPrefixTableNames()) {
					if (tableAsEnum.getId() != null && exlcudedBean.getPrefix() != null && tableAsEnum.getId().equals(exlcudedBean.getPrefix().getId())) {
						availablePrefixTableNames.remove(tableAsEnum);
					}
				}
			}
		}

		viewBean.setPrefixTableNames(availablePrefixTableNames);
	}

	/**
	 * Process the selected vats list from user.
	 */
	private void processSelectedVatsFromUser() {
		RestaurantsManagerForm form = (RestaurantsManagerForm) super.getForm();

		// Ids selected from databse: key == vat id / value == restaurant vat id
		Map<Long, String> vats = form.getVats();
		// restaurant that contains set of RestaurantValueAddedTaxDto 
		RestaurantDto restaurant = (RestaurantDto) form.getDtoBean();
		// restaurantVats set will contains RestaurantValueAddedTaxDto objects selected by user
		Set<RestaurantValueAddedTaxDto> restaurantVats = new HashSet<RestaurantValueAddedTaxDto>();
		for (Long vatId : vats.keySet()) {
			Long longValue = null;
			String value = vats.get(vatId);
			if (value != null) {
				try {
					// Check that value is a long
					longValue = Long.valueOf(value);
				} catch(Exception e) {
					// Do nothing
				}
				for (RestaurantValueAddedTaxDto restaurantVat : restaurant.getVats()) {
					if(vatId.equals(restaurantVat.getVat().getId())) {
						restaurantVat.setId(longValue);
						// RestaurantValueAddedTaxDto object selected by user
						restaurantVats.add(restaurantVat);
						break;
					}
				}
			}
		}
		restaurant.setVats(restaurantVats);
	}
	
	/**
	 * Process the selected vats list from database.
	 */
	private void processSelectedVatsFromDatabase() {
		RestaurantsManagerForm form = (RestaurantsManagerForm) super.getForm();

		// Ids selected from databse: key == vat id / value == restaurant vat id
		Map<Long, String> vats = new HashMap<Long, String>();
		// restaurant that contains set of RestaurantValueAddedTaxDto 
		RestaurantDto restaurant = (RestaurantDto) form.getDtoBean();
		for (RestaurantValueAddedTaxDto restaurantVat : restaurant.getVats()) {
			vats.put(restaurantVat.getVat().getId(), restaurantVat.getId().toString());
		}
		form.setVats(vats);
	}

	@Override
	public String save() {
		
		this.processSave(new String[] {null});
		// Return to the list
		return Constants.ACTION_RESULT_AFTER_CUD;

	}
	
	private void processSave(String... prefixIdToRemove) {
		processSelectedVatsFromUser();
		
		if (prefixIdToRemove != null && prefixIdToRemove.length == 1) {
			removePrefixBeforeSaving(prefixIdToRemove[0]);
		}
		
		super.save();
	}
	
	
	private void removePrefixBeforeSaving(String prefixIdToRemove) {
		RestaurantsManagerForm form = (RestaurantsManagerForm) super.getForm();
		RestaurantDto restaurant = (RestaurantDto) form.getDtoBean();
		Set<RestaurantPrefixTableDto> restaurantPrefixTables = new HashSet<RestaurantPrefixTableDto>();
		// Try to remove from restaurant.getPrefixTableNames() list 2 elements of RestaurantPrefixTableDto
		// One of id null and one of id equals to prefixIdToRemove
		for (RestaurantPrefixTableDto restaurantPrefixTableDto : restaurant.getPrefixTableNames()) {
			if (restaurantPrefixTableDto.getId() != null && !restaurantPrefixTableDto.getId().toString().equals(prefixIdToRemove)) {
				restaurantPrefixTables.add(restaurantPrefixTableDto);
			}
		}
		restaurant.setPrefixTableNames(restaurantPrefixTables);
	}
	
	public String removePrefix() throws Exception {
		String prefixIdToRemove = super.getRequest().getParameter("method:removePrefix");
		this.processSave(prefixIdToRemove);
		this.form();
		return Constants.ACTION_RESULT_AFTER_CUD_PREFIX_TABLE;
	}

	public String addPrefix() throws Exception {
		this.processSave();
		this.form();
		return Constants.ACTION_RESULT_AFTER_CUD_PREFIX_TABLE;
	}
	
}
