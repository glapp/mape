package ch.uzh.glapp;

import ch.uzh.glapp.model.Rules;
import java.io.IOException;
import java.util.List;

public class MainLoop {

    public static void main (String[] args) throws IOException {

        List<Rules> rulesList;
        List<String> appList;

        appList = new SailsRetriever().getAppIds();

        int appListSize = appList.size();
        for (int i = 0; i< appListSize; i++) {
            System.out.println("App ID: " + appList.get(i));
        }
        String appId = "572db263295d1ea114a74a71";
//		String appId = appList.get(0);
		// TODO apply code below on all App IDs (not only on index 0)



		// TODO: Stage 1 get Data:
		// 1. user defined policies
		// 2. Prometheus metrics
		// 3. infrastructure details
		// at the end of Stage 1 we hve a list of healthiness values.
		// overall healthiness value is between 0 and 1. It's an average of all rules. A rule has 0 or 1.


        double value = 0.017;
		int function = 2;  // 1 = greater than, 2 = smaller than, 3 = equal

        SailsRetriever sailsRetriever = new SailsRetriever();
        rulesList = sailsRetriever.getRules(appId);
        value = Double.parseDouble(rulesList.get(0).getValue());
		function = Integer.parseInt(rulesList.get(0).getOperator());

        System.out.println("Sails API call (value): "+value +
				", Function is set to: "+function + " ||| 1 = greater than, 2 = smaller than, 3 = equal");

        PrometheusRetriever prometheusRetriever = new PrometheusRetriever();
        String query = "rate(process_cpu_seconds_total[30s])";
        float metric = prometheusRetriever.retrieveInt(query);
        System.out.println("ch.uzh.glapp.model.Result from Prometheus API call (metric): " + metric);


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




/*
        // TODO: HTTP POST to Sails for infrastructure changes.
        // from: http://stackoverflow.com/questions/3324717/sending-http-post-request-in-java
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost("http://localhost:1337/api_v1/containers/");

        // Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("param-1", "12345"));
        params.add(new BasicNameValuePair("param-2", "Hello!"));
        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        //Execute and get the response.
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            InputStream instream = entity.getContent();
            try {
                // do something useful
            } finally {
                instream.close();
            }
        }
*/

    }

}
