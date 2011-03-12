$(document).ready(function() {

	// For sorting list
	$(".sortable").mdoTablesorter({
		headers: {3: {sorter: 'mdodate'}, 4:{sorter: false}},
	});

	$("#form-dtoBean-birthdate").mdoDatePicker({
		changeMonth: true,
		changeYear: true,
		yearRange: '1940:2000'
	});
		
	new UpsideDown();

	$("#tabs").tabs();

});