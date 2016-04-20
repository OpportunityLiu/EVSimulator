package map;

/**
 * Created by liuzh on 2016/4/17.
 * x & y coordinates.
 */

public final class MeterXY extends CoordinateBase
{
    public MeterXY(double x, double y)
    {
        super(x, y);
    }

    @Override
    public int typeCode()
    {
        return 6;
    }

    @Override
    public String typeString()
    {
        return "bd09mc";
    }
}
