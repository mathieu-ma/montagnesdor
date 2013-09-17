'use strict';

/**
 * This file will contain Mdo header controllers.
 */

Mdo.DateTimeDialogController = Ember.ObjectController.extend({
});

Mdo.UserEntryDateTimeForm = Ember.Object.extend({
	date: null,
	password: null,
});

Mdo.DateTimeDialogController = Ember.ObjectController.extend({
	userEntryDate: new Date(),
	dateDialog: Mdo.DateDialogView.create(),
	actions: {
		openDateDialog: function () {
			this.dateDialog.set('controller', this);
			var userEntryDateChecked = this.get('userEntryDate') || new Date();
			this.dateDialog.set('userEntryDate', userEntryDateChecked);
			this.dateDialog.openDialog();
		},
		saveDate: function (form) {
			var self = this;
			// Check form
			// Check password
			Ember.run.later(null, function() {
				// TODO: Ajax to confirm password the set displayedDate
				// If password false then display warning message
				// Password OK then change date
				var date = form.get("date");
				if (Mdo.DateTimeUtils.areEqualIgnoreTime(new Date(), date)) {
					date = null;
				}
				self.set("userEntryDate", date);
			}, 2000);
		},
	}
});
