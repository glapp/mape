package ch.uzh.glapp;

import ch.uzh.glapp.model.Violation;
import ch.uzh.glapp.model.sails.MdpTriggerObject;
import ch.uzh.glapp.mdp.BasicBehaviorMape;
import ch.uzh.glapp.model.ObjectForMdp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class MainLoop {
	public static String prometheusServerIP;
	public static int prometheusServerPort;
	public static String sailsServerIP;
	public static boolean suppressActionToSails;

    public static void main (String[] args) throws IOException {



	    // Read the config file
	    Properties config = new Properties();
	    try {
		    FileInputStream configFile = new FileInputStream("config.txt");
		    config.load(configFile);
		    configFile.close();
	    } catch (Exception e) {
		    System.out.println("WARNING: config.txt not found");
	    }

	    sailsServerIP = config.getProperty("sailsServerIP");
	    suppressActionToSails = Boolean.parseBoolean(config.getProperty("suppressActionToSails"));

	    SailsRetriever sa = new SailsRetriever();
	    prometheusServerIP = sa.getPrometheusUrl();
	    
	    if (prometheusServerIP != "") {
		    System.out.println("Prometheus IP form infrastructure: " + prometheusServerIP);
	    } else {
		    System.out.println("retreived prometheusServerIP: " + prometheusServerIP);
		    prometheusServerIP = config.getProperty("prometheusServerIP");
		    System.out.println("Prometheus IP form file: " + prometheusServerIP);
	    }
	    prometheusServerPort = 19090;


//    	MapeUtils mapeUtils = new MapeUtils();

    	
//    	prometheusRetriever.findContainerID("bcf548d11ab3");

//	    List<Cell> cells = sa.getCellInfo();
//	    for (Cell c : cells) {
//		    System.out.println("Cell ID: " + c.getHost().getLabels().getRegion());
//	    }

	    while (true) {


		    Map<String, String> appMap;
		    List<String> appList = new ArrayList<>();
		    String appId;

		    appMap = sa.getAppIds();
		    
		    // Display app status
		    System.out.println("Application status:");
		    for (String appKey : appMap.keySet()) {
			    String value = appMap.get(appKey);
			    System.out.println(appKey + " " + value);
			    if ("deployed".equals(value)) {
				    appList.add(appKey);
			    }

		    }
//		    System.out.println();

		    // Start processing the applications one by one
		    for (int i = 0; i < appList.size(); ++i) {

			    appId = appList.get(i);
			    
			    if (sa.getRules(appId).isEmpty()) {
			    	System.out.println("MainLoop: No rule defined.");
			    } else {

				    System.out.println("MAPE started for app ID: " + appList.get(i) + "\n");
				    System.out.println("Stage 1: Get data and compute healthiness value");
	
				    // Stage 1: Get data and compute healthiness value
				    // 1. Retrieve user defined policy (a set of rules)
	
				    // get the application healthiness value
				    // for metric data, range=10 and duration=180 means
				    // get a per-second average metric value from a 10-second range in the 3 minutes (180 seconds)
				    MdpTriggerObject mdpTriggerObject = MapeUtils.healthiness(appId, 10, 10, 10, false);
	
				    // Stage 2: MDP
				    // if any rule is violated, perform MDP to find an adaptation action
				    if (mdpTriggerObject.isRuleViolated()) {
					    System.out.println("Stage 2: Perform MDP");
					    ObjectForMdp objectForMdp;
	
					    if (mdpTriggerObject.isRuleViolated()) {
//						    System.out.println("Take real ObjectForMdp");
						    objectForMdp = new ObjectForMdp(
								    mdpTriggerObject.getViolationList().get(0).getMetric(),
								    mdpTriggerObject.getViolationList().get(0).getCellId(),
								    mdpTriggerObject.getViolationList().get(0).getOrganId(),
								    mdpTriggerObject.getViolationList().get(0).getAppId(),
								    mdpTriggerObject.getAppHealthiness()
						    );
					    } else {
						    // Simulate a violated rule
//						    System.out.println("Simulate a violated rule");
						    List<Violation> dummyViolationList = null;
	
						    Violation violation = new Violation(config.getProperty("violatedCellId"), "0", config.getProperty("violatedOrganId"), 
						    		appId, "0", config.getProperty("violoatedMetric"), -1);
						    dummyViolationList.add(violation);
	
						    mdpTriggerObject = new MdpTriggerObject(dummyViolationList, Double.parseDouble(config.getProperty("healthinessValue")), true);
	
					    }
	
	
					    BasicBehaviorMape basicBehaviorMape = new BasicBehaviorMape(mdpTriggerObject);
					    String outputPath = "output/" + appId + "/"; // directory to record results
	
					    // solve MDP
					    basicBehaviorMape.MyQLearningFunc(outputPath);
	//    			basicBehaviorMape.MySarsaLearningFunc(outputPath);
	
	
					    // Stage 3: Send actions to the platform to execute the action from MDP (implement in MapeEnviroment class)
					    // GETConnection to sails API.
					    // pass three pieces of information:
					    // 1. action (move, delete, create cell)
					    // 2. application, organ, cell information
					    // 3. Infrastructure information.
					    // store the (improved) healthiness value somewhere, after taking the action.
					    // (improved) healthiness value = value of new node.
				    }
			    }
		    }

		    // sleep some time.
		    try {
			    System.out.println("####### Wait 5 secs before starting next MAPE loop.");
			    System.out.println();
			    TimeUnit.SECONDS.sleep(5);
		    } catch (InterruptedException e) {
			    e.printStackTrace();
		    }

	    }
    }
}
