import java.io.IOException;
import java.util.List;

/**
 * Created by riccardo on 16.02.16.
 */
public class PrometheusRetriever {

    public static void main (String[] args) throws IOException {

        String urlPrometheus = "http://localhost:9090";
        String paramPrometheus = "/api/v1/query?query=process_cpu_seconds_total&time=2016-02-27T23:52:00.000Z";

        HttpRequest con = new HttpRequest();
        List list = con.connect(urlPrometheus, paramPrometheus);

        for (Object o : list) {
            System.out.println(o);
        }


    }

}