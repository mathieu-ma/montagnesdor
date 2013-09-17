'use strict';
	
	Mdo.USER_MENU_ITEM_KEY = "header.access.user";
	Mdo.ORDERS_MENU_ITEM_KEY = "header.access.orders";
	Mdo.CASHED_ORDERS_MENU_ITEM_KEY = "header.access.cashed.orders";
	Mdo.LOCKED_ORDERS_MENU_ITEM_KEY = "header.access.locked.orders";

	Mdo.HEADER_MENUS = [
                       	{
    						key: Mdo.USER_MENU_ITEM_KEY,
    						icon: "ui-icon-person",
    					},
    					{
    						key: Mdo.ORDERS_MENU_ITEM_KEY,
    				    	icon: "ui-icon-cart"
    					},
    					{
    						key: Mdo.CASHED_ORDERS_MENU_ITEM_KEY,
    				    	icon: "ui-icon-document"
    					},
    					{
    						key: Mdo.LOCKED_ORDERS_MENU_ITEM_KEY,
    				    	icon: "ui-icon-locked"
    					}
    				];
	
	Mdo.DefaultDateTimeFormat = Ember.Object.extend({
		datePattern: 'DD dd MM yy',
		dateTimeSeparator: ' - ',
		timePattern: 'HH:mm:ss',
		formatDate: function(date) {
			var result = ""; 
			if (date) {
				result = Mdo.DateTimeUtils.formatDate(date, this.datePattern);
			}
			return result;
		},
		formatDateTime: function(date) {
			var result = ""; 
			if (date) {
				result = Mdo.DateTimeUtils.formatDateTime(date, this.datePattern, this.dateTimeSeparator, this.timePattern);
			}
			return result;
		},
	});

	Mdo.DateTimeFormat = Mdo.DefaultDateTimeFormat.extend({
	});
	
	Mdo.UserEntryDate = Ember.Object.extend({
		// This value is bind to controller DateTimeDialogController.userEntryDate.
		date: null,
	});

	Mdo.Header = Ember.Object.extend();
	Mdo.HeaderOrder = Ember.Object.extend({
		currentOrderId: null,
		currentOrderRegistrationDate: null,
		currentOrderNumber: null,
		currentOrderType: null,
		currentOrderCustomersNumber: null,
	});
	
	Mdo.Language = Ember.Object.extend({
		languageIso2: null,
		id: null, 
		displayLanguage: null,
		selected: null, 
	});