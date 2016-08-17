package ch.uzh.glapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

public class HttpRequest {

    public String GETConnection(String address, String parameter) throws IOException {

	    try {
		    HttpURLConnection httpConnection = (HttpURLConnection) new URL(address + parameter).openConnection();

		    BufferedReader in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));

		    String inputLine;
		    String result = "";

		    while ((inputLine = in.readLine()) != null) {
			    result += inputLine;
		    }
		    in.close();
		    return result;

	    } catch (IOException e) {
		    System.out.println("Connection could not be established. Retrying in ### secs.");

		    // waiting a bit for sails comming up.
		    try {
			    System.out.println("Sleep 5 secs.");
			    TimeUnit.SECONDS.sleep(5);
		    } catch (InterruptedException ex) {
			    ex.printStackTrace();
		    }

		    String str = GETConnection(address, parameter);
		    return str;
	    }



    }

	public String POSTConnection (String address, String parameter) throws IOException {


		HttpURLConnection httpConnection = (HttpURLConnection) new URL(address).openConnection();
		httpConnection.setRequestMethod("POST");
		httpConnection.setDoOutput(true);
		httpConnection.setRequestProperty("Content-Type", "text/plain");

		System.out.println("Address: "+address);
		System.out.println("Parameter: "+parameter);

		OutputStreamWriter out = new OutputStreamWriter(httpConnection.getOutputStream());
		out.write(parameter);
		out.close();

		httpConnection.connect();

		BufferedReader in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
		String inputLine;
		String result = "";
		while ((inputLine = in.readLine()) != null) {
			result += inputLine;
		}
		in.close();

		return result;

	}

}