package wasota.core.models;

public class WasotaPerformanceModel implements Comparable<WasotaPerformanceModel> {

	public String userMail;

	public String experimentID;

	public String experimentTitle;

	public String algorithmLbl;

	public String algorithmClass;

	public String performance;

	public String performanceValue;

	public String url;

	public int compareTo(WasotaPerformanceModel o) {
		Double thisValue = Double.valueOf(this.performanceValue);
		Double oValue = Double.valueOf(o.performanceValue);
		
		if (thisValue > oValue){
			return 1;
		}
		else
			return 0;
	}


}
