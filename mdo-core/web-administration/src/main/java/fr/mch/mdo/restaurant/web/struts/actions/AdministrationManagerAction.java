package fr.mch.mdo.restaurant.web.struts.actions;

import org.apache.commons.lang.xwork.StringUtils;

import com.opensymphony.xwork2.Preparable;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.beans.MdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.IAdministrationManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;
import fr.mch.mdo.restaurant.ui.forms.IMdoAdministrationForm;
import fr.mch.mdo.restaurant.ui.forms.IMdoForm;

public class AdministrationManagerAction extends MdoAbstractWebAction implements Preparable
{
	/**
     * Default Serial Version UID.
     */
	private static final long serialVersionUID = 1L;

	protected IAdministrationManager administrationManager;

	public AdministrationManagerAction(ILogger logger, IMdoForm form) {
		super(logger, form);
	}

	public IAdministrationManager getAdministrationManager() {
		return administrationManager;
	}

	public void setAdministrationManager(IAdministrationManager administrationManager) {
		this.administrationManager = administrationManager;
	}

	public String form() throws Exception {
		super.validate();

		MdoDtoBean dtoBean = (MdoDtoBean) super.getForm().getDtoBean();
		IMdoDtoBean newDtoBean = dtoBean; 
		if (dtoBean.getId() !=null) {
			try {
				newDtoBean = this.getAdministrationManager().findByPrimaryKey(dtoBean.getId(), (MdoUserContext) super.getForm().getUserContext());
			} catch (MdoException e) {
				super.addActionError(super.getText("error.action.technical", new String[] {this.getClass().getName(), "form"}));
			}
		}
		super.getForm().setDtoBean(newDtoBean);
		return Constants.ACTION_RESULT_AFTER_SUCCESS_FORM_LIST;
	}

	public String save() {
		boolean isCreation = super.getForm().getDtoBean().getId() == null;
		try {
			IMdoDtoBean newDtoBean = this.getAdministrationManager().save(super.getForm().getDtoBean(), (MdoUserContext) super.getForm().getUserContext());
			super.getForm().setDtoBean(newDtoBean);
		} catch (Exception e) {
			// Do not add action error because of validation
			if (StringUtils.isEmpty(e.getLocalizedMessage())) {
				super.addActionMessage(super.getText("error.save"));
			} else {
				super.addActionMessage(super.getText(e.getLocalizedMessage()));
			}
		}
		
		if (!super.hasActionMessages()) {
			String infoKey = "info.success.data.creation";
			// Saving done
			if (!isCreation) {
				infoKey = "info.success.data.update";
			}
			super.addActionMessage(super.getText(infoKey));
		}
		// Return to the list
		return Constants.ACTION_RESULT_AFTER_CUD;
	}

	public String delete() {
		try {
			this.getAdministrationManager().delete(super.getForm().getDtoBean(), (MdoUserContext) super.getForm().getUserContext());
		} catch (Exception e) {
			// Do not add action error because of validation
			super.addActionMessage(super.getText(e.getLocalizedMessage()));
		}

		if (!super.hasActionMessages()) {
			// Deletion done
			super.addActionMessage(super.getText("info.success.data.deletion"));
		}
		// Return to the list
		return Constants.ACTION_RESULT_AFTER_CUD;
	}
	
	public String list() throws Exception {
		// Everything is done in prepare method managed by Struts
		return Constants.ACTION_RESULT_AFTER_SUCCESS_FORM_LIST;
	}

	@Override
	public void prepare() {
		IAdministrationManagerViewBean viewBean = ((IMdoAdministrationForm) super.getForm()).getViewBean();
		if (viewBean != null) {
			try {
				this.getAdministrationManager().processList(viewBean, (MdoUserContext) super.getForm().getUserContext());
			} catch (MdoException e) {
				super.addActionError(super.getText("error.action.technical", new String[] { this.getClass().getName(), "prepare" }));
			}
		}
	}
}
