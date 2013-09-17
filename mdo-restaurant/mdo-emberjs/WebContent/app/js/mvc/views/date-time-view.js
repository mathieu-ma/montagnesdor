'use strict';

/**
 * This file will contain details on Mdo DateTimeView.
 */

	Mdo.DateTimeView = Ember.View.extend({
		tagName: 'a',
		classNames: ['displayed-date'],
		classNameBindings: ['displayedDateClass'],
		toggleClass: true,
		// This value is bind to controller.
		displayedDate: new Date(),
		// This value is bind to controller.
		displayedDateState: 'displayed-date-now',
		// This value is bind to controller.
		dateTimeFormat: Mdo.DefaultDateTimeFormat.create(),
		displayedDateClass: function() {
			// white: when user entry date is now(i.e null) and registration date is null
			// green: when user entry date is now(i.e null) and registration date is NOT null and equals to now(i.e registration date == entry date)
			// orange: when user entry date is now(i.e null) and registration date is NOT null and not equal to now(i.e registration date != entry date)
			// blue: when user entry date is not null(i.e != now) and registration date is null
			// pink: when user entry date is not null(i.e != now) and registration date is NOT null and equals to entry date
			// red: when user entry date is not null(i.e != now) and registration date is NOT null and not equals to entry date
			var result = this.get('displayedDateState');
			return result;
		}.property('displayedDateState'),
		displayedDateChanged: function() {
			var displayedDate = this.get('displayedDate');
			// Format the displayedDate date.
			var result = this.dateTimeFormat.formatDateTime(displayedDate);
			// Display the formatted displayedDate each time the displayedDate changed.
			$(this.get('element')).html(result);
		}.observes('displayedDate'),
		displayedDateStateChanged: function() {
			//Launch blink in case of displayedDateState contains '-vs-'
			var result = this.get('displayedDateState');
			if (result.indexOf('displayed-date-registration-vs-')>=0) {
				if (this.toggleClass) {
					this.toggleClass = false;
					result += ' odd';
				} else {
					this.toggleClass = true;
					result += ' even';
				}
			}
		}.observes('displayedDateState'),
		didInsertElement: function() {
			// Display the formatted displayedDate first time.
			$(this.get('element')).html(this.dateTimeFormat.formatDateTime(this.get('displayedDate')));

			///TODO remove the tests below.
			this.testing()
			$("#opendialogbuttontest").click(function() {
				$("#opendialogtest").dialog().dialog("open");
			});
			
		},
		click: function() {
console.log('Send to DateTimeDialogOpenRoute to open date dialog')
			// Send to the applicationRoute events
			this.get('controller').send("openDateTimeDialog");
		},
		testing: function() {
			var self = this;
			$('#tableRegistrationDateTest').click(function() {
				var toggle = $(this).data("toggle") | 0;
				var date = null;
				var htmlSuffix = "";
				switch(toggle) {
					case 0:
						toggle = 1;
						date = new Date();
						htmlSuffix = " ==> Set Another Day";
					break;
					case 1:
						toggle = 2;
						date = new Date(1970, 7, 15);
						htmlSuffix = " ==> Reset";
					break;
					case 2:
						toggle = 0;
						date = null;
						htmlSuffix = " ==> Set Today";
					break;
				}
				$(this).data("toggle", toggle);
				$(this).html("tableRegistrationDateTest " + self.dateTimeFormat.formatDate(date) + htmlSuffix);
				self.get('controller').set('registrationDate', date); 
			});
			$('#userEntryDateTest').click(function() {
				var toggle = $(this).data("toggle") | 0;
				var date = null;
				var htmlSuffix = "";
				switch(toggle) {
					case 0:
						toggle = 1;
						date = new Date(1970, 7, 15);
						htmlSuffix = " ==> Reset";
					break;
					case 1:
						toggle = 0;
						date = null;
						htmlSuffix = " ==> Set";
					break;
				}
				$(this).data("toggle", toggle);
				$(this).html("userEntryDateTest " + self.dateTimeFormat.formatDate(date) + htmlSuffix);
				self.get('controller').set('userEntryDate', date); 
			});
		}
	});
	
