package wasota.services.graph.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import wasota.exceptions.graph.NotPossibleToLoadGraph;
import wasota.exceptions.graph.NotPossibleToSaveGraph;
import wasota.properties.WasotaProperties;
import wasota.services.graph.GraphStoreInterface;
import wasota.services.graph.WasotaGraphInterface;
import wasota.utils.FileUtils;

public class GraphStoreFSImpl implements GraphStoreInterface {

	private HashMap<String, String> hashAndGraphNameMap = new HashMap<String, String>();

	@Override
	public Boolean loadGraph(String namedGraph, WasotaGraphInterface graph, String format) throws NotPossibleToLoadGraph {

		// get file
		String path = WasotaProperties.BASE_PATH + "/" + FileUtils.stringToHash(namedGraph);

		File file = new File(path);

		if (file.exists()) {
			try {
				graph.readAsStream(new FileInputStream(file), format);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new NotPossibleToLoadGraph(e.getMessage());
			}
			return true;
		}
		throw new NotPossibleToLoadGraph("Not possible to find the graph in the FS. Path: "+ path);

	}

	@Override
	public Boolean saveGraph(String namedGraph, WasotaGraphInterface graph) throws NotPossibleToSaveGraph {
		try {

			// hash of the graph name
			String hash = FileUtils.stringToHash(namedGraph);

			// save dataset sending an outputstream from the FS
			FileOutputStream fos = new FileOutputStream(new File(WasotaProperties.BASE_PATH+"/"+hash));
			
			graph.writeAsStream(fos);
			
			fos.close();

			// save hash in the main index (so we can keep the hash reference)
			addDatasetToIndex(hash, namedGraph);

		} catch (IOException e) {
			e.printStackTrace();
			throw new NotPossibleToSaveGraph(e.getMessage());
		}
		return null;
	}

	private void saveHashName() {
		try {
			FileOutputStream fos = new FileOutputStream(new File(WasotaProperties.INDEX_PATH));
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(hashAndGraphNameMap);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
			hashAndGraphNameMap = (HashMap<String, String>) ios.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void addDatasetToIndex(String hash, String namedGraph) {
		hashAndGraphNameMap.put(hash, namedGraph);
		saveHashName();
	}

	@Override
	public List<String> getAllGraphNames() {
		loadHashName();
		List<String> list = new ArrayList<String>();
		list.addAll(hashAndGraphNameMap.values());
		return list;
	}

}
