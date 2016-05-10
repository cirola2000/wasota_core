package wasota.rest.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

import wasota.graph.WasotaGraph;
import wasota.ontology.MexPredicates;
import wasota.properties.WasotaProperties;

public class AllContextTemplate {

	// private Model model = ModelFactory.createDefaultModel();

	// private Model coreModel = ModelFactory.createDefaultModel();

	public HashMap<String, String> context = new HashMap<String, String>();

	// public HashMap<String, String> contextHash2 = new HashMap<String,
	// String>();

	public HashSet<String> allContext = new HashSet<String>();

	public static void main(String[] args) {
		WasotaGraph.startModel();
		AllContextTemplate n = new AllContextTemplate();
	}

	public AllContextTemplate() {

		// get all mex context
		StmtIterator contextIt = WasotaGraph.getModel().listStatements(null, MexPredicates.subClassOf,
				MexPredicates.MEX_CONTEXT);

		while (contextIt.hasNext()) {
			Statement stmt = contextIt.next();
			allContext.add(stmt.getSubject().toString());
		}

		// for each context
		for (String _context : allContext) {

			// search for some context
			StmtIterator contextList = WasotaGraph.getModel().listStatements(null, null,
					ResourceFactory.createResource(_context));
//			System.out.println(_context.toString());

			while (contextList.hasNext()) {
				Statement r = contextList.next();
				StmtIterator identifier = WasotaGraph.getModel().listStatements(r.getSubject(), MexPredicates.label,
						(RDFNode) null);
				String label = identifier.next().getObject().toString();
				context.put(r.getObject().toString(), label);
//				System.out.println(label);
			}

		}
		// System.out.println("File " + listOfFiles[i].getName());
	}

}
