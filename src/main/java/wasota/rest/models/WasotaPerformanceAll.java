package wasota.rest.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import wasota.services.graph.WasotaGraphInterface;

public class WasotaPerformanceAll {

	
	public WasotaPerformanceAll(String context, WasotaGraphInterface wasotaGraph) {
		performanceByContext(context, wasotaGraph);
	}

	public List<String> precisionListFinal = new ArrayList<String>();

	public void performanceByContext(String context, WasotaGraphInterface wasotaGraph) {

		List<String> appList = wasotaGraph.query().getAlgorithmList(context);

		// get list of experiment
		List<String> expList = wasotaGraph.query().getExperimentList(appList);

		// get list of experiment config
		List<String> expListConfig = wasotaGraph.query().getExperimentConfigList(expList);

		// get list of execution
		List<String> executionList = wasotaGraph.query().getExecutionList(expListConfig);

		// get list measure classification
		List<String> measureClassification = wasotaGraph.query().getMeasureList(executionList);

		// get all performance types
		precisionListFinal = wasotaGraph.query().getAllPerformanceTypes(measureClassification); 


	}

}
