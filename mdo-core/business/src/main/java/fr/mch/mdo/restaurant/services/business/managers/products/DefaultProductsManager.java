package fr.mch.mdo.restaurant.services.business.managers.products;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IBeanLabelable;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.Product;
import fr.mch.mdo.restaurant.dao.beans.ProductCategory;
import fr.mch.mdo.restaurant.dao.products.IProductsDao;
import fr.mch.mdo.restaurant.dao.products.hibernate.DefaultProductsDao;
import fr.mch.mdo.restaurant.dto.beans.IAdministrationManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.LocaleDto;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.ProductDto;
import fr.mch.mdo.restaurant.dto.beans.ProductsManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;
import fr.mch.mdo.restaurant.dto.beans.ValueAddedTaxDto;
import fr.mch.mdo.restaurant.exception.MdoBusinessException;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.AbstractAdministrationManagerLabelable;
import fr.mch.mdo.restaurant.services.business.managers.ICategoriesManager;
import fr.mch.mdo.restaurant.services.business.managers.assembler.DefaultProductsAssembler;
import fr.mch.mdo.restaurant.services.business.managers.locales.DefaultLocalesManager;
import fr.mch.mdo.restaurant.services.business.managers.locales.ILocalesManager;
import fr.mch.mdo.restaurant.services.business.managers.restaurants.DefaultRestaurantsManager;
import fr.mch.mdo.restaurant.services.business.managers.restaurants.IRestaurantsManager;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultProductsManager extends AbstractAdministrationManagerLabelable implements IProductsManager
{
	/**	rateFormat is used formatting Bigdecimal rate */
	private NumberFormat vatRateFormat;
	{
		DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance();
		dfs.setDecimalSeparator('.');
		vatRateFormat = new DecimalFormat("#.00", dfs);
	}
	private IRestaurantsManager restaurantsManager;
	private IValueAddedTaxesManager valueAddedTaxesManager;
	private ICategoriesManager categoriesManager;
	private ILocalesManager localesManager;
	
	private static class LazyHolder {
		private static IProductsManager instance = new DefaultProductsManager(
				LoggerServiceImpl.getInstance().getLogger(DefaultProductsManager.class.getName()),
				DefaultProductsDao.getInstance(), DefaultProductsAssembler.getInstance());
	}

	private DefaultProductsManager(ILogger logger, IProductsDao dao, IManagerAssembler assembler) {
		super.logger = logger;
		super.dao = dao;
		super.assembler = assembler;
		this.restaurantsManager = DefaultRestaurantsManager.getInstance();
		this.valueAddedTaxesManager = DefaultValueAddedTaxesManager.getInstance();
		this.categoriesManager = DefaultCategoriesManager.getInstance();
		this.localesManager = DefaultLocalesManager.getInstance();
	}

	/**
	 * This constructor is used by ioc
	 */
	public DefaultProductsManager() {
	}

	public static IProductsManager getInstance() {
		return LazyHolder.instance;
	}

	/**
	 * @return the restaurantsManager
	 */
	public IRestaurantsManager getRestaurantsManager() {
		return restaurantsManager;
	}

	/**
	 * @param restaurantsManager the restaurantsManager to set
	 */
	public void setRestaurantsManager(IRestaurantsManager restaurantsManager) {
		this.restaurantsManager = restaurantsManager;
	}

	/**
	 * @return the valueAddedTaxesManager
	 */
	public IValueAddedTaxesManager getValueAddedTaxesManager() {
		return valueAddedTaxesManager;
	}

	/**
	 * @param valueAddedTaxesManager the valueAddedTaxesManager to set
	 */
	public void setValueAddedTaxesManager(
			IValueAddedTaxesManager valueAddedTaxesManager) {
		this.valueAddedTaxesManager = valueAddedTaxesManager;
	}

	/**
	 * @return the categoriesManager
	 */
	public ICategoriesManager getCategoriesManager() {
		return categoriesManager;
	}

	/**
	 * @param categoriesManager the categoriesManager to set
	 */
	public void setCategoriesManager(ICategoriesManager categoriesManager) {
		this.categoriesManager = categoriesManager;
	}

	/**
	 * @return the localesManager
	 */
	public ILocalesManager getLocalesManager() {
		return localesManager;
	}

	/**
	 * @param localesManager the localesManager to set
	 */
	public void setLocalesManager(ILocalesManager localesManager) {
		this.localesManager = localesManager;
	}

	@Override
	public void processList(IAdministrationManagerViewBean viewBean, MdoUserContext userContext, boolean... lazy) throws MdoBusinessException {
		super.processList(viewBean, userContext, lazy);
		ProductsManagerViewBean productsManagerViewBean = (ProductsManagerViewBean) viewBean;
		try {
			productsManagerViewBean.setLabels(super.getLabels(userContext.getCurrentLocale()));
			productsManagerViewBean.setLanguages(localesManager.getLanguages(userContext.getCurrentLocale().getLanguageCode()));
			productsManagerViewBean.setRestaurants(restaurantsManager.findAll(userContext, lazy));
			productsManagerViewBean.setVats(valueAddedTaxesManager.findAll(userContext, lazy));
			productsManagerViewBean.setCategories(categoriesManager.findAll(userContext, lazy));
		} catch (Exception e) {
			logger.error("message.error.administration.business.find.all", e);
			throw new MdoBusinessException("message.error.administration.business.find.all", e);
		}
	}

	@Override
	protected String getDefaultLabel(IBeanLabelable mdoBean) {
		String result = null;
		if (mdoBean != null) {
			Product mdoBeanCasted = (Product) mdoBean;
			result = mdoBeanCasted.getCode();
		}
		return result;
	}

	@Override
	public List<IMdoDtoBean> getList(Long restaurantId, MdoUserContext userContext) throws MdoBusinessException {
		List<IMdoDtoBean> result = new ArrayList<IMdoDtoBean>();
		
		try {
			List<IMdoBean> list = ((IProductsDao) dao).findByRestaurant(restaurantId);
			if (list != null) {
				result = assembler.marshal(list, userContext);
			}
		} catch (MdoException e) {
			logger.error("message.error.administration.business.products.by.restaurant", new Object[] {restaurantId}, e);
			throw new MdoBusinessException("message.error.administration.business.products.by.restaurant", new Object[] {restaurantId}, e);
		}

		return result;
	}
	
	@Override
	public IMdoDtoBean update(IMdoDtoBean dtoBean, MdoUserContext userContext) throws MdoBusinessException {
		Product daoBean = (Product) assembler.unmarshal(dtoBean);
		try {
			// Deleting daoBean.getCategories() before inserting new ones
			Set<ProductCategory> backup = new HashSet<ProductCategory>(daoBean.getCategories());
			// Removing
			daoBean.getCategories().clear();
			dao.update(daoBean);
			// Restoring
			daoBean.getCategories().addAll(backup);
			return assembler.marshal((IMdoDaoBean) dao.update(daoBean), userContext);
		} catch (MdoException e) {
			logger.error("message.error.administration.business.save", e);
			throw new MdoBusinessException("message.error.administration.business.save", e);
		}
	}
	
	@Override
	public IMdoDtoBean delete(IMdoDtoBean dtoBean, MdoUserContext userContext) throws MdoBusinessException {
		// No need to Delete categories before Deleting user because of hibernate mapping all-delete-orphan in collection
		// Delete dto
		return super.delete(dtoBean, userContext);
	}

	@Override
	public ProductDto findByCode(String restaurantReference, String code, MdoUserContext userContext) throws MdoException {
		ProductDto result = null;
		IMdoDaoBean product = (IMdoDaoBean) ((IProductsDao) dao).findByCode(restaurantReference, code);
		result = (ProductDto) assembler.marshal(product, userContext);
		return result;
	}

	@Override
	public void importData(String importedFileName, File file, MdoUserContext userContext) throws MdoBusinessException {
		try {
			Pattern pattern = Pattern.compile(IProductsManager.IMPORT_DATA_FILE_NAME_PATTERN);
			Matcher matcher = pattern.matcher(importedFileName);
			boolean matchFound = matcher.find();
			String restaurantReference = null;
			String language = null;
			if (matchFound) {
		        restaurantReference = matcher.group(1);
		        language = matcher.group(2);
			} else {
				throw new MdoBusinessException("message.error.administration.business.products.restaurant.not.found", new Object[]{file});
			}

			RestaurantDto restaurant = null;
			restaurant = (RestaurantDto) restaurantsManager.findByReference(restaurantReference, userContext);
			
			if (restaurant != null) {
				LocaleDto locale = null;
				locale = (LocaleDto) localesManager.findByLanguage(language, userContext);
				
				if (locale != null) {
					List<IMdoDtoBean> vats = valueAddedTaxesManager.findAll(userContext);
					Map<String, ValueAddedTaxDto> vatsRateId = new HashMap<String, ValueAddedTaxDto>();
					for (Iterator<IMdoDtoBean> iterator = vats.iterator(); iterator.hasNext();) {
						ValueAddedTaxDto vat = (ValueAddedTaxDto) iterator.next();
						// The vat rate from database is never null
						// If the vat rate is not unique then keep the last one
						vatsRateId.put(vatRateFormat.format(vat.getRate().doubleValue()), vat);
					}

					// Load the file.
					Sheet sheet = SpreadSheet.createFromFile(file).getSheet(0);
					logger.debug("Number of import rows " + sheet.getRowCount());
					logger.debug("Number of import columns " + sheet.getColumnCount());
					
					int maxRow = sheet.getRowCount();
					int maxColumn = sheet.getColumnCount();
					for (int i = 1; i < maxRow; i++) {
						ProductDto product = new ProductDto();
						for (int j = 0; j < maxColumn; j++) {
							Object cellValue = sheet.getCellAt(j, i).getValue();
							logger.debug("Cell at column " + j + " and row " + i + " is instance of " + (cellValue!=null?cellValue.getClass():"") + " with value " + cellValue);
							// Fill product fields like Code, Label, Price ... See ProductRowTable.java
							ProductRowTable.values()[j].fillValue(product, cellValue);
						}
						
						if (product.getCode() != null && !product.getCode().isEmpty()) {
							// labels(see ProductRowTable.LABEL.fillValue) has size one
							Map<Long, String> labels = product.getLabels();
							// labels is empty now
							String label = labels.remove(null);
							// labels has one size
							labels.put(locale.getId(), label);
	
							// Product from Database
							ProductDto productFromDatabase = this.findByCode(restaurant.getReference(), product.getCode(), userContext);
							if (productFromDatabase != null) {
								// Here, set Values that are not in imported file
								product.setId(productFromDatabase.getId());
								product.setCategories(productFromDatabase.getCategories());
								
								if (productFromDatabase.getLabels() != null) {
									// Merging labels
									labels = this.mergeLabels(labels, productFromDatabase.getLabels());
								}
							}
							
							// Restaurant
							product.setRestaurant(restaurant);
							
							// VAT: see ProductRowTable.VAT.fillValue
							BigDecimal rate = product.getVat().getRate();
							if (rate != null) {
								String formattedRate = vatRateFormat.format(rate.doubleValue());
								ValueAddedTaxDto vat = vatsRateId.get(formattedRate);
								if (vat != null) {
									product.setVat(vat);
								}
							} 
							if (product.getVat() == null) {
								throw new MdoBusinessException("message.error.administration.business.products.vat.not.found", new Object[]{file});
							}
							
							// Save into database
							super.save(product, userContext);
						} else {
							// Considerer that is no more row to import in the imported file
							break;
						}
					}
				}
			} else {
				throw new MdoBusinessException("message.error.administration.business.products.restaurant.not.found", new Object[]{file});
			}
		} catch (Exception e) {
			throw new MdoBusinessException("message.error.administration.business.products.restaurant.not.found", new Object[]{file});
		}
	}

	/**
	 * Merge 2 maps into 1 by key map. The reference map is the second map but all data from the first map will erase the data from the second map.
	 * @param labels1 first map.
	 * @param labels2 second map.
	 * @return a merged map.
	 */
	private Map<Long, String> mergeLabels(Map<Long, String> labels1, Map<Long, String> labels2) {
		Map<Long, String> result = new HashMap<Long, String>(labels2);
		result.putAll(labels1);
		return result;
	}

	@Override
	public String exportData(OutputStream out, String restaurantReference, String[] headers, MdoUserContext userContext) throws MdoBusinessException {
		String exportFileName = this.buildExportFileName(restaurantReference, userContext.getCurrentLocale());
		RestaurantDto restaurant;
		try {
			restaurant = (RestaurantDto) this.getRestaurantsManager().findByReference(restaurantReference, userContext);
		} catch (MdoException e) {
			throw new MdoBusinessException(e);
		}

		if (restaurant == null) {
			throw new MdoBusinessException("message.error.administration.business.products.restaurant.not.found", new Object[]{restaurantReference});
		}
		
		Long localeId = userContext.getCurrentLocale().getId();
		List<IMdoDtoBean> list = this.getList(restaurant.getId(), userContext);
		int indexRow = 0;
		// Create the data to export: 0 row, 5 columns by default
		Object[][] data = new Object[0][5];
		if (list != null) {
			data = new Object[list.size()][5];
			for (IMdoDtoBean iMdoDtoBean : list) {
				ProductDto castedBean = (ProductDto) iMdoDtoBean;
				data[indexRow++] = new Object[] { castedBean.getCode(), castedBean.getLabels().get(localeId), castedBean.getPrice(), castedBean.getVat().getRate(), castedBean.getColorRGB() };	
				logger.debug("Row index = " + indexRow 
						+ ", Code = " + castedBean.getCode() + ", Label = " + castedBean.getLabels().get(localeId) 
						+ ", Price = " +  castedBean.getPrice() + ", Vat = " +  castedBean.getVat().getRate() 
						+ ", Color = " +  castedBean.getColorRGB());
			}
		}
		logger.debug("Rows Size = " + data.length + " - Columns Size = " + (data.length==0?0:data[0].length));
		
		TableModel model = new DefaultTableModel(data, headers);

		try {
//			SpreadSheet.createEmpty(model).saveAs(file);
			SpreadSheet.createEmpty(model).getPackage().save(out);
			
		} catch (IOException e) {
			throw new MdoBusinessException("message.error.administration.business.products.export.data", new Object[]{restaurantReference});
		}
		return exportFileName;
	}
	
	/**
	 * Build the export file name.
	 * @param restaurantReference the restaurant reference.
	 * @param locale the locale for language part.
	 * @return the generated export file name.
	 */
	private String buildExportFileName(String restaurantReference, LocaleDto locale) {
		String language = "xx";
		if (locale != null) {
			language = locale.getLanguageCode();
		}
		String result = IProductsManager.PREFIX_EXPORT_DATA_FILE_NAME + "-" + restaurantReference + "-" 
		+ language + "." + IProductsManager.EXTENSION_EXPORT_IMPORT_DATA_FILE_NAME;
		
		return result;
	}
}
