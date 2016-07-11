/**
 * 
 */
package wasota.rest.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import wasota.core.WasotaAPI;
import wasota.core.exceptions.CannotAddMexNamespaces;
import wasota.core.exceptions.graph.NotPossibleToLoadGraph;
import wasota.core.exceptions.graph.NotPossibleToSaveGraph;
import wasota.core.graph.WasotaGraphInterface;
import wasota.core.models.WasotaPerformanceModel;
import wasota.rest.messages.WasotaRestModel;
import wasota.rest.messages.WasotaRestMsg;

/**
 * @author Ciro Baron Neto
 * 
 *         Jul 4, 2016
 */

@RestController
public class ExperimentController {

	/**
	 * Return the number of experiments
	 * 
	 * @return
	 */
	@RequestMapping(value = "/experiments/size", method = RequestMethod.GET)
	public WasotaRestModel getExperimentSize() {

		int numberOfExperiments = WasotaAPI.getExperimentService().numberOfExperiments();
		WasotaRestModel msg = new WasotaRestModel(WasotaRestMsg.OK, String.valueOf(numberOfExperiments).toString());
		return msg;

	}

	/**
	 * Return experiment list
	 * 
	 * @return
	 */
	@RequestMapping(value = "/experiments/list", method = RequestMethod.GET)
	public List<WasotaPerformanceModel> getExperimentList() {

		return WasotaAPI.getWasotaGraph().queries().getAllFinalPerformanceList();

	}

	/**
	 * Return context list of a graph
	 * 
	 * @return
	 * @throws NotPossibleToSaveGraph 
	 * @throws NotPossibleToLoadGraph 
	 * @throws CannotAddMexNamespaces 
	 */
	@RequestMapping(value = "/experiments/graphContext", method = RequestMethod.GET)
	public List<String> getGraphContextList(@RequestParam(value = "graphName", required = true) String graphName) throws NotPossibleToLoadGraph, CannotAddMexNamespaces {

		WasotaGraphInterface graph = WasotaAPI.getNewGraph();
		WasotaAPI.getGraphStore().loadGraph(graphName, graph, "ttl");

		WasotaAPI.getExperimentService().getContextList(graph);

		return null;

	}

}
