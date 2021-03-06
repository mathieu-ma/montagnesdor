$(document).ready(function() {
	Mdo.Label = Ember.Object.extend({
		toto: null,
		labelKey: null,
		counter: 0,
		/**
		 * The label is a computed property and depends on the 2 properties labelKey and counter.
		 * The labelKey property is used to find the label.
		 * The counter is only used for observing because we want the observer to be called whenever we need.
		 * See the Ember note on observable api:
		 * Note that if propertyKey is a computed property, the observer will be called when any of the property dependencies are changed, 
		 * even if the resulting value of the computed property is unchanged. 
		 * This is necessary because computed properties are not computed until get is called.
		 */
		label: function() {
			return $.mdoI18n.prop(this.labelKey);
		}.property("labelKey", "counter")
	});
	
	
	Mdo.DateTime = Ember.Object.extend({
		datePattern: null,
		dateTimeSeparator: ' - ',
		timePattern: 'HH:mm:ss',
	});
	
	Mdo.Header = Ember.Object.extend();
	Mdo.HeaderButton = Ember.Object.extend({
		name: "",
		labelKey: "",
		icons: {},
		// Could be false, true or null
		selected: null
	});
	Mdo.Header.reopenClass({
		_stubDataSource: [
		                  	{
				        	  	name: "user",
								labelKey: "header.access.user",
								icons: {
									primary: "ui-icon-person"
								}
							},
							{
				        	  	name: "orders",
								labelKey: "header.access.orders",
						    	icons: {
						            primary: "ui-icon-cart"
						        }
							},
							{
				        	  	name: "cashedOrders",
								labelKey: "header.access.cashed.orders",
						    	icons: {
						            primary: "ui-icon-document"
						        }
							},
							{
				        	  	name: "lockedOrders",
								labelKey: "header.access.locked.orders",
						    	icons: {
						            primary: "ui-icon-locked"
						        }
							}
						],
		allButtons: function() {
			var allButtons = this._stubDataSource;
	    	return allButtons;
		}
	});
	Mdo.HeaderOrder = Ember.Object.extend({
		id: null,
		registrationDate: null,
		number: null,
		takeaway: false,
		customersNumber: null,
	});
	
	Mdo.Language = Ember.Object.extend({
		languageIso2: null,
		id: null, 
		displayLanguage: null,
		selected: null, 
	});
	
	Mdo.User = Ember.Object.extend({
	    id: 1,
	    restaurantId: 1,
	    login: "mch",
		    locales: {
	        "zh": {id: 2, displayLanguage: "chinois", selected: false},
	        "fr": {id: 1, displayLanguage: "français", selected: true},
	    },
	    datePattern: 'DD dd MM yy',
	    // Will be set later in HeaderLanguagesController
	    selectedLanguageIso2: function() {
	    	$.each(this.locales, function(index, value) {
				if (value.selected) {
					this.selectedLanguageIso2 = index;
					// break.
					return false;
				}
			});
	    }.property('locales'),
	    user: {
	        name: "MA",
	        forename1: "Mathieu",
	        title: "MISTER"
	    }
	});
	Mdo.userManager = Ember.Object.create({
		find: function(id) {
			return Mdo.User.create(); 
		}
	});
});