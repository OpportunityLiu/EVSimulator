package map;

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
        return String.format("%f,%f", x, y);
    }
}
