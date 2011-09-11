$(document).ready(function() {
	var jOrdersBody = $("#orders-body");
	var jCurrentOrderLine = $("#current-order-line");
	jQuery.mdoFocus(jCurrentOrderLine);
	jCurrentOrderLine.live("keyup", function(eventParam) {
		processUserEntry(eventParam, jQuery(this)); 
		return false;
	});
	
	var processUserEntry = function(eventParam, jCell) {
		switch(eventParam.keyCode) {     	  
			//38 == key Up
			case 38 :
				up(jCell);
			return;
			//40 == key Down
			case 40 :
				down(jCell);
			return;
		};
    }

	var up = function(jCell) {
		console.log(jCell);
		console.log(jOrdersBody);
    }
	
	var down = function(jCell) {
    }

});
