package ch.uzh.glapp;

import ch.uzh.glapp.model.sails.ruleinfo.Rule;
import ch.uzh.glapp.PrometheusRetriever.MetricNotFoundException;
import ch.uzh.glapp.mdp.BasicBehaviorMape;
import ch.uzh.glapp.model.ObjectForMdp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class MainLoop {
    public static void main (String[] args) throws IOException {
    	
    	// Read the config file
    	FileInputStream configFile = new FileInputStream("config.txt");
    	Properties config = new Properties();
    	config.load(configFile);
    	configFile.close();
    	
    	String prometheusServerIP = config.getProperty("prometheusServerIP");
    	int prometheusServerPort = Integer.parseInt(config.getProperty("prometheusServerPort"));
    	PrometheusRetriever prometheusRetriever = new PrometheusRetriever(prometheusServerIP, prometheusServerPort);
    	
    	MapeUtils mapeUtils = new MapeUtils();
    	SailsRetriever sa = new SailsRetriever();
    	
//    	prometheusRetriever.findContainerID("bcf548d11ab3");

//	    List<Cell> cells = sa.getCellInfo();
//	    for (Cell c : cells) {
//		    System.out.println("Cell ID: " + c.getHost().getLabels().getRegion());
//	    }

        Map<String, String> appMap;
		List<String> appList = new ArrayList<>();
		String appId;

        appMap = sa.getAppIds();

        // Display app status
        System.out.println("Application status:");
		for (String appKey: appMap.keySet()){
			String value = appMap.get(appKey);
			System.out.println(appKey + " " + value);
			if ("deployed".equals(value)) {
				appList.add(appKey);
			}

		}
		System.out.println();

		// Start processing the applications one by one
        for (int i = 0; i < appList.size(); ++i) {
        	boolean ruleViolated = false;
        	
        	appId = appList.get(i);
        	
        	System.out.println("MAPE started for app ID: " + appList.get(i) + "\n");
        	System.out.println("Stage 1: Get data and compute healthiness value");
        	
        	// Stage 1: Get data and compute healthiness value
            // 1. Retrieve user defined policy (a set of rules)
        	List<Rule> ruleList;
        	ruleList = sa.getRules(appId);
        	double appHealthiness = 0;
        	double appHealthinessWeighted;
        	double totalWeight = 0;
        	
    		for (Rule rule : ruleList) {
    			double ruleHealthiness = 0;
    			double totalCellHealthiness = 0;
    			
    			String metricName = rule.getMetric();
    			List<String> cellIDs = new ArrayList<String>();
    			
    			// TODO: get the cell IDs from sails 
    			cellIDs.add("bcf548d11ab3b3575e0e89d6978c2d4bd3857976f3fde41b698a2ca3ffd2185a"); // Hardcoded at the moment
//    			cellIDs.add("blahblah");
    			
    			double thresholdValue = Double.parseDouble(rule.getValue());
    			int function = Integer.parseInt(rule.getOperator()); // 1 = greater than, 2 = smaller than, 3 = equal
    			double weight = Double.parseDouble(rule.getWeight());
    			totalWeight += weight;

    			System.out.println("Rule: Metric: "+ metricName + ", Function: " + function + " (1 = greater than, 2 = smaller than, 3 = equal), Threshold: " + thresholdValue);

    			// 2. Retrieve Prometheus metrics
    			// get the metric value. e.g. get a per-second average metric value from a 60-second range in the past hour (3600 seconds)
    			// NOTE: consider other computation of metric value that may be meaningful
    			
    			for (int j = 0; j < cellIDs.size(); ++j) {
    				System.out.println("Computation for cell " + cellIDs.get(j) + "started.");
    				float metricValue = 0;
    				try {
    					metricValue = prometheusRetriever.getMetric(cellIDs.get(j), metricName, 60, 3600);
    				} catch (MetricNotFoundException e) {
    					e.printStackTrace();
    					System.out.println("Testing Testing Testing");
    				}  
        			System.out.println("Query result (cell metric value): " + metricValue);

        			// e.g. a rule specifying threshold = 50% means when the utilization is at 70%, there will be a 20% difference above threshold.
        			// degree of healthiness = difference / threshold = 0.2 / 0.5 = 0.4
        			double cellHealthiness = mapeUtils.cellHealthiness(thresholdValue, metricValue, function);
        			totalCellHealthiness += cellHealthiness;
        			
        			System.out.print("Comparison result: ");
        			if (cellHealthiness < 0) {
        				System.out.println("Not compliant, trigger MDP.");
        				ruleViolated = true;
        			} else {
        				System.out.println("Compliant, proceed to next rule.");
        			}
    			}
    			
    			System.out.println("totalCellHealthiness: " + totalCellHealthiness + " number of cells: " + cellIDs.size());
    			ruleHealthiness = totalCellHealthiness/cellIDs.size();
    			
    			appHealthiness += ruleHealthiness * weight;
    			
    			System.out.println();
    		}

            // application healthiness value which is an weighted average of healthiness value of all rules.
    		appHealthinessWeighted = appHealthiness / totalWeight;
    		System.out.println("Application healthiness value (normalized): " + appHealthinessWeighted);

    		// Stage 2: MDP
    		// if any rule is violated, perform MDP to find an adaptation action
    		if (ruleViolated) {
        		System.out.println("Stage 2: Perform MDP");
    			
    			// Simulate a violated rule
    			String violoatedMetric = config.getProperty("violoatedMetric");
    			String violatedCellId = config.getProperty("violatedCellId");
    			String violatedOrganId = config.getProperty("violatedOrganId");
    			String violatedAppId = config.getProperty("violatedAppId");
    			float healthinessValue = Float.parseFloat(config.getProperty("healthinessValue"));

    			ObjectForMdp o = new ObjectForMdp(violoatedMetric, violatedCellId, violatedOrganId, violatedAppId, healthinessValue);

    			BasicBehaviorMape basicBehaviorMape = new BasicBehaviorMape(o);
    			String outputPath = "output/" + appId + "/"; // directory to record results

    			// solve MDP (Q-Learning)
    			basicBehaviorMape.MyQLearningFunc(outputPath);
    		

	    		// TODO: (Riccardo) implement the call to Sails platform to execute the action from MDP (implement in MapeEnviroment class)
	    		// Stage 3: Send actions to the platform
	    		// connect to sails API.
	    		// pass three pieces of information:
	    		// 1. action (move, delete, create cell)
	    		// 2. application, organ, cell information
	    		// 3. Infrastructure information.
	    		// store the (improved) healthiness value somewhere, after taking the action.
	    		// (improved) healthiness value = value of new node.
    		}
        }
    }
}
