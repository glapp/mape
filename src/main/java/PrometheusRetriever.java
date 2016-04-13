import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;


public class PrometheusRetriever {

    public float retrieveInt (String query) {

        //String query2 = "rate(process_cpu_seconds_total[30s])";

        String host = "localhost";
        int port = 9090;

        long currTime = System.currentTimeMillis()/1000;
        long startTime = currTime - 3600; // 1 hour
        String step = "600s"; // 10 minutes

        String urlPrometheus = "http://" + host + ":" + port;

        String paramPrometheus = "/api/v1/query_range" +
                "?query=" + query +         // or query2
                "&start=" + startTime +
                "&end=" + currTime +
                "&step=" + step;

//        System.out.println(urlPrometheus + paramPrometheus);


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
//        System.out.println(jsonString);
        jobj = new Gson().fromJson(jsonString, PrometheusDataObject.class);
        int listSize = jobj.getData().getResult().get(0).getValues().size();
        result = jobj.getData().getResult().get(0).getValues().get(listSize-1).get(1);


        return Float.parseFloat(result);

    }

}