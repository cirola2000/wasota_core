package wasota.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import wasota.rest.models.AllContextTemplate;

@RestController
public class ContextController {

	@RequestMapping(value = "/context/getAll", method = RequestMethod.GET)
	public AllContextTemplate getAllContext() {
		AllContextTemplate template = new AllContextTemplate();
		return template;
	}
	
//	@RequestMapping(value = "/context/get", method = RequestMethod.GET)
//	public AllContextTemplate getContext() {
//		AllContextTemplate template = new AllContextTemplate();
//		return template;
//	}
	
	
	
}
