package wasota.services.currentservices;

import wasota.services.graph.GraphServiceInterface;
import wasota.services.graph.impl.GraphServiceImpl;

public class CurrentGraphService {
	
	/**
	 * Current implemented service for graphService
	 */
	public static GraphServiceInterface graphService = new GraphServiceImpl();

}
