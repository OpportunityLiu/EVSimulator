package map;

import loon.utils.MathUtils;

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

    public static MeterXY parse(String value)
    {
        String[] c = value.split(",");
        return new MeterXY(Double.parseDouble(c[0]), Double.parseDouble(c[1]));
    }

    public MapTileInfo getTileOfThis(final int scale)
    {
        final int scaler = 1 << (26 - scale);
        return new MapTileInfo(MathUtils.floor((float)(x() / scaler)), MathUtils.floor((float)(y() / scaler)), scale);
    }

    public class MapTileInfo
    {
        public final int x, y, scale;

        public MapTileInfo(int x, int y, int scale)
        {
            this.x = x;
            this.y = y;
            this.scale = scale;
        }
    }

    public LongLat toLongLat()
    {
        return GeoConverter.toLongLat(this);
    }
}
