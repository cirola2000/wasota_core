package wasota.rest.controller;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import wasota.rest.models.WasotaPerformance;
import wasota.rest.models.WasotaPerformanceAll;
import wasota.services.currentservices.CurrentWasotaGraph;
import wasota.utils.JSONUtils;

@RestController
public class PerformanceController {

	@RequestMapping(value = "/performance", method = RequestMethod.POST)
	public WasotaPerformanceAll getPerformance(@RequestBody String body) {

		WasotaPerformanceAll performance = new WasotaPerformanceAll(JSONUtils.getField(body.toString(), "context"),
				CurrentWasotaGraph.getWasotaGraph());

		return performance;
	}

	@RequestMapping(value = "/performance/get", method = RequestMethod.POST)
	public WasotaPerformance get(@RequestBody String body) {

		JSONObject json = new JSONObject(body);

		WasotaPerformance performance = new WasotaPerformance(json.getString("context"), json.getString("precision"),
				CurrentWasotaGraph.getWasotaGraph());

		return performance;
	}

}
