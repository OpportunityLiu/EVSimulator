/**
 * Created by liuzh on 2016/4/14.
 * The world for splash images
 */
package ev;

import greenfoot.*;
import fullscreen.*;

import java.awt.*;

public class SplashWorld extends FullScreenWorld
{
    public SplashWorld()
    {
        super(4/3.0,1,false);
        GreenfootImage b = new GreenfootImage(this.getWidth(), this.getHeight());
        b.setColor(Color.black);
        b.fill();
        this.setBackground(b);
        Greenfoot.setSpeed(70);
        setSplash();
        FullScreenWindow.setCursorVisibility(false);
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
    public void run()
    {
    }

    void nextSplash()
    {
        if(!setSplash()) FullScreenWindow.setDisplayedWorld(new HomeWorld());
    }
}
