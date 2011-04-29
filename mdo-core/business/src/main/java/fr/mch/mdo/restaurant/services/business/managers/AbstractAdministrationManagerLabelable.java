package fr.mch.mdo.restaurant.services.business.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import fr.mch.mdo.restaurant.beans.IBeanLabelable;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dto.beans.LocaleDto;
import fr.mch.mdo.restaurant.exception.MdoBusinessException;
import fr.mch.mdo.restaurant.exception.MdoException;


public abstract class AbstractAdministrationManagerLabelable extends AbstractAdministrationManager implements IManagerLabelable 
{
	@Override
	public Map<Long, String> getLabels(LocaleDto currentLocale) throws MdoBusinessException {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		try {
			list = dao.findAll();
		} catch (MdoException e) {
			logger.error("message.error.administration.business.find.all", e);
			throw new MdoBusinessException("message.error.administration.business.find.all", e);
		}
		Map<Long, String> result = new HashMap<Long, String>(list.size());

		for (Iterator<IMdoBean> iterator = list.iterator(); iterator.hasNext();) {
			IBeanLabelable mdoBean = (IBeanLabelable) iterator.next();
		
			String label = null;
			if (currentLocale != null && mdoBean.getLabels() != null && !mdoBean.getLabels().isEmpty()) {
				label = mdoBean.getLabels().get(currentLocale.getId());
				if (label == null) {
					label = mdoBean.getLabels().values().iterator().next();
				}
			}
			if (label == null) {
				label = this.getDefaultLabel(mdoBean);
			}
			result.put(mdoBean.getId(), label);
		}
		return result;
	}
	
	protected abstract String getDefaultLabel(IBeanLabelable mdoBean);
}