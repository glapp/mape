package ch.uzh.glapp;

import ch.uzh.glapp.model.cellinfo.Cell;
import ch.uzh.glapp.model.ruleinfo.Rule;
import ch.uzh.glapp.mdp2.BasicBehaviorMape;
import ch.uzh.glapp.model.ObjectForMdp;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainLoop {
    public static void main (String[] args) throws IOException {
    	MapeUtils mapeUtils = new MapeUtils();
    	SailsRetriever sa = new SailsRetriever();

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

//        int appListSize = appList.size();
//        for (int i = 0; i< appListSize; i++) {
//            System.out.println("App ID: " + appList.get(i));
//        }
        
        for (int i = 0; i < appList.size(); ++i) {
        	appId = appList.get(i);
        	
        	System.out.println("Processing app. App ID: " + appList.get(i));
        	
        	// Stage 1 get Data:
            // 1. Retrieve user defined policy (a set of rules)
        	List<Rule> ruleList;
        	ruleList = sa.getRules(appId);
        	double appHealthiness = 0;
        	double appHealthinessNormalized; // [0,1]
        	double totalWeight = 0;
        	
    		for (Rule rule : ruleList) {
    			String metricName = rule.getMetric();
    			double value = Double.parseDouble(rule.getValue());
    			int function = Integer.parseInt(rule.getOperator()); // 1 = greater than, 2 = smaller than, 3 = equal
    			double weight = 1;
    			totalWeight += weight;

    			System.out.println("Rule: Metric: "+ metricName + ", Function: " + function + " (1 = greater than, 2 = smaller than, 3 = equal), Threshold: " + value);

    			int smoothed = 30; // second
    			String query = "rate(" + metricName + "[" + smoothed + "s])"; // rate(process_cpu_seconds_total[30s])
    			
    			System.out.println("Query to Prometheus: " + query);
    			System.out.println();

    			// 2. Retrieve Prometheus metrics

    			PrometheusRetriever prometheusRetriever = new PrometheusRetriever("95.85.11.77");
    			float metricValue = prometheusRetriever.retrieveInt(query);
    			System.out.println("Query result " + metricValue);

    			// TODO: implement function to calculate the degree of compliance (healthiness) and normalize to range [0,1] 
    			
    			boolean compliant = mapeUtils.compareInt(value, metricValue, function);
    			System.out.print("Comparison result: ");
    			if (!compliant) {
    				System.out.println("Not compliant, trigger MDP.");
    				appHealthiness += 0 * weight;
    			} else {
    				System.out.println("Compliant, proceed to next rule.");
    				appHealthiness += 1 * weight;
    			}
    			
    			System.out.println();
    		}

            // application healthiness value is normalized to range [0,1]. It's an weighted average of healthiness value of all rules.
    		appHealthinessNormalized = appHealthiness / totalWeight;
    		System.out.println("Application healthiness value (normalized): " + appHealthinessNormalized);
            
            

    		// Simulate a violated rule
    		String violoatedMetric = "process_cpu_seconds_total";
    		String violatedCellId = "57725130644b311b20c4d8a2";
    		String violatedOrganId = "57724fef644b311b20c4d898";
    		String violatedAppId = "57724fee644b311b20c4d896";
    		float healthinessValue = 0;

    		ObjectForMdp o = new ObjectForMdp(violoatedMetric, violatedCellId, violatedOrganId, violatedAppId, healthinessValue);

    		BasicBehaviorMape basicBehaviorMape = new BasicBehaviorMape(o);
    		String outputPath = "output/"; // directory to record results

    		basicBehaviorMape.MyQLearningFunc(outputPath);
    		
    		
    		// 3. infrastructure details
            

    		// TODO: Stage 2 MDP calculations.
    		// get state from stage 1
    		// solve MDP --> policies (policy iteration)
            // Possible statuses: HEALTHY, WARNING, UNHEALTHY
            // HEALTHY means: all rules are satisfied.
    		// 1. give a random healthiness values to the set of next states or update the value based
    		// 		on previous iterations.
    		// 2. choose the action that lead to the state with the highest healthiness value


    		// TODO: Stage 3 send actions to the platform.
    		// connect to sails API.
    		// pass three pieces of information:
    		// 1. action (move, delete, create container)
    		// 2. application, organ, cell information
    		// 3. Infrastructure information.
    		// store the (improved) healthiness value somewhere, after taking the action.
    		// (improved) healthiness value = value of new node.
        	
        }

    }

}
