package fr.mch.mdo.restaurant.ui.forms;

public class ResetTableForm extends TableHeaderForm
{
	private Long restaurantId;
	private Long userAuthenticationId;

	/**
	 * @return the restaurantId
	 */
	public Long getRestaurantId() {
		return restaurantId;
	}
	/**
	 * @param restaurantId the restaurantId to set
	 */
	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}
	/**
	 * @return the userAuthenticationId
	 */
	public Long getUserAuthenticationId() {
		return userAuthenticationId;
	}
	/**
	 * @param userAuthenticationId the userAuthenticationId to set
	 */
	public void setUserAuthenticationId(Long userAuthenticationId) {
		this.userAuthenticationId = userAuthenticationId;
	}

	@Override
	public String toString() {
		return "ResetTableForm [restaurantId=" + restaurantId
				+ ", userAuthenticationId=" + userAuthenticationId
				+ ", getNumber()=" + getNumber() + ", getCustomersNumber()="
				+ getCustomersNumber() + "]";
	}
}
