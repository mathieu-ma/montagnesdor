/**
 * This file will contain all Mdo controllers.
 */
$(document).ready(function() {
	Mdo.HeaderButtonsController = Ember.ArrayController.extend({
		headerOrderController: null,
		content: [],
		currentEventName: null, 
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
			this.currentEventName = 'gotoUser';
			// 1) Filter each state by route property with value equals to this.router.location.location.hash.substring(1)==Remove #==the first letter
			// 2) Iterate over found by filter and get the last one.
			$.each(this.target.currentState.childStates.filterProperty("route", this.target.location.location.hash.substring(1)), function(index, state) {
				selected = state.name;
			});
					
			$.each(data, function(index, value) {
				var isSelected = false;
				if (value.name == selected) {
					// null is pseudo state before true state.
					isSelected = null;
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
			// Observe button selected change
			$.each(buttons, function(index, button1) {
				$.each(buttons, function(index, button2) {
					if (button1 != button2) {
						button1.addObserver('selected', button2, function(sender, key) {
							var selected = sender.get(key);
							if (selected) {
								this.set('selected', false);
							}
						});
					}
				});
			});
		},
		user: function() {
			this._redirect('gotoUser');
		},
		orders: function() {
			this._redirect('gotoOrders');
		},
		cashedOrders: function() {
			this._redirect('gotoCashedOrders');
		},	
		lockedOrders: function() {
			this._redirect('gotoLockedOrders');
		},
		refreshCurrentSelection: function() {
			this._redirect(this.currentEventName);
		},
		// Forward to other event
		_redirect: function(eventName) {
			this.currentEventName = eventName; 
			// Reset header order
			this.headerOrderController.resetHeaderOrder();
			// Send redirect
			this.target.router.send(eventName);
		}
	});
});
