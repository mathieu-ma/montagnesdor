$(document).ready(function() {

	// For sorting list
	// Sort only from the Second th==eq(1)(0-based index)
	var selectorHeaders = "thead tr:eq(1) th";
	var headers = {4:{sorter: false}};
	$("table.sortable").mdoTablesorter({
		selectorHeaders: selectorHeaders,
		headers: headers,
	});

	new UpsideDown();

  	//Override the Struts validate function
//  	jQuery.mdoSubmit();
});