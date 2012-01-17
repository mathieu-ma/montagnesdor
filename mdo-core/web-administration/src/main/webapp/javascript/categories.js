$(document).ready(function() {

	// For sorting list
	$("#listsortable").mdoTableSorter({
		headers: {1:{sorter: false}},
	});
	
	$("#languages").mdoCrudList();

	new UpsideDown();

});