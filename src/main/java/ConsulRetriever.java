import java.io.IOException;
import java.util.List;

/**
 * Created by riccardo on 16.02.16.
 */
public class ConsulRetriever {

    public static void main (String[] args) throws IOException {

        String urlConsul = "http://82.196.15.58:8500";
        String paramConsul = "/v1/kv/docker?recursive&keys&pretty";

        HttpRequest con = new HttpRequest();
        List list = con.connect(urlConsul, paramConsul);

        for (Object o : list) {
            System.out.println(o);
        }


    }

}