/**
 * This file will contain all Mdo controllers.
 */
$(document).ready(function() {
	// This is the application controller. It is required by Mdo application for initial context.
	Mdo.ApplicationController = Ember.Controller.extend();

	Mdo.HeaderController = Ember.ObjectController.extend({
		content: [],
	});
	Mdo.HeaderLanguagesController = Ember.ArrayController.extend({
		content: [],
		init: function() {
			this.content.clear();
			var languages = this.content;
			languages.clear();
			var locales = Mdo.User.create().get("locales");
			$.each(locales, function(index, value) {
				var language = Ember.Object.create({
						languageIso2: index,
						id: value.id, 
						displayLanguage: value.displayLanguage, 
					});
				languages.pushObject(language);
			});
		}
	});
	Mdo.HeaderButtonsController = Ember.ArrayController.extend({
		content: [],
		initButtons: function(selected) {
			this.content.clear();
			var buttons = this.content;  
			buttons.clear();
			var data = Mdo.Header.allButtons();
			$.each(data, function(index, value) {
				var isSelected = false;
				if (value.name == selected) {
					isSelected = true;
				}
				value.selected = isSelected;
				value.click = function() {
					// this.get('controller') == HeaderController
					// If index == user then this.get('controller')[index] == this.get('controller')[user] = HeaderController.user
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
				button.set('click', value.click);
				buttons.pushObject(button);
			});
		},
		refreshButtons: function(selected) {
			var isContentEmpty = false;
			var buttons = this.content;
			if (buttons.length == 0) {
				isContentEmpty = true;
				this.initButtons(selected);
			} else {
				$.each(buttons, function(index, value) {
					var isSelected = false;
					if (value.name == selected) {
						isSelected = true;
					}
					// Change and fire selected
					value.set('selected', isSelected);
				});
			}
			return isContentEmpty;
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
