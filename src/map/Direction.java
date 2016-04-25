package map;

import loon.Json;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzh on 2016/4/18.
 * Direction API
 */
public abstract class Direction
{
    private static final String uri = "http://api.map.baidu.com/direction/v1" +
            "?mode=driving" +
            "&origin=%s" +
            "&destination=%s" +
            "&origin_region=%s" +
            "&destination_region=%<s" +
            "&coord_type=%s" +
            "&output=json&ak=" + MapHelper.AK;

    public static <T extends CoordinateBase> List<LongLat> getDirection(T origin, T destination, String region) throws IOException
    {
        region = URLEncoder.encode(region, "utf-8");
        String reqUri = String.format(uri, origin.toYXString(), destination.toYXString(), region, origin.typeString());
        Json.Object response = MapHelper.httpGet(reqUri);
        if(response.getInt("status") != 0)
            throw new IOException(response.getString("message"));
        ArrayList<LongLat> route = new ArrayList<>();
        Json.Object routeJson=response.getObject("result").getArray("routes").getObject(0);
        Json.Array stepsJson = routeJson.getArray("steps");
        for(int i = 0; i < stepsJson.length(); i++)
        {
            Json.Object stepJson = stepsJson.getObject(i);
            Json.Object o = stepJson.getObject("stepOriginLocation");
            //Json.Object d = step.getObject("stepDestinationLocation");
            String p = stepJson.getString("path");
            route.add(new LongLat(o.getDouble("lng"), o.getDouble("lat")));
            final String[] paths = p.split("[;]");
            for(String path : paths)
            {
                route.add(LongLat.parse(path));
            }
        }
        Json.Object d=routeJson.getObject("destinationLocation");
        route.add(new LongLat(d.getDouble("lng"), d.getDouble("lat")));
        return route;
    }
}
