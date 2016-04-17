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

    private static String uriMeterXY = "http://api.map.baidu.com/geoconv/v1/?coords=%s&from=5&to=6&ak=" + Map.AK;
    private static String uriLongLat = "http://api.map.baidu.com/geoconv/v1/?coords=%s&from=6&to=5&ak=" + Map.AK;
    
    public static MeterXY[] toMeterXY(LongLat... coordinates) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        for(LongLat coordinate : coordinates)
        {
            sb.append(coordinate.longitude);
            sb.append(',');
            sb.append(coordinate.latitude);
            sb.append(';');
        }
        sb.deleteCharAt(sb.length() - 1);
        String requestUri = String.format(uriMeterXY, sb.toString());
        URL url = new URL(requestUri);
        URLConnection connection = url.openConnection();
        connection.connect();
        String response = convertStreamToString(connection.getInputStream());
        Json.Object resJson = LSystem.json().parse(response);
        int status = resJson.getInt("status");
        if(status != 0)
            throw new IOException(ErrorCode.getError(status).name());
        Json.Array results = resJson.getArray("result");
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
        StringBuilder sb = new StringBuilder();
        for(MeterXY coordinate : coordinates)
        {
            sb.append(coordinate.x);
            sb.append(',');
            sb.append(coordinate.y);
            sb.append(';');
        }
        sb.deleteCharAt(sb.length() - 1);
        String requestUri = String.format(uriLongLat, sb.toString());
        URL url = new URL(requestUri);
        URLConnection connection = url.openConnection();
        connection.connect();
        String response = convertStreamToString(connection.getInputStream());
        Json.Object resJson = LSystem.json().parse(response);
        int status = resJson.getInt("status");
        if(status != 0)
            throw new IOException(ErrorCode.getError(status).name());
        Json.Array results = resJson.getArray("result");
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

    static String convertStreamToString(java.io.InputStream is)
    {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }


}
