import java.io.IOException;
import java.util.List;

public class PolicyRetriever {

    public int retrieveValue (int appId) throws IOException {

        String urlSails = "localhost:1337";
        String paramSails = "/policy?app_id=" + appId;


        HttpRequest con = new HttpRequest();
        List list = con.connect(urlSails, paramSails);

        int i=0;
        for (Object o : list) {
            System.out.println(o);

        }

        return appId;
    }
}