package ch.uzh.glapp;

import ch.uzh.glapp.model.Applications;
import ch.uzh.glapp.model.PolicyDataObject;
import ch.uzh.glapp.model.Rules;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SailsRetriever {

	private String urlSails = "http://localhost:1337";

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
			System.out.println(" "+ myList.get(i));
		}

		return myList;

    }

	public List<String> getAppIds () {

		String paramSails = "/application/getUserApps";
		List<String> appIdList = new ArrayList<>();

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
		//System.out.println(jsonString);
		jobj = new Gson().fromJson(jsonString, Applications.class);

		int sizeApplications = jobj.getApps().size();
		System.out.println();
		for (int i = 0; i<sizeApplications; i++) {
			appIdList.add(jobj.getApps().get(i).getId());
			System.out.println(" "+ appIdList.get(i));
		}

		return appIdList;
	}


//    public double retrieveValue (String appId) {
//
//        String urlSails = "http://localhost:1337";
//        String paramSails = "/policy?app_id=" + appId;
//
////        System.out.println(urlSails + paramSails);
//
//
//        HttpRequest con = new HttpRequest();
//        String str = "";
//        try {
//            str = con.connect(urlSails, paramSails);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        String jsonString;
//        PolicyDataObject jobj;
//        String result;
//
//        jsonString = str;
//        //System.out.println(jsonString);
//        jobj = new Gson().fromJson(jsonString, PolicyDataObject.class);
//        result = jobj.getRules().get(0).getValue();
//
//        return Double.parseDouble(result);
//    }
}