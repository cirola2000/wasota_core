package wasota.core.authentication;

import java.util.List;

import wasota.core.graph.WasotaGraphInterface;

/**
 * Interface with methods to handle with user data
 * @author Ciro Baron Neto
 * 
 * Jul 3, 2016
 */
public interface UserAuthenticationServiceInterface {
	
	/**
	 * Create a new user
	 * 
	 * @param user
	 * @param email
	 * @param password
	 * @return boolean indicating if the user was created
	 * @throws Exception
	 */
	
	public boolean addUser(String user, String email, String password) throws Exception;

	/**
	 * Load user
	 * @param user
	 * @return the user
	 * @throws Exception
	 */
	public UserAuth loadUser(String user);
	
	/**
	 * Get the current authenticated user.
	 * @return the authenticated user
	 */
	public UserAuth getAuthenticatedUser();
	
}
