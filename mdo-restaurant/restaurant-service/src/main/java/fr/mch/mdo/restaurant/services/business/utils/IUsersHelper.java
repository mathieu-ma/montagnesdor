package fr.mch.mdo.restaurant.services.business.utils;

import fr.mch.mdo.restaurant.beans.dto.UserAuthenticationDto;
import fr.mch.mdo.restaurant.dao.beans.UserAuthentication;

public interface IUsersHelper {

	UserAuthenticationDto fromUserAuthentication(UserAuthentication user);

	UserAuthentication toUserAuthentication(UserAuthenticationDto user);

}
