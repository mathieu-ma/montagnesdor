/**
 * This file will contain all Mdo views.
 */
$(document).ready(function() {

	Mdo.ButtonView = Ember.View.extend({
		tagName: 'button',
		labelKey: null,
		selected: false,
		controllerChangeButton: null,
		i18nLabelChanged: function(sender, key) {
			var label = sender.get(key);
			this.$().button({ label: label });
		},
		click: function() {
			this.set('selected', true);			
			this.controllerChangeButton();
		},
		disabledButton: function() {
			var jButton = this.$().button({ disabled: this.selected });
			jButton.removeClass('ui-state-disabled');
			if (this.selected) {
				jButton.addClass('ui-state-active');
			} else {
				jButton.removeClass('ui-state-hover ui-state-focus ui-state-active');
			}
		}.observes('selected'),
		didInsertElement: function() {
			var self = this;
			var button = self.$().button({
		    	// labelKey comes from HeaderView template.
		    	//label: $.mdoI18n.prop(self.labelKey),
		    	// icons comes from HeaderView template.
		    	icons: self.get('icons'),
		    });
			// Process the i18n label and wait the return from server with Ember.Observable
			// The label will be set in the method i18nLabelChanged
			Mdo.I18n.propAddObserver(this.labelKey, null, this, this.i18nLabelChanged);
			// selected comes from HeaderView template.
			if (this.get('selected') == null) {
				this.set('selected', true);
			}
		},
		willDestroyElement: function() {
			// Have to remove because when this view is deleted, the observable object did not know of it.
			Mdo.I18n.propRemoveObserver(this.labelKey, this, this.i18nLabelChanged);
		},
	});
	Mdo.HeaderButtonsView = Ember.View.extend({
		templateName: "headerButtons",
		didInsertElement: function() {
			$("#header-buttons").buttonset();
		},
	});
});