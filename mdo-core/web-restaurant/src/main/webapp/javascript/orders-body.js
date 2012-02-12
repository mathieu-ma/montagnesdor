// Use jQuery via jQuery(...)
jQuery(document).ready(function() {
	// Prevent Header Input elements
	$("#input-header-order-table-number[value!=''],#input-header-order-table-customer[value!='']").unbind();
	// Process the height of the main body data
	var scrollTableInnerBody = jQuery(".scroll-table-inner-body").height(jQuery("#body").height()-50);
//	scrollTableInnerBody.height(150);
	// Get the inner width
	var innerWidth = jQuery(".scroll-table-inner-body table").innerWidth();
	// Set the width of the container in case of scroll bar appears
	jQuery("div.scroll-table-outer-body thead tr").width(innerWidth);

	// For updating table
	var jDinnerTableId = $("#dinnerTableId");
	// For creating new table
	var jDinnerTableNumber = $("#input-header-order-table-number");
	
	// For list tables
	$("#listsortable").mdoTableSorter({
		// sortList is empty array because we do not want default sorting by column 0
		sortList: {}, 
		// pass the headers argument and assing a object 
        headers: { 
            // assign the 7 column (we start counting zero) 
            6: { 
                // disable it by setting the property sorter to false 
                sorter: false 
            } 
        } 
	});
	// For sorting list: sortList is empty array because we do not want default sorting by column 0
	var jSortable = $("#table-orders").mdoTableSorter({sortList: {}});

	function OrderLine(id, deletedId, color, quantity, code, label, price, amount, dinnerTableId, dinnerTableNumber, dataCode, quantitiesSum, amountsSum) {
		this.id = id || "";
		this.deletedId = deletedId || "";
		this.color = color || "";
		this.quantity = quantity || "";
		this.code = code || "";
		this.label = label || "";
		this.price = price || "";
		this.amount = amount || "";
		this.dinnerTableId = dinnerTableId || "";
		// Only used when the table is not yet created
		this.dinnerTableNumber = dinnerTableNumber || "";
		// Currently the dataCode is used for merging 2 rows
		this.dataCode = dataCode || "";
		this.quantitiesSum = quantitiesSum || "";
		this.amountsSum = amountsSum || "";
	};
	function fillArrayWithObject(prefix, obj, array) {
		if ( obj != null && typeof obj === "object" ) {
			// Serialize object item.
			for(var name in obj) {
				fillArrayWithObject(prefix + "." + name, obj[name], array);
			}
		} else {
			// Serialize scalar item.
			array.push({name: prefix, value: obj});
		}
	}
	function convertObjectToParamsArray(prefix, obj, array) {
		array = array || [];
		fillArrayWithObject(prefix, obj, array);
		return array;
	}
	// Check if the table is sorted, default value false
	var isSorted = false;
	var jTableOrders = $("#table-orders");
	var jBodyOrders = jQuery("#body-orders");
	var jFootOrders = jQuery("#foot-orders");
	// After each click on header for sorting
	$("#table-orders thead tr th").click(function() {
		var maxRowLength = jTableOrders.get(0).rows.length;
		var jText = jQuery(":text", jTableOrders);
		// Move to the last row
		jText = verticalMove(jText, maxRowLength);
		// Reset the last row values
		jText.val("");
		var jCurrentRow = jText.parent().parent();
		// Skip the first cell
		resetRow(jCurrentRow, 1);
		// User click for sorting
		isSorted = true;
	});
	jQuery(":text", jTableOrders).each(function() {
		// Initialize this in case of user enters "/" and do a backward
		backupValues(jQuery(this));
	}).keyup(function(event) {
		// Check data Format
		if	(event.which!=27 && 
				!checkDataFormat(jQuery(this))) {
			return;
		}
		switch(event.which) {
			case 27:
				// ESC key
				backward(jQuery(this));
			break;
			case 38:
				// Up key
				up(jQuery(this));
			break;
			case 13:
				// Enter key
				right(jQuery(this));
			break;
			case 40:
				// Down key
				down(jQuery(this));
			break;
			case 46 :
				// Suppr key
				processDeleteOrderLine(jQuery(this));
			break;
		}
		return false;
	})
	// Used to prevent user submit form when he key presses Enter key and set the focus
	.keypress(function(event) {if (event.which==13) return false;}).focus();
	jQuery("td.cell0", jTableOrders).click(function() { 
		var jNextRow = jQuery(this).parent();
		var jText = jQuery(":text", jTableOrders);
		var jCurrentRow = jText.parent().parent();
		// Backup this row into the input text
		resetBackupValues(jText);
		// Move
		verticalMove(jText, getRowIndex(jNextRow)-getRowIndex(jCurrentRow));
	});
	function checkDataFormat(jText) {
		// Get the current cell index
		var currentCellIndex = getCellIndex(jText.parent());
		var result = false;
		switch(currentCellIndex) {
			case 0:
				// Quantity
				result = isOrderQuantity(jText.val());
			break;	
			case 1:
				// Code
				result = isOrderCode(jText.val()); 
			break;	
			case 2:
				// Label
				result = isOrderLabel(jText.val()); 
			break;	
			case 3:
				// Price
				result = isOrderPrice(jText.val()); 
			break;
		}
		if (!result) {
			jText.val(jText.data("backupCheckedValue" + currentCellIndex));
		} else {
			jText.data("backupCheckedValue" + currentCellIndex, jText.val());
		}
		return result;
	};			
	function formatData(jText) {
		// Get the current cell index
		var currentCellIndex = getCellIndex(jText.parent());
		var result = jText.val();
		switch(currentCellIndex) {
			case 0:
				// Quantity
			case 3:
				// Price
				result = formatNumber(result);
			break;
		}
		return result;
	};
	function formatNumber(value) {
		var result = value;
		if (result && !isNaN(result)) {
			result = parseFloat(result).toFixed(2); 
		}
		return result;
	}
	function isLastRow(jRow) {
		var rows = jTableOrders.get(0).rows;
		return rows[rows.length-1]==jRow[0];
	}
	function getRowIndex(jRow) {
		// The rowIndex depends on the whole rows of the table(thead+tbody) and not only the tbody
		return jRow[0].rowIndex;
	}
	function getCellIndex(jCell) {
		return jCell[0].cellIndex;
	}
	function resetBackupValues(jText) {
		var jCurrentRow = jText.parent().parent();
		// Get the current cell index
		var currentCellIndex = getCellIndex(jText.parent());
		jText.val(jText.data("backupValue" + currentCellIndex));
		jQuery("[class^=cell]", jCurrentRow).each(function() {
			var cellIndex = getCellIndex(jQuery(this));
			if (currentCellIndex==cellIndex) {
				// Continue
				return true;
			}
			jQuery(this).html(jText.data("backupValue" + cellIndex));
		});
	}
	function backward(jText) {
		resetBackupValues(jText);
		var maxRowLength = jTableOrders.get(0).rows.length;
		// Get the current cell index
		var jCell = jText.parent();
		var jRow  = jCell.parent();
		var currentCellIndex = getCellIndex(jCell);
		if (currentCellIndex==0) {
			// Quantity cell
			if (isLastRow(jRow)) {
	    		$("#mdo-overlay").css('display', 'block');
				window.location.href = jQuery("#menu-top a.toggle").attr("href");
			} else {
				jText = verticalMove(jText, maxRowLength);
			}
		} else {
			// We consider that all rows have the same number of children so we get the first row children number
			jText = horizontalMove(jText, -jTableOrders.get(0).rows[0].childElementCount);
		}
	}
	function up(jText) {
		upDown(jText, -1);
	};
	function right(jText) {
		// Get the current cell index
		var currentCellIndex = getCellIndex(jText.parent());
		if (!jText.val()) {
			// Value is empty and press "Enter"
			if (currentCellIndex==0) {
				// Set default quantity value
				jText.val(1);
			} else {
				// Do nothing
				return;
			}
		}
		var jCurrentRow = jText.parent().parent();
		var maxRowLength = jTableOrders.get(0).rows.length;
		switch(currentCellIndex) {
			case 0:
				// Quantity
				var quantity = parseFloat(jText.val());
				if (isNaN(quantity)) {
					alert("Quantity must not be a number");
					return;
				}
				if (quantity == 0) {
					jText.val(jText.data("backupValue" + currentCellIndex));
					alert("Quantity must not be zero");
					return;
				}
			case 2:
				// Label
				horizontalMove(jText, +1);
			break;
			case 1:
				// Code
			case 3:
				// Unit price
				processUpdateOrderLine(jText, maxRowLength);
			break;
			default:
			return;
		}
	};
	function down(jText) {
		upDown(jText, +1);
	};
	function upDown(jText, step) {
		var jCurrentCell = jText.parent();
		// Get the current cell index
		var currentCellIndex = getCellIndex(jCurrentCell);
		if (currentCellIndex == 0) {
			var jCurrentRow = jCurrentCell.parent();
			// Backup this row into the input text
			resetBackupValues(jText);
			// Move
			verticalMove(jText, step);
		}
	}
	function horizontalMove(jText, step) {
		// Get the current cell index
		var currentCellIndex = getCellIndex(jText.parent());
		// We consider that all rows have the same number of children so we get the first row children number
		var maxCellLength = jTableOrders.get(0).rows[0].childElementCount-1;

		// Move to the next cell
		var result = move(jText, getNextProcessingCellFromCell, jText.parent().parent(), currentCellIndex, maxCellLength, 0, step);
		return result;
	}
	function getNextProcessingCellFromCell(nextProcessingIndex, jContext) {
		return jQuery(".cell" + nextProcessingIndex, jContext);
	}
	function processUpdateOrderLine(jText, step) {
		var jCurrentCell = jText.parent();
		var currentCellIndex = getCellIndex(jCurrentCell);
		var jCurrentRow = jCurrentCell.parent();
		var isRowByCodeExisted = false;
		
		// Build order line from user entries
		var orderLine =  buildOrderLine(jCurrentRow, jText, currentCellIndex);
		var code = orderLine.code;
		
/************ START TEST **************/
		jQuery.ajaxSetup({
			async: false,
		});
//		var url = "/web-restaurant/jsp/tables-orders/productC1.xml?" + jQuery.param(orderLine);
/************* END TEST ***************/
		// orderAutoUpdate is object of class OrderAutoUpdate
		var orderAutoUpdate = processOrderAutoUpdate(code, jCurrentRow, orderLine);
		var jFoundRow = orderAutoUpdate.jFoundRow;
		orderLine = orderAutoUpdate.orderLine;
		isRowByCodeExisted = orderAutoUpdate.isRowByCodeExisted;
		
		if(parseFloat(orderLine.quantity)==0) {
			alert("Quantity must not be zero");
			backward(jText, -1);
			return;
		}		
		// Set the Order Line Id if it exists
		orderLine.id = jFoundRow.attr("id") || "";
		var array = convertObjectToParamsArray("form.orderLine", orderLine);
		var url = jQuery("#TablesOrders").attr("action") + "?" + jQuery.param(array);
		jQuery.ajax({
			type: 'POST',
			url: url,
			dataType: "xml"
		}).success(function(xml, textStatus, errorThrown) {
			orderLine = fillOrderLine(xml, orderLine);
			if (!orderLine.code) {
				alert("Code produit n'existe pas !!!");
				return;
			}
			if (!jDinnerTableId.val()) {
				// Dinner Table does not exist or was deleted then set the new Id.
				jDinnerTableId.val(orderLine.dinnerTableId);
			}
			// 3) Move now ==> the current row number is jText.parent().parent().attr("id").substr(3)("row".length == 3)
			processVerticalMove(jText, step, orderLine, jCurrentRow, jFoundRow, isRowByCodeExisted);
			
			// Process footer
			$("#quantities-sum").html(orderLine.quantitiesSum);
			$("#amounts-sum").html(orderLine.amountsSum);
		}).error(function(jqXHR, textStatus, errorThrown) {
			alert("Ajax Error after Order Line process: textStatus==" + textStatus);
		});
	}			
	function processDeleteOrderLine(jText) {
		var jCurrentCell = jText.parent();
		// Get the current cell index
		var currentCellIndex = getCellIndex(jCurrentCell);
		var jCurrentRow = jCurrentCell.parent();
		if (!isLastRow(jCurrentRow) && currentCellIndex == 0) {
			// Build order line from user entries
			var orderLine =  buildOrderLine(jCurrentRow, jText, currentCellIndex);
			orderLine.quantity = jText.data("backupValue" + currentCellIndex);
			orderLine.deletedId = jCurrentRow.attr("id");
			var maxRowLength = jTableOrders.get(0).rows.length;
			var array = convertObjectToParamsArray("form.orderLine", orderLine);
			var url = jQuery("#TablesOrders").attr("action").replace("saveOrderLine", "deleteOrderLine") + "?" + jQuery.param(array);
			jQuery.ajax({
				type: 'POST',
				url: url,
				dataType: "xml"
			}).success(function(xml, textStatus, errorThrown) {
				verticalMove(jText, maxRowLength);		
				deleteRow(jCurrentRow);
				if (!jQuery(xml).find("dinnerTableId").text()) {
					// Dinner Table is deleted
					jDinnerTableId.val("");
				}
				// Process footer
				$("#quantities-sum").html(orderLine.quantitiesSum);
				$("#amounts-sum").html(orderLine.amountsSum);
			}).error(function(jqXHR, textStatus, errorThrown) {
				alert("Could not delete Order Line: textStatus==" + textStatus);
			});

		}
	}
	function deleteRow(jRow) {
		jRow.remove();	
		// For sortable because of cache
		jSortable.trigger("update");
	}
	function buildOrderLine(jRow, jText, currentCellIndex) {
		var orderLine =  new OrderLine();
		var cells = jRow[0].cells;
		// Order Line Id
		orderLine.id = jRow.attr("id") || "";
		// Quantiy = Quantity Cell
		orderLine.quantity = jQuery(cells[0]).html();
		// Label = Label Cell
		orderLine.label = jQuery(cells[2]).html();
		// Amount = Amount Cell
		orderLine.amount = jQuery(cells[4]).html();

		if (currentCellIndex == 1) {
			// Code
			orderLine.code = jText.val();
			orderLine.price = "";
		} else if (currentCellIndex == 3) {
			// Price
			orderLine.price = jText.val();
			orderLine.code = jQuery(cells[1]).html();
		}
		
		// Dinner Table Id
		orderLine.dinnerTableId = jDinnerTableId.val() || "";
		orderLine.dinnerTableNumber = jDinnerTableNumber.val() || "";
		return orderLine;
	}
	function fillOrderLine(xml, orderLine) {
		orderLine.id = jQuery(xml).find("id").text();
		orderLine.dinnerTableId = jQuery(xml).find("dinnerTableId").text();
		orderLine.color = jQuery(xml).find("color").text();
		orderLine.quantity = jQuery(xml).find("quantity").text();
		orderLine.code = jQuery(xml).find("code").text();
		orderLine.label = jQuery(xml).find("label").text();
		orderLine.price = jQuery(xml).find("price").text();
		orderLine.amount = jQuery(xml).find("amount").text();
		orderLine.dataCode = jQuery(xml).find("dataCode").text();
		orderLine.quantitiesSum = jQuery(xml).find("quantitiesSum").text();
		orderLine.amountsSum = jQuery(xml).find("amountsSum").text();
//console.log("orderLine.code=="+orderLine.code);
		return orderLine;
	}
	function processVerticalMove(jText, step, orderLine, jCurrentRow, jFoundRow, isRowByCodeExisted) {
		if (!orderLine.price) {
			// There is no price set for this order line: this means that we must move to the next cell.
			horizontalMove(jText, +1);
			return;
		}
		if (isRowByCodeExisted) {
			// Only when found row is not the current one
			
			// When Row By Code Exist, then reset backup values 
			resetBackupValues(jText);
			
			// 2) Move now ==> the current row number is jText.parent().parent().attr("id").substr(3)("row".length == 3)
			verticalMove(jText, step);
			if (!isLastRow(jCurrentRow)) {
				deleteRow(jCurrentRow);
			}
		} else {
			// No row found with the specified code so set this code in row class attribute
			if (isLastRow(jCurrentRow)) {
				// 2) Add new row
				addNewRow(jCurrentRow);
			}
			// 2) Move now ==> the current row number is jText.parent().parent().attr("id").substr(3)("row".length == 3)
			verticalMove(jText, step);
		}
		// Update found row with orderLine
		updateRowByOrderLine(jFoundRow, orderLine);
		// Warning message that adding/saving row may not keep table in right order
		if (isSorted) {
			$("#table-orders thead tr th").each(function() {
				$("span.ui-icon-triangle-2-n-s", $(this)).fadeOut().fadeIn("slow");
				$(this).removeClass("headerSortUp").removeClass("headerSortDown");
				// Tell that the table is not sorted anymore
				isSorted = false;
			});
		}
	}
	/**
	 * This method return a Jquery object as table row. It will always return a non null object.
	 * The result could be an array.
	 */
	function getRowByCode(code) {
		// Create Jquery empty object
		var jResult = $();
		if (code) {
			try {
				//return jQuery("tr." + code, jBodyOrders);
				jResult = jQuery("tr[data-code][data-code='" + code +"']", jBodyOrders);
			} catch (e) {
				// Error could be raised if code equals to "/" or with characters not compatible with class name.
				// Return empty object.
			}
		}
		return jResult;
	}
	/**
	 * This is POJO object.
	 * This class is not required in javascript but it is usefull for semantic.
	 */
	function OrderAutoUpdate() {
		/** Fields Part */
		this.jFoundRow = null;
		this.isRowByCodeExisted = false;
		this.orderLine = null;

		/** Methods Part */
	}
	/**
	 * This method will search a row by code.
	 * It follows the following process:
	 * 1) if the parameter jExcludedElement is empty then it will return the first element not the entire found array.
	 * 2) if the parameter jExcludedElement is not empty then 
	 */
	function processOrderAutoUpdate(code, jCurrentRow, orderLine) {
		var result = new OrderAutoUpdate();
		// Default values
		result.jFoundRow = jCurrentRow;
		result.isRowByCodeExisted = false;
		result.orderLine = orderLine;
		
		// Default value for isOrderAutoUpdateFirstFoundRowOnly : maybe this local variable will be global or value set by user
//		var isOrderAutoUpdateFirstFoundRowOnly = true;
		var isOrderAutoUpdateFirstFoundRowOnly = false;
		var isOrderAutoUpdate = $("#header-order-auto-update:checked").length;
		// Find if there is another row with the same code but it is not the current row
		var jFoundRow = getRowByCode(code);
		if (isOrderAutoUpdate && jFoundRow.length > 0) {
			if (jFoundRow.index(jCurrentRow[0]) != -1) {
				//jFoundRow contains at least the element jCurrentRow
				if (jFoundRow.length == 1 || isOrderAutoUpdateFirstFoundRowOnly) {
					 // jFoundRow == jCurrentRow or we want isOrderAutoUpdateFirstFoundRowOnly
					return result;
				}
				jFoundRow = jFoundRow.not(jCurrentRow[0]);
			}
			// Get the first one because we could get an array of elements
			jFoundRow = jFoundRow.eq(0);
			
			// Only when found row is not the current one
			// Update old order line and delete the current one
			result.orderLine.quantity = parseFloat(orderLine.quantity) + parseFloat(jFoundRow[0].cells[0].innerHTML);
			// Delete the current Row
			result.orderLine.deletedId = jCurrentRow.attr("id") || "";
			// Reset or Delete row at the end of the process
			result.isRowByCodeExisted = true;
			result.jFoundRow = jFoundRow;
		}
		return result;
	}
	function updateRowByOrderLine(jRow, orderLine) {
		var cells = jRow[0].cells;
		
		// id
		jRow.attr("id", orderLine.id);
		// Set the code in row data-code attribute: this is only useful if the attribute is not already set.
		if (orderLine.dataCode) {
			jRow.attr("data-code", orderLine.dataCode);
		}
		// Background Color
		var backgroundColor = orderLine.color?"#"+orderLine.color:"inherit";
		jRow.css("background-color", backgroundColor);
		// Quantiy = Quantity Cell From + Quantity Cell To
		jQuery(cells[0]).html(formatNumber(orderLine.quantity));
		// Label = Label Cell From
		jQuery(cells[2]).html(orderLine.label);
		// Unit Price = Unit Price Cell From
		jQuery(cells[3]).html(formatNumber(orderLine.price));
		// Amount = Quantiy * Unit Price
		jQuery(cells[4]).html(formatNumber(orderLine.amount));
	}
	function verticalMove(jText, step) {
		var jCurrentRow = jText.parent().parent();
		var currentRowIndex = getRowIndex(jCurrentRow);
		var maxRowLength = jTableOrders.get(0).rows.length;
		// Move now ==> the current row number is jText.parent().parent().attr("id").substr(3)("row".length == 3)
		var jNextText = move(jText, getNextProcessingCellFromRow, jTableOrders, currentRowIndex, maxRowLength, 1, step);
		// Show the next row
		jNextText.parent().parent().show();
		// Focus next text
		jNextText.focus();
		if (isLastRow(jCurrentRow)) {
			if (step<0) {
				// Reset last row only if the step is negative
				resetRow(jCurrentRow);
				jCurrentRow.hide();
			}
		}
		return jNextText;
	}
	function getNextProcessingCellFromRow(nextProcessingIndex, jContext) {
		return jQuery(jTableOrders[0].rows[nextProcessingIndex].cells[0]);
	}
	function move(jText, getNextProcessingCell, jContext, currentIndex, maxLength, minIndex, step) {
//console.log("jText==" + jText + " getNextProcessingCell==" + getNextProcessingCell + " jContext==" + jContext + " currentIndex==" + currentIndex + " maxLength==" + maxLength + " step==" + step);
		var nextProcessingIndex = currentIndex + step;
		if (nextProcessingIndex>=maxLength) {
			nextProcessingIndex = maxLength-1;
		} else if (nextProcessingIndex<minIndex) {
			nextProcessingIndex = minIndex;
		}
		// Look for the next processing cell.
		var jNextProcessingCell = getNextProcessingCell(nextProcessingIndex, jContext);
		// Clone the input text
		var jTextCloned = cloneText(jText, jNextProcessingCell);
		return jTextCloned;
	}
	function addNewRow(jRow) {
		var jClonedRow = jRow.clone(true);
		jBodyOrders.append(jRow);
		resetRow(jClonedRow);
		// For sortable because of cache
		jSortable.trigger("update");
		jFootOrders.append(jClonedRow);
	}
	function resetRow(jRow, beginIndex) {
		beginIndex = beginIndex || 0;
		jRow.removeAttr("data-code");
		jQuery("[class^=cell]", jRow).each(function(index) {
			if (index >= beginIndex) {
				jQuery(this).html("");
				jQuery(this).removeClass("cell-center-align");
			} else {
				// true means continue this loop
				// false means break this loop.
				return true;
			}
		});
	}
	function cloneText(jText, jNextProcessingCell) {
		// Remove the center align for the current parent cell
		jText.parent().removeClass("cell-center-align");
		var jTextCloned = jText.clone(true);
		// Add the center align for the next parent cell
		jNextProcessingCell.addClass("cell-center-align");
		// Increase the class suffix number
		jTextCloned.attr("class", jTextCloned.attr("class").replace(/text(\d)+/gi, "text" + getCellIndex(jNextProcessingCell)));
		// Replace the current input text value by text node
		jText.replaceWith(formatData(jText));
		// Replace the text node in the next processing cell by the cloned input text and focus the former.
		jTextCloned.val(jNextProcessingCell.text()).prependTo(jNextProcessingCell.empty()).focus();
		// Select the input text data
		jTextCloned.select();
		// Backup this row into the input text
		backupValues(jTextCloned);

		return jTextCloned;
	}
	function backupValues(jText) {
		// Get the current cell index
		var currentCellIndex = getCellIndex(jText.parent());
		// Backup values only on the quantity cell
		if (currentCellIndex==0) {
			var jCurrentRow = jText.parent().parent();
			// Backup for reseting data
			// Quantity
			jText.data("backupValue0", jText.val());
			// Code
			jText.data("backupValue1", jQuery(".cell1", jCurrentRow).html());
			// Label
			jText.data("backupValue2", jQuery(".cell2", jCurrentRow).html());
			// Price
			jText.data("backupValue3", jQuery(".cell3", jCurrentRow).html());
			// Backup for checking data
			// Quantity
			jText.data("backupCheckedValue0", jText.val());
			// Code
			jText.data("backupCheckedValue1", jQuery(".cell1", jCurrentRow).html());
			// Label
			jText.data("backupCheckedValue2", jQuery(".cell2", jCurrentRow).html());
			// Price
			jText.data("backupCheckedValue3", jQuery(".cell3", jCurrentRow).html());
		}
	}
	function isOrderQuantity(value) {
		var result = false;
		result = value.match(getRegex("decimal", 2, true));
		return result;
	}
	function isOrderPrice(value) {
		var result = false;
		result = value.match(getRegex("decimal", 2, true));
		return result;
	}
	function isOrderCode(value) {
		var result = false;
		result = value.match(getRegex("code"));
		return result;
	}
	function isOrderLabel(value) {
		var result = false;
		result = value.match(getRegex("alphabet"));
		return result;
	}
    function getRegex(type, precision, allowNegative) {
        var result = "";
		var decimal = ".";
        if (type=="decimal") {
          var e = (allowNegative)?"-?":"";
          if(precision>0) {
            	result = "^" + e + "\\d*$|^"+e+"\\d*\\" + decimal + "\\d{0," + precision + "}$";
          } else {
        	  	result = "^" + e + "\\d+$";
          }
        } else if (type=="code") {
        	// Code length is between 0 and 5.
          	result = "^[#\-/0-9A-Za-z]{0,5}$";
        }
        return new RegExp(result);
     }
});