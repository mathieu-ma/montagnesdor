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
		dateTime: Mdo.DateTime.create(),
		displayedDate: function() {
			var result = new Date();
			if (this.dateTime.currentTableRegistrationDate) {
				result = this.dateTime.currentTableRegistrationDate;
			} else if (this.dateTime.userEntryDate) {
				result = this.dateTime.userEntryDate;
			}
			return result;
		}.property('dateTime.userEntryDate', 'dateTime.currentTableRegistrationDate'),
		// Used for refreshing time in the method startDateTime in order to display date.
		displayedTime: new Date(),
		displayedFormattedDate: function() {
			var displayedDate = this.get('displayedDate');			
			var formattedDisplayedDate = $.datepicker.formatDate(this.dateTime.datePattern, displayedDate);
			var formattedDisplayedTime = $.datepicker.formatTime(this.dateTime.timePattern, this.time(this.displayedTime));
			var result = formattedDisplayedDate + this.dateTime.dateTimeSeparator + formattedDisplayedTime;
			return result;
			// Because of setInterval in method startDateTime, we could avoid to make this property depends on the property 'displayedDate'.
		}.property('displayedDate', 'displayedTime'),
		init: function() {
			this.dateTime.reopen({
				datePattern: Mdo.user.datePattern,
				// Extends 3 following properties: think as DTO.
				currentTableRegistrationDate: null,
				userEntryDate: new Date(),
				controllerChangeDateTime: function(form) {
					Mdo.router.get('headerDateTimeController')['saveDateTime'](form);
				},				
			});
			this.startDateTime();
		},
		time: function(date) {
			var result = {
				hour: date.getHours(),
				minute: date.getMinutes(),
				second: date.getSeconds(),
				millisec: date.getMilliseconds(),
				timezone: date.getTimezoneOffset()
			};
			return result;
		},
		formatDateTime: function(date) {
			var result = $.datepicker.formatDate(this.dateTime.datePattern, date) + this.dateTime.dateTimeSeparator 
			+ $.datepicker.formatTime(this.dateTime.timePattern, this.time(new Date()));
			return result;
		},
		saveDateTime: function(dateTimeForm) {
			var self = this;
			Ember.run.later(null, function() {
				// TODO: Ajax to confirm password the set displayedDate
				// If password false then display warning message
				self.set('dateTime.userEntryDate', dateTimeForm.date);
			}, 2000);
		},
		startDateTime: function() {
			var self = this;
			window.setInterval(function() {
				// This will fire the change of displayedFormattedDate property. 
				self.set('displayedTime', new Date());
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

	Mdo.HeaderOrderController = Ember.ObjectController.extend({
		headerOrder: Mdo.HeaderOrder.create(),
		step: 0,
		checkNumber: function(number) {
			var controller = Mdo.router.get('headerOrderController');
			var step = controller.get('step');
			// Before Ajax call, change the step in order to disable the view
			// Go forward to next step.
			controller.set('step', step + 1);

			Ember.run.later(null, function() {
				// TODO: Ajax to check number
				var error = false;
				if (error) {
					// In case of error Ajax error
					// Go back the previous step.
					controller.set('step', step);
				} else {
					controller.set('headerOrder.number', number);
					controller.set('headerOrder.customersNumber', 2);
					controller.set('headerOrder.takeaway', true);
				}
			}, 100);
		},
		saveCustomersNumber: function(customersNumber) {
			var controller = Mdo.router.get('headerOrderController');
			var step = controller.get('step');
			// Before Ajax call, change the step in order to disable the view
			// Go forward to next step.
			controller.set('step', step + 1);

			Ember.run.later(null, function() {
				// TODO: Ajax to check number
				var error = false;
				if (error) {
					// In case of error Ajax error
					// Go back the previous step.
					controller.set('step', step);
				} else {
					controller.set('headerOrder.customersNumber', customersNumber);
				}
			}, 100);
		},
		backToNumber: function() {
			var controller = Mdo.router.get('headerOrderController');
			controller.set('step', 0);
			controller.set('headerOrder.number', '');
			controller.set('headerOrder.customersNumber', '');
			controller.set('headerOrder.takeaway', false);
		}
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
