$(document).ready(function() {
	Mdo.Label = Ember.Object.extend({
		toto: null,
		labelKey: null,
		counter: 0,
		/**
		 * The label is a computed property and depends on the 2 properties labelKey and counter.
		 * The labelKey property is used to find the label.
		 * The counter is only used for observing because we want the observer to be called whenever we need.
		 * See the Ember note on observable api:
		 * Note that if propertyKey is a computed property, the observer will be called when any of the property dependencies are changed, 
		 * even if the resulting value of the computed property is unchanged. 
		 * This is necessary because computed properties are not computed until get is called.
		 */
		label: function() {
			return $.mdoI18n.prop(this.labelKey);
		}.property("labelKey", "counter")
	});
	Mdo.Header = Ember.Object.extend();
	Mdo.HeaderButton = Ember.Object.extend({
		name: "",
		labelKey: "",
		icons: {},
		selected: false
	});
	Mdo.Header.reopenClass({
		_stubDataSource: [
		                  	{
				        	  	name: "user",
								labelKey: "header.access.user",
								icons: {
									primary: "ui-icon-person"
								}
							},
							{
				        	  	name: "orders",
								labelKey: "header.access.orders",
						    	icons: {
						            primary: "ui-icon-cart"
						        }
							},
							{
				        	  	name: "cashedOrders",
								labelKey: "header.access.cashed.orders",
						    	icons: {
						            primary: "ui-icon-document"
						        }
							},
							{
				        	  	name: "lockedOrders",
								labelKey: "header.access.locked.orders",
						    	icons: {
						            primary: "ui-icon-locked"
						        }
							}
						],
		allButtons: function() {
			var allButtons = this._stubDataSource;
	    	return allButtons;
		}
	});
	Mdo.HeaderOrder = Ember.Object.extend({
		number: null,
		takeaway: false,
		customersNumber: 10,
	});
});