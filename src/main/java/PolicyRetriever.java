import java.io.IOException;
import java.util.List;

public class PolicyRetriever {

    public static void main (String[] args) throws IOException {

        String urlSails = "localhost:1337";
        String paramSails = "/policy?id=xxx";

        String test;

        HttpRequest con = new HttpRequest();
        List list = con.connect(urlSails, paramSails);

        for (Object o : list) {
            System.out.println(o);
        }
    }
}