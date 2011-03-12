$(document).ready(function() {

		// For sorting list
		$("#listsortable").mdoTablesorter({
			headers: {1: {sorter: 'mdodate'}, 5:{sorter: false}},
		});

		$("#vatsortable").mdoTablesorter();

		$("form").mdoValidate({
			resourceMessages: 'RestaurantsResources',
			messages: {
				"form.dtoBean.name": "error.restaurant.name.required",
			},
		});
		
		$("#form-dtoBean-registrationDate").mdoDatePicker();

		new UpsideDown();
		
		$("#tabs").tabs();

	  	//Override the Struts validate function
//	  	jQuery.mdoSubmit();
});