$(document).ready(function() {

	// For sorting list
	var headers = {3:{sorter: false}};
	$("table.sortable").mdoTableSorter({
		headers: headers,
	});

	$("form").mdoValidate({
		resourceMessages: 'ValueAddedTaxesManagerWebAction',
		messages: {
			"form.dtoBean.rate": "error.vat.rate.double",
		},
	});
	
	new UpsideDown();

});