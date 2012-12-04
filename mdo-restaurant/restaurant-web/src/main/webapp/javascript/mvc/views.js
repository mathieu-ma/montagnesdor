/**
 * This file will contain all Mdo views.
 */
$(document).ready(function() {
	// This is the application view. It is required by Mdo application for initial context.
	Mdo.ApplicationView = Ember.View.extend({
		template: Ember.Handlebars.compile('application')
	});
	
	Mdo.ButtonView = Ember.View.extend({
		tagName: 'button',
		selected: false,
		/**
		 * Observe the change of selected field.
		 * The selected field is binded to the HeaderButton. 
		 */
		selectedChanged: function(sender, key) {
			var viewSelected = this.get("selected");
			// selected field has changed
			this.disabledButton(viewSelected);
		}.observes('selected'),
		didInsertElement: function() {
			var thisEmberView = this;
			thisEmberView.$().button({
		    	// labelKey comes from HeaderView template.
		    	label: $.mdoI18n.prop(thisEmberView.get('labelKey')),
		    	// icons comes from HeaderView template.
		    	icons: thisEmberView.get('icons'),
		    });
	    	// selected comes from HeaderView template.
			this.disabledButton(thisEmberView.get('selected'));
		},
		disabledButton: function(selected) {
			var button = this.$().button({ disabled: selected });
			button.removeClass('ui-state-disabled');
			if (selected) {
				button.addClass('ui-state-active');
			} else {
				button.removeClass('ui-state-hover, ui-state-focus, ui-state-active');
			}
		}
	});
	
	Mdo.HeaderView = Ember.View.extend({
		templateName: "header",
		didInsertElement: function() {
			$("#menu-buttons").buttonset();
		}
	});

	Mdo.UserView = Ember.View.extend({
		templateName: "user"
	});

	Mdo.OrdersView = Ember.View.extend({
		templateName: "orders"
	});
});