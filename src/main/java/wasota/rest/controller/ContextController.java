package wasota.rest.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import wasota.core.WasotaAPI;
/**
 * 
 * @author Ciro Baron Neto
 * 
 * Jul 3, 2016
 */
@RestController
public class ContextController {

	/**
	 * Return a list of context
	 * @return
	 */
	@RequestMapping(value = "/context", method = RequestMethod.GET)
	public HashMap<String, String>  getAllContext() {
		
		HashMap<String, String> context = WasotaAPI.getWasotaGraph().queries().getAllContext();
		return context;
	}	
	
}
