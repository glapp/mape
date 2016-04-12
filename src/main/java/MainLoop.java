import java.io.IOException;

public class MainLoop {

    public static void main (String[] args) throws IOException {

        // TODO: get the structure of all Applications from Sails.
        // Application ID has Array of Organ IDs and every Organ has Array of Cell IDs.

        // TODO: get the Application ID
        int appId = 0;



        PolicyRetriever policyRetriever = new PolicyRetriever();

        int value = 100;
        //int value = policyRetriever.retrieveValue(appId);
        int function = 1;
        //int function = policyRetriever.retrieveFunction(appId);

        PrometheusRetriever prometheusRetriever = new PrometheusRetriever();
        String query = "rate(process_cpu_seconds_total[30s])";
        float metric = prometheusRetriever.retrieveInt(query);
        System.out.println("Metric: " + metric);




        MapeUtils mapeUtils = new MapeUtils();
        mapeUtils.compareInt(value, metric, function);

    }

}
