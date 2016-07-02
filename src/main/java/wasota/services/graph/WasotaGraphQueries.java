package wasota.services.graph;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import wasota.graph.WasotaPerformanceModel;

public interface WasotaGraphQueries {
	
	public HashMap<String, String> getAllContext();
	
	/**
	 * Get all performance measures
	 */
	public List<String> getPerformanceList();
	
	/**
	 * Get algorithms based on a context
	 * 
	 * @param context
	 * @return
	 */
	public List<String> getAlgorithmList(String context);
	
	/**
	 * Get a list of experiment of a list of application
	 * 
	 * @param applicationList
	 * @return experimentList
	 */
	public List<String> getExperimentList(List<String> applicationList);
	
	/**
	 * Get experiment configuration of a list of experiment
	 * 
	 * @param experimentList
	 * @return experimentConfigList
	 */
	public List<String> getExperimentConfigList(List<String> experimentList);
	
	/**
	 * Get execution list of a experiment config list
	 * 
	 * @param experimentConfigList
	 * @return executionList
	 */
	public List<String> getExecutionList(List<String> experimentConfigList);
	
	/**
	 * Get measures of an execution list
	 * 
	 * @param executionList
	 * @return measureList
	 */
	public List<String> getMeasureList(List<String> executionList);
	
	
	/**
	 * get all performance types
	 * 
	 * @param measureClassification
	 * @return list of performance type
	 */
	public List<String> getAllPerformanceTypes(List<String> measureClassification);
	
	public List<WasotaPerformanceModel> getFinalPerformanceList(List<String> measureClassification,
			List<String> performanceType);
	
	public List<WasotaPerformanceModel> getAllFinalPerformanceList();


}
