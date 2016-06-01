package ch.uzh.glapp;

import ch.uzh.glapp.model.Rules;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainLoop {

	private static final int RULE = 0;

    public static void main (String[] args) throws IOException {

        Map<String, String> appMap;
		List<String> appList = new ArrayList<>();
		String appId;

        appMap = new SailsRetriever().getAppIds();

		for (String appKey: appMap.keySet()){
			String value = appMap.get(appKey);
			System.out.println(appKey + " " + value);
			if ("ready".equals(value)) {
				appList.add(appKey);
			}

		}

        int appListSize = appList.size();
        for (int i = 0; i< appListSize; i++) {
            System.out.println("App ID: " + appList.get(i));
        }
//        String appId = "572f2524ebff73e916d194e2";
        appId = appList.get(0);
        // TODO apply code below on all App IDs (not only on index 0)



        // TODO: Stage 1 get Data:
        // 1. user defined policies
        // 2. Prometheus metrics
        // 3. infrastructure details
        // at the end of Stage 1 we hve a list of healthiness values.
        // overall healthiness value is between 0 and 1. It's an average of all rules. A rule has 0 or 1.

        // Healthiness, App ID, list of actions and transitions.


        List<Rules> rulesList;
        double value = 0.017;
		int function = 2;  // 1 = greater than, 2 = smaller than, 3 = equal

        SailsRetriever sailsRetriever = new SailsRetriever();
        rulesList = sailsRetriever.getRules(appId);
        value = Double.parseDouble(rulesList.get(RULE).getValue());
        function = Integer.parseInt(rulesList.get(RULE).getOperator());


        System.out.println("Sails API call (value): "+value +
				", Function is set to: "+function + " ||| 1 = greater than, 2 = smaller than, 3 = equal");

        String ruleName = rulesList.get(RULE).getMetric();
        String smoothed = "[30s]";
        String query = "rate(" + ruleName + smoothed+ ")"; // rate(process_cpu_seconds_total[30s])

        PrometheusRetriever prometheusRetriever = new PrometheusRetriever();
        float metric = prometheusRetriever.retrieveInt(query);
        System.out.println("Result from Prometheus API call (metric): " + metric);


        MapeUtils mapeUtils = new MapeUtils();
        boolean compare = mapeUtils.compareInt(value, metric, function);
        System.out.println("Comparison: " + compare);
        if (!compare) {
            System.out.println("Change something in the infrastructure. Send command to Sails!");
        } else {
            System.out.println("Everything OK. :-)");
        }

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
