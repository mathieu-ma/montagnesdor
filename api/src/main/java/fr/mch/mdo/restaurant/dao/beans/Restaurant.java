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

import java.math.BigDecimal;
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
	private String tripleDESKey = "12345678ABCDDCBA12345678";
	/**
	 * This is used to know if we have to apply the V.A.T(Value Added Taxes)
	 * when it is a takeaway table. The default value is true. Permet de savoir
	 * comment stocker les montants dans la table t_vat_table
	 */
	private boolean vatByTakeaway = false;
	/**
	 * This is the restaurant reduction for takeaway table we have to apply.
	 * This field depends on the field res_takeaway_min_amount_reduction.
	 */
	private BigDecimal takeawayBasicReduction = new BigDecimal(10);
	/**
	 * This is the minimum amount value to apply a reduction for take-away
	 * table.
	 */
	private BigDecimal takeawayMinAmountReduction = new BigDecimal(15);
	/**
	 * This is the specific round to apply on all amounts calculations. It is a
	 * foreign that refers to the t_enum table for type
	 * SPECIFIC_ROUND_CALCULATION. <!--1 = HALF ROUND--> <!--2 = TENTH ROUND-->
	 */
	private MdoTableAsEnum specificRound;
	/**
	 * List of prefixes table name for take-away table
	 */
	private Set<RestaurantPrefixTable> prefixTableNames;
	/**
	 * List of vats for this restaurant
	 */
	private Set<RestaurantValueAddedTax> vats;

	/**
	 * @return Renvoie addressCity.
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
	 * @return Renvoie takeawayBasicReduction.
	 */
	public BigDecimal getTakeawayBasicReduction() {
		return takeawayBasicReduction;
	}

	/**
	 * @param takeawayBasicReduction
	 *            takeawayBasicReduction à définir.
	 */
	public void setTakeawayBasicReduction(BigDecimal takeawayBasicReduction) {
		this.takeawayBasicReduction = takeawayBasicReduction;
	}

	/**
	 * @return Renvoie takeawayMinAmountReduction.
	 */
	public BigDecimal getTakeawayMinAmountReduction() {
		return takeawayMinAmountReduction;
	}

	/**
	 * @param takeawayMinAmountReduction
	 *            takeawayMinAmountReduction à définir.
	 */
	public void setTakeawayMinAmountReduction(BigDecimal takeawayMinAmountReduction) {
		this.takeawayMinAmountReduction = takeawayMinAmountReduction;
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
	 * Add RestaurantValueAddedTax to vats
	 * 
	 * @param orderLine
	 *            the order line
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
		return "Restaurant [addressCity=" + addressCity + ", addressRoad=" + addressRoad + ", addressZip=" + addressZip + ", name=" + name + ", phone=" + phone
				+ ", prefixeTakeawayNames=" + prefixTableNames + ", reference=" + reference + ", registrationDate=" + registrationDate + ", rooms=" + rooms + ", specificRound="
				+ specificRound + ", takeawayBasicReduction=" + takeawayBasicReduction + ", takeawayMinAmountReduction=" + takeawayMinAmountReduction + ", tripleDESKey="
				+ tripleDESKey + ", vatByTakeaway=" + vatByTakeaway + ", vatRef=" + vatRef + ", vats=" + vats + ", visaRef=" + visaRef + ", deleted=" + deleted + ", id=" + id
				+ "]";
	}
}
