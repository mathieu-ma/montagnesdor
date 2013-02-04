/**
 * This file will contain all Mdo views.
 */
$(document).ready(function() {
	Mdo.OrderLinesLoadingView = Ember.View.extend({
		templateName: "orderLinesLoading",
	});

	Mdo.TdView = Ember.ContainerView.extend({
		tagName: "td",
		cellView: null,
		init: function() {
			this.get('childViews').pushObject(this.cellView);
			this._super();
		},
	});
	
	Mdo.TrView = Ember.ContainerView.extend({
		tagName: "tr",
		tdViewClass: Mdo.TdView,
		rowView: null,
		init: function() {
			var self = this;
			var childViews = self.get('childViews');
			$.each(self.rowView, function(key, cellView) {
				childViews.pushObject(self.tdViewClass.create({cellView: cellView}));
			});
			this._super();
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
					var childViews = this.parent.get('childViews');
					childViews.insertAt(start, this.parent.trViewClass.create({rowView: rowView}));
				}
			},
		}),
		init: function() {
			var self = this;
			var childViews = self.get('childViews');
			$.each(self.rowViews, function(index, rowView) {
				childViews.pushObject(self.trViewClass.create({rowView: rowView}));
			});
			this._super();
			// addArrayObserver will notify any changes by calling RowViewsObserver.arrayDidChange method
			this.rowViews.addArrayObserver(this.RowViewsObserver.create({parent: self}));
		},
	});

	Mdo.TBodyView = Mdo.THeadView.extend({
		tagName: "tbody",
		focusCurrentRow: function() {
			var currentCellView = this.get('childViews')[this.currentRowIndex].get('rowView')[this.currentCellIndex];
			if (currentCellView.get('disabled')) {
//				currentCellView.set('disabled', false);
				this.set('currentRowIndex', this.backupCurrentRowIndex);
				this.set('currentCellIndex', this.backupCurrentCellIndex);
			} else {
				currentCellView.$()[0].focus();
			}
		}.observes('currentRowIndex', 'currentCellIndex'),
		backupCurrentIndex: function() {
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
			this.set('currentRowIndex', lastRowIndex);
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

	Mdo.OrderLinesView = Ember.ContainerView.extend({
		tagName: "table",
		orderLines: [],
		OrderLinesObserver: Ember.Object.extend({
			parent: null,
			arrayWillChange: function(contentArray, start, removeCount, addCount) {
			},
			arrayDidChange: function(contentArray, start, removeCount, addCount) {
				// For adding
				if (addCount>0) {
					var orderLine = contentArray[start];
					this.parent.bodyView.rowViews.insertAt(start, this.parent._buildOrderLineViews(orderLine));
				}
			},
		}),
		init: function() {
			var self = this;
			var childViews = self.get('childViews');

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
			$.each(self.orderLines, function(index, orderLine) {
				rowViews.pushObject(self._buildOrderLineViews(orderLine));
			});
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

			this._super();

			// addArrayObserver will notify any changes by calling OrderLinesObserver.arrayDidChange method
			this.orderLines.addArrayObserver(this.OrderLinesObserver.create({parent: self}));
		},
		headView: null,
		bodyView: null,
		_buildOrderLineViews: function(orderLine) {
			var self = this;
			var currentRowViews = [];
			var cellViews = [];
			$.each(orderLine, function(key, value) {
				var cellView = null;
				cellView = Mdo.OrderLineCellsViewsClasses[key].create({tableView: self, currentRowViews: currentRowViews, name: key, value: value, backupValue: value});
				currentRowViews[key] = cellView;
				cellViews.pushObject(cellView);
			});
			return cellViews;
		},
		didInsertElement: function() {
			var self = this;
			$('#addOrderLineTest').click(function() {
				//self.get('orderLines').pushObject({quantity: 2, code: 13, label: "Salade aux crabes", price: 2.5, amount: 5});
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
		focus: false,
		disabled: true,
		tableView: null,
		bodyView: function() {
			return this.tableView.bodyView;
		}.property(),
		currentRowViews: null,
		rowIndex: function() {
			// this.$() == jQuery text field. 
			// this.$().parent() ==  jQuery td.
			// this.$().parent().parent() == jQuery tr
			return this.$().parent().parent()[0].sectionRowIndex;
		}.property('bodyView.rowViews.@each'),
		lastRowIndex: function() {
			return this.get('bodyView.rowViews').length -1;
		}.property('bodyView.rowViews.@each'),
		isLastRow: function() {
			return this.get('rowIndex') + 1 == this.get('bodyView.rowViews').length;
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
		didInsertElement: function() {
		},
		backupValue: null,
		focusOut: function(event) {
			this.set('focus', false);
		},
		focusIn: function(event) {
			this.get('bodyView').setProperties({currentRowIndex: this.get('rowIndex'), currentCellIndex: this.get('cellIndex')});
			this.set('focus', true);
		},
		keyUp: function(event) {
			var self = this;
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
			self.set('value', self.get('backupValue'));
			// Default: Goto previous cell.
			var backCellIndex = self.get('cellIndex') - 1;
			var backRowIndex = self.get('rowIndex');
			if (backCellIndex < 0) {
				// Goto last row and first cell.
				backCellIndex = 0;
				backRowIndex = self.get('lastRowIndex');
				if (self.get('isLastRow')) {
					// Refresh page and Goto table number
					self.get('controller').refreshOrderLines();
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
			var upRowIndex = self.get('rowIndex') - 1;
			if (upRowIndex < 0) {
				upRowIndex = 0;
			}
			self.get('bodyView').set("currentRowIndex", upRowIndex);
		},
		down: function() {
			var self = this;
			var downRowIndex = self.get('rowIndex') + 1;
			if (self.get('isLastRow')) {
				downRowIndex--;
			}
			self.get('bodyView').set("currentRowIndex", downRowIndex);
		},
	});
	
	Mdo.OrderLineQuantityTextView = Mdo.OrderLineTextView.extend({
		disabled: false,
		forward: function() {
			if (!this.value) {
				// Default quantity value.
				this.set('value', 1);
			}
			this._super();
		},
	});

	Mdo.OrderLineCodeTextView = Mdo.OrderLineTextView.extend({
		disabled: false,
		quantityView: function() {
			return this.get('currentRowViews')['quantity'];
		}.property(),
		didQuantityChange: function() {
			this._disabledWhenQuantityEmpty();
		}.observes('quantityView.value'),
		didInsertElement: function() {
			this._disabledWhenQuantityEmpty();
		},
		forward: function() {
			var controller = this.get('controller');
			// Process order line by code
			controller.findOrderLine(this);
			this._super();
		},
		_disabledWhenQuantityEmpty: function() {
			var disabled = true;
			if (this.get('quantityView.value')) {
				disabled = false;
			}
			this.set('disabled', disabled);
		}.observes('quantityView.value'),
	});

	Mdo.OrderLineLabelTextView = Mdo.OrderLineTextView.extend({
	});

	Mdo.OrderLinePriceTextView = Mdo.OrderLineTextView.extend({
	});

	Mdo.OrderLineAmountTextView = Mdo.OrderLineTextView.extend({
		quantityView: function() {
			return this.get('currentRowViews')['quantity'];
		}.property(),
		priceView: function() {
			return this.get('currentRowViews')['price'];
		}.property(),
		valueDidChange: function() {
			this.set('value', this.get('quantityView.value')*this.get('priceView.value'));
		}.observes('quantityView.value', 'priceView.value'),
	});

	Mdo.OrderLineCellsViewsClasses = {
			"quantity": Mdo.OrderLineQuantityTextView,
			"code": Mdo.OrderLineCodeTextView,
			"label": Mdo.OrderLineLabelTextView,
			"price": Mdo.OrderLinePriceTextView,
			"amount": Mdo.OrderLineAmountTextView,
	};
});