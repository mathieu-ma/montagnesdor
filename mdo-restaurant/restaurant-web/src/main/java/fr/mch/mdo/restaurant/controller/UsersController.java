package fr.mch.mdo.restaurant.controller;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.mch.mdo.restaurant.beans.dto.UserAuthenticationDto;
import fr.mch.mdo.restaurant.services.business.managers.IFrontUsersManager;


/**
 * This class is a controller that serves client query in order to process authentication services.
 * @author m.ma
 *
 */
@Controller
@RequestMapping(UsersController.USERS_CONTROLLER)
public class UsersController extends AbstractController {

	public static final String USERS_CONTROLLER = "/users";

	public static final String FIND_USER_AUTH_ID = "/find/user/{authId}";

	@Inject
	@Named("FrontUsersManager")
	private IFrontUsersManager manager;

	/**
	 * Find the user authentication by id.
	 * 
	 * @return a map of i18n key-value pairs.
	 */
	@RequestMapping(value = FIND_USER_AUTH_ID, method = RequestMethod.GET)
	@ResponseBody
	public UserAuthenticationDto user(@PathVariable Long authId) throws Exception {
		UserAuthenticationDto result = new UserAuthenticationDto();
		result = manager.find(authId);
		return result;
	}
}
