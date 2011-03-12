package fr.mch.mdo.restaurant.dto.beans;

import java.util.List;

import fr.mch.mdo.restaurant.beans.IMdoDtoBean;

/**
 * @author Mathieu MA
 * 
 */
public class AdministrationManagerViewBean implements IAdministrationManagerViewBean 
{
	/**
	 * Default Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

	// protected MdoUserContext userContext;
	protected List<IMdoDtoBean> list;
	protected IMdoDtoBean dtoBean;

	@Override
	public List<IMdoDtoBean> getList() {
		return list;
	}

	@Override
	public void setList(List<IMdoDtoBean> list) {
		this.list = list;
	}

	// @Override
	// public MdoUserContext getUserContext() {
	// return userContext;
	// }
	//
	// @Override
	// public void setUserContext(MdoUserContext userContext) {
	// this.userContext = userContext;
	// }

	@Override
	public IMdoDtoBean getDtoBean() {
		return dtoBean;
	}

	@Override
	public void setDtoBean(IMdoDtoBean dtoBean) {
		this.dtoBean = dtoBean;
	}
}
