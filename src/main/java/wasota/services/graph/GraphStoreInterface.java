package wasota.services.graph;

import java.util.List;

import wasota.exceptions.graph.NotPossibleToLoadGraph;
import wasota.exceptions.graph.NotPossibleToSaveGraph;

public interface GraphStoreInterface {
	
	public Boolean saveGraph(String namedGraph, WasotaGraphInterface graph) throws NotPossibleToSaveGraph;

	public Boolean loadGraph(String namedGraph, WasotaGraphInterface graph, String format) throws NotPossibleToLoadGraph;

	public List<String> getAllGraphNames();

}
