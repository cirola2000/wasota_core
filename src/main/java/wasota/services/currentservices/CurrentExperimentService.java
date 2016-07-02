package wasota.services.currentservices;

import org.apache.log4j.Logger;

import wasota.services.experiments.ExperimentsServiceInterface;
import wasota.services.experiments.impl.ExperimentServicesImpl;

public class CurrentExperimentService {

	final static Logger logger = Logger.getLogger(CurrentExperimentService.class);

	private static ExperimentsServiceInterface experiment = null;

	public static ExperimentsServiceInterface getGraphStore() {

		if (experiment == null) {
			experiment = new ExperimentServicesImpl();

		}

		return experiment;
	}

}
