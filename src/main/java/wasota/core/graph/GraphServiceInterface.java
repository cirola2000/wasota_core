package wasota.core.graph;

import wasota.core.exceptions.graph.NotPossibleToLoadGraph;
import wasota.core.exceptions.graph.NotPossibleToSaveGraph;

/**
 * Interface which offers useful methods for loading a graph, creating a graph, etc..
 * @author Ciro Baron Neto
 * 
 * Jul 3, 2016
 */
public interface GraphServiceInterface {
	
	/**
	 * Service to create a new graph object
	 * @param graph
	 * @param namedGraph
	 * @param format
	 * @return
	 * @throws NotPossibleToSaveGraph
	 */
	public Boolean createGraph(String graph, String namedGraph, String format) throws NotPossibleToSaveGraph;
	
	/**
	 * Service to create a graph and bind to a user
	 * @param graph
	 * @param namedGraph
	 * @param user
	 * @param format
	 * @return
	 * @throws NotPossibleToSaveGraph
	 */
	public Boolean createGraphWithUser(String graph, String namedGraph, String user, String format) throws NotPossibleToSaveGraph;
	
	/**
	 * Load a graph 
	 * @param namedGraph
	 * @return
	 * @throws NotPossibleToLoadGraph
	 */
	public WasotaGraphInterface loadGraph(String namedGraph) throws NotPossibleToLoadGraph;

	
}
