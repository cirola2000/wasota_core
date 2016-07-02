package wasota.services.currentservices;

import wasota.services.authentication.UserServices;
import wasota.services.authentication.impl.UserServicesMongoImpl;

public class CurrentAuthenticationService {
	
	/**
	 * Current service for authentication
	 */
	public static UserServices authService = new UserServicesMongoImpl();

}
