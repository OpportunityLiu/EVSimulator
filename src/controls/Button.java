package controls;

import fullscreen.FullScreenWindow;
import greenfoot.Actor;

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
            if(FullScreenWindow.mouseClicked(this))
                listener.mouseClicked(FullScreenWindow.getMouseInfo());
            if(FullScreenWindow.mouseDragged(this))
                listener.mouseDragged(FullScreenWindow.getMouseInfo());
            if(FullScreenWindow.mouseDragEnded(this))
                listener.mouseDragEnded(FullScreenWindow.getMouseInfo());
            if(FullScreenWindow.mouseMoved(this))
                listener.mouseMoved(FullScreenWindow.getMouseInfo());
            if(FullScreenWindow.mousePressed(this))
                listener.mousePressed(FullScreenWindow.getMouseInfo());
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
