package fullscreen;

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
        Greenfoot.setWorld(new SplashWorld());
    }
}
