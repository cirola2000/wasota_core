package wasota.rest.controller;

import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import wasota.services.currentservices.CurrentAuthenticationService;
import wasota.services.currentservices.CurrentGraphService;
import wasota.utils.JSONUtils;

@RestController
public class UserController {

	@RequestMapping(value = "/user/add", method = RequestMethod.PUT)
	public void addUser(@RequestBody String body) throws Exception {

		CurrentAuthenticationService.authService.addUser(new JSONObject(body).get("user").toString(),
				new JSONObject(body).get("email").toString(), new JSONObject(body).get("pass").toString());

	}

	@RequestMapping(value = "/user/graph/add", method = RequestMethod.PUT)
	public void addUserGraph(@RequestBody String body) throws Exception {

		// get all parameters from POST request
		String format = JSONUtils.getField(body.toString(), "format");
		String namedGraph = JSONUtils.getField(body.toString(), "namedGraph");
		String graph = JSONUtils.getField(body.toString(), "graph");

		// get username from authentication process
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// case there is, link the graph and experiments with the user
		CurrentGraphService.graphService.createGraphWithUser(graph, namedGraph, auth.getName(), format);

	}

}