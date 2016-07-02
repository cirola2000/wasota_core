package wasota.rest.controller;

import java.io.StringWriter;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import wasota.exceptions.graph.NotPossibleToLoadGraph;
import wasota.exceptions.graph.NotPossibleToSaveGraph;
import wasota.services.currentservices.CurrentGraphService;
import wasota.services.graph.impl.GraphServiceImpl;
import wasota.utils.JSONUtils;

@RestController
public class GraphController {

	final static Logger logger = Logger.getLogger(GraphController.class);

	@RequestMapping(value = "/graph", method = RequestMethod.PUT)
	public void addGraph(@RequestBody String body) throws NotPossibleToSaveGraph {

		// get all parameters from POST request
		String format = JSONUtils.getField(body.toString(), "format");
		String namedGraph = JSONUtils.getField(body.toString(), "namedGraph");
		String graph = JSONUtils.getField(body.toString(), "graph");

		CurrentGraphService.getGraphService().createGraph(graph, namedGraph, format);

	}

	@RequestMapping(value = "/graph", method = RequestMethod.GET)
	public String getGraph(@RequestParam(value = "namedGraph", required = true) String namedGraph)
			throws NotPossibleToLoadGraph {

		StringWriter out = new StringWriter();

		GraphServiceImpl s = new GraphServiceImpl();
		s.loadGraph(namedGraph).writeAsString(out);

		return out.toString();
	}

}