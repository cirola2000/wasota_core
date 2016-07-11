/**
 * 
 */
package wasota.core;

import java.util.List;

import org.apache.log4j.Logger;

import wasota.core.authentication.UserAuthenticationServiceInterface;
import wasota.core.exceptions.CannotAddMexNamespaces;
import wasota.core.exceptions.graph.NotPossibleToLoadGraph;
import wasota.core.experiments.ExperimentsServiceInterface;
import wasota.core.graph.GraphServiceInterface;
import wasota.core.graph.GraphStoreInterface;
import wasota.core.graph.GraphUserServiceInterface;
import wasota.core.graph.WasotaGraphInterface;

/**
 * @author Ciro Baron Neto
 * 
 *         Jul 3, 2016
 */
public class WasotaAPI {

	final static Logger logger = Logger.getLogger(WasotaAPI.class);

	private static GraphUserServiceInterface graphUserService = null;

	private static UserAuthenticationServiceInterface authService = null;

	private static ExperimentsServiceInterface experiment = null;

	private static GraphServiceInterface graphService = null;

	private static GraphStoreInterface graphStore = null;

	private static WasotaGraphInterface wasotaGraph = null;

	/**
	 * @param graphUserService
	 *            Set the graphUserService value.
	 */
	public static void setGraphUserService(GraphUserServiceInterface graphUserService) {
		if (WasotaAPI.graphUserService == null) {
			WasotaAPI.graphUserService = graphUserService;
			logger.info("New graph user setted: " + graphUserService.getClass().getName());

		}
	}

	/**
	 * @param authService
	 *            Set the authentication service implementation.
	 */
	public static void setAuthServiceImplementation(UserAuthenticationServiceInterface authService) {
		if (WasotaAPI.authService == null) {
			WasotaAPI.authService = authService;
			logger.info("New authentication implementation setted: " + authService.getClass().getName());
		}
	}

	/**
	 * @param experiment
	 *            Set the experiment implementation.
	 */
	public static void setExperimentImplementation(ExperimentsServiceInterface experiment) {
		if (WasotaAPI.experiment == null) {
			WasotaAPI.experiment = experiment;
			logger.info("New experiment implementation setted: " + experiment.getClass().getName());

		}
	}

	/**
	 * @param graphService
	 *            Set the the implementation for the graph services
	 */
	public static void setGraphServiceImplementation(GraphServiceInterface graphService) {
		if (WasotaAPI.graphService == null) {
			WasotaAPI.graphService = graphService;
			logger.info("New graphService implementation setted: " + graphService.getClass().getName());

		}
	}

	/**
	 * @param graphStore
	 *            Set the graphStore implementation.
	 */
	public static void setGraphStoreImplementation(GraphStoreInterface graphStore) {
		if (WasotaAPI.graphStore == null) {
			WasotaAPI.graphStore = graphStore;
			logger.info("New graphStore implementation setted: " + graphStore.getClass().getName());

		}
	}

	/**
	 * @param wasotaGraph
	 *            Set the wasotaGraph value.
	 */
	public static void setWasotaGraphImplementation(WasotaGraphInterface wasotaGraph) {
		if (WasotaAPI.wasotaGraph == null) {
			WasotaAPI.wasotaGraph = wasotaGraph;
			logger.info("New wasotaGraph implementation setted: " + wasotaGraph.getClass().getName());

			try {

				// add MEX namespaces to graph
				wasotaGraph.addMexNamespacesToModel();
				logger.info("MEX vocabulaies loaded.");

				List<String> graphNames = WasotaAPI.getGraphStore().getAllGraphNames();

				// load all stored graphs
				for (String namedGraph : graphNames) {
					WasotaAPI.getGraphStore().loadGraph(namedGraph, wasotaGraph, "ttl");
				}

				logger.info("Loaded " + graphNames.size() + " graphs.");

			} catch (CannotAddMexNamespaces | NotPossibleToLoadGraph e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * @return the authService
	 * @throws Exception
	 */
	public static UserAuthenticationServiceInterface getAuthService() {
		if (authService == null)
			try {
				throw new Exception("UserAuthenticationServiceInterface should be implemented.");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return authService;
	}

	/**
	 * @return the experiment
	 */
	public static ExperimentsServiceInterface getExperimentService() {
		if (experiment == null)
			try {
				throw new Exception("ExperimentsServiceInterface should be implemented.");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return experiment;
	}

	/**
	 * @return the graphService
	 */
	public static GraphServiceInterface getGraphService() {
		if (graphService == null)
			try {
				throw new Exception("GraphServiceInterface should be implemented.");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return graphService;
	}

	/**
	 * @return the graphStore
	 */
	public static GraphStoreInterface getGraphStore() {
		if (graphStore == null)
			try {
				throw new Exception("GraphStoreInterface should be implemented.");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return graphStore;
	}

	/**
	 * @return the wasotaGraph
	 * @throws Exception
	 */
	public static WasotaGraphInterface getWasotaGraph() {
		if (wasotaGraph == null)
			try {
				throw new Exception("WasotaGraphInterface should be implemented.");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return wasotaGraph;
	}

	/**
	 * @return the graphUserService
	 */
	public static GraphUserServiceInterface getGraphUserService() {
		if (graphUserService == null)
			try {
				throw new Exception("GraphUserServiceInterface should be implemented.");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return graphUserService;
	}

	public static WasotaGraphInterface getNewGraph() {
		try {
			WasotaGraphInterface graph = (WasotaGraphInterface) wasotaGraph.getClass().newInstance();
			graph.addMexNamespacesToModel();
			return graph;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CannotAddMexNamespaces e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
