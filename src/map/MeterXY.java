package map;

/**
 * Created by liuzh on 2016/4/17.
 * x & y coordinates.
 */

public final class MeterXY implements ICoordinate
{
    private final double x, y;

    public MeterXY(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public double x()
    {
        return x;
    }

    @Override
    public double y()
    {
        return y;
    }

    @Override
    public int type()
    {
        return 6;
    }
}
