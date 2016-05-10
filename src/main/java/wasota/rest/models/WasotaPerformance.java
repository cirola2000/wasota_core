package wasota.rest.models;

import java.util.HashSet;

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;

import wasota.graph.WasotaGraph;
import wasota.ontology.MexPredicates;

public class WasotaPerformance {

	public static void main(String[] args) {
		WasotaGraph.startModel();
		// new
		// WasotaPerformance().performanceByContext("http://mex.aksw.org/mex-core#NamedEntityRecognition");
	}

	public WasotaPerformance(String context, String performanceType) {
		performanceByContextAndType(context, performanceType);
	}

	public HashSet<Results> precisionListFinal = new HashSet<Results>();

	public void performanceByContextAndType(String context, String performanceType) {

		HashSet<String> precisionList = new HashSet<String>();

		// ---------- list of performance values from ontology------------
		// get all measures

		StmtIterator measures = WasotaGraph.getModel().listStatements(null, MexPredicates.subClassOf,
				MexPredicates.MEX_PERFORMANCE_MEASURE);

		while (measures.hasNext()) {
			StmtIterator m = WasotaGraph.getModel().listStatements(null, MexPredicates.domain,
					measures.next().getSubject().asResource());
			while (m.hasNext()) {
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
			while (experimenIt.hasNext()) {
				expList.add(experimenIt.next().getSubject().toString());
			}

		}

		// get list of experiment config
		HashSet<String> expListConfig = new HashSet<String>();
		for (String exp : expList) {
			StmtIterator experimenConfigIt = WasotaGraph.getModel().listStatements(null, MexPredicates.wasStartedBy,
					ResourceFactory.createResource(exp));
			while (experimenConfigIt.hasNext()) {
				expListConfig.add(experimenConfigIt.next().getSubject().toString());
			}
		}

		// get list of execution
		HashSet<String> executionList = new HashSet<String>();
		for (String expConfig : expListConfig) {
			StmtIterator experimenConfigIt = WasotaGraph.getModel().listStatements(null, MexPredicates.wasInformedBy,
					ResourceFactory.createResource(expConfig));
			while (experimenConfigIt.hasNext()) {
				executionList.add(experimenConfigIt.next().getSubject().toString());
			}
		}

		// get measure classification

		// get list measure classification
		HashSet<String> measureClassification = new HashSet<String>();
		for (String exp : executionList) {
			StmtIterator experimenTypeIt = WasotaGraph.getModel().listStatements(null, MexPredicates.wasGeneratedBy,
					ResourceFactory.createResource(exp));
			while (experimenTypeIt.hasNext()) {
				measureClassification.add(experimenTypeIt.next().getSubject().toString());
			}
		}

		// get all performance types
		for (String measure : measureClassification) {
			StmtIterator perfTypeIt = WasotaGraph.getModel().listStatements(ResourceFactory.createResource(measure),
					ResourceFactory.createProperty(performanceType), (RDFNode) null);
			while (perfTypeIt.hasNext()) {
				Statement stmt = perfTypeIt.next();
				String p = stmt.getObject().toString();

				Results result = new Results();
				result.measure = stmt.getPredicate().toString();
				result.value = stmt.getObject().toString().split("\\^")[0];

				StmtIterator stmeIt2 = new WasotaGraph().getModel().listStatements(stmt.getSubject().asResource(),
						MexPredicates.wasGeneratedBy, (RDFNode) null);
								
				Statement measureStmt = stmeIt2.next();
				
				StmtIterator stmeIt = new WasotaGraph().getModel().listStatements(measureStmt.getObject().asResource(),
						MexPredicates.used, (RDFNode) null);

				// get algorithms
				while (stmeIt.hasNext()) {
					Statement stmtAlg = stmeIt.next();

					// get label
					StmtIterator algIt = new WasotaGraph().getModel().listStatements(stmtAlg.getObject().asResource(),
							MexPredicates.label, (RDFNode) null);
					if (algIt.hasNext()) {
						Statement algIt2 = algIt.next();
						result.algorithmLbl = algIt2.getObject().toString();
					}

					algIt = new WasotaGraph().getModel().listStatements(stmtAlg.getObject().asResource(),
							MexPredicates.HAS_ALGORITHM_CLASS, (RDFNode) null);
					if (algIt.hasNext()) {
						Statement algIt2 = algIt.next();
						result.algorithmClass = algIt2.getObject().toString();
					}
				}
				
				// get experiment detail
				
				StmtIterator it = new WasotaGraph().getModel().listStatements(measureStmt.getObject().asResource(),
						MexPredicates.wasInformedBy, (RDFNode) null);
				
				// get experiment config
				StmtIterator it2 = new WasotaGraph().getModel().listStatements(it.next().getObject().asResource(),
						MexPredicates.wasStartedBy, (RDFNode) null);
				
				// finally getting experiment
				StmtIterator itEx = new WasotaGraph().getModel().listStatements(it2.next().getSubject().asResource(),
						MexPredicates.wasStartedBy, (RDFNode) null);
				
				Statement experimentConfigStmt = itEx.next();

				Statement experimentStmt = new WasotaGraph().getModel().listStatements(experimentConfigStmt.getSubject().asResource(),
						MexPredicates.wasStartedBy, (RDFNode) null).next();

				result.experimentID =    new WasotaGraph().getModel().listStatements(experimentStmt.getObject().asResource(),
						MexPredicates.identifier, (RDFNode) null).next().getObject().toString();
				
				result.experimentTitle = new WasotaGraph().getModel().listStatements(experimentStmt.getObject().asResource(),
						MexPredicates.title,      (RDFNode) null).next().getObject().toString();
				
				
				// get application
				StmtIterator itApp = new WasotaGraph().getModel().listStatements(experimentStmt.getObject().asResource(),
						MexPredicates.wasAttributedTo, (RDFNode) null);
				
				
				result.userMail = new WasotaGraph().getModel().listStatements(itApp.next().getObject().asResource(),
						FOAF.mbox, (RDFNode) null).next().getObject().toString();
				
				
				
				precisionListFinal.add(result);

			}
		}

	}

	public class Results {

		public String userMail;

		public String experimentID;

		public String experimentTitle;

		public String algorithmLbl;

		public String algorithmClass;

		public String measure;

		public String value;

	}

}
