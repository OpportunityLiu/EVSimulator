/**
 * Created by liuzh on 2016/4/14.
 */
package ev;

import greenfoot.*;

public class SplashImage extends Actor
{
    public SplashImage(String filename) throws IllegalArgumentException
    {
        super();
        this.image = new GreenfootImage(filename);
        this.image.setTransparency(0);
        this.image.scale(Settings.getWorldWidth(), Settings.getWorldHeight());
        super.setImage(this.image);
    }

    private GreenfootImage image;

    private int state = 0;
    private int delay = 0;

    private int delayLength = 200;

    @Override
    public void act()
    {
        int t = this.image.getTransparency();
        switch(state)
        {
            case 0:
                t++;
                if(t >= 255) state = 1;
                break;
            case 1:
                delay++;
                if(delay > delayLength) state = 2;
                break;
            case 2:
                t--;
                if(t <= 0) state = 3;
                break;
            case 3:
                SplashWorld w = (SplashWorld)this.getWorld();
                w.nextSplash();
        }
        t = t > 255 ? 255 : t;
        this.image.setTransparency(t);
        if(t > 255)
        {

        }
    }

    public int getDelayLength()
    {
        return delayLength;
    }

    public void setDelayLength(int delayLength) throws IndexOutOfBoundsException
    {
        if(delayLength <= 0)
        {
            throw new IndexOutOfBoundsException();
        }
        this.delayLength = delayLength;
    }
}
