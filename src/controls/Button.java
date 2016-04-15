package controls;

import greenfoot.Actor;
import greenfoot.Greenfoot;

/**
 * Default button in the program.
 * Created by liuzh on 2016/4/15.
 */
public class Button extends Actor
{
    public Button()
    {
        setImage("Icon.png");
    }

    @Override
    public void act()
    {
        if(listener != null)
        {
            if(Greenfoot.mouseClicked(this))
                listener.mouseClicked(Greenfoot.getMouseInfo());
            if(Greenfoot.mouseDragged(this))
                listener.mouseDragged(Greenfoot.getMouseInfo());
            if(Greenfoot.mouseDragEnded(this))
                listener.mouseDragEnded(Greenfoot.getMouseInfo());
            if(Greenfoot.mouseMoved(this))
                listener.mouseMoved(Greenfoot.getMouseInfo());
            if(Greenfoot.mousePressed(this))
                listener.mousePressed(Greenfoot.getMouseInfo());
        }
    }

    private MouseListener listener;

    public MouseListener getListener()
    {
        return listener;
    }

    public Button setListener(MouseListener listener)
    {
        this.listener = listener;
        return this;
    }
}
