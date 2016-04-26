package map;

import static java.lang.Math.*;

/**
 * Created by liuzh on 2016/4/17.
 * Convert different kinds of coordinates
 */
abstract class GeoConverter
{
    /*private static String uriCovert = "http://api.map.baidu.com/geoconv/v1/?coords=%s&from=%d&to=%d&ak=" + MapHelper.AK;
    
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
    }*/

    private static final double K = 6.378206400868922e+06;
    private static final double E = 0.082271872055083;
    private static final double E_2 = E / 2;
    private static final double r2d = 180 / PI;
    private static final double d2r = PI / 180;
    private static final double PI_4 = PI / 4;
    private static final double PI_2 = PI / 2;

    static MeterXY toMeterXY(LongLat coordinate)
    {
        final double L = coordinate.longitude() * d2r;
        final double B = coordinate.latitude() * d2r;
        final double x = K * L;
        final double ESinB = E * sin(B);
        final double y = K * log(tan(PI_4 + B / 2) * pow((1 - ESinB) / (1 + ESinB), E_2));
        return new MeterXY(x, y);
    }

    static LongLat toLongLat(MeterXY coordinate)
    {
        final double x = coordinate.x();
        final double y = coordinate.y();
        final double L = x / K;
        final double kk = exp(y / K);
        //迭代初值
        double B = 2 * atan(kk) - PI_2;
        //迭代 8 次
        for(int i = 0; i < 8; i++)
        {
            double ESinB = E * sin(B);
            B = 2 * atan(kk / pow((1 - ESinB) / (1 + ESinB), E_2)) - PI_2;
        }
        return new LongLat(L * r2d, B * r2d);
    }

}
