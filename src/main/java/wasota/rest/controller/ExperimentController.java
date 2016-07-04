/**
 * 
 */
package wasota.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import wasota.core.WasotaAPI;
import wasota.rest.messages.WasotaRestModel;
import wasota.rest.messages.WasotaRestMsg;

/**
 * @author Ciro Baron Neto
 * 
 * Jul 4, 2016
 */

@RestController
public class ExperimentController {
	
	/**
	 * Return the number of experiments
	 * @return 
	 */
	@RequestMapping(value = "/experiments/size", method = RequestMethod.GET)
	public WasotaRestModel  getAllContext() {
		
		int numberOfExperiments = WasotaAPI.getExperimentService().numberOfExperiments();
		WasotaRestModel msg = new WasotaRestModel(WasotaRestMsg.OK,String.valueOf(numberOfExperiments).toString());
		return msg;
		
	}	
	

}
