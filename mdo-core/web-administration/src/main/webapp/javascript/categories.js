$(document).ready(function() {

	// For sorting list
	$("#listsortable").mdoTablesorter({
		headers: {1:{sorter: false}},
	});
	
	$("#languages").mdoCrudList();

	new UpsideDown();

});