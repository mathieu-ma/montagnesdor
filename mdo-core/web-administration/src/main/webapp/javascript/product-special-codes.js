$(document).ready(function() {
	// For sorting list
	$("#listsortable").mdoTableSorter({
		headers: {1:{sorter: false}},
	});

	$("#languages").mdoCrudList();

	new UpsideDown();

	var vat = $("#ProductSpecialCodesManagerCUD_form_dtoBean_vat_id");
	var vatBackupName = vat.attr("name"); 
	vat.change(function() {
		var selectedOptionValue = $(this).val();
		if (selectedOptionValue=="") {
			$(this).attr("name", "dummy");
		} else {
			$(this).attr("name", vatBackupName);
		}
	}).change();
	
});