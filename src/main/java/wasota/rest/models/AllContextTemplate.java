package wasota.rest.models;

import java.util.HashMap;

import wasota.graph.WasotaMainGraph;
import wasota.services.graph.WasotaGraphInterface;

public class AllContextTemplate {

	public HashMap<String, String> context = new HashMap<String, String>();

	public AllContextTemplate(WasotaGraphInterface wasotaGraph) {
		context = wasotaGraph.query().getAllContext();
	}

}
