$(document).ready(
	function()
	{
		$("#languages").mdoCrudList();

		new UpsideDown();

	  	//Override the Struts validate function
	  	jQuery.mdoSubmit();

	  	//Change value of long code when changing value of restaurant
		var jForm = $("form#ProductSpecialCodesManager");
	    var params = {
	    		"method:getProductSpecialCodeLongCodesByRestaurant":"",
	    		"form.dtoBean.daoBean.id":function() {return $(":hidden[name='form.dtoBean.daoBean.id']").val();},
	    };
	  	$("#restaurant").change(function() {
		    params["form.dtoBean.daoBean.restaurant.id"] = $(this).val();
		    var paramsForAjax = jQuery.param(params);
		    jQuery.post(jForm.attr("action"), paramsForAjax,
				function (data) {
		    		var dataInDiv = $("<div></div>").html(data);
					var selectLongCode = $("#psc_long_code", dataInDiv);
					$("#psc_long_code").html(selectLongCode.html());
					var hiddenShortCode = $("#hidden_div_psc_short_code", dataInDiv);
					$("#hidden_div_psc_short_code").html(hiddenShortCode.html());
					$("#psc_long_code").change();
			});
	  	});
	  	
	  	//Change value of short code when changing value of long code
	  	$("#psc_long_code").change(function() {
	  		$("#psc_short_code").val($(":hidden[name='"+$(this).val()+"']").val());
	  	});
	}
);