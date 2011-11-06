package fr.mch.mdo.restaurant.dao.orders.hibernate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Projections;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.dao.beans.OrderLine;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServices;
import fr.mch.mdo.restaurant.dao.orders.IOrderLinesDao;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DefaultOrderLinesDao extends DefaultDaoServices implements IOrderLinesDao 
{
	private static class LazyHolder {
		private static IOrderLinesDao instance = new DefaultOrderLinesDao(LoggerServiceImpl.getInstance().getLogger(DefaultOrderLinesDao.class.getName()), new OrderLine());
	}

	private DefaultOrderLinesDao(ILogger logger, IMdoDaoBean bean) {
		super(true);
		this.setLogger(logger);
		this.setBean(bean);
	}

	public static IOrderLinesDao getInstance() {
		return LazyHolder.instance;
	}

	public DefaultOrderLinesDao() {
	}

	@Override
	public OrderLine getOrderLine(Long id) throws MdoException {
		OrderLine result = null;
		List<MdoCriteria> criterias = new ArrayList<MdoCriteria>();
		criterias.add(new MdoCriteria("id", PropertiesRestrictions.EQUALS, id));
		
		criterias.add(new MdoCriteria("quantity", PropertiesRestrictions.PROJECTION, Projections.property("quantity")));
		criterias.add(new MdoCriteria("label", PropertiesRestrictions.PROJECTION, Projections.property("label")));
		criterias.add(new MdoCriteria("unitPrice", PropertiesRestrictions.PROJECTION, Projections.property("unitPrice")));
		criterias.add(new MdoCriteria("amount", PropertiesRestrictions.PROJECTION, Projections.property("amount")));

		Object[] object = (Object[]) super.uniqueResult(super.findByCriteria(super.getBean().getClass(), criterias));
		if (object != null) {
			result = new OrderLine();
			result.setId(id);
			result.setQuantity(new BigDecimal(object[0].toString()));
			result.setLabel((String) object[1]);
			result.setUnitPrice(new BigDecimal(object[2].toString()));
			result.setAmount(new BigDecimal(object[3].toString()));
		}
		
		return result;
	}
}
