/*
 * Project		montagnesdor
 * File name		Restaurant.java
 * Created on		4 sept. 2004
 * @author		Mathieu MA sous conrad
 * @version		1.0
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dao.beans;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import fr.mch.mdo.restaurant.beans.MdoDaoBean;

/**
 * This class is a t_restaurant mapping. This table contains the restaurants
 * informations.
 * 
 * @author Mathieu
 */
public class Restaurant extends MdoDaoBean {
	/**
	 * Default Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

	private HashMap<String, Room> rooms = new HashMap<String, Room>();

	/**
	 * This is the restaurant creation date in the application.
	 */
	private Date registrationDate;
	/**
	 * This is the restaurant reference in the application.
	 */
	private String reference;
	/**
	 * This is the restaurant name.
	 */
	private String name;
	/**
	 * This is the restaurant address road.
	 */
	private String addressRoad;
	/**
	 * This is the restaurant address zip code.
	 */
	private String addressZip;
	/**
	 * This is the restaurant address city.
	 */
	private String addressCity;
	/**
	 * This is the restaurant phone number.
	 */
	private String phone;
	/**
	 * This is the restaurant V.A.T(Value Added Taxes) reference.
	 */
	private String vatRef;
	/**
	 * This is the restaurant visa reference.
	 */
	private String visaRef;
	/**
	 * This is the restaurant triple DES key. Utiliser poue créer des référence
	 * de facture
	 */
	private String tripleDESKey;
	/**
	 * This is used to know if we have to apply the V.A.T(Value Added Taxes)
	 * when it is a takeaway table. The default value is true.
	 * 
	 * Permet de savoir comment stocker les montants dans la table t_vat_table.
	 * Avant, pour une table à emporter, le montant de la TVA était appliqué sur le montant global des produits consommés de la table.  
	 * Actuelllement, quelque soit la table, le montant de la TVA dépend de chaque produit consommé de la table.
	 * 
	 * TODO: Maybe create new table t_restaurant_vat_table. The new table is a associated table with vat, restaurant, and table type.
	 * And we will have private Set<RestaurantVatTable> vatTables;
	 */
	private boolean vatByTakeaway = false;
	/**
	 * This is the specific round to apply on all amounts calculations. It is a
	 * foreign that refers to the t_enum table for type
	 * SPECIFIC_ROUND_CALCULATION. <!--1 = HALF ROUND--> <!--2 = TENTH ROUND-->
	 */
	private MdoTableAsEnum specificRound;
	/**
	 * This is the default table type.
	 * It is a foreign that refers to the t_table_type table.
	 * It is used to specify the dinner table type which can be EAT_IN, TAKEAWAY, ....
	 */
	private TableType defaultTableType;
    /**
	 * This is a foreign key that refers to t_value_added_tax. 
     * It is used to specify the default VAT custom order line when the former order line is not defined by a product in restaurant catalog.'
     */
    private ValueAddedTax vat;
	
	/**
	 * List of prefixes table names for take-away table(for instance).
	 */
	private Set<RestaurantPrefixTable> prefixTableNames;

	/**
	 * List of reduction tables. For example, a take-away table may have a reduction depending on a minimum amount.
	 */
	private Set<RestaurantReductionTable> reductionTables;

	/**
	 * List of vats for this restaurant
	 */
	private Set<RestaurantValueAddedTax> vats;

	/**
	 * List of VAT table types for take-away table(for instance).
	 */
	private Set<RestaurantVatTableType> vatTableTypes;
	
    /**
     * Map of ProductSpecialCode with key == short code and value == ProductSpecialCode
     */
    private Set<ProductSpecialCode> productSpecialCodes;

    /**
	 * @return the address City.
	 */
	public String getAddressCity() {
		return addressCity;
	}

	/**
	 * @param addressCity
	 *            addressCity à définir.
	 */
	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}

	/**
	 * @return Renvoie addressRoad.
	 */
	public String getAddressRoad() {
		return addressRoad;
	}

	/**
	 * @param addressRoad
	 *            addressRoad à définir.
	 */
	public void setAddressRoad(String addressRoad) {
		this.addressRoad = addressRoad;
	}

	/**
	 * @return Renvoie addressZip.
	 */
	public String getAddressZip() {
		return addressZip;
	}

	/**
	 * @param addressZip
	 *            addressZip à définir.
	 */
	public void setAddressZip(String addressZip) {
		this.addressZip = addressZip;
	}

	/**
	 * @return Renvoie phone.
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            phone à définir.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return Renvoie vatRef.
	 */
	public String getVatRef() {
		return vatRef;
	}

	/**
	 * @param vatRef
	 *            vatRef à définir.
	 */
	public void setVatRef(String vatRef) {
		this.vatRef = vatRef;
	}

	/**
	 * @return Renvoie visaRef.
	 */
	public String getVisaRef() {
		return visaRef;
	}

	/**
	 * @param visaRef
	 *            visaRef à définir.
	 */
	public void setVisaRef(String visaRef) {
		this.visaRef = visaRef;
	}

	public Restaurant() {
	}

	public Restaurant(String name) {
		this.name = name;
	}

	/**
	 * @return Renvoie name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            name à définir.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return rooms.
	 */
	public Room getRoom(String roomName) {
		return rooms.get(roomName);
	}

	/**
	 * @param rooms
	 *            rooms à définir.
	 */
	public void setRoom(String roomName) {
		rooms.put(roomName, new Room(roomName));
	}

	/**
	 * @return Renvoie tripleDESKey.
	 */
	public String getTripleDESKey() {
		return tripleDESKey;
	}

	/**
	 * @param tripleDESKey
	 *            tripleDESKey à définir.
	 */
	public void setTripleDESKey(String tripleDESKey) {
		this.tripleDESKey = tripleDESKey;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public boolean isVatByTakeaway() {
		return vatByTakeaway;
	}

	public void setVatByTakeaway(boolean vatByTakeaway) {
		this.vatByTakeaway = vatByTakeaway;
	}

	/**
	 * @return the prefixTableNames
	 */
	public Set<RestaurantPrefixTable> getPrefixTableNames() {
		return prefixTableNames;
	}

	/**
	 * @param prefixTableNames
	 *            the prefixTableNames to set
	 */
	public void setPrefixTableNames(Set<RestaurantPrefixTable> prefixTableNames) {
		this.prefixTableNames = prefixTableNames;
	}

	/**
	 * Add RestaurantValueAddedTax to vats
	 * 
	 * @param orderLine
	 *            the order line
	 */
	public void addPrefixTableName(RestaurantPrefixTable restaurantPrefixTable) {
		if (prefixTableNames == null) {
			prefixTableNames = new HashSet<RestaurantPrefixTable>();
		}
		if (restaurantPrefixTable != null) {
			restaurantPrefixTable.setRestaurant(this);
		}
		prefixTableNames.add(restaurantPrefixTable);
	}

	/**
	 * @return the reductionTables
	 */
	public Set<RestaurantReductionTable> getReductionTables() {
		return reductionTables;
	}

	/**
	 * @param reductionTables the reductionTables to set
	 */
	public void setReductionTables(Set<RestaurantReductionTable> reductionTables) {
		this.reductionTables = reductionTables;
	}

	/**
	 * Add RestaurantValueAddedTax to vats
	 * 
	 * @param restaurantReductionTable
	 *            the restaurant Reduction Table
	 */
	public void addReductionTable(RestaurantReductionTable restaurantReductionTable) {
		if (reductionTables == null) {
			reductionTables = new HashSet<RestaurantReductionTable>();
		}
		if (restaurantReductionTable != null) {
			restaurantReductionTable.setRestaurant(this);
		}
		reductionTables.add(restaurantReductionTable);
	}

	/**
	 * @param vats
	 *            the vats to set
	 */
	public void setVats(Set<RestaurantValueAddedTax> vats) {
		this.vats = vats;
	}

	/**
	 * @return the vats
	 */
	public Set<RestaurantValueAddedTax> getVats() {
		return vats;
	}

	/**
	 * @return the vatTableTypes
	 */
	public Set<RestaurantVatTableType> getVatTableTypes() {
		return vatTableTypes;
	}

	/**
	 * @param vatTableTypes the vatTableTypes to set
	 */
	public void setVatTableTypes(Set<RestaurantVatTableType> vatTableTypes) {
		this.vatTableTypes = vatTableTypes;
	}

	/**
	 * Add RestaurantVatTableType to vatTableTypes
	 * 
	 * @param vatTableType
	 *            the VAT table type
	 */
	public void addVatTableType(RestaurantVatTableType vatTableType) {
		if (vatTableTypes == null) {
			vatTableTypes = new HashSet<RestaurantVatTableType>();
		}
		if (vatTableType != null) {
			vatTableType.setRestaurant(this);
		}
		vatTableTypes.add(vatTableType);
	}

	/**
	 * Add RestaurantValueAddedTax to vats
	 * 
	 * @param vat
	 *            the vat
	 */
	public void addVat(RestaurantValueAddedTax vat) {
		if (vats == null) {
			vats = new HashSet<RestaurantValueAddedTax>();
		}
		if (vat != null) {
			vat.setRestaurant(this);
		}
		vats.add(vat);
	}

	/**
	 * @param specificRound
	 *            the specificRound to set
	 */
	public void setSpecificRound(MdoTableAsEnum specificRound) {
		this.specificRound = specificRound;
	}

	/**
	 * @return the specificRound
	 */
	public MdoTableAsEnum getSpecificRound() {
		return specificRound;
	}

	/**
	 * @param defaultTableType the defaultTableType to set
	 */
	public void setDefaultTableType(TableType defaultTableType) {
		this.defaultTableType = defaultTableType;
	}

	/**
	 * @return the defaultTableType
	 */
	public TableType getDefaultTableType() {
		return defaultTableType;
	}

	/**
	 * @return the vat
	 */
	public ValueAddedTax getVat() {
		return vat;
	}

	/**
	 * @param vat the vat to set
	 */
	public void setVat(ValueAddedTax vat) {
		this.vat = vat;
	}

	/**
	 * @param productSpecialCodes the productSpecialCodes to set
	 */
	public void setProductSpecialCodes(Set<ProductSpecialCode> productSpecialCodes) {
		this.productSpecialCodes = productSpecialCodes;
	}

	/**
	 * @return the productSpecialCodes
	 */
	public Set<ProductSpecialCode> getProductSpecialCodes() {
		return productSpecialCodes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((reference == null) ? 0 : reference.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Restaurant other = (Restaurant) obj;
		if (reference == null) {
			if (other.reference != null)
				return false;
		} else if (!reference.equals(other.reference))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Restaurant [rooms=" + rooms + ", registrationDate="	+ registrationDate + ", reference=" + reference
				+ ", name="	+ name + ", addressRoad=" + addressRoad + ", addressZip=" + addressZip 
				+ ", addressCity=" + addressCity + ", phone=" + phone + ", vatRef=" + vatRef + ", visaRef=" + visaRef
				+ ", tripleDESKey=" + tripleDESKey + ", vatByTakeaway="	+ vatByTakeaway 
				+ ", specificRound=" + specificRound + ", defaultTableType=" + defaultTableType
				+ ", vat=" + vat + ", id=" + id + ", deleted=" + deleted + "]";
	}
}
