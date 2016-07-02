package wasota.services.experiments.impl;

import wasota.mongo.collections.UserExperiment;
import wasota.services.experiments.ExperimentsServiceInterface;

public class ExperimentServicesImpl implements ExperimentsServiceInterface {

	@Override
	public Boolean isPublic(String experimentURI) {

		UserExperiment experiment = new UserExperiment(experimentURI);
		if (experiment.find(true)){
			if(experiment.getVisible())
				return true;			
		}
		return true;
	}

}
