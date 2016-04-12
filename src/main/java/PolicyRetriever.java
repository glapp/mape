import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

public class PolicyRetriever {

    public int retrieveValue (int appId) throws IOException {

        String urlSails = "localhost:1337";
        String paramSails = "/policy?app_id=" + appId;


        HttpRequest con = new HttpRequest();
        List list = con.connect(urlSails, paramSails);

        String jsonString;
        PolicyDataObject jobj;
        String result = "";
        for (Object o : list) {
            jsonString = o.toString();
            //System.out.println(jsonString);
            jobj = new Gson().fromJson(jsonString, PolicyDataObject.class);
            result = jobj.getRules().get(0).getValue();
            System.out.println("Result policy"+result);
        }

        int value = Integer.getInteger(result);

        return value;
    }
}