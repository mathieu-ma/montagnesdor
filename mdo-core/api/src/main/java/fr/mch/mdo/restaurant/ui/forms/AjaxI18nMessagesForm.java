package fr.mch.mdo.restaurant.ui.forms;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class AjaxI18nMessagesForm extends MdoForm 
{
	/** Field to be sent to struts configuration */
	private InputStream fileInputStream;

	/** The resource properties name */
	private String resource;
	/** List of property keys */
	private List<String> keys = new ArrayList<String>();
	
	/**
	 * Constructor.
	 */
	public AjaxI18nMessagesForm() {
		super(null);
	}


	/**
	 * @param fileInputStream the fileInputStream to set
	 */
	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}


	/**
	 * @return the fileInputStream
	 */
	public InputStream getFileInputStream() {
		return fileInputStream;
	}


	/**
	 * @param resource the resource to set
	 */
	public void setResource(String resource) {
		this.resource = resource;
	}

	/**
	 * @return the resource
	 */
	public String getResource() {
		return resource;
	}


	/**
	 * @param keys the keys to set
	 */
	public void setKeys(List<String> keys) {
		this.keys = keys;
	}


	/**
	 * @return the keys
	 */
	public List<String> getKeys() {
		return keys;
	}

}
