package wasota.rest.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import wasota.graph.WasotaMainGraph;
import wasota.graph.WasotaPerformanceModel;
import wasota.services.graph.WasotaGraphInterface;

public class WasotaPerformance {

	public WasotaPerformance(String context, String performanceType, WasotaGraphInterface wasotaGraph) {
		performanceByContextAndType(context, performanceType, wasotaGraph);
	}

	public List<WasotaPerformanceModel> performanceListFinal;

	public List<WasotaPerformanceModel> performanceByContextAndType(String context, String performanceType, WasotaGraphInterface wasotaGraph) {

		// pipeline to get all measures

		// get all application for the context
		List<String> appList = wasotaGraph.query().getAlgorithmList(context);

		// get list of experiment
		List<String> expList = wasotaGraph.query().getExperimentList(appList);

		// get list of experiment config
		List<String> expListConfig = wasotaGraph.query().getExperimentConfigList(expList);

		// get list of execution
		List<String> executionList = wasotaGraph.query().getExecutionList(expListConfig);

		// get list measure classification
		List<String> measureClassification = wasotaGraph.query().getMeasureList(executionList);
		
		List<String> performanceTypeList = new ArrayList<String>();
		performanceTypeList.add(performanceType);

		// get all performance types
		performanceListFinal = WasotaMainGraph.mainGraph.graphExtractor
				.getFinalPerformanceList(measureClassification, performanceTypeList);
		
		Collections.sort(performanceListFinal);
		
		return performanceListFinal; 

	}

}
