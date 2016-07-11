package wasota.rest.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import wasota.core.WasotaAPI;
import wasota.core.exceptions.ParameterNotFound;
import wasota.rest.models.WasotaPerformance;
import wasota.rest.models.WasotaPerformanceAll;
import wasota.utils.JSONUtils;

/**
 * 
 * @author Ciro Baron Neto
 * 
 *         Jul 3, 2016
 */

@RestController
public class PerformanceController {

	/**
	 * Get all performance from a given context
	 * 
	 * @param body
	 *            - should be a POST with a JSON body containing a 'context'
	 *            key. An example of body to retrieve all fact prediction:
	 *            "{'context': 'http://mex.aksw.org/mex-core#FactPrediction'}"
	 * @return
	 * @throws ParameterNotFound
	 */
	@RequestMapping(value = "/performance", method = RequestMethod.POST)
	public WasotaPerformanceAll getPerformance(@RequestBody String body) throws ParameterNotFound {

		WasotaPerformanceAll performance = new WasotaPerformanceAll(JSONUtils.getField(body.toString(), "context"),
				WasotaAPI.getWasotaGraph());

		return performance;
	}

	/**
	 * Get all performance from a given context and precision value
	 * 
	 * @param body
	 *            - should be a POST with a JSON body containing a 'context' and
	 *            a 'performance' key. An example of body to retrieve all
	 *            accuracy of fact prediction:
	 *            "{'context': 'http://mex.aksw.org/mex-core#FactPrediction', 'performance': 'http://mex.aksw.org/mex-perf#accuracy'}"
	 * @return
	 * @throws ParameterNotFound
	 */
	@RequestMapping(value = "/performance/get", method = RequestMethod.POST)
	public WasotaPerformance get(@RequestBody String body) throws ParameterNotFound {

		String context = JSONUtils.getField(body.toString(), "context");
		String precision = JSONUtils.getField(body.toString(), "performance");

		WasotaPerformance performance = new WasotaPerformance(context, precision, WasotaAPI.getWasotaGraph());

		return performance;
	}
	

}
