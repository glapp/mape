package ch.uzh.glapp;

import ch.uzh.glapp.model.sails.appinfo.AppDataObject;
import ch.uzh.glapp.model.sails.cellinfo.Cell;
import ch.uzh.glapp.model.sails.cellinfo.CellDataObject;
import ch.uzh.glapp.model.sails.hostinfo.Host;
import ch.uzh.glapp.model.sails.hostinfo.HostDataObject;
import ch.uzh.glapp.model.sails.ruleinfo.RuleDataObject;
import ch.uzh.glapp.model.sails.ruleinfo.Rule;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SailsRetriever {

	private String sailsHost;
	private String urlSails;
	private String urlCellMove;
	private String urlOrganScaleUp;
	private String urlOrganScaleDown;
	private HttpRequest con = new HttpRequest();


	public SailsRetriever() {
		if (System.getenv("SAILS_HOST") == null) {
			this.sailsHost = MainLoop.sailsServerIP;
			System.out.println("Environment variable SAILS_HOST is not set.");
		} else {
			this.sailsHost = System.getenv("SAILS_HOST");
			System.out.println("Environment variable SAILS_HOST is set to: " + sailsHost);
		}

		this.urlSails = "http://" + sailsHost + ":1337";
		this.urlCellMove = "http://" + sailsHost + ":1337/cell/move";
		this.urlOrganScaleUp = "http://" + sailsHost + ":1337/organ/scaleUp";
		this.urlOrganScaleDown = "http://" + sailsHost + ":1337/organ/scaleDown";
	}

	public String getPrometheusUrl () {

		String paramSails = "/host/getPrometheusUrl";
		String str = callSailsGET(paramSails);
		str = str.substring(21, str.length()-8);
//		System.out.println("getPrometheusUrl: " + str);
		return str;
	}

	private String callSailsGET(String paramSails) {
		String str = null;
		try {
//			System.out.println(urlSails);
//			System.out.println(paramSails);
			str = con.GETConnection(urlSails, paramSails);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}

		public void postMove (String cellId, String options) {
		String paramSails = "{\"cell_id\":\"" + cellId + "\",\"options\":" + options + "}";
//			System.out.println(urlCellMove + "" + paramSails);
		String str = null;
		try {
			str = con.POSTConnection(urlCellMove, paramSails);
		} catch (IOException e) {
			e.printStackTrace();
		}

//		System.out.println("str: "+str);
	}

	public void postCreate (String organId, String options) {
		String paramSails = "{\"organ_id\":\"" + organId + "\",\"options\":" + options + "}";
//			System.out.println(urlOrganScaleUp + "" + paramSails);
		String str = null;
		try {
			str = con.POSTConnection(urlOrganScaleUp, paramSails);
		} catch (IOException e) {
			e.printStackTrace();
		}

//		System.out.println(str);
	}

	public void postRemove (String organId, String cellId) {
		String paramSails = "{\"organ_id\":\"" + organId + "{\"cell_id\":\"" + cellId + "}";
//			System.out.println(urlOrganScaleDown + "" + paramSails);
		String str = null;
		try {
			str = con.POSTConnection(urlOrganScaleDown, paramSails);
		} catch (IOException e) {
			e.printStackTrace();
		}

//		System.out.println(str);
	}


	public List<Host> getHostInfo () {

		String paramSails = "/host/infoMape";
		List<Host> hostList = new ArrayList<>();
		String str = callSailsGET(paramSails);

		String jsonString;
		HostDataObject jobj;

		jsonString = str;
		//System.out.println(jsonString);
		jobj = new Gson().fromJson(jsonString, HostDataObject.class);

		int sizeHosts = jobj.getHosts().size();
		for (int i = 0; i<sizeHosts; i++) {
			hostList.add(jobj.getHosts().get(i));
//			System.out.println("Get Host: "+ hostList.get(i));
		}

		return hostList;
	}


	public Map<String, String> getAppIds () {

		String paramSails = "/application/getAppInfo";
		Map appIdMap = new HashMap<>();
		String str = callSailsGET(paramSails);

		String jsonString;
		AppDataObject jobj;

		jsonString = str;
//		System.out.println(jsonString);
		jobj = new Gson().fromJson(jsonString, AppDataObject.class);

		int sizeApplications = jobj.getApps().size();
//		System.out.println();
		for (int i = 0; i<sizeApplications; i++) {
			appIdMap.put(jobj.getApps().get(i).getId(), jobj.getApps().get(i).getStatus());
//			System.out.println("Get App: "+ appIdMap.get(i));
		}

		return appIdMap;
	}


    public List<Rule> getRules (String appId) {

		String paramSails = "/policy?app_id=" + appId;
		List<Rule> myList = new ArrayList<>();
	    String str = callSailsGET(paramSails);

        String jsonString;
        RuleDataObject jobj;

        jsonString = str;
//        System.out.println(jsonString);
        jobj = new Gson().fromJson(jsonString, RuleDataObject.class);

		int sizeRules = jobj.getRules().size();
		for (int i = 0; i<sizeRules; i++) {
			myList.add(jobj.getRules().get(i));
//			System.out.println("Get Rule: "+ myList.get(i));
		}

		return myList;

    }


	public List<Cell> getCellInfo () {

		String paramSails = "/application/getCellInfo";
		List<Cell> cellList = new ArrayList<>();
		String str = callSailsGET(paramSails);

		String jsonString;
		CellDataObject jobj;

		jsonString = str;
//		System.out.println(jsonString);
		jobj = new Gson().fromJson(jsonString, CellDataObject.class);

		int sizeCells = jobj.getCells().size();
//		System.out.println(sizeCells);
		for (int i = 0; i<sizeCells; i++) {
			cellList = jobj.getCells();

//			MapeCellObject cellObject = new MapeCellObject(provider, region, tier, cells);
		}

		return cellList;
	}

}