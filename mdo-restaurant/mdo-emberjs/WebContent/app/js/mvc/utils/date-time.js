'use strict';

Mdo.DateTimeUtils = {
	time: function (date) {
		var result = {
			hour: date.getHours(),
			minute: date.getMinutes(),
			second: date.getSeconds(),
			millisec: date.getMilliseconds(),
			timezone: date.getTimezoneOffset()
		};
		return result;
	},
	formatDateTime: function (date, datePattern, dateTimeSeparator, timePattern) {
		var result = ""; 
		if (date) {
			result = $.datepicker.formatDate(datePattern, date) + dateTimeSeparator 
			+ $.datepicker.formatTime(timePattern, this.time(date));
		}
		return result;
	},
	formatDate: function (date, datePattern) {
		var result = ""; 
		if (date) {
			result = $.datepicker.formatDate(datePattern, date);
		}
		return result;
	},
	areEqualIgnoreTime: function (date1, date2) {
		var result = false;
		if (!date1 && !date2) {
			result = true;
		} else if (date1 && date2) {
			result = (date1.getFullYear() == date2.getFullYear()) && (date1.getMonth() == date2.getMonth()) && (date1.getDay() == date2.getDay());
		}
		return result;
	}
};
