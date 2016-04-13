import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

public class PolicyRetriever {

    public double retrieveValue (String appId) {

        String urlSails = "http://localhost:1337";
        String paramSails = "/policy?app_id=" + appId;

//        System.out.println(urlSails + paramSails);


        HttpRequest con = new HttpRequest();
        String str = "";
        try {
            str = con.connect(urlSails, paramSails);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String jsonString;
        PolicyDataObject jobj;
        String result;

        jsonString = str;
        //System.out.println(jsonString);
        jobj = new Gson().fromJson(jsonString, PolicyDataObject.class);
        result = jobj.getRules().get(0).getValue();

        return Double.parseDouble(result);
    }
}