package fr.mch.mdo.restaurant.services.business.managers.locales;

import java.util.List;
import java.util.Map;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.locales.ILocalesDao;
import fr.mch.mdo.restaurant.exception.MdoException;

public class LocalesDaoForLocalesManagerTest implements ILocalesDao 
{
	@Override
	public IMdoBean delete(IMdoBean bean, boolean... isLazy) throws MdoException {
		return null;
	}

	@Override
	public List<IMdoBean> findAll(boolean... isLazy) throws MdoException {
		return null;
	}

	@Override
	public IMdoBean findByPrimaryKey(Object key, boolean... isLazy) throws MdoException {
		return null;
	}

	@Override
	public IMdoBean findByUniqueKey(Object propertyValue, boolean... isLazy) throws MdoException {
		return null;
	}

	@Override
	public IMdoBean findByUniqueKey(Object[] propertyValues, boolean... isLazy) throws MdoException {

		return null;
	}

	@Override
	public IMdoBean insert(IMdoBean bean, boolean... isLazy) throws MdoException {
		return null;
	}

	@Override
	public void load(IMdoBean daoBean, boolean... isLazy) throws MdoException {

	}

	@Override
	public IMdoBean update(IMdoBean bean, boolean... isLazy) throws MdoException {
		return null;
	}

	@Override
	public IMdoBean getBean() {

		return null;
	}

	@Override
	public void setBean(IMdoBean bean) {

	}

	@Override
	public ILogger getLogger() {

		return null;
	}

	@Override
	public void setLogger(ILogger logger) {

	}

//	@Override
	public void closeSession() throws MdoException {
	}

//	@Override
	public Object getCurrentSession() throws MdoException {
		return null;
	}

	@Override
	public void updateFieldsByKeys(Map<String, Object> fields, Map<String, Object> keys) throws MdoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateFieldsByKeys(Class<? extends IMdoBean> clazz, Map<String, Object> fields, Map<String, Object> keys)
			throws MdoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteByKeys(Map<String, Object> keys) throws MdoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteByKeys(Class<? extends IMdoBean> clazz, Map<String, Object> keys) throws MdoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) throws MdoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IMdoBean save(IMdoBean bean, boolean... isLazy) throws MdoException {
		// TODO Auto-generated method stub
		return null;
	}
}
