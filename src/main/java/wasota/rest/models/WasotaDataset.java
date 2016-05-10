package wasota.rest.models;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import wasota.graph.WasotaGraph;
import wasota.properties.WasotaProperties;
import wasota.utils.FileUtils;
import wasota.utils.Formats;

public class WasotaDataset {

	private String namedGraph;
	
	private String hash; 
	
	private String format; 
	
	private Model model;
	
	public WasotaDataset(String namedGraph, String format, String graph) {
		this.namedGraph = namedGraph;
		model = ModelFactory.createDefaultModel();
		this.format = Formats.getJenaFormat(format);
		hash = FileUtils.stringToHash(namedGraph);
		try {
			model.read(new ByteArrayInputStream(graph.getBytes("UTF-8")), null, this.format);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	
	public void saveToFile(){
		try {
			model.write(new FileOutputStream(new File(
					WasotaProperties.BASE_PATH + hash)),"TTL");
			
			WasotaGraph.addDatasetToIndex(hash, namedGraph);
			
			WasotaGraph.startModel();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}


	public String getNamedGraph() {
		return namedGraph;
	}


	public void setNamedGraph(String namedGraph) {
		this.namedGraph = namedGraph;
	}


	public String getHash() {
		return hash;
	}


	public void setHash(String hash) {
		this.hash = hash;
	}


	public String getFormat() {
		return format;
	}


	public void setFormat(String format) {
		this.format = format;
	}
	
	
	
}
