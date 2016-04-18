package map;


import loon.Json;
import loon.LSystem;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by liuzh on 2016/4/17.
 * Convert different kinds of coordinates
 */
public class GeoConverter
{
    private enum ErrorCode
    {
        Ok(0),

        /**
         * 内部错误
         */
        UnknownError(1),
        
        /**
         * from非法
         */
        InvalidForm(21),
        
        /**
         * to非法
         */
        InvalidTo(22),
        
        /**
         * coords格式非法
         */
        InvalidCoordinateFormat(24),
        
        /**
         * coords个数非法，超过限制
         */
        InvalidCoordinateCount(25);
        
        ErrorCode(int error)
        {
            code = error;
        }
        
        private int code;
        
        @NotNull
        public static ErrorCode getError(int errorCode)
        {
            for(ErrorCode code : ErrorCode.values())
            {
                if(code.code == errorCode)
                    return code;
            }
            return UnknownError;
        }
    }

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

    private static Json.Array convert(ICoordinate[] coordinates, int from, int to) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        for(ICoordinate coordinate : coordinates)
        {
            sb.append(coordinate.x());
            sb.append(',');
            sb.append(coordinate.y());
            sb.append(';');
        }
        sb.deleteCharAt(sb.length() - 1);
        String requestUri = String.format(uriCovert, sb.toString(), from, to);
        URL url = new URL(requestUri);
        URLConnection connection = url.openConnection();
        connection.connect();
        String response = convertStreamToString(connection.getInputStream());
        Json.Object resJson = LSystem.json().parse(response);
        int status = resJson.getInt("status");
        if(status != 0)
            throw new IOException(ErrorCode.getError(status).name());
        return resJson.getArray("result");
    }

    private static String convertStreamToString(java.io.InputStream is)
    {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }


}