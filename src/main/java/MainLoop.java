import com.google.gson.Gson;

import java.io.IOException;

public class MainLoop {

    public static void main (String[] args) throws IOException {

        // TODO: get the structure of all Applications from Sails.
        // Application ID has Array of Organ IDs and every Organ has Array of Cell IDs.

        // TODO: get the Application ID
        String appId = "570e50852168466222b365f9";



        double value = 0.017;
        PolicyRetriever policyRetriever = new PolicyRetriever();
        value = policyRetriever.retrieveValue(appId); // set up docker and sails to retrieve policy!!!!!!!!!!!!!!!!
        System.out.println("Result from Sails API call (value): "+value);

        int function = 2;  // 1 = greater than, 2 = smaller than, 3 = equal
        System.out.println("Function is set to: "+function + ". 1 = greater than, 2 = smaller than, 3 = equal");
        //int function = policyRetriever.retrieveFunction(appId); // TODO: retrieve function from sails.

        PrometheusRetriever prometheusRetriever = new PrometheusRetriever();
        String query = "rate(process_cpu_seconds_total[30s])";
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


    }

}
