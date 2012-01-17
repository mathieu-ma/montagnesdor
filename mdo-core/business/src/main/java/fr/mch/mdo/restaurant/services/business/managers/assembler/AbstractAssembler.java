package fr.mch.mdo.restaurant.services.business.managers.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.mch.mdo.logs.ILoggerBean;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.utils.IManagerAssembler;

public abstract class AbstractAssembler implements IManagerAssembler, ILoggerBean 
{
	@Override
	public Set<IMdoDtoBean> marshal(Set<? extends IMdoBean> set, MdoUserContext userContext) {
		Set<IMdoDtoBean> result = new LinkedHashSet<IMdoDtoBean>();
		this.marshal(result, set, userContext);
		return result;
	}

	@Override
	public List<IMdoDtoBean> marshal(List<? extends IMdoBean> list, MdoUserContext userContext) {
		List<IMdoDtoBean> result = new ArrayList<IMdoDtoBean>();
		this.marshal(result, list, userContext);
		return result;
	}

	private void marshal(Collection<IMdoDtoBean> collections, Collection<? extends IMdoBean> collection, MdoUserContext userContext) {
		if (collection != null) {
			for (IMdoBean iMdoBean : collection) {
				collections.add(marshal((IMdoDaoBean) iMdoBean, userContext));
			}
		}
	}

	@Override
	public Set<IMdoDaoBean> unmarshal(Set<? extends IMdoBean> set, IMdoDaoBean... parents) {
		Set<IMdoDaoBean> result = new HashSet<IMdoDaoBean>();
		this.unmarshal(result, set, parents);
		return result;
	}

	@Override
	public List<IMdoDaoBean> unmarshal(List<? extends IMdoBean> list, IMdoDaoBean... parents) {
		List<IMdoDaoBean> result = new ArrayList<IMdoDaoBean>();
		this.unmarshal(result, list, parents);
		return result;
	}

	private void unmarshal(Collection<IMdoDaoBean> collections, Collection<? extends IMdoBean> collection, IMdoDaoBean... parents) {
		if (collection != null) {
			for (IMdoBean iMdoBean : collection) {
				IMdoDaoBean bean = unmarshal((IMdoDtoBean) iMdoBean, parents);
				collections.add(bean);
			}
		}
	}

	protected Map<Long, String> getLabels(Map<Long, String> labels) {
		Map<Long, String> result = new HashMap<Long, String>();
		
		if (labels != null) {
			result = new LinkedHashMap<Long, String>(labels.size());
			for (Long key : labels.keySet()) {
				result.put(key, labels.get(key));
			}
		}
		
		return result;
	}
}
