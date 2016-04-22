package map;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by liuzh on 2016/4/18.
 * coordinate in a map
 */
public abstract class CoordinateBase
{
    private final double x, y;

    protected CoordinateBase(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public double x()
    {
        return x;
    }

    public double y()
    {
        return y;
    }

    public abstract int typeCode();

    public abstract String typeString();

    public String toString()
    {
        final String yy = formater.format(y);
        final String xx = formater.format(x);
        return String.format("%s,%s", xx, yy);
    }

    public String toYXString()
    {
        final String yy = formater.format(y);
        final String xx = formater.format(x);
        return String.format("%s,%s", yy, xx);
    }

    private static NumberFormat formater = new DecimalFormat("#.###########");

    public static <T extends CoordinateBase> double distance(T a, T b)
    {
        double dx = a.x() - b.x();
        double dy = a.y() - b.y();
        double c = Math.abs(dx);
        double d = Math.abs(dy);

        if(c > d)
        {
            double r = d / c;
            return c * Math.sqrt(1.0 + r * r);
        }
        else if(d == 0.0)
        {
            return c;  // c is either 0.0 or NaN
        }
        else
        {
            double r = c / d;
            return d * Math.sqrt(1.0 + r * r);
        }
    }
}
