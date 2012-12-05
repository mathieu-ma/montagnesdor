package fr.mch.mdo.restaurant.controller;

import junit.framework.Assert;

import org.junit.Test;

import fr.mch.mdo.restaurant.beans.dto.UserAuthenticationDto;
import fr.mch.mdo.restaurant.web.AbstractControllerTest;


public final class UsersControllerTest extends AbstractControllerTest
{
	public static final String context = SERVER_URL + UsersController.USERS_CONTROLLER;
	
	@Test
	public void user() {
		Long userAuthenticationId = 1L;
        StringBuilder sb = new StringBuilder(context).append(UsersController.FIND_USER_AUTH_ID);
        UserAuthenticationDto user = restTemplate.getForObject(sb.toString(), UserAuthenticationDto.class, userAuthenticationId);
    	Assert.assertNotNull("UserAuthenticationDto", user);
	}
}
