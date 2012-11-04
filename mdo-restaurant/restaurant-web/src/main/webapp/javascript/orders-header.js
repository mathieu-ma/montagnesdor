$(document).ready(function() {
		$("input[type=checkbox]").click(function() {
			jQuery.mdoFocus();
		});
		var inputPrefixesTakeawayTableName = $("#prefixesTakeawayName").val();
		var prefixesTakeawayTableName = inputPrefixesTakeawayTableName==""?[]:inputPrefixesTakeawayTableName.split(","); 
		var jTakeawayCheckbox = $("#checkbox-header-order-table-takeaway");
		var jDivCheckbox = $("div.header-order-takeaway");
		var jTableNumber = $("#input-header-order-table-number");
		var jTableCustomer = $("#input-header-order-table-customer");
		var jDivTableCustomer = $("div.header-order-table-customer");
		//URL for ajax call
		var tableCustomerUrl = $("#HeaderTablesOrders").attr("action").replace("displayDinnerTable", "tableCustomersNumber");		
		var options = {
			processPreKeyup: function(jCell) {
				//Ajax autocomplete
				if(prefixesTakeawayTableName.length>0) {
					if(jQuery.inArray(jCell.val().toUpperCase().charAt(0), prefixesTakeawayTableName)>-1) {
						jDivCheckbox.css("visibility", "visible");
						jTakeawayCheckbox.attr("checked", "true");
					} else {
						jDivCheckbox.css("visibility", "hidden");
						jTakeawayCheckbox.removeAttr("checked");
					}
				}
			},
			processEsc: function(jCell) {
				jTableNumber.val("");
				jDivCheckbox.css("visibility", "hidden");
				jTakeawayCheckbox.removeAttr("checked");
				jQuery.mdoFocus(jTableNumber);
			},
			processEnter: function(jCell) {
				jTakeawayCheckbox.css('visibility', 'hidden');
				
				//Prevent display autocomplete select by changing the focus
				jCell.blur();

				if(jTakeawayCheckbox.attr("checked")) {
					jTableCustomer.val("0");
					jTableCustomer.attr("readOnly", "true");
					jDivTableCustomer.css('visibility', 'visible');
					displayDinnerTable();
				} else {
					jTableCustomer.val("");
					//Check table customer number with ajax call
					var params = {"form.dtoBean.number":function() {return jTableNumber.val().toUpperCase();}};
					//Ajax call
					jQuery.post(tableCustomerUrl, params, function (data) {
						var jAjaxTableCustomer = $("#ajax-header_order_table_customer", $("<div></div>").html(data));
						//Display data get from ajax 
						jTableCustomer.val(jAjaxTableCustomer.val());
					});
					jDivCheckbox.css("visibility", "hidden");
					jDivTableCustomer.css('visibility', 'visible');
					jTableCustomer.removeAttr("readOnly");
				}
				window.setTimeout(function() {jQuery.mdoFocus(jTableCustomer);}, 100);
			}
		}
		jTableNumber.mdoInputText(options);
//		//URL for ajax call
//		var autoCompleteTablesNamesUrl = $("#HeaderTablesOrders").attr("action").replace("displayDinnerTable", "autoCompleteTablesNames");		
//		jTableNumber.autocomplete(autoCompleteTablesNamesUrl, 
//			{
//				extraParams: {"form.dtoBean.prefixTableNumber": function() {return jTableNumber.val().toUpperCase();}},
//				cacheLength: 0,
//				selectFirst: true,
//				scroll: true,
//				scrollHeight: 250,
//				width: 65
//			}
//		).result( function(event, data, formatted) {
//			if(data)
//			{
//				$("#form_dtoBean_dinnerTableId").val(data[1]);
//			}
//		});
		//New options for jTableCustomer
		options = {
			processEnter: function(jCell) {
				displayDinnerTable();
			},
			processEsc: function(jCell) {
				if(prefixesTakeawayTableName.length==0) {
					//No prefix for takeaway so let the user check if the table is takeaway
					jTakeawayCheckbox.css('visibility', 'visible');
					jDivCheckbox.css("visibility", "visible");
				}
				jDivTableCustomer.css('visibility', 'hidden');
				jTableNumber.removeAttr("readOnly");
				jQuery.mdoFocus(jTableNumber);
			}
		};
		jTableCustomer.mdoInputText(options);
		
		function displayDinnerTable() {
			// Form TablesOrders
			var jForm = $("#HeaderTablesOrders");
    		$("#mdo-overlay").css('display', 'block');
			window.location.href = jForm.attr("action") + "?" + $(":text", jForm).serialize();

//			var jParameters = jForm.serialize();
//			// URL for ajax call
//			var displayDinnerTableUrl = jForm.attr("action").replace("displayDinnerTable", "displayDinnerTable");
//			// display overlay block in order to prevent user entry
//  			$("#mdo-overlay").css('display', 'block');
//			$("#menu-body-footer").load(displayDinnerTableUrl, jParameters);
//			// display overlay none in order to enable user entry
//  			$("#mdo-overlay").css('display', 'none');
//  			jTableNumber.unbind();
//  			jTableCustomer.unbind();
		}

		jQuery.mdoFocus("#input-header-order-table-number");
		jQuery.mdoSubmit();
});
