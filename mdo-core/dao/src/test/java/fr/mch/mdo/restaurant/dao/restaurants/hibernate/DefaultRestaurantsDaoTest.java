package fr.mch.mdo.restaurant.dao.restaurants.hibernate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.DaoServicesFactory;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.beans.MdoTableAsEnum;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dao.beans.RestaurantPrefixTable;
import fr.mch.mdo.restaurant.dao.beans.RestaurantReductionTable;
import fr.mch.mdo.restaurant.dao.beans.RestaurantValueAddedTax;
import fr.mch.mdo.restaurant.dao.beans.RestaurantVatTableType;
import fr.mch.mdo.restaurant.dao.beans.TableType;
import fr.mch.mdo.restaurant.dao.beans.ValueAddedTax;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServicesTestCase;
import fr.mch.mdo.restaurant.dao.restaurants.IRestaurantsDao;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultRestaurantsDaoTest extends DefaultDaoServicesTestCase 
{
	private static long basicRestaurantReference = Long.valueOf(DefaultDaoServicesTestCase.RESTAURANT_FIRST_REFERENCE);

	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultRestaurantsDaoTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultRestaurantsDaoTest.class);
	}

	@Override
	protected IDaoServices getInstance() {
		return DefaultRestaurantsDao.getInstance();
	}

	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof IRestaurantsDao);
		assertTrue(this.getInstance() instanceof DefaultRestaurantsDao);
	}

	@Override
	protected IMdoBean createNewBean() {
		Date registrationDate = Calendar.getInstance().getTime();
		String reference = Long.toString(++basicRestaurantReference);
		String name = "Kim-San 95";
		String addressRoad = "3 place de la Piscine";
		String addressZip = "95 334";
		String addressCity = "Pontoise";
		String phone = "0130321200";
		String vatRef = "1234567890";
		String visaRef = "A1Z2E3R4T5Y6";
		String tripleDESKey = "1A2B3C4D5E6F";
		boolean vatByTakeaway = true;
		MdoTableAsEnum specificRound = new MdoTableAsEnum();
		specificRound.setId(2L);
		TableType defaultTableType = new TableType();
		defaultTableType.setId(1L);
		Set<RestaurantPrefixTable> prefixTableNames = new HashSet<RestaurantPrefixTable>();
		Set<RestaurantReductionTable> reductionTables = new HashSet<RestaurantReductionTable>();
		Set<RestaurantVatTableType> vatTableTypes = new HashSet<RestaurantVatTableType>();
		Set<RestaurantValueAddedTax> vats = null;
		ValueAddedTax orderLineDefaultVat = new ValueAddedTax();
		orderLineDefaultVat.setId(1L);
		Restaurant restaurant = (Restaurant) createNewBean(registrationDate, reference, name, addressRoad, addressZip, addressCity, phone, vatRef, visaRef, tripleDESKey,
				vatByTakeaway, specificRound, defaultTableType, prefixTableNames, reductionTables, vats, vatTableTypes, orderLineDefaultVat);

		RestaurantPrefixTable restaurantPrefixTable = new RestaurantPrefixTable();
		MdoTableAsEnum prefix = new MdoTableAsEnum();
		// Use the existing data in database
		try {
			prefix = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(1L);
		} catch (MdoException e) {
			fail("Could not found the prefix.");
		}
		restaurantPrefixTable.setPrefix(prefix);
		TableType type = new TableType();
		// Use the existing data in database
		try {
			type = (TableType) DaoServicesFactory.getTableTypesDao().findByPrimaryKey(1L);
		} catch (MdoException e) {
			fail("Could not found the type.");
		}
		restaurantPrefixTable.setType(type);
		restaurant.addPrefixTableName(restaurantPrefixTable);

		RestaurantReductionTable restaurantReductionTable = new RestaurantReductionTable();
		restaurantReductionTable.setType(type);
		restaurantReductionTable.setMinAmount(BigDecimal.TEN);
		restaurantReductionTable.setValue(BigDecimal.TEN);
		restaurant.addReductionTable(restaurantReductionTable);

		RestaurantVatTableType restaurantVatTableType = new RestaurantVatTableType();
		restaurantVatTableType.setType(type);
		ValueAddedTax vat = new ValueAddedTax();
		vat.setId(1L);
		restaurantVatTableType.setVat(vat);
		restaurant.addVatTableType(restaurantVatTableType);

		return restaurant;
	}

	@Override
	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		// First bean in list
		IMdoBean newBean = null;
		Date registrationDate = Calendar.getInstance().getTime();
		String reference = Long.toString(++basicRestaurantReference);
		String name = "Kim-San 95";
		String addressRoad = "3 place de la Piscine";
		String addressZip = "95 334";
		String addressCity = "Pontoise";
		String phone = "0130321200";
		String vatRef = "1234567890";
		String visaRef = "A1Z2E3R4T5Y6";
		String tripleDESKey = "1A2B3C4D5E6F";
		boolean vatByTakeaway = true;
		MdoTableAsEnum specificRound = new MdoTableAsEnum();
		specificRound.setId(3L);
		TableType defaultTableType = new TableType();
		defaultTableType.setId(2L);
		Set<RestaurantPrefixTable> prefixTableNames = new HashSet<RestaurantPrefixTable>();
		Set<RestaurantReductionTable> reductionTables = new HashSet<RestaurantReductionTable>();
		Set<RestaurantValueAddedTax> vats = new HashSet<RestaurantValueAddedTax>();
		Set<RestaurantVatTableType> vatTableTypes = new HashSet<RestaurantVatTableType>();
		ValueAddedTax orderLineDefaultVat = new ValueAddedTax();
		orderLineDefaultVat.setId(1L);
		newBean = createNewBean(registrationDate, reference, name, addressRoad, addressZip, addressCity, phone, vatRef, visaRef, tripleDESKey, vatByTakeaway,
				specificRound, defaultTableType, prefixTableNames, reductionTables, vats, vatTableTypes, orderLineDefaultVat);

		RestaurantPrefixTable restaurantPrefixTable = new RestaurantPrefixTable();
		MdoTableAsEnum prefix = new MdoTableAsEnum();
		// Use the existing data in database
		try {
			prefix = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(1L);
		} catch (MdoException e) {
			fail("Could not found the prefix.");
		}
		restaurantPrefixTable.setPrefix(prefix);
		TableType type = new TableType();
		// Use the existing data in database
		try {
			type = (TableType) DaoServicesFactory.getTableTypesDao().findByPrimaryKey(1L);
		} catch (MdoException e) {
			fail("Could not found the type.");
		}
		restaurantPrefixTable.setType(type);
		((Restaurant) newBean).addPrefixTableName(restaurantPrefixTable);

		RestaurantReductionTable restaurantReductionTable = new RestaurantReductionTable();
		restaurantReductionTable.setType(type);
		restaurantReductionTable.setMinAmount(BigDecimal.TEN);
		restaurantReductionTable.setValue(BigDecimal.TEN);
		((Restaurant) newBean).addReductionTable(restaurantReductionTable);

		RestaurantVatTableType restaurantVatTableType = new RestaurantVatTableType();
		restaurantVatTableType.setType(type);
		ValueAddedTax vat = new ValueAddedTax();
		vat.setId(2L);
		restaurantVatTableType.setVat(vat);
		((Restaurant) newBean).addVatTableType(restaurantVatTableType);

		RestaurantValueAddedTax restaurantVat = new RestaurantValueAddedTax();
		ValueAddedTax valueAddedTax = new ValueAddedTax();
		valueAddedTax.setId(1L);
		restaurantVat.setVat(valueAddedTax);
		((Restaurant) newBean).addVat(restaurantVat);

		list.add(newBean);
		// Second bean in list
		registrationDate = Calendar.getInstance().getTime();
		reference = Long.toString(++basicRestaurantReference);
		name = "Fokuda";
		addressRoad = "2 rue de Paris";
		addressZip = "75 005";
		addressCity = "Paris";
		phone = "0130321200";
		vatRef = "1234567890";
		visaRef = "A1Z2E3R4T5Y6";
		tripleDESKey = "1A2B3C4D5E6F";
		vatByTakeaway = true;
		prefixTableNames = new HashSet<RestaurantPrefixTable>();
		reductionTables = new HashSet<RestaurantReductionTable>();
		// Use the existing data in database
		try {
			specificRound = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(new Long(2));
		} catch (MdoException e) {
			fail("Could not found the Specific Round.");
		}
		defaultTableType = new TableType();
		defaultTableType.setId(1L);
		vats = new HashSet<RestaurantValueAddedTax>();
		vatTableTypes = new HashSet<RestaurantVatTableType>();
		orderLineDefaultVat = new ValueAddedTax();
		orderLineDefaultVat.setId(2L);
		newBean = createNewBean(registrationDate, reference, name, addressRoad, addressZip, addressCity, phone, vatRef, visaRef, tripleDESKey, vatByTakeaway,
				specificRound, defaultTableType, prefixTableNames, reductionTables, vats, vatTableTypes, orderLineDefaultVat);

		restaurantPrefixTable = new RestaurantPrefixTable();
		// Use the existing data in database
		prefix = new MdoTableAsEnum();
		// Use the existing data in database
		try {
			prefix = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(1L);
		} catch (MdoException e) {
			fail("Could not found the prefix.");
		}
		restaurantPrefixTable.setPrefix(prefix);
		type = new TableType();
		// Use the existing data in database
		try {
			type = (TableType) DaoServicesFactory.getTableTypesDao().findByPrimaryKey(1L);
		} catch (MdoException e) {
			fail("Could not found the type.");
		}
		restaurantPrefixTable.setType(type);
		((Restaurant) newBean).addPrefixTableName(restaurantPrefixTable);

		restaurantReductionTable = new RestaurantReductionTable();
		restaurantReductionTable.setType(type);
		restaurantReductionTable.setMinAmount(BigDecimal.TEN.add(BigDecimal.ONE));
		restaurantReductionTable.setValue(BigDecimal.TEN.multiply(BigDecimal.TEN));
		((Restaurant) newBean).addReductionTable(restaurantReductionTable);

		restaurantVatTableType = new RestaurantVatTableType();
		restaurantVatTableType.setType(type);
		vat = new ValueAddedTax();
		vat.setId(2L);
		restaurantVatTableType.setVat(vat);
		((Restaurant) newBean).addVatTableType(restaurantVatTableType);

		restaurantVat = new RestaurantValueAddedTax();
		valueAddedTax = new ValueAddedTax();
		valueAddedTax.setId(2L);
		restaurantVat.setVat(valueAddedTax);
		((Restaurant) newBean).addVat(restaurantVat);
		list.add(newBean);

		return list;
	}

	@Override
	public void doFindByUniqueKey() {
		// DefaultDaoServicesTestCase.RESTAURANT_FIRST_REFERENCE was created at
		// HSQLDB startup
		try {
			IMdoBean bean = this.getInstance().findByUniqueKey(DefaultDaoServicesTestCase.RESTAURANT_FIRST_REFERENCE);
			assertNotNull("Restaurant must not be null", bean);
			assertTrue("IMdoBean must be instance of " + Restaurant.class, bean instanceof Restaurant);
			Restaurant castedBean = (Restaurant) bean;
			assertNotNull("Restaurant Reference must not be null", castedBean.getReference());
			assertEquals("Restaurant Reference must be equals to unique key", DefaultDaoServicesTestCase.RESTAURANT_FIRST_REFERENCE, castedBean.getReference());
			assertFalse("Restaurant must not be deleted", castedBean.isDeleted());

			IRestaurantsDao restaurantDao = (IRestaurantsDao) this.getInstance();
			bean = restaurantDao.findByReference(DefaultDaoServicesTestCase.RESTAURANT_FIRST_REFERENCE);
			assertNotNull("Restaurant must not be null", bean);
			assertTrue("IMdoBean must be instance of " + Restaurant.class, bean instanceof Restaurant);
			castedBean = (Restaurant) bean;
			assertNotNull("Restaurant Reference must not be null", castedBean.getReference());
			assertEquals("Restaurant Reference must be equals to unique key", DefaultDaoServicesTestCase.RESTAURANT_FIRST_REFERENCE, castedBean.getReference());
			assertFalse("Restaurant must not be deleted", castedBean.isDeleted());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Override
	public void doUpdate() {
		IMdoBean newBean = null;
		Date registrationDate = Calendar.getInstance().getTime();
		String reference = Long.toString(++basicRestaurantReference);
		String name = "Kim-San 78";
		String addressRoad = "3 place de la Piscine";
		String addressZip = "95 334";
		String addressCity = "Versaille";
		String phone = "0130321200";
		String vatRef = "1234567890";
		String visaRef = "A1Z2E3R4T5Y6";
		String tripleDESKey = "1A2B3C4D5E6F";
		boolean vatByTakeaway = true;
		// Use the existing data in database
		HashSet<RestaurantPrefixTable> prefixTableNames = new HashSet<RestaurantPrefixTable>();
		Set<RestaurantReductionTable> reductionTables = new HashSet<RestaurantReductionTable>();
		Set<RestaurantVatTableType> vatTableTypes = new HashSet<RestaurantVatTableType>();
		// Use the existing data in database
		MdoTableAsEnum specificRound = new MdoTableAsEnum();
		try {
			specificRound = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(new Long(2));
		} catch (MdoException e) {
			fail("Could not found the Specific Round.");
		}
		TableType defaultTableType = new TableType();
		defaultTableType.setId(2L);
		Set<RestaurantValueAddedTax> vats = new HashSet<RestaurantValueAddedTax>();
		ValueAddedTax orderLineDefaultVat = new ValueAddedTax();
		orderLineDefaultVat.setId(1L);
		newBean = createNewBean(registrationDate, reference, name, addressRoad, addressZip, addressCity, phone, vatRef, visaRef, tripleDESKey, vatByTakeaway,
				specificRound, defaultTableType, prefixTableNames, reductionTables, vats, vatTableTypes, orderLineDefaultVat);

		RestaurantPrefixTable restaurantPrefixTable = new RestaurantPrefixTable();
		// Use the existing data in database
		MdoTableAsEnum prefix = new MdoTableAsEnum();
		try {
			prefix = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(2L);
		} catch (MdoException e) {
			fail("Could not found the prefix.");
		}
		restaurantPrefixTable.setPrefix(prefix);
		TableType type = new TableType();
		// Use the existing data in database
		try {
			type = (TableType) DaoServicesFactory.getTableTypesDao().findByPrimaryKey(2L);
		} catch (MdoException e) {
			fail("Could not found the type.");
		}
		restaurantPrefixTable.setType(type);
		((Restaurant) newBean).addPrefixTableName(restaurantPrefixTable);

		RestaurantReductionTable restaurantReductionTable = new RestaurantReductionTable();
		restaurantReductionTable.setType(type);
		restaurantReductionTable.setMinAmount(BigDecimal.TEN.add(BigDecimal.ONE));
		restaurantReductionTable.setValue(BigDecimal.TEN.multiply(BigDecimal.TEN));
		((Restaurant) newBean).addReductionTable(restaurantReductionTable);

		RestaurantVatTableType restaurantVatTableType = new RestaurantVatTableType();
		restaurantVatTableType.setType(type);
		ValueAddedTax vat = new ValueAddedTax();
		vat.setId(1L);
		restaurantVatTableType.setVat(vat);
		((Restaurant) newBean).addVatTableType(restaurantVatTableType);

		RestaurantValueAddedTax restaurantVat = new RestaurantValueAddedTax();
		ValueAddedTax valueAddedTax = new ValueAddedTax();
		valueAddedTax.setId(1L);
		restaurantVat.setVat(valueAddedTax);
		((Restaurant) newBean).addVat(restaurantVat);
		restaurantVat = new RestaurantValueAddedTax();
		valueAddedTax = new ValueAddedTax();
		valueAddedTax.setId(2L);
		restaurantVat.setVat(valueAddedTax);
		((Restaurant) newBean).addVat(restaurantVat);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + Restaurant.class, beanToBeUpdated instanceof Restaurant);
			Restaurant castedBean = (Restaurant) beanToBeUpdated;
			assertNotNull("Restaurant name must not be null", castedBean.getName());
			assertEquals("Restaurant name must be equals to the inserted value", name, castedBean.getName());
			assertNotNull("Restaurant prefixeTakeawayNames must not be null", castedBean.getPrefixTableNames());
			assertEquals("Check Restaurant prefixeTakeawayNames size", prefixTableNames.size(), castedBean.getPrefixTableNames().size());
			assertNotNull("Restaurant vats must not be null", castedBean.getVats());
			assertEquals("Check Restaurant vats size", vats.size(), castedBean.getVats().size());
			assertNotNull("Restaurant ReductionTables must not be null", castedBean.getReductionTables());
			assertEquals("Check Restaurant ReductionTables size", reductionTables.size(), castedBean.getReductionTables().size());
			assertNotNull("Restaurant VatTableTypes must not be null", castedBean.getVatTableTypes());
			assertEquals("Check Restaurant VatTableTypes size", vatTableTypes.size(), castedBean.getVatTableTypes().size());
			assertFalse("Restaurant must not be deleted", castedBean.isDeleted());
			// Update the created bean
			castedBean.setName("E");
			castedBean.getPrefixTableNames().clear();
			restaurantPrefixTable = new RestaurantPrefixTable();
			// Use the existing data in database
			prefix = new MdoTableAsEnum();
			prefix.setId(2L);
			restaurantPrefixTable.setPrefix(prefix);
			type = new TableType();
			type.setId(1L);
			restaurantPrefixTable.setType(type);
			castedBean.addPrefixTableName(restaurantPrefixTable);
			castedBean.getVats().clear();
			castedBean.addVat(restaurantVat);
			castedBean.setDeleted(true);
			this.getInstance().update(castedBean);
			// Reload the modified bean
			Restaurant updatedBean = (Restaurant) createNewBean();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertNotNull("Restaurant name must not be null", updatedBean.getName());
			assertEquals("Restaurant name must be equals to updated value", castedBean.getName(), updatedBean.getName());
			assertTrue("Restaurant prefixeTakeawayNames must not be empty", !updatedBean.getPrefixTableNames().isEmpty());
			assertNotNull("Restaurant vats must not be null", updatedBean.getVats());
			assertEquals("Check Restaurant vats size", vats.size(), updatedBean.getVats().size());
			assertTrue("Restaurant must be deleted", updatedBean.isDeleted());
			this.getInstance().delete(updatedBean);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Override
	public void doUpdateFieldsAndDeleteByKeysSpecific() {
		IMdoBean newBean = null;
		Date registrationDate = Calendar.getInstance().getTime();
		String reference = Long.toString(++basicRestaurantReference);
		String name = "Kim-San 78";
		String addressRoad = "3 place de la Piscine";
		String addressZip = "95 334";
		String addressCity = "Versaille";
		String phone = "0130321200";
		String vatRef = "1234567890";
		String visaRef = "A1Z2E3R4T5Y6";
		String tripleDESKey = "1A2B3C4D5E6F";
		boolean vatByTakeaway = true;
		// Use the existing data in database
		HashSet<RestaurantPrefixTable> prefixTableNames = new HashSet<RestaurantPrefixTable>();
		Set<RestaurantReductionTable> reductionTables = new HashSet<RestaurantReductionTable>();
		// Use the existing data in database
		MdoTableAsEnum specificRound = new MdoTableAsEnum();
		try {
			specificRound = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(new Long(2));
		} catch (MdoException e) {
			fail("Could not found the Specific Round.");
		}
		TableType defaultTableType = new TableType();
		defaultTableType.setId(2L);
		Set<RestaurantValueAddedTax> vats = new HashSet<RestaurantValueAddedTax>();
		Set<RestaurantVatTableType> vatTableTypes = new HashSet<RestaurantVatTableType>();
		ValueAddedTax orderLineDefaultVat = new ValueAddedTax();
		orderLineDefaultVat.setId(1L);
		newBean = createNewBean(registrationDate, reference, name, addressRoad, addressZip, addressCity, phone, vatRef, visaRef, tripleDESKey, vatByTakeaway,
				specificRound, defaultTableType, prefixTableNames, reductionTables, vats, vatTableTypes, orderLineDefaultVat);

		RestaurantPrefixTable restaurantPrefixTable = new RestaurantPrefixTable();
		// Use the existing data in database
		MdoTableAsEnum prefix = new MdoTableAsEnum();
		try {
			prefix = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(2L);
		} catch (MdoException e) {
			fail("Could not found the prefix.");
		}
		restaurantPrefixTable.setPrefix(prefix);
		TableType type = new TableType();
		// Use the existing data in database
		try {
			type = (TableType) DaoServicesFactory.getTableTypesDao().findByPrimaryKey(2L);
		} catch (MdoException e) {
			fail("Could not found the type.");
		}
		restaurantPrefixTable.setType(type);
		((Restaurant) newBean).addPrefixTableName(restaurantPrefixTable);

		RestaurantReductionTable restaurantReductionTable = new RestaurantReductionTable();
		restaurantReductionTable.setType(type);
		restaurantReductionTable.setMinAmount(BigDecimal.TEN.add(BigDecimal.ONE));
		restaurantReductionTable.setValue(BigDecimal.TEN.multiply(BigDecimal.TEN));
		((Restaurant) newBean).addReductionTable(restaurantReductionTable);

		RestaurantValueAddedTax vat = new RestaurantValueAddedTax();
		ValueAddedTax valueAddedTax = new ValueAddedTax();
		valueAddedTax.setId(1L);
		vat.setVat(valueAddedTax);
		((Restaurant) newBean).addVat(vat);
		vat = new RestaurantValueAddedTax();
		valueAddedTax = new ValueAddedTax();
		valueAddedTax.setId(2L);
		vat.setVat(valueAddedTax);
		((Restaurant) newBean).addVat(vat);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + Restaurant.class, beanToBeUpdated instanceof Restaurant);
			Restaurant castedBean = (Restaurant) beanToBeUpdated;
			assertNotNull("Restaurant name must not be null", castedBean.getName());
			assertEquals("Restaurant name must be equals to the inserted value", name, castedBean.getName());
			assertNotNull("Restaurant prefixeTakeawayNames must not be null", castedBean.getPrefixTableNames());
			assertEquals("Check Restaurant prefixeTakeawayNames size", prefixTableNames.size(), castedBean.getPrefixTableNames().size());
			assertNotNull("Restaurant vats must not be null", castedBean.getVats());
			assertEquals("Check Restaurant vats size", vats.size(), castedBean.getVats().size());
			assertFalse("Restaurant must not be deleted", castedBean.isDeleted());

			// Update the created bean
			Map<String, Object> fields = new HashMap<String, Object>();
			Map<String, Object> keys = new HashMap<String, Object>();
			castedBean.setAddressCity("addressCity");
			castedBean.setAddressRoad("addressRoad");
			castedBean.setAddressZip("addressZip");
			castedBean.setName("name");
			castedBean.setPhone("phone");
			castedBean.setReference("reference");
			castedBean.setRegistrationDate(new Date());
			castedBean.setTripleDESKey("tripleDESKey");
			castedBean.setVatRef("vatRef");
			castedBean.setVisaRef("visaRef");
			fields.put("addressCity", castedBean.getAddressCity());
			fields.put("addressRoad", castedBean.getAddressRoad());
			fields.put("addressZip", castedBean.getAddressZip());
			fields.put("name", castedBean.getName());
			fields.put("phone", castedBean.getPhone());
			fields.put("reference", castedBean.getReference());
			fields.put("registrationDate", castedBean.getRegistrationDate());
			fields.put("tripleDESKey", castedBean.getTripleDESKey());
			fields.put("vatRef", castedBean.getVatRef());
			fields.put("visaRef", castedBean.getVisaRef());
			keys.put("id", castedBean.getId());
			this.getInstance().updateFieldsByKeys(fields, keys);
			// Reload the modified bean
			Restaurant updatedBean = (Restaurant) createNewBean();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertEquals("Check updated fields ", castedBean.getAddressCity(), updatedBean.getAddressCity());
			assertEquals("Check updated fields ", castedBean.getAddressRoad(), updatedBean.getAddressRoad());
			assertEquals("Check updated fields ", castedBean.getAddressZip(), updatedBean.getAddressZip());
			assertEquals("Check updated fields ", castedBean.getName(), updatedBean.getName());
			assertEquals("Check updated fields ", castedBean.getPhone(), updatedBean.getPhone());
			assertEquals("Check updated fields ", castedBean.getReference(), updatedBean.getReference());
//			assertEquals("Check updated fields ", castedBean.getRegistrationDate(), updatedBean.getRegistrationDate());
			assertEquals("Check updated fields ", castedBean.getTripleDESKey(), updatedBean.getTripleDESKey());
			assertEquals("Check updated fields ", castedBean.getVatRef(), updatedBean.getVatRef());
			assertEquals("Check updated fields ", castedBean.getVisaRef(), updatedBean.getVisaRef());

			// Delete the bean by keys
			// Take the fields as keys
			try {
				super.doDeleteByKeysSpecific(updatedBean, keys, true);
			} catch (Exception e) {
				// We Have to delete following tables in the following order deleting the table t_restaurant.
				// 1) t_user_restaurant
				// 2) t_restaurant_vat
				// 3) t_restaurant_prefix_table
				// 4) t_restaurant_reduction_table
				// 5) t_user_authentication-->t_dinner_table, t_user_locale
				// 6) t_printing_information-->t_printing_information_language
				// 7) t_revenue-->t_revenue_vat, t_revenue_cashing
				// 8) t_product-->t_product_category, t_product_language, t_order_line, t_product_sold
				// 9) t_product_special_code-->t_product_special_code_language, t_order_line,
				// 10) t_dinner_table-->t_table_credit, t_table_bill, t_table_vat, t_order_line, t_table_cashing, t_cashing_type
				// 11) t_credit-->t_order_line
				Object parentId = keys.get("id");
				Map<String, Object> childrenKeys = new HashMap<String, Object>();
				childrenKeys.put("restaurant.id", parentId);
				super.doDeleteByKeysSpecific(RestaurantPrefixTable.class, childrenKeys);
				super.doDeleteByKeysSpecific(RestaurantReductionTable.class, childrenKeys);
				super.doDeleteByKeysSpecific(RestaurantValueAddedTax.class, childrenKeys);
				super.doDeleteByKeysSpecific(RestaurantVatTableType.class, childrenKeys);
				super.doDeleteByKeysSpecific(updatedBean, keys);
			}
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + " " + e.getMessage());
		}
	}
	
	public void testFindRestaurantsByUser() {

		IRestaurantsDao dao = (IRestaurantsDao) this.getInstance();

		// Test with data from database
		List<IMdoBean> list;
		try {
			list = dao.findRestaurants(1L);
			assertNotNull("UserRestaurants list not be null", list);
			assertFalse("UserRestaurants list must not be empty", list.isEmpty());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	public void testUpdateNotModifiedProductSpecialCodesList() {
		try {
			Restaurant restaurant = (Restaurant) this.getInstance().findByPrimaryKey(super.primaryKey);
			assertNotNull("Restaurant not null", restaurant);
			assertNotNull("List of Product Special Code not null", restaurant.getProductSpecialCodes());
			assertFalse("List of Product Special Code not empty", restaurant.getProductSpecialCodes().isEmpty());
			String backupedName = restaurant.getName();
			String newName = "restaurant.getName()";
			restaurant.setName(newName);
			this.getInstance().update(restaurant);
			restaurant = (Restaurant) this.getInstance().findByPrimaryKey(super.primaryKey);
			assertNotNull("Restaurant not null", restaurant);
			assertNotNull("List of Product Special Code not null", restaurant.getProductSpecialCodes());
			assertFalse("List of Product Special Code not empty", restaurant.getProductSpecialCodes().isEmpty());
			assertEquals("Check restaurant name", newName, restaurant.getName());
			assertFalse("Restaurant name is changed", backupedName.equals(restaurant.getName()));
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	public void testGetDefaultTableType() {
		try {
			IRestaurantsDao dao = (IRestaurantsDao) this.getInstance();
			TableType type = dao.getDefaultTableType(1L);
			assertNotNull("Checking DefaultTableType", type);
			assertNotNull("Checking DefaultTableType", type.getCode());
			assertNotNull("Checking DefaultTableType", type.getCode().getLanguageKeyLabel());
			assertNotNull("Checking DefaultTableType", type.getCode().getName());
			assertNotNull("Checking DefaultTableType", type.getCode().getType());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	private IMdoBean createNewBean(Date registrationDate, String reference, String name, String addressRoad, String addressZip, String addressCity, String phone, String vatRef,
			String visaRef, String tripleDESKey, boolean vatByTakeaway, MdoTableAsEnum specificRound,
			TableType defaultTableType, Set<RestaurantPrefixTable> prefixTableNames, Set<RestaurantReductionTable> reductionTables, 
			Set<RestaurantValueAddedTax> vats, Set<RestaurantVatTableType> vatTableTypes, ValueAddedTax vat) {
		Restaurant newBean = new Restaurant();
		newBean.setRegistrationDate(registrationDate);
		newBean.setReference(reference);
		newBean.setName(name);
		newBean.setAddressRoad(addressRoad);
		newBean.setAddressZip(addressZip);
		newBean.setAddressCity(addressCity);
		newBean.setPhone(phone);
		newBean.setVatRef(vatRef);
		newBean.setVisaRef(visaRef);
		newBean.setTripleDESKey(tripleDESKey);
		newBean.setVatByTakeaway(vatByTakeaway);
		newBean.setSpecificRound(specificRound);
		newBean.setDefaultTableType(defaultTableType);
		newBean.setPrefixTableNames(prefixTableNames);
		newBean.setReductionTables(reductionTables);
		newBean.setVats(vats);
		newBean.setVatTableTypes(vatTableTypes);
		newBean.setVat(vat);
		return newBean;
	}

}
