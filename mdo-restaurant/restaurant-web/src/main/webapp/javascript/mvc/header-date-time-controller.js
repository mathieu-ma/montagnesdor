/**
 * This file will contain all Mdo controllers.
 */
$(document).ready(function() {
	Mdo.DateTimeForm = Ember.Object.extend({
		date: null,
		password: null,
	});
	Mdo.HeaderDateTimeController = Ember.ObjectController.extend({
		dateTime: Mdo.DateTime.create(),
		displayedDate: function() {
			var result = new Date();
			if (this.dateTime.currentTableRegistrationDate) {
				result = this.dateTime.currentTableRegistrationDate;
			} else if (this.dateTime.userEntryDate) {
				result = this.dateTime.userEntryDate;
			}
			return result;
		}.property('dateTime.userEntryDate', 'dateTime.currentTableRegistrationDate'),
		// Used for refreshing time in the method startDateTime in order to display date.
		displayedTime: new Date(),
		displayedFormattedDate: function() {
			var displayedDate = this.get('displayedDate');			
			var formattedDisplayedDate = $.datepicker.formatDate(this.dateTime.datePattern, displayedDate);
			var formattedDisplayedTime = $.datepicker.formatTime(this.dateTime.timePattern, this.time(this.displayedTime));
			var result = formattedDisplayedDate + this.dateTime.dateTimeSeparator + formattedDisplayedTime;
			return result;
			// Because of setInterval in method startDateTime, we could avoid to make this property depends on the property 'displayedDate'.
		}.property('displayedDate', 'displayedTime'),
		dateDialog: null,
		openDateDialog: function () {
			var self = this;
			this.dateDialog = Mdo.DateDialogView.create({
				controller: self,
				displayedDate: self.displayedDate,
				changeDateTimeController: self.dateTime.changeDateTimeController,
			});
			this.dateDialog.openDialog();
		},
		closeDateDialog: function () {
		},
		time: function(date) {
			var result = {
				hour: date.getHours(),
				minute: date.getMinutes(),
				second: date.getSeconds(),
				millisec: date.getMilliseconds(),
				timezone: date.getTimezoneOffset()
			};
			return result;
		},
		formatDateTime: function(date) {
			var result = $.datepicker.formatDate(this.dateTime.datePattern, date) + this.dateTime.dateTimeSeparator 
			+ $.datepicker.formatTime(this.dateTime.timePattern, this.time(new Date()));
			return result;
		},
		saveDateTime: function(dateTimeForm) {
			var self = this;
			Ember.run.later(null, function() {
				// TODO: Ajax to confirm password the set displayedDate
				// If password false then display warning message
				self.set('dateTime.userEntryDate', dateTimeForm.date);
			}, 2000);
		},
		startDateTime: function(datePattern) {
			var self = this;
			self.dateTime.reopen({
				datePattern: datePattern,
				// Extends 3 following properties: think as DTO.
				currentTableRegistrationDate: null,
				userEntryDate: new Date(),
				changeDateTimeController: function(form) {
					self.target.get('headerDateTimeController')['saveDateTime'](form);
				},				
			});
			window.setInterval(function() {
				// This will fire the change of displayedFormattedDate property. 
				self.set('displayedTime', new Date());
			},
			1000);
		}
	});
});
