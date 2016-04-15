package fullscreen;

import com.sun.istack.internal.NotNull;
import ev.SplashWorld;
import greenfoot.Greenfoot;
import greenfoot.World;

/**
 * Created by liuzh on 2016/4/14.
 * World for loading full screen window and start running.
 */
public class InitWorld extends World
{
    public InitWorld()
    {
        super(1, 1, 1);
        Greenfoot.start();
    }

    @Override
    public void started()
    {
        FullScreenWorld.fullScreenWindow = new FullScreenWindow();
        FullScreenWindow.setDisplayedWorld(new SplashWorld());
    }

    @Override
    public void act()
    {
    }
}
