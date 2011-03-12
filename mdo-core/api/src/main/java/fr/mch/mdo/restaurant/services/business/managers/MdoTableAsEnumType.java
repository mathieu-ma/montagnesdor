package fr.mch.mdo.restaurant.services.business.managers;

import java.util.List;

import fr.mch.mdo.restaurant.dao.IMdoTableAsEnumsDao;
import fr.mch.mdo.restaurant.dao.beans.MdoTableAsEnum;
import fr.mch.mdo.restaurant.exception.MdoException;

public enum MdoTableAsEnumType
{
	DEFAULT() {
		public List<MdoTableAsEnum> getList(IMdoTableAsEnumsDao dao, String... type) throws MdoException {
			super.getList(dao);
			return dao.getBeans(type[0]);
		}
	},
	SPECIFIC_ROUND() {
		public List<MdoTableAsEnum> getList(IMdoTableAsEnumsDao dao, String... type) throws MdoException {
			super.getList(dao);
			return dao.getSpecificRounds();
		}
	},
	TABLE_TYPE() {
		public List<MdoTableAsEnum> getList(IMdoTableAsEnumsDao dao, String... type) throws MdoException {
			super.getList(dao);
			return dao.getTableTypes();
		}
	},
	PREFIX_TABLE_NAME() {
		public List<MdoTableAsEnum> getList(IMdoTableAsEnumsDao dao, String... type) throws MdoException {
			super.getList(dao);
			return dao.getRestaurantPrefixTakeawayNames();
		}
	},
	PRINTING_INFORMATION_ALIGNMENT() {
		public List<MdoTableAsEnum> getList(IMdoTableAsEnumsDao dao, String... type) throws MdoException {
			super.getList(dao);
			return dao.getPrintingInformationAlignments();
		}
	},
	PRINTING_INFORMATION_SIZE() {
		public List<MdoTableAsEnum> getList(IMdoTableAsEnumsDao dao, String... type) throws MdoException {
			super.getList(dao);
			return dao.getPrintingInformationSizes();
		}
	},
	PRINTING_INFORMATION_PART() {
		public List<MdoTableAsEnum> getList(IMdoTableAsEnumsDao dao, String... type) throws MdoException {
			super.getList(dao);
			return dao.getPrintingInformationParts();
		}
	},
	USER_ROLE() {
		public List<MdoTableAsEnum> getList(IMdoTableAsEnumsDao dao, String... type) throws MdoException {
			super.getList(dao);
			return dao.getUserRoles();
		}
	},
	USER_TITLE() {
		public List<MdoTableAsEnum> getList(IMdoTableAsEnumsDao dao, String... type) throws MdoException {
			super.getList(dao);
			return dao.getUserTitles();
		}
	},
	CATEGORY() {
		public List<MdoTableAsEnum> getList(IMdoTableAsEnumsDao dao, String... type) throws MdoException {
			super.getList(dao);
			return dao.getCategories();
		}
	},
	PRODUCT_SPECIAL_CODE() {
		public List<MdoTableAsEnum> getList(IMdoTableAsEnumsDao dao, String... type) throws MdoException {
			super.getList(dao);
			return dao.getProductSpecialCodes();
		}
	},
	PRODUCT_PART() {
		public List<MdoTableAsEnum> getList(IMdoTableAsEnumsDao dao, String... type) throws MdoException {
			super.getList(dao);
			return dao.getProductParts();
		}
	},
	VALUE_ADDED_TAX() {
		public List<MdoTableAsEnum> getList(IMdoTableAsEnumsDao dao, String... type) throws MdoException {
			super.getList(dao);
			return dao.getValueAddedTaxes();
		}
	},
	CASHING_TYPE() {
		public List<MdoTableAsEnum> getList(IMdoTableAsEnumsDao dao, String... type) throws MdoException {
			super.getList(dao);
			return dao.getCashings();
		}
	};

	public List<MdoTableAsEnum> getList(IMdoTableAsEnumsDao dao, String... type) throws MdoException {
		if (dao == null) {
			throw new NullPointerException(IMdoTableAsEnumsDao.class.getName());
		}
		return null;
	}
}
