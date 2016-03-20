import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

/**
 * Created by riccardo on 16.02.16.
 */
public class PrometheusRetriever {

    public static void main (String[] args) throws IOException {

        String host = "localhost";
        int port = 9090;
        long currTime = System.currentTimeMillis()/1000;
        long startTime = currTime - 3600;
        String step = "600s";
        String query = "rate(process_cpu_seconds_total[30s])";


        String urlPrometheus = "http://" + host + ":" + port;

        String paramPrometheus = "/api/v1/query_range" +
                "?query=" + query +
                "&start=" + startTime +
                "&end=" + currTime +
                "&step=" + step;

        System.out.println(paramPrometheus);

        HttpRequest con = new HttpRequest();
        List list = con.connect(urlPrometheus, paramPrometheus);

        for (Object o : list) {
            System.out.println(o);
        }

    }

}