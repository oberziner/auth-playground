package br.com.oberziner.security;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.security.auth.login.LoginException;

import br.com.oberziner.dao.AuthUserDAO;
import br.com.oberziner.entity.AuthUser;

public final class Authenticator {

	@Inject
	AuthUserDAO users;

	private static final Map<String, String> authorizationTokensStorage = new HashMap<>();

	public String login(String username, String password)
			throws LoginException {
		AuthUser user = users.getUserByUserName(username);
		if (user != null) {
			try {
				if (user.getPassword().equals(Encrypter.encryptMD5(password))) {

					/**
					 * Once all params are matched, the authToken will be generated
					 * and will be stored in the authorizationTokensStorage. The
					 * authToken will be needed for every REST API invocation and is
					 * only valid within the login session
					 */
					String authToken = UUID.randomUUID().toString();
					authorizationTokensStorage.put(authToken, username);

					return authToken;
				}
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		throw new LoginException("Don't Come Here Again!");
	}

	/**
	 * The method that pre-validates if the client which invokes the REST API is
	 * from a authorized and authenticated source.
	 *
	 * @param authToken
	 *            The authorization token generated after login
	 * @return TRUE for acceptance and FALSE for denied.
	 */
	public boolean isAuthTokenValid(String authToken) {
		if (authorizationTokensStorage.containsKey(authToken)) {
			return true;
		}
		return false;
	}

	public void logout(String authToken)
			throws GeneralSecurityException {
		if (authorizationTokensStorage.containsKey(authToken)) {
			/**
			 * When a client logs out, the authentication token will be remove
			 * and will be made invalid.
			 */
			authorizationTokensStorage.remove(authToken);
			return;
		}
	}
}