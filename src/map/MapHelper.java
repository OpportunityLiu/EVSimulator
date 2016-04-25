package map;

import loon.Json;
import loon.LSystem;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by liuzh on 2016/4/17.
 * Baidu map API
 */
class MapHelper
{
    static final String AK="IyNOuhmURakqti0KikUfmTkXUXcneZrm";

    static String convertStreamToString(java.io.InputStream is)
    {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    static Json.Object httpGet(String uri) throws IOException
    {
        URL url =  new URL(uri);
        URLConnection connection = url.openConnection();
        connection.connect();
        String response = convertStreamToString(connection.getInputStream());
        return LSystem.json().parse(response);
    }
}
