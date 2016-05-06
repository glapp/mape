package ch.uzh.glapp;

import java.io.IOException;

public class MainLoop {

    public static void main (String[] args) throws IOException {

        // TODO: get the structure of all Applications from Sails.
        // Application ID has Array of Organ IDs and every Organ has Array of Cell IDs.

        // TODO: get the Application ID
        String appId = "572c5dafbc569c5116e1924f";



        double value = 0.017;
        PolicyRetriever policyRetriever = new PolicyRetriever();
        //value = policyRetriever.retrieveValue(appId); // set up docker and sails to retrieve policy!!!!!!!!!!!!!!!!
        System.out.println("ch.uzh.glapp.model.Result from Sails API call (value): "+value);

        int function = 2;  // 1 = greater than, 2 = smaller than, 3 = equal
        System.out.println("Function is set to: "+function + ". 1 = greater than, 2 = smaller than, 3 = equal");
        //int function = policyRetriever.retrieveFunction(appId); // TODO: retrieve function from sails policy.

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




/*
        // HTTP POST to Sails for infrastructure changes.
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
