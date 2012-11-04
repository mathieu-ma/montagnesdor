package fr.mch.mdo.restaurant.services.dto;

import java.util.List;
import java.util.Set;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.UserAuthentication;
import fr.mch.mdo.restaurant.dto.beans.LogonDto;
import fr.mch.mdo.utils.IManagerAssembler;

public class LogonDaoDtoBeanHelper implements IManagerAssembler 
{
	public IMdoBean unmarshal(IMdoBean dtoBean) {
		UserAuthentication daoBean = null;

		if (dtoBean instanceof LogonDto) {
			daoBean = new UserAuthentication();
			LogonDto dtoBeanX = (LogonDto) dtoBean;
			daoBean.setLogin(dtoBeanX.getLogin());
			daoBean.setPassword(dtoBeanX.getPassword());
		}

		return daoBean;
	}

	public IMdoBean marshal(IMdoBean daoBean) {
		return daoBean;
	}

	@Override
	public IMdoDtoBean marshal(IMdoDaoBean daoBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IMdoDtoBean> marshal(List<? extends IMdoBean> list) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<IMdoDtoBean> marshal(Set<? extends IMdoBean> set) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IMdoDaoBean> unmarshal(List<? extends IMdoBean> list, IMdoDaoBean... parents) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<IMdoDaoBean> unmarshal(Set<? extends IMdoBean> set, IMdoDaoBean... parents) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IMdoDaoBean unmarshal(IMdoDtoBean dtoBean, IMdoDaoBean... parents) {
		// TODO Auto-generated method stub
		return null;
	}
}
