/*global Mdo DS Ember */
'use strict';

Mdo.UserAuthentication = DS.Model.extend({
    user: DS.belongsTo('user'),
    restaurant: DS.belongsTo('restaurant', {
        async: true
    }),
    login: DS.attr('string'),
	locales: DS.hasMany('locale', {
        async: true
    }),
    datePattern: DS.attr('string'),
    // Will be set later in HeaderLanguagesController
    selectedLanguageIso2: function() {
    	var result = "";
    	$.each(this.locales, function(index, value) {
			if (value.selected) {
				result = value.languageIso2;
				// break.
				return false;
			}
		});
    	return result;
    }.property('locales'),
});

Mdo.Locale = DS.Model.extend({
	languageIso2: DS.attr('string'),
    displayLanguage: DS.attr('string'),
    selected: DS.attr('boolean'),
});

Mdo.User = DS.Model.extend({
    name: DS.attr('string'),
    forename1: DS.attr('string'),
    title: DS.attr('string'),
});

Mdo.Restaurant = DS.Model.extend({
});

// Tests
Mdo.User.FIXTURES = [
	{
		id: 1,
    	name: "MA",
        forename1: "Mathieu",
        title: "MISTER"
	}
];

Mdo.Restaurant.FIXTURES = [
   	{
		id: 1,
	}
];

Mdo.Locale.FIXTURES = [
	{id: 1, languageIso2: "fr", displayLanguage: "français", selected: true},
	{id: 2, languageIso2: "zh", displayLanguage: "chinois", selected: false},
];

Mdo.UserAuthentication.FIXTURES = [
	{
		id: 1,
		user: 1,
		restaurant: 1,
		login: "mch",
		locales: [ 1, 2],
		datePattern: 'DD dd MM yy',
	}
];
