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
package fr.mch.mdo.restaurant.dto.beans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import fr.mch.mdo.restaurant.beans.MdoDtoBean;

/**
 * This class is a DTO for the restaurants informations.
 * 
 * @author Mathieu
 */
public class RestaurantDto extends MdoDtoBean
{
    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

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
     * This is the restaurant triple DES key.
     * Used to create bill references.
     */
    private String tripleDESKey;
    /**
     * This is used to know if we have to apply the V.A.T(Value Added Taxes) when it is a takeaway table.
     * The default value is true.
     * Permet de savoir comment stocker les montants dans la table t_vat_table
     */
    private boolean vatByTakeaway = false;
    /**
     * This is the restaurant reduction for takeaway table we have to apply.
     * This field depends on the field res_takeaway_min_amount_reduction.
     */
    private BigDecimal takeawayBasicReduction = new BigDecimal(10);
    /**
     * This is the minimum amount value to apply a reduction for take-away table.
     */
    private BigDecimal takeawayMinAmountReduction = new BigDecimal(15);
    /**
     * This is the specific round to apply on all amounts calculations.
     * It is a foreign that refers to the t_enum table for type SPECIFIC_ROUND_CALCULATION.
     * <!--1 = HALF ROUND-->
     * <!--2 = TENTH ROUND-->
     */
    private MdoTableAsEnumDto specificRound;
    /**
     * List of vats for this restaurant
     */
    private Set<RestaurantValueAddedTaxDto> vats = new HashSet<RestaurantValueAddedTaxDto>();
    /**
     * List of prefix table names for this restaurant
     */
    private Set<RestaurantPrefixTableDto> prefixTableNames = new HashSet<RestaurantPrefixTableDto>();
    /**
     * @return the registrationDate
     */
    public Date getRegistrationDate() {
        return registrationDate;
    }
    /**
     * @param registrationDate the registrationDate to set
     */
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
    /**
     * @return the reference
     */
    public String getReference() {
        return reference;
    }
    /**
     * @param reference the reference to set
     */
    public void setReference(String reference) {
        this.reference = reference;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the addressRoad
     */
    public String getAddressRoad() {
        return addressRoad;
    }
    /**
     * @param addressRoad the addressRoad to set
     */
    public void setAddressRoad(String addressRoad) {
        this.addressRoad = addressRoad;
    }
    /**
     * @return the addressZip
     */
    public String getAddressZip() {
        return addressZip;
    }
    /**
     * @param addressZip the addressZip to set
     */
    public void setAddressZip(String addressZip) {
        this.addressZip = addressZip;
    }
    /**
     * @return the addressCity
     */
    public String getAddressCity() {
        return addressCity;
    }
    /**
     * @param addressCity the addressCity to set
     */
    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }
    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }
    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**
     * @return the vatRef
     */
    public String getVatRef() {
        return vatRef;
    }
    /**
     * @param vatRef the vatRef to set
     */
    public void setVatRef(String vatRef) {
        this.vatRef = vatRef;
    }
    /**
     * @return the visaRef
     */
    public String getVisaRef() {
        return visaRef;
    }
    /**
     * @param visaRef the visaRef to set
     */
    public void setVisaRef(String visaRef) {
        this.visaRef = visaRef;
    }
    /**
     * @return the tripleDESKey
     */
    public String getTripleDESKey() {
        return tripleDESKey;
    }
    /**
     * @param tripleDESKey the tripleDESKey to set
     */
    public void setTripleDESKey(String tripleDESKey) {
        this.tripleDESKey = tripleDESKey;
    }
    /**
     * @return the vatByTakeaway
     */
    public boolean isVatByTakeaway() {
        return vatByTakeaway;
    }
    /**
     * @param vatByTakeaway the vatByTakeaway to set
     */
    public void setVatByTakeaway(boolean vatByTakeaway) {
        this.vatByTakeaway = vatByTakeaway;
    }
    /**
     * @return the takeawayBasicReduction
     */
    public BigDecimal getTakeawayBasicReduction() {
        return takeawayBasicReduction;
    }
    /**
     * @param takeawayBasicReduction the takeawayBasicReduction to set
     */
    public void setTakeawayBasicReduction(BigDecimal takeawayBasicReduction) {
        this.takeawayBasicReduction = takeawayBasicReduction;
    }
    /**
     * @return the takeawayMinAmountReduction
     */
    public BigDecimal getTakeawayMinAmountReduction() {
        return takeawayMinAmountReduction;
    }
    /**
     * @param takeawayMinAmountReduction the takeawayMinAmountReduction to set
     */
    public void setTakeawayMinAmountReduction(BigDecimal takeawayMinAmountReduction) {
        this.takeawayMinAmountReduction = takeawayMinAmountReduction;
    }
    /**
     * @return the specificRound
     */
    public MdoTableAsEnumDto getSpecificRound() {
        return specificRound;
    }
    /**
     * @param specificRound the specificRound to set
     */
    public void setSpecificRound(MdoTableAsEnumDto specificRound) {
        this.specificRound = specificRound;
    }
    /**
     * @return the vats
     */
    public Set<RestaurantValueAddedTaxDto> getVats() {
        return vats;
    }
    /**
     * @param vats the vats to set
     */
    public void setVats(Set<RestaurantValueAddedTaxDto> vats) {
        this.vats = vats;
    }

    /**
	 * @param prefixTableNames the prefixTableNames to set
	 */
	public void setPrefixTableNames(Set<RestaurantPrefixTableDto> prefixTableNames) {
		this.prefixTableNames = prefixTableNames;
	}
	/**
	 * @return the prefixTableNames
	 */
	public Set<RestaurantPrefixTableDto> getPrefixTableNames() {
		return prefixTableNames;
	}
	@Override
    public String toString() {
	return "RestaurantDto [addressCity=" + addressCity + ", addressRoad="
		+ addressRoad + ", addressZip=" + addressZip + ", name=" + name
		+ ", phone=" + phone + ", reference=" + reference
		+ ", registrationDate=" + registrationDate + ", specificRound="
		+ specificRound + ", takeawayBasicReduction="
		+ takeawayBasicReduction + ", takeawayMinAmountReduction="
		+ takeawayMinAmountReduction + ", tripleDESKey=" + tripleDESKey
		+ ", vatByTakeaway=" + vatByTakeaway + ", vatRef=" + vatRef
		+ ", vats=" + vats + ", visaRef=" + visaRef + ", id=" + id
		+ "]";
    }

}
