/**
 * This file will contain all Mdo controllers.
 */
$(document).ready(function() {
	// This is the application controller. It is required by Mdo application for initial context.
	Mdo.ApplicationController = Ember.Controller.extend();
	
	Mdo.HeaderController = Ember.ArrayController.extend({
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
					}
				};
				var resultItem = Ember.Object.create({ 
					name: value.name,
					labelKey: value.labelKey,
					icons: value.icons,
					selected: value.selected,
					click: value.click
				});
				buttons.pushObject(resultItem);
			});
		},
		refreshButtons: function(selected) {
			var buttons = this.content;
			if (buttons.length == 0) {
				this.initButtons(selected);
			} else {
				$.each(buttons, function(index, value) {
					var isSelected = false;
					if (value.name == selected) {
						isSelected = true;
					}
					value.selected = isSelected;
				});
			}
		},
		user: function() {
			this.target.router.send('gotoUser');
		},
		orders: function() {
			this.target.router.send('gotoOrders');
		}	
	});
	
	Mdo.UserController = Ember.ObjectController.extend({
	});
	
	Mdo.OrdersController = Ember.ObjectController.extend({
	});

});
