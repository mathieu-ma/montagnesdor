package fr.mch.mdo.restaurant.services.business.managers.restaurants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.MdoTableAsEnumDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantPrefixTableDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantReductionTableDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantValueAddedTaxDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantVatTableTypeDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantsManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.TableTypeDto;
import fr.mch.mdo.restaurant.dto.beans.ValueAddedTaxDto;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.DefaultAdministrationManagerTest;
import fr.mch.mdo.restaurant.services.business.managers.DefaultMdoTableAsEnumsManager;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;
import fr.mch.mdo.restaurant.services.business.managers.tables.DefaultTableTypesManager;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultRestaurantsManagerTest extends DefaultAdministrationManagerTest 
{
	private static long basicRestaurantReference = Long.valueOf(DefaultAdministrationManagerTest.RESTAURANT_FIRST_REFERENCE);

	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultRestaurantsManagerTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultRestaurantsManagerTest.class);
	}

	@Override
	protected IAdministrationManager getInstance() {
		return DefaultRestaurantsManager.getInstance();
	}

	@Override
	protected IMdoDtoBean createNewBean() {
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
		MdoTableAsEnumDto specificRound = new MdoTableAsEnumDto();
		specificRound.setId(2L);
		TableTypeDto defaultTableType = new TableTypeDto();
		defaultTableType.setId(1L);
		Set<RestaurantValueAddedTaxDto> vats = null;
		Set<RestaurantPrefixTableDto> prefixTableNames = new HashSet<RestaurantPrefixTableDto>();
		Set<RestaurantReductionTableDto> reductionTables = new HashSet<RestaurantReductionTableDto>();
		Set<RestaurantVatTableTypeDto> vatTableTypes = new HashSet<RestaurantVatTableTypeDto>();
		ValueAddedTaxDto orderLineDefaultVat = new ValueAddedTaxDto();
		orderLineDefaultVat.setId(1L);
		RestaurantDto restaurant = (RestaurantDto) createNewBean(registrationDate, reference, name, addressRoad, addressZip, addressCity, phone, vatRef, visaRef, tripleDESKey,
				vatByTakeaway, specificRound, defaultTableType, vats, vatTableTypes, prefixTableNames, reductionTables, orderLineDefaultVat);

		RestaurantPrefixTableDto restaurantPrefixTable = new RestaurantPrefixTableDto();
		MdoTableAsEnumDto prefix = new MdoTableAsEnumDto();
		// Use the existing data in database
		try {
			prefix = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(1L);
		} catch (MdoException e) {
			fail("Could not found the prefix.");
		}
		restaurantPrefixTable.setPrefix(prefix);
		TableTypeDto type = new TableTypeDto();
		// Use the existing data in database
		try {
			type = (TableTypeDto) DefaultTableTypesManager.getInstance().findByPrimaryKey(1L);
		} catch (MdoException e) {
			fail("Could not found the type.");
		}
		restaurantPrefixTable.setType(type);
		prefixTableNames.add(restaurantPrefixTable);
		
		RestaurantReductionTableDto restaurantReductionTable = new RestaurantReductionTableDto();
		restaurantReductionTable.setType(type);
		restaurantReductionTable.setMinAmount(BigDecimal.TEN);
		restaurantReductionTable.setValue(BigDecimal.TEN);
		restaurant.getReductionTables().add(restaurantReductionTable);

		RestaurantVatTableTypeDto restaurantVatTableType = new RestaurantVatTableTypeDto();
		restaurantVatTableType.setType(type);
		ValueAddedTaxDto vat = new ValueAddedTaxDto();
		vat.setId(1L);
		restaurantVatTableType.setVat(vat);
		restaurant.getVatTableTypes().add(restaurantVatTableType);

		return restaurant;
	}

	@Override
	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		// First bean in list
		IMdoDtoBean newBean = null;
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
		MdoTableAsEnumDto specificRound = new MdoTableAsEnumDto();
		specificRound.setId(3L);
		TableTypeDto defaultTableType = new TableTypeDto();
		defaultTableType.setId(2L);
		Set<RestaurantValueAddedTaxDto> vats = new HashSet<RestaurantValueAddedTaxDto>();
		Set<RestaurantPrefixTableDto> prefixTableNames = new HashSet<RestaurantPrefixTableDto>();
		Set<RestaurantReductionTableDto> reductionTables = new HashSet<RestaurantReductionTableDto>();
		Set<RestaurantVatTableTypeDto> vatTableTypes = new HashSet<RestaurantVatTableTypeDto>();
		ValueAddedTaxDto orderLineDefaultVat = new ValueAddedTaxDto();
		orderLineDefaultVat.setId(1L);
		newBean = createNewBean(registrationDate, reference, name, addressRoad, addressZip, addressCity, phone, vatRef, visaRef, tripleDESKey, vatByTakeaway,
				specificRound, defaultTableType, vats, vatTableTypes, prefixTableNames, reductionTables, orderLineDefaultVat);

		RestaurantValueAddedTaxDto restaurantVat = new RestaurantValueAddedTaxDto();
		ValueAddedTaxDto valueAddedTax = new ValueAddedTaxDto();
		valueAddedTax.setId(1L);
		restaurantVat.setVat(valueAddedTax);
		vats.add(restaurantVat);
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
		// Use the existing data in database
		try {
			specificRound = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(new Long(2));
		} catch (MdoException e) {
			fail("Could not found the Specific Round.");
		}
		defaultTableType = new TableTypeDto();
		defaultTableType.setId(1L);
		vats = new HashSet<RestaurantValueAddedTaxDto>();
		reductionTables = new HashSet<RestaurantReductionTableDto>();
		vatTableTypes = new HashSet<RestaurantVatTableTypeDto>();
		prefixTableNames = null;
		orderLineDefaultVat = new ValueAddedTaxDto();
		orderLineDefaultVat.setId(1L);
		newBean = createNewBean(registrationDate, reference, name, addressRoad, addressZip, addressCity, phone, vatRef, visaRef, tripleDESKey, vatByTakeaway,
				specificRound, defaultTableType, vats, vatTableTypes, prefixTableNames, reductionTables, orderLineDefaultVat);

		restaurantVat = new RestaurantValueAddedTaxDto();
		valueAddedTax = new ValueAddedTaxDto();
		valueAddedTax.setId(2L);
		restaurantVat.setVat(valueAddedTax);
		vats.add(restaurantVat);

		RestaurantReductionTableDto restaurantReductionTable = new RestaurantReductionTableDto();
		TableTypeDto type = new TableTypeDto();
		type.setId(1L);
		restaurantReductionTable.setType(type);
		restaurantReductionTable.setMinAmount(BigDecimal.TEN);
		restaurantReductionTable.setValue(BigDecimal.TEN);
		((RestaurantDto) newBean).getReductionTables().add(restaurantReductionTable);

		RestaurantVatTableTypeDto restaurantVatTableType = new RestaurantVatTableTypeDto();
		restaurantVatTableType.setType(type);
		ValueAddedTaxDto vat = new ValueAddedTaxDto();
		vat.setId(1L);
		restaurantVatTableType.setVat(vat);
		((RestaurantDto) newBean).getVatTableTypes().add(restaurantVatTableType);

		list.add(newBean);

		return list;
	}

	@Override
	public void doUpdate() {
		IMdoDtoBean newBean = null;
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
		MdoTableAsEnumDto specificRound = new MdoTableAsEnumDto();
		try {
			specificRound = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(new Long(2));
		} catch (MdoException e) {
			fail("Could not found the Specific Round.");
		}
		TableTypeDto defaultTableType = new TableTypeDto();
		defaultTableType.setId(2L);
		Set<RestaurantValueAddedTaxDto> vats = new HashSet<RestaurantValueAddedTaxDto>();
		Set<RestaurantPrefixTableDto> prefixTableNames = new HashSet<RestaurantPrefixTableDto>();
		Set<RestaurantReductionTableDto> reductionTables = new HashSet<RestaurantReductionTableDto>();
		Set<RestaurantVatTableTypeDto> vatTableTypes = new HashSet<RestaurantVatTableTypeDto>();
		ValueAddedTaxDto orderLineDefaultVat = new ValueAddedTaxDto();
		orderLineDefaultVat.setId(1L);
		newBean = createNewBean(registrationDate, reference, name, addressRoad, addressZip, addressCity, phone, vatRef, visaRef, tripleDESKey, vatByTakeaway,
				specificRound, defaultTableType, vats, vatTableTypes, prefixTableNames, reductionTables, orderLineDefaultVat);

		// VATs part
		RestaurantValueAddedTaxDto restaurantVat = new RestaurantValueAddedTaxDto();
		ValueAddedTaxDto valueAddedTax = new ValueAddedTaxDto();
		valueAddedTax.setId(1L);
		restaurantVat.setVat(valueAddedTax);
		vats.add(restaurantVat);
		restaurantVat = new RestaurantValueAddedTaxDto();
		valueAddedTax = new ValueAddedTaxDto();
		valueAddedTax.setId(2L);
		restaurantVat.setVat(valueAddedTax);
		vats.add(restaurantVat);

		RestaurantReductionTableDto restaurantReductionTable = new RestaurantReductionTableDto();
		TableTypeDto type = new TableTypeDto();
		type.setId(1L);
		restaurantReductionTable.setType(type);
		restaurantReductionTable.setMinAmount(BigDecimal.TEN);
		restaurantReductionTable.setValue(BigDecimal.TEN);
		((RestaurantDto) newBean).getReductionTables().add(restaurantReductionTable);

		RestaurantVatTableTypeDto restaurantVatTableType = new RestaurantVatTableTypeDto();
		restaurantVatTableType.setType(type);
		ValueAddedTaxDto vat = new ValueAddedTaxDto();
		vat.setId(1L);
		restaurantVatTableType.setVat(vat);
		((RestaurantDto) newBean).getVatTableTypes().add(restaurantVatTableType);

		// Prefix Table Names part
		// First
		RestaurantPrefixTableDto restaurantPrefixTable = new RestaurantPrefixTableDto();
		MdoTableAsEnumDto prefix = new MdoTableAsEnumDto();
		// Use the existing data in database
		try {
			prefix = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(1L);
		} catch (MdoException e) {
			fail("Could not found the prefix.");
		}
		restaurantPrefixTable.setPrefix(prefix);
		type = new TableTypeDto();
		// Use the existing data in database
		try {
			type = (TableTypeDto) DefaultTableTypesManager.getInstance().findByPrimaryKey(1L);
		} catch (MdoException e) {
			fail("Could not found the type.");
		}
		restaurantPrefixTable.setType(type);
		prefixTableNames.add(restaurantPrefixTable);
		// Second
		restaurantPrefixTable = new RestaurantPrefixTableDto();
		prefix = new MdoTableAsEnumDto();
		// Use the existing data in database
		prefix.setId(2L);
		restaurantPrefixTable.setPrefix(prefix);
		type = new TableTypeDto();
		// Use the existing data in database
		try {
			type = (TableTypeDto) DefaultTableTypesManager.getInstance().findByPrimaryKey(2L);
		} catch (MdoException e) {
			fail("Could not found the type.");
		}
		restaurantPrefixTable.setType(type);
		prefixTableNames.add(restaurantPrefixTable);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + RestaurantDto.class, beanToBeUpdated instanceof RestaurantDto);
			RestaurantDto castedBean = (RestaurantDto) beanToBeUpdated;
			assertNotNull("Restaurant name must not be null", castedBean.getName());
			assertEquals("Restaurant name must be equals to the inserted value", name, castedBean.getName());
			assertNotNull("Restaurant vats must not be null", castedBean.getVats());
			assertEquals("Check Restaurant vats size", vats.size(), castedBean.getVats().size());
			assertNotNull("Restaurant ReductionTables must not be null", castedBean.getReductionTables());
			assertEquals("Check Restaurant ReductionTables size", reductionTables.size(), castedBean.getReductionTables().size());
			assertNotNull("Restaurant VatTableTypes must not be null", castedBean.getVatTableTypes());
			assertEquals("Check Restaurant VatTableTypes size", vatTableTypes.size(), castedBean.getVatTableTypes().size());
			// Update the created bean
			castedBean.setName("E");
			restaurantVat = castedBean.getVats().iterator().next();
			// Id null then insert not update
			restaurantVat.setId(null);
			castedBean.getVats().clear();
			castedBean.getVats().add(restaurantVat);
			restaurantPrefixTable = castedBean.getPrefixTableNames().iterator().next();
			// Id null then insert not update
			restaurantPrefixTable.setId(null);
			castedBean.getPrefixTableNames().clear();
			castedBean.getPrefixTableNames().add(restaurantPrefixTable);
			this.getInstance().update(castedBean);
			// Reload the modified bean
			RestaurantDto updatedBean = (RestaurantDto) createNewBean();
			updatedBean.setId(castedBean.getId());
			updatedBean = (RestaurantDto) this.getInstance().load(updatedBean);
			assertNotNull("Restaurant name must not be null", updatedBean.getName());
			assertEquals("Restaurant name must be equals to updated value", castedBean.getName(), updatedBean.getName());
			assertNotNull("Restaurant vats must not be null", updatedBean.getVats());
			// Because of RestaurantDto is a bean that is not attach to Hibernate session so the collection vats is not too.
			// Then vats collection is never removed(but still updated) and the size must be the same as before updating the RestaurantDto
			assertEquals("Check Restaurant vats size", castedBean.getVats().size(), updatedBean.getVats().size());
			assertNotNull("Restaurant prefix table names must not be null", updatedBean.getPrefixTableNames());
			// Because of RestaurantDto is a bean that is not attach to Hibernate session so the collection PrefixTableNames is not too.
			// Then prefixTableNames collection is never removed(but still updated) and the size must be the same as before updating the RestaurantDto
			assertEquals("Check Restaurant prefix table names size", castedBean.getPrefixTableNames().size(), updatedBean.getPrefixTableNames().size());
			assertNotNull("Restaurant Reduction Tables must not be null", updatedBean.getReductionTables());
			// Because of RestaurantDto is a bean that is not attach to Hibernate session so the collection ReductionTables is not too.
			// Then ReductionTables collection is never removed(but still updated) and the size must be the same as before updating the RestaurantDto
			assertEquals("Check Restaurant Reduction Tables size", castedBean.getReductionTables().size(), updatedBean.getReductionTables().size());
			assertNotNull("Restaurant VatTableTypes must not be null", updatedBean.getVatTableTypes());
			// Because of RestaurantDto is a bean that is not attach to Hibernate session so the collection VatTableTypes is not too.
			// Then VatTableTypes collection is never removed(but still updated) and the size must be the same as before updating the RestaurantDto
			assertEquals("Check Restaurant VatTableTypes size", castedBean.getVatTableTypes().size(), updatedBean.getVatTableTypes().size());
			this.getInstance().delete(updatedBean);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getLocalizedMessage());
		}
	}

	@Override
	public void doProcessList() {
		RestaurantsManagerViewBean viewBean = new RestaurantsManagerViewBean();
		try {
			this.getInstance().processList(viewBean);
			assertNotNull("Main list not be null", viewBean.getList());
			assertFalse("Main list not be empty", viewBean.getList().isEmpty());
			assertNotNull("Specific rounds list not be null", viewBean.getSpecificRounds());
			assertFalse("Specific rounds list not be empty", viewBean.getSpecificRounds().isEmpty());
			assertNotNull("Prefix Table names list not be null", viewBean.getPrefixTableNames());
			assertFalse("Prefix Table names list not be empty", viewBean.getPrefixTableNames().isEmpty());
			assertNotNull("Value Added Taxes list not be null", viewBean.getValueAddedTaxes());
			assertFalse("Value Added Taxes list not be empty", viewBean.getValueAddedTaxes().isEmpty());
			assertNotNull("Table Types list not be null", viewBean.getTableTypes());
			assertFalse("Table Types list not be empty", viewBean.getTableTypes().isEmpty());
			assertNotNull("ReductionTables list not be null", viewBean.getReductionTableTypes());
			assertFalse("ReductionTables list not be empty", viewBean.getReductionTableTypes().isEmpty());
			assertNotNull("VAT Table Types list not be null", viewBean.getVatTableTypes());
			assertFalse("VAT Table Types list not be empty", viewBean.getVatTableTypes().isEmpty());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getLocalizedMessage());
		}
	}

	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof IRestaurantsManager);
		assertTrue(this.getInstance() instanceof DefaultRestaurantsManager);
	}

	public void testFindRestaurantsByUser() {
		Long userId = 1L;
		try {
			List<IMdoDtoBean> list = ((IRestaurantsManager) this.getInstance()).findRestaurantsByUser(userId);
			assertNotNull("UserRestaurants list not be null", list);
			assertFalse("UserRestaurants list must not be empty", list.isEmpty());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}	
	}
	
	public void testFindByReference() {
		Long userId = 1L;
		try {
			List<IMdoDtoBean> list = ((IRestaurantsManager) this.getInstance()).findRestaurantsByUser(userId);
			assertNotNull("UserRestaurants list not be null", list);
			assertFalse("UserRestaurants list must not be empty", list.isEmpty());
			// Get the first bean
			IMdoDtoBean returnedBean = list.get(0);
			assertTrue("IMdoBean must be instance of " + RestaurantDto.class, returnedBean instanceof RestaurantDto);
			RestaurantDto castedBean = (RestaurantDto) returnedBean;
			assertNotNull("RestaurantDto name must not be null", castedBean.getReference());
			
			IMdoDtoBean foundBean = ((IRestaurantsManager) this.getInstance()).findByReference(castedBean.getReference());
			assertNotNull("IMdoBean must not be null", foundBean);
			assertTrue("IMdoBean must be instance of " + RestaurantDto.class, foundBean instanceof RestaurantDto);
			
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
		
	}

	private IMdoDtoBean createNewBean(Date registrationDate, String reference, String name, String addressRoad, String addressZip, String addressCity, String phone, String vatRef,
			String visaRef, String tripleDESKey, boolean vatByTakeaway, MdoTableAsEnumDto specificRound,
			TableTypeDto defaultTableType, Set<RestaurantValueAddedTaxDto> vats, Set<RestaurantVatTableTypeDto> vatTableTypes, 
			Set<RestaurantPrefixTableDto> prefixTableNames, Set<RestaurantReductionTableDto> reductionTables, ValueAddedTaxDto vat) {

		RestaurantDto newBean = new RestaurantDto();
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
		newBean.setVats(vats);
		newBean.setVatTableTypes(vatTableTypes);
		newBean.setPrefixTableNames(prefixTableNames);
		newBean.setReductionTables(reductionTables);
		newBean.setVat(vat);
		return newBean;
	}
}
