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
		didInsertElement: function() {
			var thisEmberView = this;
			var button = thisEmberView.$().button({
		    	// labelKey comes from HeaderView template.
		    	label: $.mdoI18n.prop(thisEmberView.get('labelKey')),
		    	// icons comes from HeaderView template.
		    	icons: thisEmberView.get('icons'),
		    });
//alert(thisEmberView.get('selected'))			
	    	// selected comes from HeaderView template.
			if (thisEmberView.get('selected')) {
				button.addClass('ui-state-active');
			} else {
				button.removeClass('ui-state-active');
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