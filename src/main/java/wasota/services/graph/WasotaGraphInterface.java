package wasota.services.graph;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;

import wasota.exceptions.CannotAddMexNamespaces;

public interface WasotaGraphInterface {

	Boolean writeAsStream(OutputStream out);

	Boolean writeAsString(StringWriter strWriter);

	void readAsStream(InputStream in, String format);
	
	public void addMexNamespacesToModel() throws CannotAddMexNamespaces;
	
	public void mergeGraph(InputStream graphStream);
	
	public WasotaGraphQueries query();

}
