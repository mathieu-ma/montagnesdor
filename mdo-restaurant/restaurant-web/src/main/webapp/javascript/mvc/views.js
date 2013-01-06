/**
 * This file will contain all Mdo views.
 */
$(document).ready(function() {
	// This is the application view. It is required by Mdo application for initial context.
	Mdo.ApplicationView = Ember.View.extend({
		template: Ember.Handlebars.compile('application'),
		didInsertElement: function() {
			$("#menu, #header, #footer").mdoResizable();
		},
	});

	Mdo.LabelView = Ember.View.extend({
		tagName: 'span',
		label: null,
		labelKey: null,
		i18nLabelChanged: function(sender, key) {
			var label = this.label;
			if (!this.label) {
				label = sender.get(key);
			}
			this.$().html(label);
		},
		didInsertElement: function() {
			if (!this.label) {
				Mdo.I18n.propAddObserver(this.labelKey, null, this, this.i18nLabelChanged);
			} else {
				this.$().html(this.label);
			}
		},
		willDestroyElement: function() {
			if (!this.label) {
				// Have to remove because when this view is deleted, the observable object did not know of it.
				Mdo.I18n.propRemoveObserver(this.labelKey, this, this.i18nLabelChanged);
			}
		}
	});

	Mdo.HeaderView = Ember.View.extend({
		templateName: "header",
	});

	Mdo.HeaderDateTimeView = Ember.View.extend({
		templateName: "headerDateTime",
	});

	Mdo.DateTimeView = Ember.View.extend({
		tagName: 'a',
		classNames: ['displayed-date'],
		classNameBindings: ['displayedDateClass'],
		toggleClass: true,
		dateTime: null,
		registrationDate: null,
		displayedDateClass: function() {
			var registrationDateFormatted = null;
			if (this.registrationDate) {
				registrationDateFormatted = $.datepicker.formatDate(this.dateTime.datePattern, this.registrationDate);
			}
			var userEntryDateFormatted = $.datepicker.formatDate(this.dateTime.datePattern, this.dateTime.userEntryDate);
			var result = 'displayed-date-now';
//console.log(registrationDateFormatted + "==" + userEntryDateFormatted + "==" + nowFormatted)
			if (registrationDateFormatted && registrationDateFormatted!=userEntryDateFormatted) {
				result = 'displayed-date-registration';
			} else {
//console.log(this.displayedDate)
				// Current date
				var nowFormatted = jQuery.datepicker.formatDate(this.dateTime.datePattern, new Date());
//console.log(registrationDateFormatted + "==" + userEntryDateFormatted + "==" + nowFormatted)
				if (nowFormatted!=userEntryDateFormatted) {
					if (this.toggleClass) {
						this.toggleClass = false;
						result = 'displayed-date-user-entry-odd';
					} else {
						this.toggleClass = true;
						result = 'displayed-date-user-entry-even';
					}
				}
			}
			return result;
		}.property('controller.displayedFormattedDate'),
		didInsertElement: function() {
			var self = this;
			///TODO remove the tests below.
			$('#setCurrentTableRegistrationDateTest').click(function() {
				self.get('controller').set('registrationDate', new Date(1970, 7, 15)); 
			});
			$('#resetCurrentTableRegistrationDateTest').click(function() {
				self.get('controller').set('registrationDate', null); 
			});
		},
		click: function() {
			this.get('controller.target').send('gotoDateDialog', 'open');
		}
	});
	
	Mdo.FlagView = Ember.View.extend({
		tagName: 'div',
		classNameBindings: ['flagClasses'],
		attributeBindings: ['title'],
		id: null,
		languageIso2: null,
		displayLanguage: null,
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
			var self = this;
			var controller = self.get('controller');
			if (!this.selected) {
				this.$().click(function() {
					controller.changeLanguage(self.languageIso2);
				});
			}
		},
	});
	Mdo.HeaderLanguagesView = Ember.View.extend({
		templateName: "headerLanguages",
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