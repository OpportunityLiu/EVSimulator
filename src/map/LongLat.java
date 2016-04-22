package map;

/**
 * Created by liuzh on 2016/4/17.
 * longitude & latitude coordinate.
 */
public final class LongLat extends CoordinateBase
{
    public LongLat(double longitude, double latitude)
    {
        super(longitude, latitude);
    }

    /**
     * 经度
     */
    public double longitude()
    {
        return super.x();
    }

    /**
     * 纬度
     */
    public double latitude()
    {
        return super.y();
    }

    @Override
    public int typeCode()
    {
        return 5;
    }

    @Override
    public String typeString()
    {
        return "bd09ll";
    }

    public static LongLat parse(String value)
    {
        String[] c = value.split(",");
        return new LongLat(Double.parseDouble(c[0]), Double.parseDouble(c[1]));
    }
}
