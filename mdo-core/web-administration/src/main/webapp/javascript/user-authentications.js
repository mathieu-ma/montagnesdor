$(document).ready(
	function()
	{
		var jSelectLanguage = $("select[name='"+jQuery.mdoPreSelectorName("form.dtoBean.daoBean.preferedLocale.id")+"']");
		$(":checkbox").each(
			function()
			{
				$(this).bind("click",
					function()
					{
						var isChecked = $(this).attr("checked");
						var checkboxLabel = $("#checkboxLabel"+$(this).val());
						var checkboxHidden = $("#"+jQuery.mdoPreSelectorName("form.dtoBean.userLocales.language.")+$(this).val());

						if(isChecked)
						{
							checkboxHidden.removeAttr("disabled");
							if(jSelectLanguage.is(":not(:contains('"+checkboxLabel.text()+"'))"))
							{ 
								$("option[selected]", jSelectLanguage).removeAttr("selected");
								jSelectLanguage.append("<option selected='selected' value='"+$(this).val()+"'>"+checkboxLabel.text()+"</option>");
							}
						}
						else
						{
							checkboxHidden.attr("disabled", "disabled");
							if(jSelectLanguage.is(":contains('"+checkboxLabel.text()+"')"))
							{ 
								$("option[value='"+$(this).val()+"']", jSelectLanguage).remove();
							}
						}
								
					}
				);
			}
		);

		var jRestaurant = $("#UserAuthenticationsManager_form_dtoBean_daoBean_restaurant_id");
		$("#form-dtoBean-daoBean-restaurant-name").val($("option:selected", jRestaurant).html());
		jRestaurant.change( 
			function() {
				$("#form-dtoBean-daoBean-restaurant-name").val($("option:selected", jRestaurant).html());
			}
		);
		var savedRestaurantId = $("#saved-user-restaurant-id").val() || "";
		$("#UserAuthenticationsManager_form_dtoBean_daoBean_user_id").change(
			function()
			{
				jRestaurant.load("/mdo-web-administration/administration/UserAuthenticationsManager!findRestaurantsByUser.action?form.dtoBean.daoBean.restaurant.id="+savedRestaurantId+"&form.dtoBean.daoBean.user.id="+$(this).val());
			}
		);
		
		$("#submit").click(
	  		function() 
	  		{
     			$("#hidden-submit-form").click();
    		}
		);
			
		new UpsideDown();

	  	//Override the Struts validate function
	  	jQuery.mdoSubmit();
   	}
);