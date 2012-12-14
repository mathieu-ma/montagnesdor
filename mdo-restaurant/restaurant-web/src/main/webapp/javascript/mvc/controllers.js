/**
 * This file will contain all Mdo controllers.
 */
$(document).ready(function() {
	// This is the application controller. It is required by Mdo application for initial context.
	Mdo.ApplicationController = Ember.Controller.extend({
	});

	Mdo.HeaderController = Ember.ObjectController.extend({
		content: [],
		init: function() {
		},
	});
	Mdo.HeaderLanguagesController = Ember.ArrayController.extend({
		content: [],
		init: function() {
			this.content.clear();
			var languages = this.content;
			languages.clear();
			var locales = Mdo.user.get("locales");
			$.each(locales, function(index, value) {
				var language = Ember.Object.create({
					languageIso2: index,
					id: value.id, 
					displayLanguage: value.displayLanguage,
					selected: value.selected, 
					controllerModifyLanguage: function(lang) {
						this.get('controller')['modifyLanguage'](lang);
					}
				});
				if (value.selected) {
					// Set Global variable for Mdo.selectedLocale.
					Mdo.user.set('selectedLanguage', language);
				}
				languages.pushObject(language);
			});
		},
		modifyLanguage: function(lang) {
			var route = this.target.get('currentState.route');
			if (route=="/") {
				route = "";
			}
			window.location.href = "?lang=" + lang + "#" + route;
		}
	});
	Mdo.DateTimeForm = Ember.Object.extend({
		date: null,
		password: null,
	});
	Mdo.HeaderDateTimeController = Ember.ObjectController.extend({
		dateTime: null,
		displayedDate: function() {
			var now = new Date();
			var result = $.datepicker.formatDate(this.dateTime.datePattern, now) + this.dateTime.dateTimeSeparator + $.datepicker.formatTime(this.dateTime.timePattern, now);
			return result;
		}.property(),
		init: function() {
			this.dateTime = Mdo.DateTime.create({
				datePattern: Mdo.user.datePattern,
				controllerChangeDateTime: function(form) {
					Mdo.router.get('headerDateTimeController')['openDialog'](form);
				}
			});
			this.startDateTime();
		},
		openDialog: function(dateTimeForm) {
			// TODO
			alert(dateTimeForm)
		},
		startDateTime: function() {
			var self = this;
			window.setInterval(function() {
				var now = new Date();
				var entryFormattedDate = $.datepicker.formatDate(this.dateTime.datePattern, now) + this.dateTime.dateTimeSeparator + $.datepicker.formatTime(this.dateTime.timePattern, now);
//alert(1)
				self.set('displayedDate', entryFormattedDate);
			},
			1000);
		}
	});
	Mdo.HeaderButtonsController = Ember.ArrayController.extend({
		content: [],
		allButtons: function() {
			this.initButtons();
			return this.content;
		},
		initButtons: function() {
			this.content.clear();
			var buttons = this.content;  
			buttons.clear();
			var data = Mdo.Header.allButtons();

			// Default selected button
			var selected = "user";
			// 1) Filter each state by route property with value equals to this.router.location.location.hash.substring(1)==Remove #==the first letter
			// 2) Iterate over found by filter and get the last one.
			$.each(this.target.currentState.childStates.filterProperty("route", this.target.location.location.hash.substring(1)), function(index, state) {
				selected = state.name;
			});
					
			$.each(data, function(index, value) {
				var isSelected = false;
				if (value.name == selected) {
					isSelected = true;
				}
				value.selected = isSelected;
				value.controllerChangeButton = function() {
					// this.get('controller') == HeaderButtonsController
					// If index == user then this.get('controller')[index] == this.get('controller')[user] = HeaderButtonsController.user
					try {
						this.get('controller')[value.name]();
					} catch (e) {
						// If the index doesn't bind a function
						alert(e);
						throw e;
					}
				};
				
				var button = Mdo.HeaderButton.create();
				button.set('name', value.name);
				button.set('labelKey', value.labelKey);
				button.set('icons', value.icons);
				button.set('selected', value.selected);
				button.set('controllerChangeButton', value.controllerChangeButton);
				buttons.pushObject(button);
			});
		},
		user: function() {
			this.target.router.send('gotoUser');
		},
		orders: function() {
			this.target.router.send('gotoOrders');
		},
		cashedOrders: function() {
			this.target.router.send('gotoCashedOrders');
		},	
		lockedOrders: function() {
			this.target.router.send('gotoLockedOrders');
		},
	});

	Mdo.HeaderOrderNumberController = Ember.ObjectController.extend({
//		content: Mdo.HeaderOrder.create()
	});
	Mdo.HeaderOrderCustomersNumberController = Ember.ObjectController.extend({
		content: Mdo.HeaderOrder.create()
	});
	Mdo.HeaderOrderController = Ember.ObjectController.extend({
		content: Mdo.HeaderOrder.create()
	});

	Mdo.UserController = Ember.ObjectController.extend({
	});
	
	Mdo.OrdersController = Ember.ObjectController.extend({
	});

	Mdo.CashedOrdersController = Ember.ObjectController.extend({
	});

	Mdo.LockedOrdersController = Ember.ObjectController.extend({
	});
});
