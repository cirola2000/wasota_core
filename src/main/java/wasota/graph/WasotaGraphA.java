package wasota.graph;

import java.util.HashMap;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import wasota.services.graph.impl.WasotaGraphQueriesImpl;

public abstract class WasotaGraphA {
	
	/**
	 * Jena model which will hold the graph
	 */
	protected Model mainModel;
	
	/**
	 * Graph Extractor 
	 */
	public WasotaGraphQueriesImpl graphExtractor;

	/**
	 * Map that holds graphName and hash
	 */
	protected HashMap<String, String> hashName;
	
	public WasotaGraphA() {
		mainModel = ModelFactory.createDefaultModel();
		hashName = new HashMap<String, String>();
		graphExtractor = new WasotaGraphQueriesImpl(mainModel);
	}
	
	

}
