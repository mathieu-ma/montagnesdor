$(document).ready(function() {

		// For sorting list
		$("#listsortable").mdoTableSorter({
			headers: {1: {sorter: 'mdodate'}, 5:{sorter: false}},
		});

		$("#vatsortable").mdoTableSorter();

		$("form").mdoValidate({
			resourceMessages: 'RestaurantsManagerWebAction',
			messages: {
				"form.dtoBean.name": "error.restaurant.name.required",
			},
		});
		
		$("#form-dtoBean-registrationDate").hide().mdoDatePicker();

		new UpsideDown();
		
		$("#tabs").tabs();

		// Manage the Default Vat Custom Order Line
		var jSelectDefaultVatCustomOrderLine = $("#default-vat-custom-order-line").change(function() {
			$("input[type='checkbox'][name='form.vats[" + $(this).val() + "]']").attr("checked", true);
		});
		
		// Load the property restaurants.manager.warn.vat.uncheck
		jQuery.mdoI18nProperties({
			keys: ["restaurants.manager.warn.vat.uncheck"]
		});
		var jChangePasswordDialog = jQuery.mdoDialog();
		$("input[type='checkbox'][name^='form.vats']").each(function() {
			var thisCheckbox = $(this);
			// Init this checkbox by the Default Vat Custom Order Line
			var checkedName = 'form.vats[' + jSelectDefaultVatCustomOrderLine.val() + ']';
			if (thisCheckbox.attr("name") == checkedName && !thisCheckbox.attr("checked")) {
				// Force to check this checkbox
				thisCheckbox.attr("checked", true);
			}
			
			thisCheckbox.click(function() {
				var thisCheckbox = $(this);
				var checkedName = 'form.vats[' + jSelectDefaultVatCustomOrderLine.val() + ']';
				var thisCheckboxName = thisCheckbox.attr("name");
				if (thisCheckboxName == checkedName && !thisCheckbox.attr("checked")) {
					jChangePasswordDialog.html(jQuery.i18n.prop("restaurants.manager.warn.vat.uncheck"));
					jChangePasswordDialog.dialog('open');
					thisCheckbox.attr("checked", true);
				}
				// Get the id from the name
				var optionValue = thisCheckboxName.replace(/.+\[(.+)\]/, "$1");
				var jOption = $("option[value='" + optionValue + "']", jSelectDefaultVatCustomOrderLine);
				if (thisCheckbox.attr("checked")) {
					if (!jOption.get(0)) {
						var optionLabel = $(jQuery.mdoPreSelectorName("#id." + thisCheckboxName)).text();
						jSelectDefaultVatCustomOrderLine.append("<option value='" + optionValue + "'>" + optionLabel + "</option>");
					}
				} else {
					jOption.remove();
				}
			});
		});
		
	  	//Override the Struts validate function
//	  	jQuery.mdoSubmit();
});