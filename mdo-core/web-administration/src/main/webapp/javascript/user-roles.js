$(document).ready(function() {

	// For sorting list
	$(".sortable").mdoTableSorter({
		headers: {1:{sorter: false}},
	});

	$("#languages").mdoCrudList();
});