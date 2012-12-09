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
		labelKey: null,
		i18nLabelChanged: function(sender, key) {
			var label = sender.get(key);
			this.$().html(label);
		},
		didInsertElement: function() {
			Mdo.I18n.propAddObserver(this.labelKey, null, this, this.i18nLabelChanged);
		},
		willDestroyElement: function() {
			// Have to remove because when this view is deleted, the observable object did not know of it.
			Mdo.I18n.propRemoveObserver(this.labelKey, this, this.i18nLabelChanged);
		}
	});
	
	Mdo.ButtonView = Ember.View.extend({
		tagName: 'button',
		labelKey: null,
		selected: false,
		controllerChangeButton: null,
		/**
		 * Observe the change of selected field.
		 * The selected field is binded to the HeaderButton. 
		 */
		selectedChanged: function(sender, key) {
			var viewSelected = this.get("selected");
			// selected field has changed.
			this.disabledButton(viewSelected);
			// Focus the order number text field.
console.log(Em.View.views['number'])			
			Em.View.views['number'].$().focus();
		}.observes('selected'),
		i18nLabelChanged: function(sender, key) {
			var label = sender.get(key);
			this.$().button({ label: label });
		},
		click: function() {
			this.get('parentView').manageButtons(this);
			this.controllerChangeButton();
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
			Mdo.I18n.propAddObserver(this.labelKey, null, this, this.i18nLabelChanged);
			// selected comes from HeaderView template.
			this.disabledButton(this.get('selected'));
		},
		willDestroyElement: function() {
			// Have to remove because when this view is deleted, the observable object did not know of it.
			Mdo.I18n.propRemoveObserver(this.labelKey, this, this.i18nLabelChanged);
		},
		disabledButton: function(selected) {
			var button = this.$().button({ disabled: selected });
			button.removeClass('ui-state-disabled');
			if (selected) {
				button.addClass('ui-state-active');
			} else {
				button.removeClass('ui-state-hover ui-state-focus ui-state-active');
			}
		}
	});
	
	Mdo.HeaderView = Ember.View.extend({
		templateName: "header",
	});

	Mdo.FlagView = Ember.View.extend({
		tagName: 'div',
		classNameBindings: ['flagClasses'],
		attributeBindings: ['title'],
		id: null,
		languageIso2: null,
		displayLanguage: null,
		controllerModifyLanguage: null,
		title: function() {
			return this.displayLanguage;
		}.property('displayLanguage'),
		selected: false,
		flagClasses: function() {
			var result = "flag flag-" + this.languageIso2; 
			var viewSelected = this.get("selected");
			if (viewSelected) {
				result += " flag-state-active";
			}
			return result;
		}.property("languageIso2", "selected"),
		init: function() {
			this._super();
			this.set('selected', this.selected);
		},
		didInsertElement: function() {
			var thisEmberView = this;
			if (!this.selected) {
				this.$().click(function() {
					thisEmberView.controllerModifyLanguage(thisEmberView.languageIso2);
				});
			}
		}
	});
	Mdo.HeaderLanguagesView = Ember.View.extend({
		templateName: "headerLanguages",
	});

	Mdo.HeaderButtonsView = Ember.View.extend({
		templateName: "headerButtons",
		didInsertElement: function() {
			$("#header-buttons").buttonset();
		},
		manageButtons: function(selectedButtonView) {
			var thisEmberView = this;
			$("button", "#header-buttons").each(function() {
				var viewSelected = false;
				if (selectedButtonView.$().attr('id') == $(this).attr('id')) {
					viewSelected = true;
				}
				thisEmberView.disabledButton(this, viewSelected);
			});
		},
		disabledButton: function(button, selected) {
			var jButton = $(button).button({ disabled: selected });
			jButton.removeClass('ui-state-disabled');
			if (selected) {
				jButton.addClass('ui-state-active');
			} else {
				jButton.removeClass('ui-state-hover ui-state-focus ui-state-active');
			}
		}
	});
	Mdo.HeaderOrderNumberView = Ember.View.extend({
		templateName: "headerOrderNumber",
		tagName: "span",
		didInsertElement: function() {
			// Focus the order number text field.
//			this.headerOrderNumberView.$().focus();
//alert("headerOrderNumber")			
//			Em.View.views['number'].$().focus();
		},
	});
	Mdo.HeaderOrderCustomersNumberView = Ember.View.extend({
		templateName: "headerOrderCustomersNumber",
		tagName: "span",
		didInsertElement: function() {
			// Focus the order number text field.
//			this.headerOrderNumberView.$().focus();
//alert("headerOrderNumber")			
//			Em.View.views['number'].$().focus();
		},
	});
	Mdo.HeaderOrderView = Ember.View.extend({
		templateName: "headerOrder",
		didInsertElement: function() {
			// Focus the order number text field.
//			this.headerOrderNumberView.$().focus();
//			Em.View.views['number'].$().focus();
		},
	});

	Mdo.UserView = Ember.View.extend({
		templateName: "user"
	});

	Mdo.OrdersView = Ember.View.extend({
		templateName: "orders"
	});

	Mdo.CashedOrdersView = Ember.View.extend({
		templateName: "cashedOrders"
	});

	Mdo.LockedOrdersView = Ember.View.extend({
		templateName: "lockedOrders"
	});
});