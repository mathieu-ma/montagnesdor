$(document).ready(function() {

	// For sorting list
	$(".sortable").mdoTableSorter({
		headers: {3: {sorter: 'mdodate'}, 4:{sorter: false}},
	});

	$("#form-dtoBean-birthdate").hide().mdoDatePicker({
		changeMonth: true,
		changeYear: true,
		yearRange: '1940:2000'
	});
		
	new UpsideDown();

	$("form").mdoValidate({
		resourceMessages: 'UsersManagerWebAction',
		messages: {
			"form.dtoBean.name": "error.user.name.required",
			"form.dtoBean.forename1": "error.user.forename1.required",
			"form.dtoBean.birthdate": "error.user.date.required",
		},
	});

	$("#tabs").tabs();

});