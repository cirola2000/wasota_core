package wasota.services.authentication;

public interface UserServices {
	
	/**
	 * Create a new user
	 * 
	 * @param user
	 * @param email
	 * @param pass
	 * @return boolean indicating if the user was created
	 * @throws Exception
	 */
	
	public boolean addUser(String user, String email, String pass) throws Exception;

	/**
	 * Load user
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public UserAuth loadUser(String user);

}
