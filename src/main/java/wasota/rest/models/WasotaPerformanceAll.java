package wasota.rest.models;

import java.util.HashSet;

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.rdf.model.StmtIterator;

import wasota.graph.WasotaGraph;
import wasota.ontology.MexPredicates;

public class WasotaPerformanceAll {

	public static void main(String[] args) {
		WasotaGraph.startModel();
//		new WasotaPerformance().performanceByContext("http://mex.aksw.org/mex-core#NamedEntityRecognition");
	}
	
	public WasotaPerformanceAll(String context) {
		performanceByContext(context);
	}

	public HashSet<String> precisionListFinal = new HashSet<String>();


	
	public void performanceByContext(String context) {

	
		HashSet<String> precisionList = new HashSet<String>();
		
		// ----------  list of performance values from ontology------------
		// get all measures

		StmtIterator measures = WasotaGraph.getModel().listStatements(null, MexPredicates.subClassOf, MexPredicates.MEX_PERFORMANCE_MEASURE);
		
		while(measures.hasNext()){
			StmtIterator m = WasotaGraph.getModel().listStatements(null, MexPredicates.domain, measures.next().getSubject().asResource());
			while(m.hasNext()){
				precisionList.add(m.next().getSubject().toString());
			}
		}
		
		
		

		// get all application for the context
		StmtIterator contextList = WasotaGraph.getModel().listStatements(null, MexPredicates.type,
				ResourceFactory.createResource(context));

		HashSet<String> appList = new HashSet<String>();

		// get list of applications
		while (contextList.hasNext()) {
			// System.out.println(contextList.next().getSubject().toString());

			StmtIterator applications = WasotaGraph.getModel().listStatements(contextList.next().getSubject(),
					MexPredicates.wasAttributedTo, (RDFNode) null);

			while (applications.hasNext())
				appList.add(applications.next().getObject().toString());

		}

		// get list of experiment
		HashSet<String> expList = new HashSet<String>();
		for (String application : appList) {
			StmtIterator experimenIt = WasotaGraph.getModel().listStatements(null, MexPredicates.wasAttributedTo,
					ResourceFactory.createResource(application));
			while(experimenIt.hasNext()){
				expList.add(experimenIt.next().getSubject().toString());
			}

		}

		
		// get list of experiment config
		HashSet<String> expListConfig = new HashSet<String>();
		for (String exp : expList) {
			StmtIterator experimenConfigIt = WasotaGraph.getModel().listStatements(null, MexPredicates.wasStartedBy,
					ResourceFactory.createResource(exp));
			while(experimenConfigIt.hasNext()){
				expListConfig.add(experimenConfigIt.next().getSubject().toString());
			}
		}
		
		
		// get list of execution
		HashSet<String> executionList = new HashSet<String>();
		for (String expConfig : expListConfig) {
			StmtIterator experimenConfigIt = WasotaGraph.getModel().listStatements(null, MexPredicates.wasInformedBy,
					ResourceFactory.createResource(expConfig));
			while(experimenConfigIt.hasNext()){
				executionList.add(experimenConfigIt.next().getSubject().toString());
			}
		}
	
		
		
		// get measure classification
		
		// get list measure classification
		HashSet<String> measureClassification = new HashSet<String>();
		for (String exp : executionList) {
			StmtIterator experimenTypeIt = WasotaGraph.getModel().listStatements(null, MexPredicates.wasGeneratedBy,
					ResourceFactory.createResource(exp));
			while(experimenTypeIt.hasNext()){
				measureClassification.add(experimenTypeIt.next().getSubject().toString());
			}
		}
		
		
		// get all performance types
		for (String measure : measureClassification) {
			StmtIterator perfTypeIt = WasotaGraph.getModel().listStatements(ResourceFactory.createResource(measure), null,
					(RDFNode) null);
			while(perfTypeIt.hasNext()){
				String performanceType = perfTypeIt.next().getPredicate().toString();
				if(precisionList.contains(performanceType)){
					precisionListFinal.add(performanceType);
				}
			}
		}
			

//		for(String e : executionTypes)
//			System.out.println(e);
//		 
//		System.out.println();
//		
//		for(String e : precisionListFinal)
//			System.out.println(e);

	}

}
