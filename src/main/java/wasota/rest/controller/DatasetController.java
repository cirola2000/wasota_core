package wasota.rest.controller;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import wasota.graph.WasotaGraph;
import wasota.rest.models.WasotaDataset;
import wasota.utils.FileUtils;

@RestController
public class DatasetController {

	@RequestMapping(value = "/dataset/add", method = RequestMethod.POST)
	public WasotaDataset addPost(@RequestParam(value = "namedGraph", required = true) String namedGraph,
			@RequestParam(value = "format", required = true) String format, @RequestBody String body) {

		JSONObject o = new JSONObject(body.toString());
		WasotaDataset dataset = new WasotaDataset(namedGraph, format, o.get("graph").toString());
		
//		if (!WasotaGraph.queryIndex(FileUtils.stringToHash(namedGraph))) {
			dataset.saveToFile();
//		}
//		else
//			System.out.println(false);
		return dataset;
	}

	@RequestMapping(value = "/dataset/query", method = RequestMethod.POST)
	public Boolean query(@RequestBody String body) {

		return WasotaGraph.queryIndex(FileUtils.stringToHash(new JSONObject(body).get("namedGraph").toString()));
	}

}