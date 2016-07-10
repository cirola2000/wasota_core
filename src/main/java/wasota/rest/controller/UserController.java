package wasota.rest.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import wasota.core.WasotaAPI;
import wasota.core.exceptions.ParameterNotFound;
import wasota.core.exceptions.UserNotAllowed;
import wasota.core.exceptions.graph.NotPossibleToSaveGraph;
import wasota.core.models.WasotaPerformanceModel;
import wasota.rest.messages.WasotaRestMsg;
import wasota.rest.messages.WasotaRestModel;
import wasota.utils.JSONUtils;

/**
 * 
 * @author Ciro Baron Neto
 * 
 *         Jul 3, 2016
 */
@RestController
public class UserController {

	/**
	 * Add a new user
	 * 
	 * @param body
	 *            - should be a PUT with a JSON body containing a 'user',
	 *            'email', and a 'password' key. An example of body to retrieve
	 *            all accuracy of fact prediction:
	 *            "{'user':'ciro', 'password': 'mypassword', 'email':'myemail@gmail.com'}"
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/add", method = RequestMethod.PUT)
	public WasotaRestModel addUser(@RequestBody String body) throws Exception {
		WasotaRestModel restMsg = new WasotaRestModel(WasotaRestMsg.OK, "");

		WasotaAPI.getAuthService().addUser(new JSONObject(body).get("user").toString(),
				new JSONObject(body).get("email").toString(), new JSONObject(body).get("password").toString());

		return restMsg;
	}

	/**
	 * Add new private (binded to a user) graph in Wasota
	 * 
	 * @param body
	 *            - should be a PUT with a JSON body containing a 'format',
	 *            'graphName' and 'graph' key. Example: '{"format":"ttl",
	 *            "graph":". . . (your RDF here) . . .", "graphName":
	 *            "myNamedGraph"}'
	 * @throws NotPossibleToSaveGraph
	 * @throws ParameterNotFound
	 */
	@RequestMapping(value = "/user/graph/add", method = RequestMethod.PUT)
	public WasotaRestModel addUserGraph(@RequestBody String body) throws NotPossibleToSaveGraph, ParameterNotFound {

		WasotaRestModel restMsg = new WasotaRestModel(WasotaRestMsg.OK, "");
		
		// get all parameters from POST request
		String format = JSONUtils.getField(body.toString(), "format");
		String graphName = JSONUtils.getField(body.toString(), "graphName");
		String graph = JSONUtils.getField(body.toString(), "graph");

		// case there is, link the graph and experiments with the user
		WasotaAPI.getGraphService().createGraphWithUser(graph, graphName,
				WasotaAPI.getAuthService().getAuthenticatedUser().getUser(), format);
		
		return restMsg;
	}

	/**
	 * Return a list of graphs binded to a user
	 * 
	 * @return
	 */
	@RequestMapping(value = "/user/graphs", method = RequestMethod.GET)
	public List<String> getUserGraphs() {

		// get user graphs
		List<String> graphs = WasotaAPI.getGraphUserService()
				.getAllGraphs(WasotaAPI.getAuthService().getAuthenticatedUser());

		return graphs;
	}

	/**
	 * Return a list of experiment (grouped by performance) binded to a user
	 * 
	 * @return
	 */
	@RequestMapping(value = "/user/performance", method = RequestMethod.POST)
	public List<WasotaPerformanceModel> getAllPerformance() {

		// get user graphs
		List<WasotaPerformanceModel> graphs = WasotaAPI.getGraphUserService()
				.getAllPerformance(WasotaAPI.getAuthService().getAuthenticatedUser());

		return graphs;
	}

	/**
	 * Change an experiment to public or private
	 * 
	 * @param body
	 *            - should be a PUT with a JSON body containing a
	 *            'experimentURI' key. Example: '{"experimentURI":
	 *            "http://mex.aksw.org/examples/exp_cf_1_2025644708_exe_2_mea_3"
	 *            }'
	 * @throws UserNotAllowed
	 * @throws ParameterNotFound
	 */
	@RequestMapping(value = "/user/changeExperiment", method = RequestMethod.PUT)
	public WasotaRestModel  changeExperiment(@RequestBody String body) throws UserNotAllowed, ParameterNotFound {
		WasotaRestModel restMsg = new WasotaRestModel(WasotaRestMsg.OK, "");
		String experimentURI = JSONUtils.getField(body.toString(), "experimentURI");

		WasotaAPI.getExperimentService().changeExperimentState(experimentURI,
				WasotaAPI.getAuthService().getAuthenticatedUser());
		
		return restMsg;

	}

}