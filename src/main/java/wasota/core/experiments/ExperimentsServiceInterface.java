package wasota.core.experiments;

import wasota.core.authentication.UserAuth;
import wasota.core.exceptions.UserNotAllowed;

/**
 * Interface with methods for experiments
 * @author Ciro Baron Neto
 * 
 * Jul 3, 2016
 */
public interface ExperimentsServiceInterface {

	/**
	 * Return if an experiment is public (not binded to a user) or private (it is binded to a user)
	 * @param experimentURI
	 * @return
	 */
	public Boolean isPublic(String experimentURI);
	
	/**
	 * Change an experiment to public or private
	 * @param experimentURI
	 * @param user - the authenticated user
	 * @return
	 */
	public Boolean changeExperimentState(String experimentURI, UserAuth user)  throws UserNotAllowed ;
	
	
}
