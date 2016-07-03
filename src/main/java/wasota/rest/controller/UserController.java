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
import wasota.utils.JSONUtils;

@RestController
public class UserController {

	@RequestMapping(value = "/user/add", method = RequestMethod.PUT)
	public void addUser(@RequestBody String body) throws Exception {

		WasotaAPI.getAuthService().addUser(new JSONObject(body).get("user").toString(),
				new JSONObject(body).get("email").toString(), new JSONObject(body).get("pass").toString());

	}

	@RequestMapping(value = "/user/graph/add", method = RequestMethod.PUT)
	public void addUserGraph(@RequestBody String body) throws NotPossibleToSaveGraph, ParameterNotFound  {

		// get all parameters from POST request
		String format = JSONUtils.getField(body.toString(), "format");
		String namedGraph = JSONUtils.getField(body.toString(), "namedGraph");
		String graph = JSONUtils.getField(body.toString(), "graph");

		// case there is, link the graph and experiments with the user
		WasotaAPI.getGraphService().createGraphWithUser(graph, namedGraph, WasotaAPI.getAuthService().getAuthenticatedUser().getUser(), format);
	}

	@RequestMapping(value = "/user/graphs", method = RequestMethod.GET)
	public List<String> getUserGraphs()  {

		// get user graphs
		List<String> graphs = WasotaAPI.getGraphUserService()
				.getAllGraphs(WasotaAPI.getAuthService().getAuthenticatedUser());

		return graphs;
	}
	
	@RequestMapping(value = "/user/performance", method = RequestMethod.GET) 
	public List<WasotaPerformanceModel> getAllPerformance()  {

		// get user graphs
		List<WasotaPerformanceModel> graphs = WasotaAPI.getGraphUserService()
				.getAllPerformance(WasotaAPI.getAuthService().getAuthenticatedUser());

		return graphs;
	}
	
	@RequestMapping(value = "/user/changeExperiment", method = RequestMethod.POST) 
	public void changeExperiment(@RequestBody String body) throws UserNotAllowed, ParameterNotFound  {

		String experimentURI = JSONUtils.getField(body.toString(), "experimentURI");

		WasotaAPI.getExperimentService().changeExperimentState(experimentURI, WasotaAPI.getAuthService().getAuthenticatedUser());

	}

}