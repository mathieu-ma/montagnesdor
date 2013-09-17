/**
 * This file will contain all Mdo controllers.
 */
	Mdo.HeaderOrderController = Ember.ObjectController.extend({
		headerOrder: null,
		currentOrderNumber: function() {
			return this.get('headerOrder.currentOrderNumber');
		}.property('headerOrder.currentOrderNumber'),
		currentOrderCustomersNumber: function() {
			return this.get('headerOrder.currentOrderCustomersNumber');
		}.property('headerOrder.currentOrderCustomersNumber'),
		currentTableTypeBinding: function() {
			return this.get('headerOrder.currentOrderType');
		}.property('headerOrder.currentOrderType'),
		currentTakeaway: function() {
			var result = false;
			if (this.get('currentOrderType') == 'TAKE_AWAY') {
				result = true;
			}
			return result;
		}.property('currentOrderType'),
		orderLines: [],
		user: null,
		currentStep: 0,
		resetHeaderOrder: false,
		didResetHeaderOrderChange: function() {
			if (this.get('resetHeaderOrder')) {
				this.set('currentStep', 0);
				// Could not modify currentOrderNumber and currentOrderCustomersNumber here because of binding Text View
				this.set('resetHeaderOrder', false);
			}
		}.observes('resetHeaderOrder'),
		actions: {
			searchOrder: function() {
				var self = this;
				var tableNumber = self.currentOrderNumber;
				// Before Ajax call, change the step in order to disable the view
				// Go forward to next step.
				self.incrementProperty("currentStep");
				
				// Ajax 
				// 1) Find order call.
				// 2) If no order found then create empty order.
				var restaurantId = 1; //self.user.restaurantId;
				var userId = 1; //self.user.id;
				var url = "/" + restaurantId + "/" + userId 
							+ "/table/header/by/number/" + tableNumber;
				var deferredFunction = function() {
					return $.get(url);
				};

	var deferred = $.Deferred();
	//deferred.reject()
	deferred.resolve({
		id: 1,
		registrationDate: new Date(1970, 7, 15),
		number: tableNumber,
		takeaway: false,
		customersNumber: 11,
		currentOrderNumber: "5G"
	})
	deferredFunction = function() {return deferred;};

				$.when(deferredFunction()).done(function(headerOrder) {
					// Success Ajax when searching order
					// Refresh all fields
					//self.set('headerOrder', Mdo.HeaderOrder.create(headerOrder));
					self.set('currentOrderNumber', headerOrder.currentOrderNumber);
					self.set('currentOrderCustomersNumber', headerOrder.customersNumber);
				}).fail(function(value) {
					// Could not find order and create empty order
					// 1) Alert user for technical issue
					// 2) Go back the previous step.
					self.set('currentStep', 0);
				});
			},
			saveOrder: function(customersNumber) {
				var self = this;
	//
//				var self = this;
//				var step = self.get('currentStep');
//				// Before Ajax call, change the step in order to disable the view
//				// Go forward to next step.
//				self.incrementProperty("currentStep");
	//
//				// Ajax Update call
//				// Update the customers number.
//				var url = "/orders/update/table/" + self.headerOrder.id + "/customers/number/" + customersNumber;
//				var deferredFunction = function() {
//					return $.post(url);
//				};
	//
	//var deferred = $.Deferred();
	////deferred.reject()
	//deferred.resolve()
	//deferredFunction = function() {return deferred;};
	//
//				$.when(deferredFunction()).done(function() {
//					self.set('headerOrder.customersNumber', customersNumber);
//			    	// Insert OrderLinesView in body outlet with OrderLinesController content. 
//					// Forward to Display list of order lines
//					self.target.transitionTo('orders.displayOrderLines', {dinnerTableId: self.headerOrder.id});
//					// Redirect to Display list of order lines
//					//self.target.send('displayOrderLines', self.headerOrder.id);
//				}).fail(function(value) {
//					// In case of error Ajax error
//					// Go back the previous step.
//					self.set('step', step);
//				});
				
				self.set('currentStep', 3);
				self.transitionToRoute('orders.orderLines', '1');
			},			
		}
	});