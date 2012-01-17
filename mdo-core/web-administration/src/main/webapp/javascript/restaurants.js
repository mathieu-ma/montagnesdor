$(document).ready(function() {

		// For sorting list
		$("#listsortable").mdoTableSorter({
			headers: {1: {sorter: 'mdodate'}, 5:{sorter: false}},
		});

		$("#vatsortable").mdoTableSorter();

		$("form").mdoValidate({
			resourceMessages: 'RestaurantsManagerWebAction',
			messages: {
				"form.dtoBean.name": "error.restaurant.name.required",
			},
		});
		
		$("#form-dtoBean-registrationDate").hide().mdoDatePicker();

		new UpsideDown();
		
		$("#tabs").tabs();

	  	//Override the Struts validate function
//	  	jQuery.mdoSubmit();
});