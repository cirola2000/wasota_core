package wasota.services.currentservices;

import wasota.services.graph.GraphServiceInterface;
import wasota.services.graph.impl.GraphServiceImpl;

public class CurrentGraphService {
	
	/**
	 * Current implemented service for graphService
	 */
	private static GraphServiceInterface graphService = null;
	
	public static GraphServiceInterface getGraphService(){
		if(graphService == null){
			graphService = new GraphServiceImpl();
		}
		
		return graphService;
	}

}
