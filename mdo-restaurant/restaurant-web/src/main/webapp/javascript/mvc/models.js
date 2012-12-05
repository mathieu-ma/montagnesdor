$(document).ready(function() {
	Mdo.Label = Ember.Object.extend({
		label: null
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
});