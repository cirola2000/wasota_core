package wasota.services.currentservices;

import org.apache.log4j.Logger;

import wasota.services.graph.GraphStoreInterface;
import wasota.services.graph.impl.GraphStoreFSImpl;

public class CurrentGraphStore {
	
	final static Logger logger = Logger.getLogger(CurrentGraphStore.class);

	
	private static GraphStoreInterface graphStore = null;
	
	public static GraphStoreInterface getGraphStore(){

		if(graphStore == null){

			logger.info("Wasota graph store is being created...");
			graphStore = new GraphStoreFSImpl();
			logger.info("The implementation for graphStore is: " + graphStore.getClass().getName());

		}
		
		return graphStore;
	}

}
