package fr.mch.mdo.restaurant.ui.forms;

import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.IAdministrationManagerViewBean;

public class MdoAdministrationForm extends MdoForm implements IMdoAdministrationForm 
{
	private IAdministrationManagerViewBean viewBean;
	
	protected MdoAdministrationForm(IMdoDtoBean dtoBean) {
		super(dtoBean);
	}

	@Override
	public IAdministrationManagerViewBean getViewBean() {
		return viewBean;
	}

	@Override
	public void setViewBean(IAdministrationManagerViewBean viewBean) {
		this.viewBean = viewBean;
	}
}
