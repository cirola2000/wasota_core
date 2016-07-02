package wasota.graph;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import wasota.exceptions.CannotAddMexNamespaces;
import wasota.properties.WasotaProperties;
import wasota.rest.controller.GraphController;
import wasota.utils.Formats;

public class WasotaGraph extends WasotaGraphA {

	final static Logger logger = Logger.getLogger(WasotaGraphA.class);
	
	public WasotaGraph() {
		super();
	}
	public WasotaGraph(String graph, String format) throws UnsupportedEncodingException{
		super();
		mainModel.read(new ByteArrayInputStream(graph.getBytes("UTF-8")), null, Formats.getJenaFormat(format));
		logger.info("New Wasota graph created.");
	}

	/**
	 * Merge a JENA graph to the main model
	 * 
	 * @param model
	 */
	public void mergeToMainModel(Model model) {
		mainModel.add(model);
	}

	/**
	 * Merge a JENA graph (as inputstream) to model
	 * 
	 * @param graphStream
	 */
	public void mergeToMainModel(InputStream graphStream) {
		Model m = ModelFactory.createDefaultModel();
		m.read(graphStream, null, "TTL");
		mergeToMainModel(m);
	}

	/**
	 * Get the main model
	 * 
	 * @return model
	 */
	public Model getMainModel() {
		return mainModel;
	}

	/**
	 * Start the main model adding MEX namespaces
	 * 
	 * @throws CannotAddMexNamespaces
	 */
	public void addMexNamespacesToModel() throws CannotAddMexNamespaces {
		// get core model, peformance and algo
		try {
			mergeToMainModel(new URL(WasotaProperties.MEX_CORE_DOWNLOAD).openConnection().getInputStream());
			mergeToMainModel(new URL(WasotaProperties.MEX_PERF_DOWNLOAD).openConnection().getInputStream());
			mergeToMainModel(new URL(WasotaProperties.MEX_ALGO_DOWNLOAD).openConnection().getInputStream());
			
			logger.debug("MEX namespaces added to graph.");

		} catch (Exception e1) {
			e1.printStackTrace();
			throw new CannotAddMexNamespaces("We couldn't load the MEX namespaces to the Model.");
		}

	}

	/**
	 * Load models from disk to model
	 */
	public void loadModelsFromDisk() {
		// get files
		File folder = new File(WasotaProperties.BASE_PATH);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				try {
					mergeToMainModel(new FileInputStream(listOfFiles[i]));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}

		loadHashName();
	}

	private void saveHashName() {
		try {
			FileOutputStream fos = new FileOutputStream(new File(WasotaProperties.INDEX_PATH));
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(hashName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void loadHashName() {
		try {
			File f = new File(WasotaProperties.INDEX_PATH);
			if (!f.exists())
				return;
			FileInputStream fis = new FileInputStream(new File(WasotaProperties.INDEX_PATH));
			ObjectInputStream ios = new ObjectInputStream(fis);
			hashName = (HashMap<String, String>) ios.readObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addDatasetToIndex(String hash, String name) {
		hashName.put(hash, name);
		saveHashName();
	}

	public Boolean queryIndex(String hash) {
		if (hashName.containsKey(hash))
			return true;
		return false;
	}

}
