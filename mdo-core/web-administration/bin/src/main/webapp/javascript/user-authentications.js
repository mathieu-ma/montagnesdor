$(document).ready(function() {
	// For sorting list
	$(".sortable").mdoTablesorter({
		headers: {3:{sorter: false}},
	});

	new UpsideDown();

	$("form").mdoValidate({
		resourceMessages: 'UserAuthenticationsManagerWebAction',
		messages: {
			"form.dtoBean.login": "error.user.authentication.login.required",
			"form.dtoBean.password": "error.user.authentication.password.required",
			"form.dtoBean.printingLocale.id": "error.user.authentication.printing.locale.required",
		},
	});

	$("#tabs").tabs();
});