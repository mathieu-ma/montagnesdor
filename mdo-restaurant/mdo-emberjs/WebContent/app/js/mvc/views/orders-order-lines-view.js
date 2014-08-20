/**
 * This file will contain all Mdo views.
 */

	Mdo.FIELD_ORDL_QUANTITY = "quantity";
	Mdo.FIELD_ORDL_CODE = "code";
	Mdo.FIELD_ORDL_LABEL = "label";
	Mdo.FIELD_ORDL_PRICE = "price";
	Mdo.FIELD_ORDL_AMOUNT = "amount";

	Mdo.OrderLinesLoadingView = Ember.View.extend({
		templateName: "orderLinesLoading",
	});

	Mdo.TdView = Ember.ContainerView.extend({
		tagName: "td",
		cellView: null,
		init: function() {
			this._super();
			var self = this;
			var childViews = self;
			childViews.pushObject(this.cellView);
		},
	});
	
	Mdo.TrView = Ember.ContainerView.extend({
		tagName: "tr",
		tdViewClass: Mdo.TdView,
		rowView: null,
		init: function() {
			this._super();
			var self = this;
			var childViews = self;
			$.each(self.rowView, function(key, cellView) {
				childViews.pushObject(self.tdViewClass.create({cellView: cellView}));
			});
		},
	});

	Mdo.THeadView = Ember.ContainerView.extend({
		tagName: "thead",
		rowViews: [],
		trViewClass: Mdo.TrView,
		RowViewsObserver: Ember.Object.extend({
			parent: null,
			arrayWillChange: function(contentArray, start, removeCount, addCount) {
			},
			arrayDidChange: function(contentArray, start, removeCount, addCount) {
				// For adding
				if (addCount>0) {
					var rowView = contentArray[start];
					var childViews = this.parent;
					childViews.insertAt(start, this.parent.trViewClass.create({rowView: rowView}));
				}
			},
		}),
		init: function() {
			this._super();
			var self = this;
			var childViews = self;

			$.each(self.rowViews, function(index, rowView) {
				childViews.pushObject(self.trViewClass.create({rowView: rowView}));
			});
			// addArrayObserver will notify any changes by calling RowViewsObserver.arrayDidChange method
			this.rowViews.addArrayObserver(this.RowViewsObserver.create({parent: self}));
		},
	});

	Mdo.TBodyView = Mdo.THeadView.extend({
		tagName: "tbody",
		currentIndexesDidChange: function() {
			var currentRowIndex = this.currentRowIndex;
			var currentCellIndex = this.currentCellIndex;
			this.focusCell(currentRowIndex, currentCellIndex);
		}.observes('currentRowIndex', 'currentCellIndex'),
		focusCell: function(rowIndex, cellIndex) {
			var cellView = this.get('childViews')[rowIndex].get('rowView')[cellIndex];
			if (cellView.get('disabled')) {
				// Back to the last not disable cell.
				this.set('currentRowIndex', this.backupCurrentRowIndex);
				this.set('currentCellIndex', this.backupCurrentCellIndex);
			} else {
				if (cellView.$()) {
					cellView.$()[0].focus();
				}
			}
		},
		backupCurrentIndexes: function() {
			this.backupCurrentRowIndex = this.currentRowIndex;
			this.backupCurrentCellIndex = this.currentCellIndex;			
		}.observesBefore('currentRowIndex', 'currentCellIndex'),
		backupCellValues: function(obj, keyName, value) {
			// Before row index change.
			var rowView = this.get('childViews')[this.currentRowIndex].get('rowView');
			$.each(rowView, function(key, cellView) {
				cellView.set('backupValue', cellView.get('value'));
			});
		}.observesBefore('currentRowIndex'),
		currentRowIndex: 0,
		currentCellIndex: 0,
		backupCurrentRowIndex: 0,
		backupCurrentCellIndex: 0,
		didInsertElement: function() {
			var lastRowIndex = this.get('childViews').length-1;
			if (lastRowIndex == 0) {
				this.focusCell(0, 0);
			} else {
				this.set('currentRowIndex', lastRowIndex);
			}
		},
	});

	Mdo.TFootView = Mdo.THeadView.extend({
		tagName: "tfoot",
	});

	Mdo.TdOrderLinesView = Mdo.TdView.extend({
	});

	Mdo.TrOrderLinesView = Mdo.TrView.extend({
		tdViewClass: Mdo.TdOrderLinesView,
	});

	Mdo.OrdersOrderLinesView = Ember.ContainerView.extend({
		tagName: "table",
		initialized: false,
		orderLinesBinding: 'controller.orderLines',
		initChildViews: function() {
			if (this.initialized) {
				return;
			}
			this.initialized = true;
			// This observer is used to insert children into []==childViews from the controller.content.
			// BE AWARE: This observer is triggered each time the controller.content change.
			// So we must check if the content is already inserted into the []==childViews
			var self = this;
			var orderLines = self.get('orderLines');
			if (orderLines) {
				var childViews = self;
				var rowViews = [];
				rowViews = [];
				var cellViews = [];
				cellViews.pushObject(Mdo.LabelView.create({labelKey: "order.lines.quantity"}));
				cellViews.pushObject(Mdo.LabelView.create({labelKey: "order.lines.code"}));
				cellViews.pushObject(Mdo.LabelView.create({labelKey: "order.lines.description"}));
				cellViews.pushObject(Mdo.LabelView.create({labelKey: "order.lines.unit.price"}));
				cellViews.pushObject(Mdo.LabelView.create({labelKey: "order.lines.amount"}));
				rowViews.pushObject(cellViews);
				this.headView = Mdo.THeadView.create({rowViews: rowViews});

				rowViews = [];
				$.each(orderLines, function(index, orderLine) {
					rowViews.pushObject(self._buildOrderLineViews(orderLine));
				});
				// Add last line for inserting new line
				cellViews = [];
				var currentRowViews = [];
				$.each(Mdo.OrderLineCellsViewsClasses, function(key, value) {
					var cellView = Mdo.OrderLineCellsViewsClasses[key].create({tableView: self, currentRowViews: currentRowViews, name: key});
					currentRowViews[key] = cellView;
					cellViews.pushObject(cellView);
				});
				rowViews.pushObject(cellViews);
				this.bodyView = Mdo.TBodyView.create({trViewClass: Mdo.TrOrderLinesView, rowViews: rowViews});

				childViews.pushObjects([
				       			     this.headView,
				    			     this.bodyView,
	            ]);

				// addArrayObserver will notify any changes by calling OrderLinesObserver.arrayDidChange method
				orderLines.addArrayObserver(this.OrderLinesObserver.create({parent: self}));
console.log("orderLines build")				
			}
		}.observes('orderLines'),
		OrderLinesObserver: Ember.Object.extend({
			parent: null,
			arrayWillChange: function(contentArray, start, removeCount, addCount) {
			},
			arrayDidChange: function(contentArray, start, removeCount, addCount) {
console.log("orderLines add=" + start + "," + addCount)				
				// For adding
				if (addCount>0) {
					var orderLine = contentArray[start];
					this.parent.bodyView.rowViews.insertAt(start, this.parent._buildOrderLineViews(orderLine));
				}
			},
		}),
		headView: null,
		bodyView: null,
		_buildOrderLineViews: function(orderLine) {
			var self = this;
			var currentRowViews = [];
			var cellViews = [];
			$.each(orderLine, function(key, value) {
				var cellView = null;
				// TODO: Check the Mdo.OrderLineCellsViewsClasses.length and size of the order line fields
				cellView = Mdo.OrderLineCellsViewsClasses[key].create({tableView: self, currentRowViews: currentRowViews, name: key, value: value, backupValue: value, currentOrderLine: orderLine});
				currentRowViews[key] = cellView;
				cellViews.pushObject(cellView);
			});
			return cellViews;
		},
		didInsertElement: function() {
			var self = this;
			$('#addOrderLineTest').click(function() {
				self.get('orderLines').pushObject({quantity: 2, code: 13, label: "Salade aux crabes", price: 2.5, amount: 5});
//				self.get('orderLines').insertAt(0, {quantity: 2, code: 13, label: "Salade aux crabes", price: 2.5, amount: 5});
			});
		},
	});

	Mdo.OrderLineTextView = Ember.TextField.extend({
		classNames: ['ui-widget-content'],
		classNameBindings: ['textClass'],
		textClass: function() {
			var result = 'no-border ui-widget-content';
			if (this.focus) {
				result = '';
			}
			return result;
		}.property('focus'),
		field: null,
		focus: false,
		disabled: true,
		tableView: null,
		bodyView: function() {
			return this.tableView.bodyView;
		}.property(),
		currentRowViews: null,
		currentOrderLine: null,
		lastRowIndex: function() {
			return this.get('bodyView.rowViews').length -1;
		}.property('bodyView.rowViews.@each'),
		isLastRow: function() {
			return this.getRowIndex() + 1 == this.get('bodyView.rowViews').length;
		}.property('rowIndex'),
		isLastCell: function() {
			return this.get('cellIndex') + 1 == this.get('bodyView.rowViews')[0].length;
		}.property('cellIndex'),
		isFirstCell: function() {
			return this.get('cellIndex') == 0;
		}.property('cellIndex'),
		cellIndex: function() {
			// this.$() == jQuery text field. 
			// this.$().parent() ==  jQuery td.
			return this.$().parent()[0].cellIndex;
		}.property(),
		getRowIndex: function() {
			// this.$() == jQuery text field. 
			// this.$().parent() ==  jQuery td.
			// this.$().parent().parent() == jQuery tr
			return this.$().parent().parent()[0].sectionRowIndex;
		},
		didInsertElement: function() {
		},
		backupValue: null,
		focusOut: function(event) {
			// this.currentOrderLine is null when last row.
			if (this.currentOrderLine) {
				// This will update the instance order line of data array coming from controller
				this.currentOrderLine[this.field] = this.value;
			}
			this.set('focus', false);
		},
		click: function() {
			// User clicks on a cell
			this.get('bodyView').setProperties({currentRowIndex: this.getRowIndex(), currentCellIndex: this.get('cellIndex')});
		},
		focusIn: function(event) {
			// focus occurs when user clicks on a cell or javascript performs a focus.
			this.set('focus', true);
		},
		keyUp: function(event) {
			switch(event.keyCode) {     	  
				//13 == key Enter
				case 13 :
					this.forward();
				return;
				case 27 :
					this.backward();
				return;
				//38 == key Up
				case 38 :
					this.up();
				return;
				//40 == key Down
				case 40 :
					this.down();
				return;
			};
			return false;
		},
		backward: function() {
			var self = this;
			var backupValue = self.get('backupValue');
			self.set('value', backupValue);
			
			// Default: Goto previous cell.
			var backCellIndex = self.get('cellIndex') - 1;
			var backRowIndex = self.getRowIndex();
			if (backCellIndex < 0) {
				// Goto last row and first cell.
				backCellIndex = 0;
				backRowIndex = self.get('lastRowIndex');
				if (self.get('isLastRow')) {
					// Refresh order header and display list of orders.
					self.get('parentView.controller').gotoOrders();
				}
			}
			self.get('bodyView').setProperties({currentRowIndex: backRowIndex, currentCellIndex: backCellIndex});
		},
		forward: function() {
			var self = this;
			var forwardCellIndex = self.get('cellIndex') + 1;
			if (self.get('isLastCell')) {
				forwardCellIndex--;
			}
			// Goto next cell
			self.get('bodyView').set("currentCellIndex", forwardCellIndex);
		},
		up: function() {
			var self = this;
			var upRowIndex = self.getRowIndex() - 1;
			if (upRowIndex < 0) {
				upRowIndex = 0;
			}
			self.get('bodyView').set("currentRowIndex", upRowIndex);
		},
		down: function() {
			var self = this;
			var downRowIndex = self.getRowIndex() + 1;
			if (self.get('isLastRow')) {
				downRowIndex--;
			}
			self.get('bodyView').set("currentRowIndex", downRowIndex);
		},
	});
	
	Mdo.OrderLineQuantityTextView = Mdo.OrderLineTextView.extend({
		field: Mdo.FIELD_ORDL_QUANTITY,
		disabled: false,
		focusIn: function() {
			this._super();
			if (this.get('isLastRow')) {
				// Reset other cell values
				this.get('currentRowViews')[Mdo.FIELD_ORDL_QUANTITY].set('value', null);
				this.get('currentRowViews')[Mdo.FIELD_ORDL_CODE].set('value', null);
			}
		},
	});

	Mdo.OrderLineCodeTextView = Mdo.OrderLineTextView.extend({
		field: Mdo.FIELD_ORDL_CODE,
		disabled: false,
		quantityView: function() {
			return this.get('currentRowViews')[Mdo.FIELD_ORDL_QUANTITY];
		}.property(),
		focusIn: function() {
			this._super();
			if (!$.trim(this.get('quantityView.value'))) {
				// Default quantity value.
				this.set('quantityView.value', 1);
			}
		},
		focusOut: function() {
			this._super();
			if (!$.trim(this.get('value'))) {
				// Reset quantity value.
				this.set('quantityView.value', null);
			}
		},
		forward: function() {
			var controller = this.get('parentView.controller');
			// Process order line by code
			controller.findOrderLine(this.getRowIndex(), this.get('value'));
			// Goto next row and first cell if order line found
			this.get('bodyView').setProperties({currentRowIndex: this.get('lastRowIndex'), currentCellIndex: 0});
		},
		didInsertElement: function() {
		},
	});

	Mdo.OrderLineLabelTextView = Mdo.OrderLineTextView.extend({
		field: Mdo.FIELD_ORDL_LABEL,
	});

	Mdo.OrderLinePriceTextView = Mdo.OrderLineTextView.extend({
		field: Mdo.FIELD_ORDL_PRICE,
	});

	Mdo.OrderLineAmountTextView = Mdo.OrderLineTextView.extend({
		field: Mdo.FIELD_ORDL_AMOUNT,
		quantityView: function() {
			return this.get('currentRowViews')[Mdo.FIELD_ORDL_QUANTITY];
		}.property(),
		priceView: function() {
			return this.get('currentRowViews')[Mdo.FIELD_ORDL_PRICE];
		}.property(),
		valueDidChange: function() {
			this.set('value', this.get('quantityView.value')*this.get('priceView.value'));
		}.observes('quantityView.value', 'priceView.value'),
	});

	Mdo.OrderLineCellsViewsClasses = {};
	Mdo.OrderLineCellsViewsClasses[Mdo.FIELD_ORDL_QUANTITY] = Mdo.OrderLineQuantityTextView;
	Mdo.OrderLineCellsViewsClasses[Mdo.FIELD_ORDL_CODE] = Mdo.OrderLineCodeTextView;
	Mdo.OrderLineCellsViewsClasses[Mdo.FIELD_ORDL_LABEL] = Mdo.OrderLineLabelTextView;
	Mdo.OrderLineCellsViewsClasses[Mdo.FIELD_ORDL_PRICE] = Mdo.OrderLinePriceTextView;
	Mdo.OrderLineCellsViewsClasses[Mdo.FIELD_ORDL_AMOUNT] = Mdo.OrderLineAmountTextView;
