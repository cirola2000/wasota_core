package wasota.graph;

public class WasotaPerformanceModel implements Comparable<WasotaPerformanceModel> {

	public String userMail;

	public String experimentID;

	public String experimentTitle;

	public String algorithmLbl;

	public String algorithmClass;

	public String measure;

	public String value;

	public String url;

	public int compareTo(WasotaPerformanceModel o) {
		Double thisValue = Double.valueOf(this.value);
		Double oValue = Double.valueOf(o.value);
		
		if (thisValue > oValue){
			return 1;
		}
		else
			return 0;
	}


}
