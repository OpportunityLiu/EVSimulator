package map;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;

/**
 * Created by liuzh on 2016/4/18.
 * Direction API
 */
public abstract class Direction
{
    private static final String uri = "http://api.map.baidu.com/direction/v1" +
            "?origin=%s" +
            "&destination=%s" +
            "&origin_region=%s" +
            "&destination_region=%s" +
            "&output=json" +
            "&coord_type=%s" +
            /*"&waypoints="*/ "%s" +
            "&ak=" + MapInfo.AK;

    public static <T extends CoordinateBase> T[] getDirection(T origin, T destination, String region, @Nullable T[] wayPoints) throws IOException
    {
        String wayPoint;
        if(wayPoints != null)
        {
            String[] wayPointString = new String[wayPoints.length];
            for(int i = 0; i < wayPoints.length; i++)
            {
                wayPointString[i] = wayPoints[i].toString();
            }
            wayPoint = "&waypoints=" + String.join("|", (CharSequence[])wayPointString);
        }
        else
            wayPoint = "";
        String reqUri = String.format(uri, origin, destination, region, region, origin.typeString(), wayPoint);

        return null;
    }
}
