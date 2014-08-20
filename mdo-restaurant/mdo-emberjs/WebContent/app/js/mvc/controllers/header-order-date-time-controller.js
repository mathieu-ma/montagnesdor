'use strict';

/**
 * This file will contain all Mdo HeaderDateTimeController details.
 */

Mdo.HeaderOrderDateTimeController = Ember.ObjectController.extend({
	// Used for refreshing time in the method startDateTime in order to display date.
	timer: new Date(),
	init: function() {
	    this._super();
	    this.startDateTime();
	},
	// This value is bind to controller headerOrderController.headerOrder.registrationDate.
	registrationDate: null,
	// This value is bind to controller DateTimeDialogController.userEntryDate.
	userEntryDate: null,
	// This value is come from db/router.
	dateTimeFormat: Mdo.DefaultDateTimeFormat.create(),
	displayedDate: function() {
		// timer == new Date()
		var result = this.get('timer');
		var userEntryDate = this.get('userEntryDate');
		var registrationDate = this.get('registrationDate');
		if (registrationDate) {
			result = registrationDate;
		} else if (userEntryDate) {
			result = userEntryDate;
		}
		return result;
	}.property('userEntryDate', 'registrationDate', 'timer'),
	displayedDateState: function() {
		// white: when user entry date is now(i.e null) and registration date is null
		// green: when user entry date is now(i.e null) and registration date is NOT null and equals to now(i.e registration date == entry date)
		// orange: when user entry date is now(i.e null) and registration date is NOT null and not equal to now(i.e registration date != entry date)
		// blue: when user entry date is not null(i.e != now) and registration date is null
		// pink: when user entry date is not null(i.e != now) and registration date is NOT null and equals to entry date(i.e registration date == entry date)
		// red: when user entry date is not null(i.e != now) and registration date is NOT null and not equals to entry date(i.e registration date != entry date)
		var result = 'displayed-date-now';
		// now is only used when user entry date is null.
		// timer == new Date()
		var now = this.get('timer');
		var userEntryDate = this.get('userEntryDate');
		var registrationDate = this.get('registrationDate');
		if (userEntryDate) {
			if (registrationDate) {
				if (Mdo.DateTimeUtils.areEqualIgnoreTime(userEntryDate, registrationDate)) {
					// pink
					result = 'displayed-date-registration-user-entry';
				} else {
					// red
					result = 'displayed-date-registration-vs-user-entry';
				}
			} else {
				// blue
				result = 'displayed-date-user-entry';
			}
		} else {
			if (registrationDate) {
				if (Mdo.DateTimeUtils.areEqualIgnoreTime(now, registrationDate)) {
					// green
					result = 'displayed-date-registration-now';
				} else {
					// orange
					result = 'displayed-date-registration-vs-now';
				}
			} else {
				// white
				// Note: useless here but set for understanding.
				result = 'displayed-date-now';
			}
		}
		return result;
	}.property('displayedDate'),
	startDateTime: function(datePattern) {
		var self = this;
		window.setInterval(function() {
			// This will fire the change of displayedFormattedDate property. 
			self.set('timer', new Date());
		},
		1000);
	},
});

