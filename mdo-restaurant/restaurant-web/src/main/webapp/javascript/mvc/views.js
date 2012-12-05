/**
 * This file will contain all Mdo views.
 */
$(document).ready(function() {
	// This is the application view. It is required by Mdo application for initial context.
	Mdo.ApplicationView = Ember.View.extend({
		template: Ember.Handlebars.compile('application')
	});

	Mdo.LabelView = Ember.View.extend({
		tagName: 'span',
		template: Ember.Handlebars.compile('888'),
		labelKey: null,
		label: function() {
//			Mdo.I18n.prop(this.labelKey, null, this, this.i18nLabelChanged);
		}.property(),
		i18nLabelChanged: function(sender, key) {
			var label = sender.get(key);
//			alert(labelKey)
		},
		willRerender: function() {
			alert(this.labelKey)
			this.template = Ember.Handlebars.compile(this.labelKey)
//			alert(this.$().html())
		}
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
		i18nLabelChanged: function(sender, key) {
			var label = sender.get(key);
			this.$().button({ label: label });
		},
		didInsertElement: function() {
			var thisEmberView = this;
			var button = thisEmberView.$().button({
		    	// labelKey comes from HeaderView template.
		    	//label: $.mdoI18n.prop(thisEmberView.labelKey),
		    	// icons comes from HeaderView template.
		    	icons: thisEmberView.get('icons'),
		    });
			// Process the i18n label and wait the return from server with Ember.Observable
			// The label will be set in the method i18nLabelChanged
			Mdo.I18n.prop(thisEmberView.labelKey, null, this, this.i18nLabelChanged);
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