package wasota.services.graph;

import wasota.exceptions.graph.NotPossibleToLoadGraph;
import wasota.exceptions.graph.NotPossibleToSaveGraph;

public interface GraphServiceInterface {
	public Boolean createGraph(String graph, String namedGraph, String format) throws NotPossibleToSaveGraph;
	
	public Boolean createGraphWithUser(String graph, String namedGraph, String user, String format) throws NotPossibleToSaveGraph;
	
	public WasotaGraphInterface loadGraph(String namedGraph) throws NotPossibleToLoadGraph;

	
}
