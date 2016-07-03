package wasota.rest.controller;

import java.io.StringWriter;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import wasota.core.WasotaAPI;
import wasota.core.exceptions.ParameterNotFound;
import wasota.core.exceptions.graph.NotPossibleToLoadGraph;
import wasota.core.exceptions.graph.NotPossibleToSaveGraph;
import wasota.core.graph.impl.GraphServiceImpl;
import wasota.rest.messages.RestMsg;
import wasota.rest.messages.WasotaRestModel;
import wasota.rest.messages.WasotaRestModel;
import wasota.utils.JSONUtils;

/**
 * 
 * @author Ciro Baron Neto
 * 
 *         Jul 3, 2016
 */
@RestController
public class GraphController {

	final static Logger logger = Logger.getLogger(GraphController.class);

	/**
	 * Add new public graph in Wasota
	 * 
	 * @param body
	 *            - should be a PUT with a JSON body containing a 'format',
	 *            'graphName' and 'graph' key. Example: '{"format":"ttl",
	 *            "graph":". . . (your RDF here) . . .", "graphName":
	 *            "myNamedGraph"}'
	 * @throws NotPossibleToSaveGraph
	 * @throws ParameterNotFound
	 */
	@RequestMapping(value = "/graph", method = RequestMethod.PUT)
	public WasotaRestModel addGraph(@RequestBody String body) throws ParameterNotFound, NotPossibleToSaveGraph {

		WasotaRestModel restMsg = new WasotaRestModel(RestMsg.OK, "");

		// get all parameters from PUT request

		String format;

		format = JSONUtils.getField(body.toString(), "format");
		String graphName = JSONUtils.getField(body.toString(), "graphName");
		String graph = JSONUtils.getField(body.toString(), "graph");

		WasotaAPI.getGraphService().createGraph(graph, graphName, format);

		return restMsg;

	}

	/**
	 * Return a public graph stored in Wasota
	 * 
	 * @param graphName
	 *            - name or identifier of the graph
	 * @return
	 * @throws NotPossibleToLoadGraph
	 */
	@RequestMapping(value = "/graph", method = RequestMethod.GET)
	public String getGraph(@RequestParam(value = "graphName", required = true) String graphName)
			throws NotPossibleToLoadGraph {

		StringWriter out = new StringWriter();

		WasotaAPI.getGraphService().loadGraph(graphName).writeAsString(out);

		return out.toString();
	}

}