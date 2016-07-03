/**
 * 
 */
package wasota.core.graph.impl;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.riot.system.StreamRDFWrapper;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import wasota.core.WasotaAPI;
import wasota.core.authentication.UserAuth;
import wasota.core.exceptions.CannotAddMexNamespaces;
import wasota.core.exceptions.graph.NotPossibleToLoadGraph;
import wasota.core.graph.GraphUserServiceInterface;
import wasota.core.graph.WasotaGraphInterface;
import wasota.core.models.WasotaPerformanceModel;
import wasota.mongo.collections.UserGraph;

/**
 * @author Ciro Baron Neto
 * 
 *         Jul 3, 2016
 */
public class GraphUserServiceImpl implements GraphUserServiceInterface {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * wasota.core.graph.GraphUserServiceInterface#getAllGraphs(wasota.core.
	 * authentication.UserAuth)
	 */
	@Override
	public List<String> getAllGraphs(UserAuth user) {

		List<String> graphs = new ArrayList<>();

		// get graphs
		DBCursor graphList = UserGraph.getDBInstance().getCollection(UserGraph.COLLECTION)
				.find(new BasicDBObject(UserGraph.USER, user.getUser()));

		for (DBObject graph : graphList) {
			graphs.add(graph.get(UserGraph.GRAPH_NAME).toString());

		}
		return graphs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * wasota.core.graph.GraphUserServiceInterface#getAllPerformance(wasota.core
	 * .authentication.UserAuth)
	 */
	@Override
	public List<WasotaPerformanceModel> getAllPerformance(UserAuth user) {
		List<String> graphNames = getAllGraphs(user);
		List<WasotaPerformanceModel> performanceList = new ArrayList<>();

		for (String graphName : graphNames) {
			WasotaGraphInterface wasotaGraph = WasotaAPI.getNewGraph();
			try {
				wasotaGraph.addMexNamespacesToModel();
			} catch (CannotAddMexNamespaces e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				WasotaAPI.getGraphStore().loadGraph(graphName, wasotaGraph, "ttl");

			} catch (NotPossibleToLoadGraph e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			performanceList.addAll(wasotaGraph.queries().getAllFinalPerformanceList());

		}
		return performanceList;

	}

}
