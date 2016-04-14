/**
 * Created by liuzh on 2016/4/14.
 */
package ev;

/**
 * Class for getting and setting overall settings.
 */
public class Settings
{
    private static int WorldWidth = 1024;
    private static int WorldHeight = 768;
    private static int SplashCount = 2;

    public static int getWorldWidth()
    {
        return WorldWidth;
    }

    public static void setWorldWidth(int worldWidth)
    {
        WorldWidth = worldWidth;
    }

    public static int getWorldHeight()
    {
        return WorldHeight;
    }

    public static void setWorldHeight(int worldHeight)
    {
        WorldHeight = worldHeight;
    }

    public static int getSplashCount()
    {
        return SplashCount;
    }
}
