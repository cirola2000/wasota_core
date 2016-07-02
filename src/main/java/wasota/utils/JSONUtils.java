package wasota.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtils {

	public static String getField(String jsonObject, String filed) {
		JSONObject o = new JSONObject(jsonObject.toString());
		try {
			return o.get(filed).toString();
		} catch (JSONException e) {
			return null;
		}
	}

}
