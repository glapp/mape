package ch.uzh.glapp;

import ch.uzh.glapp.model.prometheus.PrometheusDataObject;
import ch.uzh.glapp.model.prometheus.QueryResultInstantVectors;
import ch.uzh.glapp.model.prometheus.Result;
import ch.uzh.glapp.model.prometheus.ResultInstantVectors;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;


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
	 * Send a query to Prometheus server and get a response.
	 * Refer to {@link https://prometheus.io/docs/querying/api/} for query types.
	 * @param paramPrometheus is the query string
	 * @return the metric data per query in the form of JSON response
	 */
	private String doQuery(String paramPrometheus) {
      HttpRequest con = new HttpRequest();
      String urlPrometheus = "http://" + prometheusServerIP + ":" + prometheusServerPort;
      String response = "";
      
      System.out.println("Prometheus query: " + urlPrometheus + paramPrometheus);
      
      try {
    	  response = con.GETConnection(urlPrometheus, paramPrometheus);
      } catch (IOException e) {
          e.printStackTrace();
      }
      
      return response;
	}
	

	/**
	 * Calculates the per-second average rate of increase of the time series (i.e. a metric value) in the range vector.
	 * Using Prometheus provided rate function {@link https://prometheus.io/docs/querying/functions/#rate()}.
	 * @param containerID is the Docker container ID
	 * @param metricName is the name of the metric.
	 * @param range is the range of time (in seconds) which data are averaged for each metric data point.
	 * @param duration is the duration of time (in seconds) of the data points.
	 * @param step is the time step in seconds that the duration will be broken down.
	 * @return average value of the data points in the specified duration.
	 * @throws MetricNotFoundException if the metric is not available for the given container
	 */
    public float getMetric(String containerID, String metricName, int range, int duration, int step)
		    throws MetricNotFoundException {

    	String paramPrometheus;
    	String query;
    	
        long currTime = System.currentTimeMillis()/1000;
        long startTime = currTime - duration;
    	
        query = "rate(" + metricName + "{id=\"/docker/" + containerID + "\"}[" + range + "s])";
    		
        paramPrometheus = "/api/v1/query_range" +
        		"?query=" + query +
        		"&start=" + startTime +
        		"&end=" + currTime +
        		"&step=" + step;

        String jsonString = doQuery(paramPrometheus);
        PrometheusDataObject jobj;

//        System.out.println("jsonString: "+jsonString);
        jobj = new Gson().fromJson(jsonString, PrometheusDataObject.class);
        
        List<Result> results = jobj.getData().getResult();
//        System.out.println("Size of result: " + results.size());
        
        if (results.size() != 0) {
        	Result result = results.get(0); // TODO: handle multiple results for the specified container within the specified duration (e.g. different values for eth0, eth1)
            int listSize = result.getValues().size();
//	        System.out.println("listSize: " + listSize);

	        float sum_value = 0;
        	
            for (int i=0; i< listSize; i++) {
                sum_value += Float.parseFloat(result.getValues().get(i).get(1));
//                System.out.println("value: "+Float.parseFloat(result.getValues().get(i).get(1))+", sum_value : "+sum_value+", iteration: "+i);
            }

            float averge = sum_value/listSize;
//            System.out.println("average: "+average);

            return averge;

//            result = jobj.getData().getResult().get(0).getValues().get(listSize-1).get(1);
//            return Float.parseFloat(result);
        } else {
//        	System.out.println("Metric for cell (container ID: " + cellID + ") not found.");
        	
        	throw new MetricNotFoundException("Metric for cell (container ID: " + containerID + ") not found.");
        }
    }
    
    public float getCustomMetric(String metricName, int range, int duration, int step)
		    throws MetricNotFoundException {

    	String paramPrometheus;
    	String query;
    	
        long currTime = System.currentTimeMillis()/1000;
        long startTime = currTime - duration;
    	
        query = "rate(" + metricName + "[" + range + "s])";
    		
        paramPrometheus = "/api/v1/query_range" +
        		"?query=" + query +
        		"&start=" + startTime +
        		"&end=" + currTime +
        		"&step=" + step;

        String jsonString = doQuery(paramPrometheus);
        PrometheusDataObject jobj;

//        System.out.println("jsonString: "+jsonString);
        jobj = new Gson().fromJson(jsonString, PrometheusDataObject.class);
        
        List<Result> results = jobj.getData().getResult();
//        System.out.println("Size of result: " + results.size());
        
        if (results.size() != 0) {
        	Result result = results.get(0); // TODO: handle multiple results for the specified container within the specified duration (e.g. different values for eth0, eth1)
            int listSize = result.getValues().size();
//	        System.out.println("listSize: " + listSize);

	        float sum_value = 0;
        	
            for (int i=0; i< listSize; i++) {
                sum_value += Float.parseFloat(result.getValues().get(i).get(1));
//                System.out.println("value: "+Float.parseFloat(result.getValues().get(i).get(1))+", sum_value : "+sum_value+", iteration: "+i);
            }

            float averge = sum_value/listSize;
//            System.out.println("average: "+average);

            return averge;

//            result = jobj.getData().getResult().get(0).getValues().get(listSize-1).get(1);
//            return Float.parseFloat(result);
        } else {
//        	System.out.println("Metric for cell (container ID: " + cellID + ") not found.");
        	
        	throw new MetricNotFoundException("Custom metric " + metricName + ") not found.");
        }
    }
    
    
    /**
	 * Get the cost metrics.
	 * Using Prometheus query function {@link https://prometheus.io/docs/querying/api/#instant-queries}.
	 * @param metricName is the name of the metric.
	 * @return cost metric specified.
	 * @throws MetricNotFoundException if the metric is not available for the given container
	 */
    public float getCostMetric(String metricName) throws MetricNotFoundException {

    	String paramPrometheus;
//        long currTime = System.currentTimeMillis()/1000;
        paramPrometheus = "/api/v1/query" +
        		"?query=" + metricName;// +
//        		"&time=" + currTime;

        String jsonString = doQuery(paramPrometheus);
        QueryResultInstantVectors jobj;

        jobj = new Gson().fromJson(jsonString, QueryResultInstantVectors.class);
        
        List<ResultInstantVectors> results = jobj.getData().getResult();
        
        if (results.size() != 0) {
        	ResultInstantVectors result = results.get(0);
            String resultStr = result.getValue().get(1);
            
//            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
//            System.out.println(result.getValue().get(0) + ", " + formater.format(Math.round(Double.parseDouble(result.getValue().get(0))*1000)) + ", " + result.getValue().get(1));
            
            System.out.println("Cost: " + Float.parseFloat(resultStr));
            
            return Float.parseFloat(resultStr);
        } else {
        	throw new MetricNotFoundException("Metric \"" + metricName + "\") not found.");
        }
    }
    
    
    class MetricNotFoundException extends Exception {
    	public MetricNotFoundException(String message) {
    		super(message);
    	}
    }
}