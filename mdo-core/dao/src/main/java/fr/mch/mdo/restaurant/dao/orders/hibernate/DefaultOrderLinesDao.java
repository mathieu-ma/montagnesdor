package fr.mch.mdo.restaurant.dao.orders.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Projections;
import org.hibernate.transform.ResultTransformer;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.dao.beans.OrderLine;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServices;
import fr.mch.mdo.restaurant.dao.hibernate.MdoAliasToBean;
import fr.mch.mdo.restaurant.dao.orders.IOrderLinesDao;
import fr.mch.mdo.restaurant.exception.MdoDataBeanException;
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
	public IMdoBean getOrderLine(Long id) throws MdoDataBeanException {
		OrderLine result = null;
		List<MdoCriteria> criterias = new ArrayList<MdoCriteria>();
		criterias.add(new MdoCriteria("id", PropertiesRestrictions.EQUALS, id));
		
		criterias.add(new MdoCriteria("quantity", PropertiesRestrictions.PROJECTION));
		criterias.add(new MdoCriteria("label", PropertiesRestrictions.PROJECTION));
		criterias.add(new MdoCriteria("unitPrice", PropertiesRestrictions.PROJECTION));
		criterias.add(new MdoCriteria("amount", PropertiesRestrictions.PROJECTION));

		result = (OrderLine) super.uniqueResult(super.findByCriteria(super.getBean().getClass(), OrderLine.class, criterias, true));
		
		return result;
	}
	
	@Override
	public int getOrderLinesSize(Long dinnerTableId) throws MdoDataBeanException {
		int result = 0;

		List<MdoCriteria> criterias = new ArrayList<MdoCriteria>();
		criterias.add(new MdoCriteria("dinnerTable.id", PropertiesRestrictions.EQUALS, dinnerTableId));
		criterias.add(new MdoCriteria("count", PropertiesRestrictions.PROJECTION_ROW_COUNT, Projections.rowCount()));
		Object object = super.uniqueResult(super.findByCriteria(OrderLine.class, criterias));
		if (object != null) {
			result = new Integer(object.toString());
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderLine> findAllScalarFieldsByDinnerTableId(Long dinnerTableId, Long locId) throws MdoDataBeanException {
		List<OrderLine> result = new ArrayList<OrderLine>();
		
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("dinnerTableId", dinnerTableId);
		values.put("locId", locId);
		ResultTransformer resultTransformer = new MdoAliasToBean(OrderLine.class, new String[] {
			"id", "quantity", "unitPrice", "amount", 
			"productSpecialCode.shortCode", "productSpecialCode.code.name", 
			"product.code", "product.colorRGB", "label"
		});

		result = super.findAllByQuery(Constants.HQL_ORDER_LINE_FIND_BY_DINNER_TABLE_ID, values, resultTransformer);

		return result;
	}
}
