package fr.mch.mdo.restaurant.dao.authentication;

public enum AuthenticationPasswordLevel {
	PASSWORD_LEVEL_ZERO, PASSWORD_LEVEL_ONE {
		public void setPassword(IAuthenticationPasswordLevel userAuthentication, String newPassword) {
			userAuthentication.setLevelPass1(newPassword);
		}

		public String getPassword(IAuthenticationPasswordLevel userAuthentication) {
			return userAuthentication.getLevelPass1();
		}
	},
	PASSWORD_LEVEL_TWO {
		public void setPassword(IAuthenticationPasswordLevel userAuthentication, String newPassword) {
			userAuthentication.setLevelPass2(newPassword);
		}

		public String getPassword(IAuthenticationPasswordLevel userAuthentication) {
			return userAuthentication.getLevelPass2();
		}
	},
	PASSWORD_LEVEL_THREE {
		public void setPassword(IAuthenticationPasswordLevel userAuthentication, String newPassword) {
			userAuthentication.setLevelPass3(newPassword);
		}

		public String getPassword(IAuthenticationPasswordLevel userAuthentication) {
			return userAuthentication.getLevelPass3();
		}
	};

	public void setPassword(IAuthenticationPasswordLevel userAuthentication, String newPassword) {
		userAuthentication.setPassword(newPassword);
	}

	public String getPassword(IAuthenticationPasswordLevel userAuthentication) {
		return userAuthentication.getPassword();
	}
}
