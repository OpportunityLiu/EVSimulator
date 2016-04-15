/**
 * Created by liuzh on 2016/4/14.
 * The world for splash images
 */
package ev;

import controls.FullScreenWorld;
import controls.ScenarioHelper;
import greenfoot.*;
import sun.security.krb5.SCDynamicStoreConfig;

import java.awt.*;

public class SplashWorld extends FullScreenWorld
{
    public SplashWorld()
    {
        super(1);
        GreenfootImage b = new GreenfootImage(1, 1);
        b.setColor(Color.black);
        b.fill();
        this.setBackground(b);
        setSplash();
        ScenarioHelper.setCursorVisible(false);
    }

    private int count = Settings.getSplashCount();
    private int current = 0;
    private SplashImage currentSplash;

    private boolean setSplash()
    {
        current++;
        boolean check = current <= count;
        if(check)
        {
            if(currentSplash != null)
            {
                this.removeObject(currentSplash);
                currentSplash = null;
            }
            currentSplash = new SplashImage("Splashes/Splash" + current + ".png");
            this.addObject(currentSplash, this.getWidth() / 2, this.getHeight() / 2);
        }
        return check;
    }

    @Override
    public void started()
    {
    }

    @Override
    public void act()
    {
    }

    void nextSplash()
    {
        if(!setSplash())
            Greenfoot.setWorld(new HomeWorld());
    }
}
