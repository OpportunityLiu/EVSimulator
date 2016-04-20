package map;

/**
 * Created by liuzh on 2016/4/17.
 * Baidu map API
 */
class MapInfo
{
    static final String AK="IyNOuhmURakqti0KikUfmTkXUXcneZrm";

    static String convertStreamToString(java.io.InputStream is)
    {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
