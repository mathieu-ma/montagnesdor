package fr.mch.mdo.restaurant.web.struts.actions;

import com.opensymphony.xwork2.Preparable;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;
import fr.mch.mdo.restaurant.ui.forms.IMdoForm;

public class RestaurantWebAction extends MdoAbstractWebAction implements Preparable
{
	/**
     * Default Serial Version UID.
     */
	private static final long serialVersionUID = 1L;

	protected IAdministrationManager administrationManager;

	public RestaurantWebAction(ILogger logger, IMdoForm form) {
		super(logger, form);
	}

	@Override
	public void prepare() {
	}
}
