package map;


import loon.Json;
import loon.utils.MathUtils;
import loon.utils.TArray;

import java.io.*;

/**
 * Created by liuzh on 2016/4/17.
 * Convert different kinds of coordinates
 */
public abstract class GeoConverter
{
    private static String uriCovert = "http://api.map.baidu.com/geoconv/v1/?coords=%s&from=%d&to=%d&ak=" + MapHelper.AK;
    
    public static MeterXY[] toMeterXY(LongLat... coordinates) throws IOException
    {
        XY[] results = convert(coordinates, 5, 6);
        int count = results.length;
        MeterXY[] ret = new MeterXY[count];
        for(int i = 0; i < count; i++)
        {
            ret[i] = new MeterXY(results[i].x, results[i].y);
        }
        return ret;
    }

    public static MeterXY toMeterXY(LongLat coordinate) throws IOException
    {
        return toMeterXY(new LongLat[]{coordinate})[0];
    }

    public static LongLat[] toLongLat(MeterXY... coordinates) throws IOException
    {
        XY[] results = convert(coordinates, 6, 5);
        int count = results.length;
        LongLat[] ret = new LongLat[count];
        for(int i = 0; i < count; i++)
        {
            ret[i] = new LongLat(results[i].x, results[i].y);
        }
        return ret;
    }

    public static LongLat toLongLat(MeterXY coordinate) throws IOException
    {
        return toLongLat(new MeterXY[]{coordinate})[0];
    }

    private static class XY
    {
        double x, y;

        XY(double x, double y)
        {
            this.x = x;
            this.y = y;
        }
    }

    private static XY[] convert(CoordinateBase[] coordinates, int from, int to) throws IOException
    {
        if(coordinates == null || coordinates.length == 0)
            return new XY[0];
        XY[] result = new XY[coordinates.length];
        int start = 0, end;
        while(start < coordinates.length)
        {
            end = MathUtils.min(start + 100, coordinates.length);
            Json.Array r = convertCore(coordinates, start, end, from, to);
            for(int i = start; i < end; i++)
            {
                Json.Object pos = r.getObject(i);
                result[i] = new XY(pos.getDouble("x"), pos.getDouble("y"));
            }
            start = end;
        }
        return result;
    }


    private static Json.Array convertCore(CoordinateBase[] coordinates, int start, int end, int from, int to) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        for(int i = start; i < end; i++)
        {
            sb.append(coordinates[i]);
            sb.append(';');
        }
        sb.deleteCharAt(sb.length() - 1);
        String requestUri = String.format(uriCovert, sb.toString(), from, to);
        Json.Object resJson = MapHelper.httpGet(requestUri);
        int status = resJson.getInt("status");
        if(status != 0)
            throw new IOException(resJson.getString("message"));
        return resJson.getArray("result");
    }
}
