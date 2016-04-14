/**
 * Created by liuzh on 2016/4/14.
 */
package ev;

import greenfoot.*;

import java.awt.*;

public class SplashWorld extends World
{
    public SplashWorld()
    {
        super(Settings.getWorldWidth(), Settings.getWorldHeight(), 1);
        GreenfootImage b = new GreenfootImage(this.getWidth(), this.getHeight());
        b.setColor(Color.black);
        b.fill();
        this.setBackground(b);
        setSplash();
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
            currentSplash = new SplashImage("Splash" + current + ".png");
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

    public void nextSplash()
    {
        if(!setSplash()) Greenfoot.setWorld(new HomeWorld());
    }
}
