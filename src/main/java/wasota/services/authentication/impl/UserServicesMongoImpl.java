package wasota.services.authentication.impl;

import org.apache.log4j.Logger;

import wasota.mongo.collections.UserModel;
import wasota.mongo.exceptions.MissingPropertiesException;
import wasota.mongo.exceptions.NoPKFoundException;
import wasota.mongo.exceptions.ObjectAlreadyExistsException;
import wasota.services.authentication.UserAuth;
import wasota.services.authentication.UserServices;

public class UserServicesMongoImpl implements UserServices{

	final static Logger logger = Logger.getLogger(UserServicesMongoImpl.class);
	

	/**
	 * Add user in MongoDB
	 */
	public boolean addUser(String user, String email, String pass) throws Exception {

		UserModel u = new UserModel(user, email, pass);
		try {
			u.update(true);
			logger.info("New user added/updated: " + user);
		} catch (MissingPropertiesException | ObjectAlreadyExistsException | NoPKFoundException e) {
			e.printStackTrace();
			throw new Exception("Error adding user: " + e.getMessage());
		}
		return true;
	}

	/**
	 * Load user from MongoDB
	 */
	public UserAuth loadUser(String user) {

		UserModel u = new UserModel(user);
		if (u.find(true)) {
			logger.info("User loaded: " + user);
			return  new UserAuth(u.getUser(), u.getPass(), u.getEmail());
		} else {
			return null;
		}
	}

}
