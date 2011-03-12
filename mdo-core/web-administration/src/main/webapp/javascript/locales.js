$(document).ready(function() {
	
	// Sort only from the Second th==eq(1)(0-based index)
	var selectorHeaders = "thead tr:eq(1) th";
	var headers = {1:{sorter: false}};
	$("table.sortable").mdoTablesorter({
		selectorHeaders: selectorHeaders,
		headers: headers,
	});
});