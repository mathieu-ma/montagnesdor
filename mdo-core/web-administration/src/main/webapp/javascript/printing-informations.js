$(document).ready(function() {
	// For sorting list
	$("table.sortable").each(function() {
		var selectorHeaders = "thead th";
		var headers = {1:{sorter: false}};
		if($(this).hasClass("restaurant")) {
			// Sort only from the Second th==eq(1)(0-based index)
			selectorHeaders = "thead tr:eq(1) th";
			headers = {4:{sorter: false}};
		}
		$(this).mdoTableSorter({
			selectorHeaders: selectorHeaders,
			headers: headers,
		});
	});

	// For sorting list
	$("#listprintinginformationssortable").mdoTableSorter({
		headers: {1:{sorter: false}},
	});

	new UpsideDown(".mdo-upsidedown-1");

	$("#tabs").tabs();

	$("#languages").mdoCrudList();
});