package wasota.services.currentservices;

import java.util.List;

import org.apache.log4j.Logger;

import wasota.exceptions.CannotAddMexNamespaces;
import wasota.exceptions.graph.NotPossibleToLoadGraph;
import wasota.exceptions.graph.NotPossibleToSaveGraph;
import wasota.services.graph.WasotaGraphInterface;
import wasota.services.graph.impl.WasotaGraphJenaImpl;

public class CurrentWasotaGraph {

	final static Logger logger = Logger.getLogger(CurrentWasotaGraph.class);

	/**
	 * Current implemented service for graphService
	 */
	private static WasotaGraphInterface wasotaGraph = null;

	public static WasotaGraphInterface getWasotaGraph() {

		if (wasotaGraph == null) {

			logger.info("Wasota main graph is being created...");

			// add an implementation
			wasotaGraph = new WasotaGraphJenaImpl();
			logger.info("The implementation for wasotaGraph is: " + wasotaGraph.getClass().getName());

			try {

				// add MEX namespaces to graph
				wasotaGraph.addMexNamespacesToModel();
				logger.info("MEX vocabulaies loaded.");

				List<String> graphNames = CurrentGraphStore.getGraphStore().getAllGraphNames();

				// load all stored graphs
				for (String namedGraph : graphNames) {
					CurrentGraphStore.getGraphStore().loadGraph(namedGraph, wasotaGraph, "ttl");
				}

				logger.info("Loaded " + graphNames.size() + " graphs.");

			} catch (CannotAddMexNamespaces | NotPossibleToLoadGraph e) {
				e.printStackTrace();
			}

		}

		return wasotaGraph;
	}

	public static WasotaGraphInterface getNewGraph() {
		return new WasotaGraphJenaImpl();
	}

}
