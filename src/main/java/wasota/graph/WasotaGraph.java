package wasota.graph;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.HashMap;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import wasota.properties.WasotaProperties;

public class WasotaGraph {

	private static Model model = ModelFactory.createDefaultModel();

	private static HashMap<String, String> hashName = new HashMap<String, String>();

	public static void mergeToModel(Model m) {
		model.add(m);
	}

	public static void addToModel(InputStream stream) {
		Model m = ModelFactory.createDefaultModel();
		m.read(stream, null, "TTL");
		mergeToModel(m);
	}

	public static Model getModel() {
		return model;
	}

	public static void startModel() {
		// get core model, peformance and algo
		try {
			addToModel(new URL(WasotaProperties.MEX_CORE_DOWNLOAD).openConnection().getInputStream());
			addToModel(new URL(WasotaProperties.MEX_PERF_DOWNLOAD).openConnection().getInputStream());
			addToModel(new URL(WasotaProperties.MEX_ALGO_DOWNLOAD).openConnection().getInputStream());

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		// get files
		File folder = new File(WasotaProperties.BASE_PATH);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				try {
					addToModel(new FileInputStream(listOfFiles[i]));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}

		loadHashName();

	}

	private static void saveHashName() {
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

	private static void loadHashName() {
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

	public static void addDatasetToIndex(String hash, String name) {
		hashName.put(hash, name);
		saveHashName();
	}

	public static Boolean queryIndex(String hash) {
		if (hashName.containsKey(hash))
			return true;
		return false;
	}

}
