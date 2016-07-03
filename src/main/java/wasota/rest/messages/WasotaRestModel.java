package wasota.rest.messages;

/**
 * @author Ciro Baron Neto
 * 
 * Jul 4, 2016
 */
public class WasotaRestModel {
	
	public String status;

	public String content;

	/**
	 * Constructor for Class WasotaRestMsg 
	 */
	public WasotaRestModel(String status, String content) {
		this.status = status;
		this.content = content;
	}

	
}
