package ch.uzh.glapp;

import ch.uzh.glapp.model.prometheus.PrometheusDataObject;
import com.google.gson.Gson;
import java.io.IOException;


public class PrometheusRetriever {
	
	private String prometheusServerIP;
	private int prometheusServerPort;
	
	public PrometheusRetriever() {
		this.prometheusServerIP = "127.0.0.1";
		this.prometheusServerPort = 19090;
	}
	
	public PrometheusRetriever(String prometheusServerIP, int prometheusServerPort) {
		this.prometheusServerIP = prometheusServerIP;
		this.prometheusServerPort = prometheusServerPort;
	}

	/**
	 * Calculates the per-second average rate of increase of the time series in the range vector.
	 * Using Prometheus provided rate function {@link https://prometheus.io/docs/querying/functions/#rate()}.
	 * @param metricName
	 * @param range (in seconds) of data averaged for each metric data point
	 * @param duration (in seconds) of the data points
	 * @return average value of the data points in the specified duration
	 */
    public float getMetric (String metricName, int range, int duration) {

        //String query2 = "rate(process_cpu_seconds_total[30s])";
    	
    	String query = "rate(" + metricName + "[" + range + "s])";

        long currTime = System.currentTimeMillis()/1000;
        long startTime = currTime - duration;
        String step = "60s"; // query resolution. e.g. 60 seconds

        String urlPrometheus = "http://" + prometheusServerIP + ":" + prometheusServerPort;

        String paramPrometheus = "/api/v1/query_range" +
                "?query=" + query +         // or query2
                "&start=" + startTime +
                "&end=" + currTime +
                "&step=" + step;

        System.out.println("Prometheus call: " + urlPrometheus + paramPrometheus);

        HttpRequest con = new HttpRequest();
        String str = "";
        try {
            str = con.connect(urlPrometheus, paramPrometheus);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String jsonString;
        PrometheusDataObject jobj;
        String result = "";

        jsonString = str;
//        System.out.println("jsonString: "+jsonString);
        jobj = new Gson().fromJson(jsonString, PrometheusDataObject.class);
        
        // TODO: get the value of the required instance
        int listSize = jobj.getData().getResult().get(0).getValues().size();

        float sum_value = 0;
        for (int i=0; i< listSize; i++) {
            sum_value += Float.parseFloat(jobj.getData().getResult().get(0).getValues().get(i).get(1));
//            System.out.println("sum_value iterate: "+sum_value);
        }

        float averge = sum_value/listSize;
//        System.out.println("average: "+average);

        return averge;

//        result = jobj.getData().getResult().get(0).getValues().get(listSize-1).get(1);
//        return Float.parseFloat(result);
    }

}