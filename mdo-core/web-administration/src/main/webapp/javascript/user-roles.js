$(document).ready(function() {

	// For sorting list
	$(".sortable").mdoTablesorter({
		headers: {1:{sorter: false}},
	});

	$("#languages").mdoCrudList();
});