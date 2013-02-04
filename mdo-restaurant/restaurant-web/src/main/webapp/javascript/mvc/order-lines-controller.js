/**
 * This file will contain all Mdo controllers.
 */
$(document).ready(function() {
	Mdo.OrderLinesLoadingController = Ember.Controller.extend({
		orderLines: [],
		/**
			createOutletView` is a hook you may want to override if you need to do
			something special with the view created for the outlet. For example
			you may want to implement views sharing across outlets.
	
			@method createOutletView
			@param outletName {String}
			@param viewClass {Ember.View}
		*/
		createOutletView: function(outletName, viewClass) {
			// viewClass is OrderLinesView 
			var result =  viewClass.create({orderLines: this.orderLines});
			var controller = this.get('controllers')[outletName + 'Controller'];
			controller.set('view', result);
			return result;
		}
	});
	Mdo.OrderLinesController = Ember.Controller.extend({
		headerButtonsController: null,
		refreshOrderLines: function() {
			this.headerButtonsController.refreshCurrentSelection();
		},
		findOrderLine: function(view) {
			var self = this;
			var form = {"quantity": 2,
					"orderCode": view.get("value")};
			var restaurantId = 1;
			var locId = "1";
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

//var deferred = $.Deferred();
////deferred.reject()
//deferred.resolve({
//	headerOrder: {
//		id: id,
//		registrationDate: new Date(1970, 7, 15),
//		number: "tableNumber",
//		takeaway: false,
//		customersNumber: 7,
//	},
//	orderLines: [
//	    {quantity: 2.5, code: 11, label: "Nems", price: 2, amount: 5},
//	    {quantity: 1.0, code: 12, label: "Salade aux crevettes", price: 2.8, amount: 2.8},
//	]
//})
//deferredFunction = function() {return deferred;};

			$.when(deferredFunction()).done(function(orderLine) {
				//var orderLine = {"quantity":2,"code":"11","label":"Nems","unitPrice":3.5000,"amount":7.0000,"vatId":1,"product":{"id":1,"code":"11","price":3.5000,"colorRGB":"","label":"Nems","vatId":1}};
console.log(orderLine)
alert(1)
			}).fail(function(value) {
				// In case of error Ajax error
console.log(self)
alert("error==" + self.get('path'))
			});
		}
	});
});
