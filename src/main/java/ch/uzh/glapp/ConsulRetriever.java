package ch.uzh.glapp;

import java.io.IOException;

public class ConsulRetriever {

    // TODO: this class is only a stub and has to be finished.

    public static void main (String[] args) {

        String urlConsul = "http://82.196.15.58:8500";
        String paramConsul = "/v1/kv/docker?recursive&keys&pretty";

        HttpRequest con = new HttpRequest();
        String str = "";
        try {
            str = con.connect(urlConsul, paramConsul);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(str);


    }

}