package fr.mch.mdo.restaurant.web.struts.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.xwork.StringUtils;

import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.IAdministrationManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.MdoTableAsEnumDto;
import fr.mch.mdo.restaurant.dto.beans.MdoTableAsEnumsManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.UserRestaurantDto;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.ioc.spring.WebAdministrationBeanFactory;
import fr.mch.mdo.restaurant.services.business.managers.products.IMdoTableAsEnumsManager;
import fr.mch.mdo.restaurant.ui.forms.IMdoAdministrationForm;
import fr.mch.mdo.restaurant.ui.forms.MdoTableAsEnumsManagerForm;

public class MdoTableAsEnumsManagerWebAction extends AdministrationManagerAction 
{
	/**
	 * Default Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	public MdoTableAsEnumsManagerWebAction() {
		super(WebAdministrationBeanFactory.getInstance().getLogger(MdoTableAsEnumsManagerWebAction.class.getName()), new MdoTableAsEnumsManagerForm());
		administrationManager = WebAdministrationBeanFactory.getInstance().getMdoTableAsEnumsManager();
	}

	public void validateSave() {
		// This method is to be called after Struts Validation Interceptor even
		// there is exception.
		// So we can call custom method to do what we want
		super.getLogger().debug("validateSave");
	}

	@Override
	public String form() throws Exception {
		String result = super.form();

		IAdministrationManagerViewBean viewBean = ((IMdoAdministrationForm) super.getForm()).getViewBean();
		if (viewBean != null) {
			try {
				this.getAdministrationManager().processList(viewBean, (MdoUserContext) super.getForm().getUserContext());
			} catch (MdoException e) {
				super.addActionError(super.getText("error.action.technical", new String[] { this.getClass().getName(), "form" }));
			}
		}
		return result;
	}

	public String listType() throws Exception {
		String result = Constants.ACTION_RESULT_AFTER_SUCCESS_FORM_LIST;

		MdoTableAsEnumDto dtoBean = (MdoTableAsEnumDto) super.getForm().getDtoBean();
		MdoTableAsEnumsManagerViewBean viewBean = (MdoTableAsEnumsManagerViewBean) ((IMdoAdministrationForm) super.getForm()).getViewBean();
		if (viewBean != null) {
			IMdoTableAsEnumsManager manager = (IMdoTableAsEnumsManager) administrationManager;
			MdoUserContext userContext = (MdoUserContext) super.getForm().getUserContext();
			List<IMdoDtoBean> list = manager.getList(dtoBean.getType(), userContext);
			viewBean.setList(list);
			// START Check bean that must not be deleted
			List<Long> notDeletedBeanIds = new ArrayList<Long>();
			if (userContext.getUser() != null) {
				Set<UserRestaurantDto> userRestaurants = userContext.getUser().getRestaurants();
				if (userRestaurants != null) {
					for (Iterator<UserRestaurantDto> iterator = userRestaurants.iterator(); iterator.hasNext();) {
						UserRestaurantDto userRestaurantDto = (UserRestaurantDto) iterator.next();
						if (userRestaurantDto.getRestaurant() != null && userRestaurantDto.getRestaurant().getSpecificRound() != null
								&& userRestaurantDto.getRestaurant().getSpecificRound().getId() != null) {
							notDeletedBeanIds.add(userRestaurantDto.getRestaurant().getSpecificRound().getId());
						}
					}
				}
			}
			if (userContext.getUserRole() != null && userContext.getUserRole().getCode() != null && userContext.getUserRole().getCode().getId() != null) {
				notDeletedBeanIds.add(userContext.getUserRole().getCode().getId());
			}
			if (userContext.getUser() != null && userContext.getUser().getTitle() != null && userContext.getUser().getTitle().getId() != null) {
				notDeletedBeanIds.add(userContext.getUser().getTitle().getId());
			}
			viewBean.setNotDeletedBeanIds(notDeletedBeanIds);
			// END Check bean that must not be deleted
		}
		return result;
	}

	@Override
	public String save() {
		super.save();
		return Constants.ACTION_RESULT_AFTER_CUD_LIST_TYPE;
	}

	@Override
	public String delete() {
		String result = super.delete();
		MdoTableAsEnumDto dtoBean = (MdoTableAsEnumDto) super.getForm().getDtoBean();
		IMdoTableAsEnumsManager manager = (IMdoTableAsEnumsManager) administrationManager;
		MdoUserContext userContext = (MdoUserContext) super.getForm().getUserContext();
		List<IMdoDtoBean> list = null;
		try {
			// Get a list by type.
			list = manager.getList(dtoBean.getType(), userContext);
		} catch (MdoException e) {
			// Do not add action error because of validation
			super.addActionMessage(super.getText(e.getLocalizedMessage()));
		}
		if (list != null && !list.isEmpty()) {
			// There is at least one element with the specify type.
			result = Constants.ACTION_RESULT_AFTER_CUD_LIST_TYPE;
		}
		return result;
	}

	@Override
	public void validate() {
		MdoTableAsEnumsManagerForm form = (MdoTableAsEnumsManagerForm) super.getForm();

		MdoTableAsEnumDto dtoBean = (MdoTableAsEnumDto) form.getDtoBean();
		if (StringUtils.isEmpty(dtoBean.getType())) {
			if (!StringUtils.isEmpty(form.getUserEntryType())) {
				dtoBean.setType(form.getUserEntryType());
			} else {
				super.addFieldError("form.dtoBean.type", super.getText("error.emuns.type.required"));
			}
		}
	}
}
