package fr.mch.mdo.restaurant.ui.forms;

import java.io.File;
import java.io.InputStream;

import fr.mch.mdo.restaurant.dto.beans.ProductDto;
import fr.mch.mdo.restaurant.dto.beans.ProductsManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;

public class ProductsManagerForm extends MdoLabelsForm 
{
	private InputStream exportInputStream;
	private String exportFileName;
	
	private File importedFile;
	private String importedFileContentType;
	private String importedFileFileName;

	private RestaurantDto restaurant;

	public ProductsManagerForm() {
		super(new ProductDto());
		super.setViewBean(new ProductsManagerViewBean());
	}

	/**
	 * @return the exportInputStream
	 */
	public InputStream getExportInputStream() {
		return exportInputStream;
	}

	/**
	 * @param exportInputStream the exportInputStream to set
	 */
	public void setExportInputStream(InputStream exportInputStream) {
		this.exportInputStream = exportInputStream;
	}

	/**
	 * @return the exportFileName
	 */
	public String getExportFileName() {
		return exportFileName;
	}

	/**
	 * @param exportFileName the exportFileName to set
	 */
	public void setExportFileName(String exportFileName) {
		this.exportFileName = exportFileName;
	}

	/**
	 * @param importedFile the importedFile to set
	 */
	public void setImportedFile(File importedFile) {
		this.importedFile = importedFile;
	}

	/**
	 * @return the importedFile
	 */
	public File getImportedFile() {
		return importedFile;
	}

	/**
	 * @return the importedFileContentType
	 */
	public String getImportedFileContentType() {
		return importedFileContentType;
	}

	/**
	 * @param importedFileContentType the importedFileContentType to set
	 */
	public void setImportedFileContentType(String importedFileContentType) {
		this.importedFileContentType = importedFileContentType;
	}

	/**
	 * @return the importedFileFileName
	 */
	public String getImportedFileFileName() {
		return importedFileFileName;
	}

	/**
	 * @param importedFileFileName the importedFileFileName to set
	 */
	public void setImportedFileFileName(String importedFileFileName) {
		this.importedFileFileName = importedFileFileName;
	}

	/**
	 * @return the restaurant
	 */
	public RestaurantDto getRestaurant() {
		return restaurant;
	}

	/**
	 * @param restaurant the restaurant to set
	 */
	public void setRestaurant(RestaurantDto restaurant) {
		this.restaurant = restaurant;
	}
}
