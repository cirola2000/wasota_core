package wasota.core.experiments.impl;

import org.apache.log4j.Logger;

import wasota.core.WasotaAPI;
import wasota.core.authentication.UserAuth;
import wasota.core.exceptions.UserNotAllowed;
import wasota.core.experiments.ExperimentsServiceInterface;
import wasota.mongo.collections.UserExperiment;
import wasota.mongo.exceptions.MissingPropertiesException;
import wasota.mongo.exceptions.NoPKFoundException;
import wasota.mongo.exceptions.ObjectAlreadyExistsException;

/**
 * Implementation of {@link ExperimentsServiceInterface}
 * 
 * @author Ciro Baron Neto
 * 
 *         Jul 3, 2016
 */
public class ExperimentServicesImpl implements ExperimentsServiceInterface {

	final static Logger logger = Logger.getLogger(ExperimentsServiceInterface.class);

	@Override
	public Boolean isPublic(String experimentURI) {

		UserExperiment experiment = new UserExperiment(experimentURI);
		if (experiment.find(true)) {
			if (experiment.getVisible())
				return true;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * wasota.core.experiments.ExperimentsServiceInterface#chengeExperimentState
	 * (java.lang.String, wasota.core.authentication.UserAuth)
	 */
	@Override
	public Boolean changeExperimentState(String experimentURI, UserAuth user) throws UserNotAllowed {

		logger.info("Changing experiment: " + experimentURI);

		UserExperiment userExperiment = new UserExperiment(experimentURI);

		if (userExperiment.getUser().equals(user.getUser())) {
			if (userExperiment.getVisible())
				userExperiment.setVisible(false);
			else
				userExperiment.setVisible(true);

			try {
				userExperiment.update(false);
			} catch (MissingPropertiesException | ObjectAlreadyExistsException | NoPKFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		} else {
			logger.error("Failed! User: " + user.getUser() + " is not the experiment owner.");
			throw new UserNotAllowed("Failed! User: " + user.getUser() + " is not the experiment owner.");

		}
	}

}
