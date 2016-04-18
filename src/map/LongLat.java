package map;

/**
 * Created by liuzh on 2016/4/17.
 * longitude & latitude coordinate.
 */
public final class LongLat implements ICoordinate
{
    private final double longitude;
    private final double latitude;

    public LongLat(double longitude, double latitude)
    {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * 经度
     */
    @Override
    public double x()
    {
        return longitude;
    }

    /**
     * 纬度
     */
    @Override
    public double y()
    {
        return latitude;
    }

    @Override
    public int type()
    {
        return 5;
    }
}
