$(document).ready(function() {
	Mdo.Header = Ember.Object.extend();
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
				        	  	name: "cashed.orders",
								labelKey: "header.access.cashed.orders",
						    	icons: {
						            primary: "ui-icon-document"
						        }
							},
							{
				        	  	name: "locked.orders",
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