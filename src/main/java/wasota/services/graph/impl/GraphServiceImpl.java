package wasota.services.graph.impl;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.log4j.Logger;

import wasota.exceptions.CannotAddMexNamespaces;
import wasota.exceptions.graph.NotPossibleToLoadGraph;
import wasota.exceptions.graph.NotPossibleToSaveGraph;
import wasota.graph.WasotaPerformanceModel;
import wasota.mongo.collections.UserExperiment;
import wasota.mongo.collections.UserGraph;
import wasota.mongo.exceptions.MissingPropertiesException;
import wasota.mongo.exceptions.NoPKFoundException;
import wasota.mongo.exceptions.ObjectAlreadyExistsException;
import wasota.services.currentservices.CurrentWasotaGraph;
import wasota.services.graph.GraphServiceInterface;
import wasota.services.graph.GraphStoreInterface;
import wasota.services.graph.WasotaGraphInterface;
import wasota.utils.FileUtils;
import wasota.utils.Formats;

public class GraphServiceImpl implements GraphServiceInterface {

	final static Logger logger = Logger.getLogger(GraphServiceImpl.class);

	static GraphStoreInterface store = new GraphStoreFSImpl();

	/**
	 * Create a new graph and save.
	 * 
	 * @param graph
	 * @param namedGraph
	 * @return
	 * @throws NotPossibleToSaveGraph
	 */

	public Boolean createGraph(String graph, String namedGraph, String format) throws NotPossibleToSaveGraph {

		WasotaGraphInterface wasotaGraph = new WasotaGraphJenaImpl();
		try {
			wasotaGraph.readAsStream(new ByteArrayInputStream(graph.getBytes("UTF-8")), format);
			store.saveGraph(namedGraph, wasotaGraph);

			logger.info("Graph uploaded/saved: " + namedGraph);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return true;
	}

	public Boolean createGraphWithUser(String graph, String namedGraph, String user, String format) throws NotPossibleToSaveGraph {

		WasotaGraphInterface wasotaGraph = new WasotaGraphJenaImpl();
		try {
			wasotaGraph.readAsStream(new ByteArrayInputStream(graph.getBytes("UTF-8")), format);
			store.saveGraph(namedGraph, wasotaGraph);

			logger.info("Graph uploaded/saved: " + namedGraph);

			// update user/graph relation
			UserGraph userGraph = new UserGraph(namedGraph, FileUtils.stringToHash(namedGraph), user);
			userGraph.update(true);


			// now update the relation graph-experiment
			try {
				
				WasotaGraphInterface wasotaGraphImpl = CurrentWasotaGraph.getNewGraph();
				wasotaGraphImpl.readAsStream(
						new ByteArrayInputStream(graph.getBytes("UTF-8")), format);
				wasotaGraphImpl.addMexNamespacesToModel();
				
				
				List<WasotaPerformanceModel> finalPerformanceList = wasotaGraphImpl.query().getAllFinalPerformanceList(); 
				
				for (WasotaPerformanceModel model : finalPerformanceList) {
					UserExperiment graphExperiment = new UserExperiment(model.url, user);
					graphExperiment.setVisible(true);
					graphExperiment.update(true);
					logger.info(model.url+ " experiment added for user: '"+ user +"'");
				}

			} catch (CannotAddMexNamespaces e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (UnsupportedEncodingException | MissingPropertiesException | ObjectAlreadyExistsException
				| NoPKFoundException e) {
			e.printStackTrace();
			throw new NotPossibleToSaveGraph(e.getMessage());
		}
		return true;
	}

	public WasotaGraphInterface loadGraph(String namedGraph) throws NotPossibleToLoadGraph {

		WasotaGraphInterface wasotaGraph = new WasotaGraphJenaImpl();

		store.loadGraph(namedGraph, wasotaGraph, "ttl");

		logger.info("Graph uploaded/saved: " + namedGraph);

		return wasotaGraph;
	}

}
