package wasota.rest.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import wasota.comparators.PerformanceComparator;
import wasota.core.WasotaAPI;
import wasota.core.exceptions.ExperimentNotFound;
import wasota.core.graph.WasotaGraphInterface;
import wasota.core.models.WasotaPerformanceModel;

public class WasotaPerformance {

	public WasotaPerformance(String context, String performanceType, WasotaGraphInterface wasotaGraph) {
		performanceByContextAndType(context, performanceType, wasotaGraph);
	}

	private List<WasotaPerformanceModel> performanceList;

	public List<WasotaPerformanceModel> performanceListFinal = new ArrayList<>();

	public List<WasotaPerformanceModel> performanceByContextAndType(String context, String performanceType, WasotaGraphInterface wasotaGraph) {

		// pipeline to get all measures

		// get all application for the context
		List<String> appList = wasotaGraph.queries().getAlgorithmList(context);
 
		// get list of experiment
		List<String> expList = wasotaGraph.queries().getExperimentList(appList);

		// get list of experiment config
		List<String> expListConfig = wasotaGraph.queries().getExperimentConfigList(expList);

		// get list of execution
		List<String> executionList = wasotaGraph.queries().getExecutionList(expListConfig);

		// get list measure classification
		List<String> measureClassification = wasotaGraph.queries().getMeasureList(executionList);
		
		List<String> performanceTypeList = new ArrayList<String>();
		performanceTypeList.add(performanceType);

		// get all performance types
		performanceList = wasotaGraph.queries()
				.getFinalPerformanceList(measureClassification, performanceTypeList);
		
		
		
		for(WasotaPerformanceModel performance : performanceList){
			try {
				if(WasotaAPI.getExperimentService().isPublic(performance.url)){
					performanceListFinal.add(performance);
				}
			} catch (ExperimentNotFound e) {
				performanceListFinal.add(performance);

			}
		}
		
		
		Collections.sort(performanceListFinal, new PerformanceComparator());
		
		return performanceListFinal; 

	}

}
