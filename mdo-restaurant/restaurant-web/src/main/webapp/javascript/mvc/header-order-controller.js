/**
 * This file will contain all Mdo controllers.
 */
$(document).ready(function() {

	Mdo.HeaderOrderController = Ember.ObjectController.extend({
		// This will be connected in the router.
		headerDateTimeController: null,
		headerOrder: Mdo.HeaderOrder.create(),
		step: 0,
		resetHeaderOrder: function() {
			this.headerDateTimeController.set('dateTime.currentTableRegistrationDate', null);
			this.set('step', 0);
			this.set('headerOrder', Mdo.HeaderOrder.create());
		},
		backToNumber: function() {
			this.set('step', 0);
			var headerOrder = Mdo.HeaderOrder.create();
			headerOrder.number = this.headerOrder.number; 
			this.set('headerOrder', headerOrder);
		},		
		customersNumber: function(restaurantId, userAuthenticationId, tableNumber) {
			var self = this;
			var step = self.get('step');
			// Before Ajax call, change the step in order to disable the view
			// Go forward to next step.
			self.set('step', step + 1);
			
			// Ajax find dinner table call
			var url = "/" + restaurantId + "/" + userAuthenticationId + "/table/header/by/number/" + tableNumber;
			var deferredFunction = function() {
				return $.get(url);
			};

console.log(self.target)			
var deferred = $.Deferred();
//deferred.reject()
deferred.resolve({
	id: 1,
	number: tableNumber,
	takeaway: false,
	customersNumber: 7,
})
deferredFunction = function() {return deferred;};

			$.when(deferredFunction()).done(function(value) {
				// TODO: Ajax to check number
				// Refresh all fields
				var headerOrder = Mdo.HeaderOrder.create(value); 
				self.set('headerOrder', headerOrder);
			}).fail(function(value) {
				// Go back the previous step.
				self.target.send('gotoBackToNumber');
			});
		},
		updateCustomersNumber: function(customersNumber) {
			var self = this;
			var step = self.get('step');
			// Before Ajax call, change the step in order to disable the view
			// Go forward to next step.
			self.set('step', step + 1);

			// Ajax Update call
			// Update the customers number.
			var url = "/update/table/" + self.headerOrder.id + "/customers/number/" + customersNumber;
			var deferredFunction = function() {
				return $.post(url);
			};

var deferred = $.Deferred();
//deferred.reject()
deferred.resolve()
deferredFunction = function() {return deferred;};

			$.when(deferredFunction()).done(function() {
				self.set('headerOrder.customersNumber', customersNumber);
		    	// Insert OrderLinesView in body outlet with OrderLinesController content. 
				// Forward to Display list of order lines
				self.target.transitionTo('displayOrderLines', self.headerOrder.id);
				// Redirect to Display list of order lines
				//self.target.send('displayOrderLines', self.headerOrder.id);
			}).fail(function(value) {
				// In case of error Ajax error
				// Go back the previous step.
				self.set('step', step);
			});
		},
		displayOrderLines: function(id) {
			var self = this;
			
			// Ajax find dinner table call
			var url = "/table/header/" + id;
			var deferredFunction = function() {
				return $.get(url);
			};

var deferred = $.Deferred();
//deferred.reject()
deferred.resolve()
deferredFunction = function() {return deferred;};

			$.when(deferredFunction()).done(function(dinnerTable) {
console.log(dinnerTable)
				if (!self.headerOrder.id) {
					// Refresh the headerOrder in case of reload by user.
				}
				self.set('step', 3);
				self.set('headerOrder', headerOrder);
				self.headerDateTimeController.set('dateTime.currentTableRegistrationDate', new Date(1970, 7, 15));
			}).fail(function(value) {
				// In case of error Ajax error
				// Go back the previous step.
				self.target.send('gotoBackToNumber');
			});
		}
	});
});
