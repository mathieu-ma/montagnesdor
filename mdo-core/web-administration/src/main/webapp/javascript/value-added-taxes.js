$(document).ready(function() {

	// For sorting list
	var headers = {3:{sorter: false}};
	$("table.sortable").mdoTablesorter({
		headers: headers,
	});

	$("form").mdoValidate({
		resourceMessages: 'ValueAddedTaxesResources',
		messages: {
			"form.dtoBean.rate": "error.vat.rate.double",
		},
	});
	
	new UpsideDown();

});