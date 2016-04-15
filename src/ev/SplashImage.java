/**
 * Created by liuzh on 2016/4/14.
 * Image shows in splash screen.
 */
package ev;

import controls.ScenarioHelper;
import greenfoot.*;

class SplashImage extends Actor
{
    SplashImage(String filename) throws IllegalArgumentException
    {
        this.image = new GreenfootImage(filename);
        this.image.setTransparency(0);
        double scale = Math.min((double)ScenarioHelper.getFrameWidth() / image.getWidth(), (double)ScenarioHelper.getFrameHeight() / image.getHeight());
        this.image.scale((int)(image.getWidth() * scale), (int)(image.getHeight() * scale));
        super.setImage(this.image);
    }

    SplashImage(String filename, int delayLength, int stepLength)
    {
        this(filename);
        this.delayLength = delayLength;
        this.stepLength = stepLength;
    }

    private GreenfootImage image;

    private int state = 0;
    private int delay = 0;

    private int delayLength = 200;
    private int stepLength = 1;

    @Override
    public void act()
    {
        int t = this.image.getTransparency();
        switch(state)
        {
            case 0:
                t += stepLength;
                t = t > 255 ? 255 : t;
                if(t >= 255) state = 1;
                break;
            case 1:
                delay++;
                if(delay > delayLength) state = 2;
                break;
            case 2:
                t -= stepLength;
                t = t < 0 ? 0 : t;
                if(t <= 0) state = 3;
                break;
            case 3:
                SplashWorld w = (SplashWorld)this.getWorld();
                w.nextSplash();
        }
        this.image.setTransparency(t);
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
