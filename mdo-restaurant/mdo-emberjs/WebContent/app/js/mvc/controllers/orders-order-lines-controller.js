/**
 * This file will contain all Mdo controllers.
 */
	Mdo.OrdersOrderLinesController = Ember.ObjectController.extend({
		needs: ["headerOrder"],
		order: null,
		orderLines: [],
		fillOrder: function(orderId) {
			this.get('controllers.headerOrder').set('currentStep', 3);
			this.findOrder(orderId);
		},
		findOrder: function(id) {
			var self = this;
			// Ajax find dinner table call
			var url = "/orders/table/header/" + id;
			var deferredFunction = function() {
				return $.get(url);
			};

var deferred = $.Deferred();
//deferred.reject()
deferred.resolve({
	headerOrder: {
		id: id,
		registrationDate: new Date(1970, 7, 15),
		number: "tableNumber",
		takeaway: false,
		customersNumber: 7,
	},
	orderLines: [
	    {quantity: 2.5, code: 11, label: "Nems", price: 2, amount: 5},
	    {quantity: 1.0, code: 12, label: "Salade aux crevettes", price: 2.8, amount: 2.8},
	]
})
deferredFunction = function() {return deferred;};

			$.when(deferredFunction()).done(function(order) {
				self.set('order', order);
				self.set('orderLines', order.orderLines);
console.log(self.get('orderLines'))
				
			}).fail(function(value) {
				// In case of error Ajax error
				// Go back the previous step.
				self.target.send('gotoBackToNumber');
			});
		},
		gotoOrders: function() {
			this.transitionToRoute("orders");
		},
		findOrderLine: function(index, orderCode) {
			var self = this;

			var form = {"quantity": 2,
					"orderCode": orderCode};
			var restaurantId = 1;
			var locId = "1";

			var updatingOrderLine = self.orderLines[index];
			if (!updatingOrderLine) {
			} else {
				// The order line does exist, we may have to update it.
				console.log(updatingOrderLine)
			}
			// The order line does not yet exist, we may have to create it.
			var url = "orders/" + restaurantId + "/find/order/line/" + locId;
	 		// The JSON is come from json2.js
	 		var data = JSON.stringify(form); // or keys for GET method.
			var deferredFunction = function() {
				return $.ajax({
					url: url,
					type: "POST",
					contentType: "application/json",
		 	        data : data,
		 	        dataType : "json"
				});
			};

var deferred = $.Deferred();
////deferred.reject()
deferred.resolve(
	    {quantity: 1.5, code: '13', label: "Salade aux crabes", price: 2, amount: 3}
)
deferredFunction = function() {return deferred;};

			$.when(deferredFunction()).done(function(orderLine) {
				//var orderLine = {"quantity":2,"code":"11","label":"Nems","unitPrice":3.5000,"amount":7.0000,"vatId":1,"product":{"id":1,"code":"11","price":3.5000,"colorRGB":"","label":"Nems","vatId":1}};
				// Check if we have to update or insert the order line
				self.orderLines.pushObject(orderLine);
			}).fail(function(value) {
				// In case of error Ajax error
console.log(self)
alert("error==" + self.get('path'))
			});				
		},		
	});
	 	