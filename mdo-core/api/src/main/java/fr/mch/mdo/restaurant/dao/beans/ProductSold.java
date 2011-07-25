/*
 * Created on 29 avr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dao.beans;

import java.math.BigDecimal;

import fr.mch.mdo.restaurant.beans.MdoDaoBean;

/**
 * This class is a t_product_sold mapping.
 * This table is used for reporting of sold product.
 * 
 * @author Mathieu MA sous conrad
 */
public class ProductSold extends MdoDaoBean 
{
    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;
   
    /**
     * This is the sold year of the product. 
     * This field and the others pdt_id, pds_sold_month, and pds_sold_day consist of a unique field.     
     */
    private Integer soldYear;
    /**
     * This is the sold month of the product.
     * This field and the others pdt_id, pds_sold_year, and pds_sold_day consist of a unique field.
     */
    private Integer soldMonth;
    /**
     * This is the sold day of the product.
     * This field and the others pdt_id, pds_sold_year, and pds_sold_month consist of a unique field.
     */
    private Integer soldDay;
    /**
     * This is a foreign key that refers to t_product.
     * It is used to specify the sold product.
     * This field and the others pds_sold_year, pds_sold_month and pds_sold_day consist of a unique field.
     */
    private Product product;
    /**
     * This is the quantity of the sold product for a specific date.
     */
    private BigDecimal quantity;
	/**
	 * @return the soldYear
	 */
	public Integer getSoldYear() {
		return soldYear;
	}
	/**
	 * @param soldYear the soldYear to set
	 */
	public void setSoldYear(Integer soldYear) {
		this.soldYear = soldYear;
	}
	/**
	 * @return the soldMonth
	 */
	public Integer getSoldMonth() {
		return soldMonth;
	}
	/**
	 * @param soldMonth the soldMonth to set
	 */
	public void setSoldMonth(Integer soldMonth) {
		this.soldMonth = soldMonth;
	}
	/**
	 * @return the soldDay
	 */
	public Integer getSoldDay() {
		return soldDay;
	}
	/**
	 * @param soldDay the soldDay to set
	 */
	public void setSoldDay(Integer soldDay) {
		this.soldDay = soldDay;
	}
	/**
	 * @return the product
	 */
	public Product getProduct() {
		return product;
	}
	/**
	 * @param product the product to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}
	/**
	 * @return the quantity
	 */
	public BigDecimal getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + ((soldDay == null) ? 0 : soldDay.hashCode());
		result = prime * result + ((soldMonth == null) ? 0 : soldMonth.hashCode());
		result = prime * result + ((soldYear == null) ? 0 : soldYear.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ProductSold other = (ProductSold) obj;
		if (product == null) {
		    if (other.product != null) {
			return false;
		    }
		} else if (product.getId() == null) {
		    if (other.product.getId() != null) {
			return false;
		    }
		} else if (other.product == null || !product.getId().equals(other.product.getId())) {
		    return false;
		}
		if (soldDay == null) {
			if (other.soldDay != null) {
				return false;
			}
		} else if (!soldDay.equals(other.soldDay)) {
			return false;
		}
		if (soldMonth == null) {
			if (other.soldMonth != null) {
				return false;
			}
		} else if (!soldMonth.equals(other.soldMonth)) {
			return false;
		}
		if (soldYear == null) {
			if (other.soldYear != null) {
				return false;
			}
		} else if (!soldYear.equals(other.soldYear)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ProductSold [soldYear=" + soldYear + ", soldMonth=" + soldMonth + ", soldDay=" + soldDay + ", product=" + product + ", quantity=" + quantity + ", id=" + id + ", deleted=" + deleted
				+ "]";
	}
}
