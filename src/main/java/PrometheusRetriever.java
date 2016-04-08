import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;


public class PrometheusRetriever {

    public static void main (String[] args) {

        String host = "localhost";
        int port = 9090;
        String query = "rate(process_cpu_seconds_total[30s])";
        long currTime = System.currentTimeMillis()/1000;
        long startTime = currTime - 3600; // 1 hour
        String step = "600s"; // 10 minutes

        String urlPrometheus = "http://" + host + ":" + port;

        String paramPrometheus = "/api/v1/query_range" +
                "?query=" + query +
                "&start=" + startTime +
                "&end=" + currTime +
                "&step=" + step;

        System.out.println(paramPrometheus);


        HttpRequest con = new HttpRequest();
        List list = null;
        try {
            list = con.connect(urlPrometheus, paramPrometheus);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String jsonString;
        PrometheusDataObject jobj;
        String result;
        for (Object o : list) {
            jsonString = o.toString();
            System.out.println(jsonString);
            jobj = new Gson().fromJson(jsonString, PrometheusDataObject.class);
            result = jobj.getData().getResult().get(0).getValues().get(0).get(1);
            System.out.println(result);

        }

    }

}