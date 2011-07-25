package fr.mch.mdo.restaurant.ui.forms;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;

public class MdoForm implements IMdoForm 
{
	protected IMdoDtoBean dtoBean;
	private IMdoBean userContext;
	
	protected MdoForm(IMdoDtoBean dtoBean) {
		this.dtoBean = dtoBean;
	}

	@Override
	public IMdoDtoBean getDtoBean() {
		return dtoBean;
	}

	@Override
	public void setDtoBean(IMdoDtoBean dtoBean) {
		this.dtoBean = dtoBean;
	}

	@Override
	public IMdoBean getUserContext() {
		return userContext;
	}

	@Override
	public void setUserContext(IMdoBean userContext) {
		this.userContext = userContext;
	}
}
