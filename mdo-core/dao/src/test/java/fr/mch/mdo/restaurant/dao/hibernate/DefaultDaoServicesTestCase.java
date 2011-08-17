package fr.mch.mdo.restaurant.dao.hibernate;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.test.MdoTestCase;

public abstract class DefaultDaoServicesTestCase extends MdoDaoBasicTestCase 
{

	protected final static String CREDIT_FIRST_REFERENCE = "123456789";

	public static final String RESTAURANT_FIRST_REFERENCE = "10203040506070";
	public static final Long ENUM_USER_TITLE_ID_0 = 28L;
	public static final Long ENUM_VAT_ID_0 = 31L;

	protected NumberFormat decimalFormat = DecimalFormat.getInstance();
	{
		decimalFormat.setMaximumFractionDigits(4);
	}

	private IMdoDaoBean insertedBeanToBeDeleted = null;

	/** This value MUST exist in database. */
	protected Long primaryKey = 1L;

	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultDaoServicesTestCase(String testName) {
		super(testName);
	}

	protected abstract IDaoServices getInstance();

	protected abstract IMdoBean createNewBean();

	protected abstract List<IMdoBean> createListBeans();

	public void testAll() {
		this.doInsert();
		this.doUpdate();
		this.doLoad();
		this.doFindByPrimaryKey();
		this.doFindByUniqueKey();
		this.doFindAll();
		this.doDelete();
	}

	public void doDelete() {
		try {
			// Create bean to be deleted
			assertNotNull("Inserted bean must not be null", insertedBeanToBeDeleted);
			// Delete the created bean
			getInstance().delete(insertedBeanToBeDeleted);
			IMdoBean deletedBean = getInstance().findByPrimaryKey(insertedBeanToBeDeleted.getId());
			assertNull("deletedBean must be null", deletedBean);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	public void doFindAll() {
		try {
			List<IMdoBean> beans = createListBeans();
			assertNotNull("beans must not be null", beans);
			// assertFalse("beans must not be empty", beans.isEmpty());
			if (!beans.isEmpty()) {
				for (IMdoBean bean : beans) {
					assertTrue("The bean must be an instance of IMdoDaoBean", bean instanceof IMdoDaoBean);
					// Create
					getInstance().insert(bean);
				}
				List<IMdoBean> list = getInstance().findAll();
				assertNotNull("list must not be null", list);
				assertFalse("list must not be empty", list.isEmpty());
				assertTrue("The findAll list size must be greateror equals than beans list size", list.size() >= beans.size());

				for (IMdoBean bean : beans) {
					boolean beanInFindAllList = false;
					int indexList = 0;
					for (IMdoBean iMdoBean : list) {
						assertTrue("The iMdoBean must be an instance of IMdoDaoBean " + indexList, iMdoBean instanceof IMdoDaoBean);
						indexList++;
						if (iMdoBean.equals(bean)) {
							beanInFindAllList = true;
							break;
						}
					}
					assertTrue("bean must be in the findAllList", beanInFindAllList);
					// Remove after created
					getInstance().delete(bean);
				}
			}
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	/**
	 * This method tests the findByPrimaryKey method of DAO part.
	 */
	public void doFindByPrimaryKey(boolean... lazy) {
		try {
			IMdoBean bean = getInstance().findByPrimaryKey(primaryKey);
			assertNotNull("Bean is not null", bean);
			assertTrue("The bean must be an instance of IMdoDaoBean", bean instanceof IMdoDaoBean);
			IMdoDaoBean castedBean = (IMdoDaoBean) bean;
			assertNotNull("Bean id is not null", castedBean.getId());
			assertEquals("Bean id is equals to the searched id", primaryKey, castedBean.getId());
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
		if (lazy.length == 0) {
			doFindByPrimaryKey(false);
		}
	}

	/**
	 * This method tests the load method of DAO part.
	 */
	public void doLoad() {
		try {
			IMdoBean bean = createNewBean();
			assertTrue("The bean must be an instance of IMdoDaoBean", bean instanceof IMdoDaoBean);
			IMdoDaoBean beanWithIdSet = (IMdoDaoBean) bean;
			assertNull("Bean id is null", beanWithIdSet.getId());
			beanWithIdSet.setId(primaryKey);
			getInstance().load(beanWithIdSet);
			assertNotNull("bean must not be null", bean);
			assertNotNull("Bean id is not null", beanWithIdSet.getId());
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	/**
	 * This method tests the insert method of DAO part.
	 */
	public void doInsert() {
		try {
			IMdoBean bean = createNewBean();
			assertNotNull("Bean must not be null", bean);
			assertTrue("The bean must be an instance of IMdoDaoBean", bean instanceof IMdoDaoBean);
			insertedBeanToBeDeleted = (IMdoDaoBean) bean;
			assertNull("Bean id is null", insertedBeanToBeDeleted.getId());
			// Be careful to not insert data already exist in database
			bean = getInstance().insert(bean);
			assertNotNull("Bean id is not null now", insertedBeanToBeDeleted.getId());
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	public abstract void doUpdate();

	public abstract void doFindByUniqueKey();
}
