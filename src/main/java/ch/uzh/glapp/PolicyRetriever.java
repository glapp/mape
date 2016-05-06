package ch.uzh.glapp;

import ch.uzh.glapp.model.PolicyDataObject;
import ch.uzh.glapp.model.Rules;
import com.google.gson.Gson;
import com.sun.deploy.security.ruleset.Rule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PolicyRetriever {

    public List<Rules> getRules (String appId) {

		List<Rules> myList = new ArrayList<>();

		String urlSails = "http://localhost:1337";
        String paramSails = "/policy?app_id=" + appId;

//        System.out.println(urlSails + paramSails);

        HttpRequest con = new HttpRequest();
        String str = "";
        try {
            str = con.connect(urlSails, paramSails);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String jsonString;
        PolicyDataObject jobj;
        String result;

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

    public void retrieveFunction (String appId) {}
}