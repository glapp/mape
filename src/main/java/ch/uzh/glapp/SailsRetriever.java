package ch.uzh.glapp;

import ch.uzh.glapp.model.Applications;
import ch.uzh.glapp.model.PolicyDataObject;
import ch.uzh.glapp.model.Rules;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SailsRetriever {

	private String urlSails = "http://localhost:1337";

	public Map<String, String> getAppIds () {
		String paramSails = "/application/getAppInfo";
		Map appIdList = new HashMap<>();

//		System.out.println(urlSails+paramSails);

		HttpRequest con = new HttpRequest();
		String str = "";
		try {
			str = con.connect(urlSails, paramSails);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String jsonString;
		Applications jobj;

		jsonString = str;
//		System.out.println(jsonString);
		jobj = new Gson().fromJson(jsonString, Applications.class);

		int sizeApplications = jobj.getApps().size();
//		System.out.println();
		for (int i = 0; i<sizeApplications; i++) {
			appIdList.put(jobj.getApps().get(i).getId(), jobj.getApps().get(i).getStatus());
//			System.out.println("Get App: "+ appIdList.get(i));
		}

		return appIdList;
	}

    public List<Rules> getRules (String appId) {
		String paramSails = "/policy?app_id=" + appId;
		List<Rules> myList = new ArrayList<>();

        HttpRequest con = new HttpRequest();
        String str = "";
        try {
            str = con.connect(urlSails, paramSails);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String jsonString;
        PolicyDataObject jobj;

        jsonString = str;
        //System.out.println(jsonString);
        jobj = new Gson().fromJson(jsonString, PolicyDataObject.class);

		int sizeRules = jobj.getRules().size();
		for (int i = 0; i<sizeRules; i++) {
			myList.add(jobj.getRules().get(i));
//			System.out.println("Get Rule: "+ myList.get(i));
		}

		return myList;

    }

}