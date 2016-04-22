package map;


import loon.Json;
import loon.LSystem;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Formatter;

import static map.MapInfo.convertStreamToString;

/**
 * Created by liuzh on 2016/4/17.
 * Convert different kinds of coordinates
 */
public abstract class GeoConverter
{
    private static String uriCovert = "http://api.map.baidu.com/geoconv/v1/?coords=%s&from=%d&to=%d&ak=" + MapInfo.AK;
    
    public static MeterXY[] toMeterXY(LongLat... coordinates) throws IOException
    {
        Json.Array results = convert(coordinates, 5, 6);
        int count = results.length();
        MeterXY[] ret = new MeterXY[count];
        for(int i = 0; i < count; i++)
        {
            Json.Object pos = results.getObject(i);
            ret[i] = new MeterXY(pos.getDouble("x"), pos.getDouble("y"));
        }
        return ret;
    }

    public static MeterXY toMeterXY(LongLat coordinate) throws IOException
    {
        return toMeterXY(new LongLat[]{coordinate})[0];
    }

    public static LongLat[] toLongLat(MeterXY... coordinates) throws IOException
    {
        Json.Array results = convert(coordinates, 6, 5);
        int count = results.length();
        LongLat[] ret = new LongLat[count];
        for(int i = 0; i < count; i++)
        {
            Json.Object pos = results.getObject(i);
            ret[i] = new LongLat(pos.getDouble("x"), pos.getDouble("y"));
        }
        return ret;
    }

    public static LongLat toLongLat(MeterXY coordinate) throws IOException
    {
        return toLongLat(new MeterXY[]{coordinate})[0];
    }

    private static Json.Array convert(CoordinateBase[] coordinates, int from, int to) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        for(CoordinateBase coordinate : coordinates)
        {
            sb.append(coordinate);
            sb.append(';');
        }
        sb.deleteCharAt(sb.length() - 1);
        String requestUri = String.format(uriCovert, sb.toString(), from, to);
        Json.Object resJson = MapInfo.httpGet(requestUri);
        int status = resJson.getInt("status");
        if(status != 0)
            throw new IOException(resJson.getString("message"));
        return resJson.getArray("result");
    }


}
