package ev;

/**
 * Created by liuzh on 2016/4/18.
 * Debug settings
 */
public class DebugSettings
{
    public static void setMode(boolean debug)
    {
        if(debug)
        {
            skipSplash = true;
            fullscreen = false;
        }
    }

    public static boolean skipSplash = false;
    public static boolean fullscreen = false;
}
