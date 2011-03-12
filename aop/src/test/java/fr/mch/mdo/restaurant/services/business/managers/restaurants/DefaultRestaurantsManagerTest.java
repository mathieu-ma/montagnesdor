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
import fr.mch.mdo.restaurant.dto.beans.RestaurantValueAddedTaxDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantsManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.TableTypeDto;
import fr.mch.mdo.restaurant.dto.beans.ValueAddedTaxDto;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.DefaultAdministrationManagerTest;
import fr.mch.mdo.restaurant.services.business.managers.DefaultMdoTableAsEnumsManager;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;
import fr.mch.mdo.restaurant.services.business.managers.tables.DefaultTableTypesManager;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultRestaurantsManagerTest extends DefaultAdministrationManagerTest {
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
		BigDecimal takeawayBasicReduction = new BigDecimal(15);
		BigDecimal takeawayMinAmountReduction = new BigDecimal(10);
		MdoTableAsEnumDto specificRound = new MdoTableAsEnumDto();
		specificRound.setId(2L);
		Set<RestaurantPrefixTableDto> prefixTableNames = new HashSet<RestaurantPrefixTableDto>();
		Set<RestaurantValueAddedTaxDto> vats = null;
		RestaurantDto restaurant = (RestaurantDto) createNewBean(registrationDate, reference, name, addressRoad, addressZip, addressCity, phone, vatRef, visaRef, tripleDESKey,
				vatByTakeaway, takeawayBasicReduction, takeawayMinAmountReduction, specificRound, prefixTableNames, vats);

		RestaurantPrefixTableDto restaurantPrefixTable = new RestaurantPrefixTableDto();
		MdoTableAsEnumDto prefix = new MdoTableAsEnumDto();
		// Use the existing data in database
		try {
			prefix = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(1L, userContext, true);
		} catch (MdoException e) {
			fail("Could not found the prefix.");
		}
		restaurantPrefixTable.setPrefix(prefix);
		TableTypeDto type = new TableTypeDto();
		// Use the existing data in database
		try {
			type = (TableTypeDto) DefaultTableTypesManager.getInstance().findByPrimaryKey(1L, userContext, true);
		} catch (MdoException e) {
			fail("Could not found the type.");
		}
		restaurantPrefixTable.setType(type);
		prefixTableNames.add(restaurantPrefixTable);
//		restaurant.setPrefixTableNames(prefixTableNames);

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
		BigDecimal takeawayBasicReduction = new BigDecimal(15);
		BigDecimal takeawayMinAmountReduction = new BigDecimal(10);
		MdoTableAsEnumDto specificRound = new MdoTableAsEnumDto();
		specificRound.setId(3L);
		Set<RestaurantPrefixTableDto> prefixTableNames = new HashSet<RestaurantPrefixTableDto>();
		Set<RestaurantValueAddedTaxDto> vats = new HashSet<RestaurantValueAddedTaxDto>();
		newBean = createNewBean(registrationDate, reference, name, addressRoad, addressZip, addressCity, phone, vatRef, visaRef, tripleDESKey, vatByTakeaway,
				takeawayBasicReduction, takeawayMinAmountReduction, specificRound, prefixTableNames, vats);

		RestaurantPrefixTableDto restaurantPrefixTable = new RestaurantPrefixTableDto();
		MdoTableAsEnumDto prefix = new MdoTableAsEnumDto();
		// Use the existing data in database
		try {
			prefix = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(1L, userContext);
		} catch (MdoException e) {
			fail("Could not found the prefix.");
		}
		restaurantPrefixTable.setPrefix(prefix);
		TableTypeDto type = new TableTypeDto();
		// Use the existing data in database
		try {
			type = (TableTypeDto) DefaultTableTypesManager.getInstance().findByPrimaryKey(1L, userContext, true);
		} catch (MdoException e) {
			fail("Could not found the type.");
		}
		restaurantPrefixTable.setType(type);
		prefixTableNames.add(restaurantPrefixTable);

		RestaurantValueAddedTaxDto vat = new RestaurantValueAddedTaxDto();
		ValueAddedTaxDto valueAddedTax = new ValueAddedTaxDto();
		valueAddedTax.setId(1L);
		vat.setVat(valueAddedTax);
		vats.add(vat);
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
		takeawayBasicReduction = new BigDecimal(15);
		takeawayMinAmountReduction = new BigDecimal(10);
		prefixTableNames = new HashSet<RestaurantPrefixTableDto>();
		// Use the existing data in database
		try {
			specificRound = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(new Long(2), userContext);
		} catch (MdoException e) {
			fail("Could not found the Specific Round.");
		}
		vats = new HashSet<RestaurantValueAddedTaxDto>();
		newBean = createNewBean(registrationDate, reference, name, addressRoad, addressZip, addressCity, phone, vatRef, visaRef, tripleDESKey, vatByTakeaway,
				takeawayBasicReduction, takeawayMinAmountReduction, specificRound, prefixTableNames, vats);

		restaurantPrefixTable = new RestaurantPrefixTableDto();
		// Use the existing data in database
		prefix = new MdoTableAsEnumDto();
		// Use the existing data in database
		try {
			prefix = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(1L, userContext);
		} catch (MdoException e) {
			fail("Could not found the prefix.");
		}
		restaurantPrefixTable.setPrefix(prefix);
		type = new TableTypeDto();
		// Use the existing data in database
		try {
			type = (TableTypeDto) DefaultTableTypesManager.getInstance().findByPrimaryKey(1L, userContext, true);
		} catch (MdoException e) {
			fail("Could not found the type.");
		}
		restaurantPrefixTable.setType(type);
		prefixTableNames.add(restaurantPrefixTable);

		vat = new RestaurantValueAddedTaxDto();
		valueAddedTax = new ValueAddedTaxDto();
		valueAddedTax.setId(2L);
		vat.setVat(valueAddedTax);
		vats.add(vat);

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
		BigDecimal takeawayBasicReduction = new BigDecimal(15);
		BigDecimal takeawayMinAmountReduction = new BigDecimal(10);
		// Use the existing data in database
		HashSet<RestaurantPrefixTableDto> prefixTableNames = new HashSet<RestaurantPrefixTableDto>();
		// Use the existing data in database
		MdoTableAsEnumDto specificRound = new MdoTableAsEnumDto();
		try {
			specificRound = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(new Long(2), userContext);
		} catch (MdoException e) {
			fail("Could not found the Specific Round.");
		}
		Set<RestaurantValueAddedTaxDto> vats = new HashSet<RestaurantValueAddedTaxDto>();
		newBean = createNewBean(registrationDate, reference, name, addressRoad, addressZip, addressCity, phone, vatRef, visaRef, tripleDESKey, vatByTakeaway,
				takeawayBasicReduction, takeawayMinAmountReduction, specificRound, prefixTableNames, vats);

		RestaurantPrefixTableDto restaurantPrefixTable = new RestaurantPrefixTableDto();
		// Use the existing data in database
		MdoTableAsEnumDto prefix = new MdoTableAsEnumDto();
		try {
			prefix = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(new Long(2), userContext);
		} catch (MdoException e) {
			fail("Could not found the prefix.");
		}
		restaurantPrefixTable.setPrefix(prefix);
		TableTypeDto type = new TableTypeDto();
		// Use the existing data in database
		try {
			type = (TableTypeDto) DefaultTableTypesManager.getInstance().findByPrimaryKey(2L, userContext, true);
		} catch (MdoException e) {
			fail("Could not found the type.");
		}
		restaurantPrefixTable.setType(type);
		prefixTableNames.add(restaurantPrefixTable);

		RestaurantValueAddedTaxDto vat = new RestaurantValueAddedTaxDto();
		ValueAddedTaxDto valueAddedTax = new ValueAddedTaxDto();
		valueAddedTax.setId(1L);
		vat.setVat(valueAddedTax);
		vats.add(vat);
		vat = new RestaurantValueAddedTaxDto();
		valueAddedTax = new ValueAddedTaxDto();
		valueAddedTax.setId(2L);
		vat.setVat(valueAddedTax);
		vats.add(vat);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean, userContext);
			assertTrue("IMdoBean must be instance of " + RestaurantDto.class, beanToBeUpdated instanceof RestaurantDto);
			RestaurantDto castedBean = (RestaurantDto) beanToBeUpdated;
			assertNotNull("Restaurant name must not be null", castedBean.getName());
			assertEquals("Restaurant name must be equals to the inserted value", name, castedBean.getName());
//			assertNotNull("Restaurant prefixeTakeawayNames must not be null", castedBean.getPrefixTableNames());
//			assertEquals("Check Restaurant prefixeTakeawayNames size", prefixTableNames.size(), castedBean.getPrefixTableNames().size());
			assertNotNull("Restaurant vats must not be null", castedBean.getVats());
			assertEquals("Check Restaurant vats size", vats.size(), castedBean.getVats().size());
			// Update the created bean
			castedBean.setName("E");
//			castedBean.getPrefixTableNames().clear();
			restaurantPrefixTable = new RestaurantPrefixTableDto();
			// Use the existing data in database
			prefix = new MdoTableAsEnumDto();
			prefix.setId(2L);
			restaurantPrefixTable.setPrefix(prefix);
			type = new TableTypeDto();
			type.setId(1L);
			restaurantPrefixTable.setType(type);
//			castedBean.getPrefixTableNames().add(restaurantPrefixTable);
			vat = castedBean.getVats().iterator().next();
			castedBean.getVats().clear();
			castedBean.getVats().add(vat);
			this.getInstance().update(castedBean, userContext);
			// Reload the modified bean
			RestaurantDto updatedBean = (RestaurantDto) createNewBean();
			updatedBean.setId(castedBean.getId());
			updatedBean = (RestaurantDto) this.getInstance().load(updatedBean, userContext, true);
			assertNotNull("Restaurant name must not be null", updatedBean.getName());
			assertEquals("Restaurant name must be equals to updated value", castedBean.getName(), updatedBean.getName());
//			assertTrue("Restaurant prefixeTakeawayNames must not be empty", !updatedBean.getPrefixTableNames().isEmpty());
			assertNotNull("Restaurant vats must not be null", updatedBean.getVats());
			assertEquals("Check Restaurant vats size", castedBean.getVats().size(), updatedBean.getVats().size());
			this.getInstance().delete(updatedBean, userContext);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getLocalizedMessage());
		}
	}

	@Override
	public void doProcessList() {
		RestaurantsManagerViewBean viewBean = new RestaurantsManagerViewBean();
		try {
			this.getInstance().processList(viewBean, DefaultAdministrationManagerTest.userContext, true);
			assertNotNull("Main list not be null", viewBean.getList());
			assertFalse("Main list not be empty", viewBean.getList().isEmpty());
			assertNotNull("Specific rounds list not be null", viewBean.getSpecificRounds());
			assertFalse("Specific rounds list not be empty", viewBean.getSpecificRounds().isEmpty());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getLocalizedMessage());
		}
	}

	private IMdoDtoBean createNewBean(Date registrationDate, String reference, String name, String addressRoad, String addressZip, String addressCity, String phone, String vatRef,
			String visaRef, String tripleDESKey, Boolean vatByTakeaway, BigDecimal takeawayBasicReduction, BigDecimal takeawayMinAmountReduction, MdoTableAsEnumDto specificRound,
			Set<RestaurantPrefixTableDto> prefixTableNames, Set<RestaurantValueAddedTaxDto> vats) {

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
		newBean.setTakeawayBasicReduction(takeawayBasicReduction);
		newBean.setTakeawayMinAmountReduction(takeawayMinAmountReduction);
		newBean.setSpecificRound(specificRound);
//		newBean.setPrefixTableNames(prefixTableNames);
		newBean.setVats(vats);
		return newBean;
	}
}
