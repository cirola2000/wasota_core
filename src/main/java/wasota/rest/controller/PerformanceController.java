package wasota.rest.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import wasota.rest.models.WasotaPerformance;
import wasota.rest.models.WasotaPerformanceAll;

@RestController
public class PerformanceController {
	
	
	@RequestMapping(value = "/performance/getAll", method = RequestMethod.POST)
	public WasotaPerformanceAll getPerformance(@RequestBody String body) {
		JSONObject json = new JSONObject(body);
		
		WasotaPerformanceAll performance = new WasotaPerformanceAll(json.getString("context"));

		return performance;
	}
	
	@RequestMapping(value = "/performance/get", method = RequestMethod.POST)
	public WasotaPerformance get(@RequestBody String body) {
		
		JSONObject json = new JSONObject(body);
		
		WasotaPerformance performance = new WasotaPerformance(json.getString("context"), json.getString("precision"));

		return performance;
	}
	
	
}
