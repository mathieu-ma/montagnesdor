/**
 * This file will contain all Mdo views.
 */
	Mdo.HeaderMenusView = Ember.ContainerView.extend({
		tagName: 'form',
		menusBinding: 'controller.menus',
		selectedMenuItemKeyBinding: 'controller.selectedMenuItem',
		lastChildInserted: false,
		didLastChildInserted: function() {
			// Apply button set
			this.$().buttonset();
			this.forEach(function(childView, index) {
				if (childView.get('tagName')=='input') {
					// Apply icons
					childView.$().button("option", "icons", childView.get('icons'));
				}
			});
			this.performSelection();
		}.observes('lastChildInserted'),
		didSelectedMenuItemKeyChange: function() {
			this.performSelection();
		}.observes('selectedMenuItemKey'),
		performSelection: function() {
			var self = this;
			var selectedMenuItemKey = this.get('selectedMenuItemKey');
			self.forEach(function(childView, index) {
				if (childView.$()) {
					if (childView.get('tagName')=='label' && childView.get('key') == selectedMenuItemKey) {
						childView.$().addClass('ui-state-active');
					} else {
						childView.$().removeClass('ui-state-active');
					}
				}
			});
		},
		updateChildViews: function() {
			// This observer is used to insert children into []==childViews from the controller.content.
			// BE AWARE: This observer is triggered each time the controller.content change.
			// So we must check if the content is already inserted into the []==childViews
			var items = this.get('menus');
			if (items) {
				var self = this;
				$.each(items, function(index, item) {
					var isAlreadyInChildViews = false;
					self.forEach(function(childView, index) {
						if (childView.get('tagName')=='label' && childView.get('key') == item.key) {
							isAlreadyInChildViews = true;
							return;
						}
					});

					if (!isAlreadyInChildViews) {
						// Radio
						var checked = item.checked;
						if(checked) {
							checked = "checked";
						}
						var menuItem = Mdo.MenuItemRadioView.create({name: "headerMenuItem", checked: checked, icons: item.icons});
						// Insert child.
						self.pushObject(menuItem);
						// Label
						var label = Mdo.MenuItemLabelView.create({"for": menuItem.get('elementId'), key: item.key});
						// Insert child.
						self.pushObject(label);
					}
				});
			}
		}.observes('menus'),
	});

	Mdo.MenuItemRadioView = Ember.View.extend({
		tagName: 'input',
		attributeBindings: ['type', 'checked', 'name'],
		id: null,
		type: 'radio',
		checked: null,
		name: null,
		icons: null,
	});
	
	Mdo.MenuItemLabelView = Mdo.LabelView.extend({
		click: function() {
			this.get('parentView.controller').send("routeTo", this.key);
		},
		didInsertElement: function() {
			var self = this;
			Mdo.MessageObserver.create({key: this.key, callback: function(message) {
				if (self.$()) {
					// See JQuery UI button text
					var messageContainer = self.$('span.ui-button-text', self.$());
					if (!messageContainer.length) {
						// Have to check that because the call of .buttonset() could be later.
						messageContainer = self.$();
					}
					messageContainer.html(message);
				}
			}});
			var parentView = self.get('parentView');
			if (parentView.get('lastObject') == self) {
				parentView.set('lastChildInserted', true);
			}
		},
	});
