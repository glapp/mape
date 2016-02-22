import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

/**
 * Created by riccardo on 16.02.16.
 */
public class ContainerRetriever {

    public static void main (String[] args) throws IOException {

        String urlSwarm = "http://192.168.99.101:3376/containers/json?all=1";

        HttpRequest con = new HttpRequest();
        List list = con.connect(urlSwarm, "");

        for (Object o : list) {
            System.out.println(o);
        }

    }

}