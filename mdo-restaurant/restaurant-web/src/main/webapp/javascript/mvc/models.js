$(document).ready(function() {
	Mdo.Header = Ember.Object.extend();
	Mdo.Header.reopenClass({
		_buttons: Em.A(),
		_stubDataSource: {	
				          "user":	{
								labelKey: "header.access.user",
								icons: {
									primary: "ui-icon-person"
								},
								click: function(event) {
									this.get('controller').gotoUser();
								}
							},
							"orders": {
								labelKey: "header.access.orders",
						    	icons: {
						            primary: "ui-icon-cart"
						        },
								click: function(event) {
									this.get('controller').gotoOrders();
								}
							},
							"cashed.orders" : {
								labelKey: "header.access.cashed.orders",
						    	icons: {
						            primary: "ui-icon-document"
						        }
							},
							"locked.orders": {
								labelKey: "header.access.locked.orders",
						    	icons: {
						            primary: "ui-icon-locked"
						        }
							}
						},
		allButtons: function(selectedHeaderButton) {
			var allButtons = this._buttons;
			allButtons.clear();
			$.each(this._stubDataSource, function(index, value) {
				if (index == selectedHeaderButton) {
					value.selected = true;
				} else {
					value.selected = false;
				}
				allButtons.pushObject(value);
			});
	    	//allButtons.pushObjects(this._stubDataSource);
	    	return allButtons;
		}
	});
});