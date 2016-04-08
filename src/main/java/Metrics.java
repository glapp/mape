import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Metrics {
	private String promethesURL = "localhost:9090";
	private URL url = null;
	private HttpURLConnection con = null;
	
	private long cycleElapsedTime = 0;
	private long totalElapsedTime = 0;
	private static int lineNumber = 1;
	
	
	public static void main(String[] args) {
		System.out.println("Initialising request.");
		
		Metrics getMetrics = new Metrics();
		long startTime = System.currentTimeMillis();

		getMetrics.query("process_cpu_seconds_total");
		try {
			getMetrics.receive();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		long stopTime = System.currentTimeMillis();
		getMetrics.cycleElapsedTime = stopTime - startTime;
		getMetrics.totalElapsedTime += getMetrics.cycleElapsedTime;
		System.out.println("Lines: " + lineNumber + ", Cycle elapsed Time: " + getMetrics.cycleElapsedTime);

		System.out.println("End of execution");
		System.out.println("Total elapsed Time: " + getMetrics.totalElapsedTime);
	}
	
	private void query(String metric) {
//		OffsetDateTime queryTime = new OffsetDateTime();
		
		try {
			URL url = new URL("http://" + promethesURL + "/api/v1/query?query=" + metric + "&time=2016-02-27T23:52:00.000Z");

			con = (HttpURLConnection)url.openConnection();

			con.setRequestMethod("GET"); //use post method
//			con.setDoOutput(true); //we will send stuff
//			con.setDoInput(true); //we want feedback
//			con.setUseCaches(false); //no caches
//			con.setAllowUserInteraction(false);
//			con.setRequestProperty("Content-Type","text/xml");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void receive() throws IOException {
		String responseSingle;
        InputStream in = con.getInputStream();
        BufferedReader br  = new BufferedReader(new InputStreamReader(in));
        
        responseSingle = br.readLine();
        System.out.println("Response: " + responseSingle);
        
        br.close();
        in.close();
        
		Gson g = new Gson();
		JsonObject responseObject = g.fromJson(responseSingle, JsonObject.class);
		
		JsonObject responeData = responseObject.get("data").getAsJsonObject();
		
//		System.out.println("Result: " + responeData.get("result"));
		
		JsonArray result = responeData.get("result").getAsJsonArray();
		
		for (int i = 0; i < result.size(); ++i) {
			JsonObject elementI = result.get(i).getAsJsonObject();
			if (elementI.has("value")) {
				JsonArray value = elementI.get("value").getAsJsonArray();
				String line = value.get(0) + "\t" + value.get(1);
				
				System.out.println(line);
			}
		}
        
        System.out.println("End of response");
	}
}
