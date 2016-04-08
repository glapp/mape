import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpRequest {

    public List connect (String address, String parameter) throws IOException {

        URL url = new URL(address + parameter);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(url.openStream()));

        String inputLine;
        List list = new ArrayList();

        while ((inputLine = in.readLine()) != null) {
            list.add(inputLine);
        }
        in.close();

        return list;

    }

}
