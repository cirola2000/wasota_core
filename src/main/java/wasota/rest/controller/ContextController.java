package wasota.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import wasota.rest.models.AllContextTemplate;
import wasota.services.currentservices.CurrentWasotaGraph;

@RestController
public class ContextController {

	@RequestMapping(value = "/context", method = RequestMethod.GET)
	public AllContextTemplate getAllContext() {
		AllContextTemplate template = new AllContextTemplate(CurrentWasotaGraph.getWasotaGraph());
		return template;
	}	
	
}
