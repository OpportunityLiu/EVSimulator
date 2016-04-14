package fullscreen;

import com.sun.istack.internal.NotNull;
import greenfoot.Actor;

import java.awt.event.MouseEvent;
import java.awt.Point;
import java.util.List;

/**
 * This class works like a greenfoot.MouseInfo object. The coordinates are relative to the world size so you don't have to change the values you already got.
 * This class can be used the same way like greenfoot.MouseInfo objects without any changes.
 *
 * @author Gevater_Tod4711
 * @version 1.1
 */
public class FullScreenMouseInfo
{
    private MouseEvent mouseEvent;
    
    private Point mousePoint;
    
    private FullScreenWindow callingWindow;

    FullScreenMouseInfo(@NotNull MouseEvent mouseEvent, FullScreenWindow callingWindow) throws IllegalArgumentException
    {
        this.mouseEvent = mouseEvent;
        this.callingWindow = callingWindow;
        mousePoint = mouseEvent.getLocationOnScreen();
    }
    
    /**
     * Return the actor (if any) that the current mouse behaviour is related to.
     *
     * @return The actor this mouse behaviour is related to or null if there is no such actor.
     */
    public Actor getActor()
    {
        List<Actor> actors = FullScreenWindow.getDisplayedWorld().getObjectsAt(getX(), getY(), Actor.class);
        if(actors != null)
        {
            return actors.get(0);
        }
        return null;
    }
    
    /**
     * The number of the pressed or clicked button (if any).
     *
     * @return The button number. Usually 1 is the left button, 2 is the middle button and 3 is the right button.
     */
    public int getButton()
    {
        if(mouseEvent != null)
        {
            return mouseEvent.getButton();
        }
        return -1;
    }
    
    /**
     * Return the number of mouse clicks associated with this mouse event.
     *
     * @return The number of times a button has been clicked.
     */
    public int getClickCount()
    {
        if(mouseEvent != null)
        {
            return mouseEvent.getClickCount();
        }
        return -1;
    }
    
    /**
     * Return the current x position of the mouse cursor in the world.
     * The returned value is relative to the worlds size and not to the screen size so this method can be used like in a normal greenfoot scenario.
     *
     * @return The x position in the world.
     */
    public int getX()
    {
        FullScreenWorld world = FullScreenWindow.getDisplayedWorld();
        int mouseX = (int)(((mousePoint.getX() - FullScreenWindow.MOUSE_OFFSET_X) * world.WORLD_WIDTH / FullScreenWindow.IMAGE_WIDTH) / world.getCellSize());
        if(mouseX < 0)
        {
            return 0;
        }
        else if(mouseX >= world.WORLD_WIDTH)
        {
            return world.WORLD_WIDTH - 1;
        }
        else
        {
            return mouseX;
        }
    }
    
    /**
     * Return the current y position of the mouse cursor in the world.
     * The returned value is relative to the worlds size and not to the screen size so this method can be used like in a normal greenfoot scenario.
     *
     * @return The y position in the world.
     */
    public int getY()
    {
        FullScreenWorld world = FullScreenWindow.getDisplayedWorld();
        int mouseY = (int)(((mousePoint.getY() - FullScreenWindow.MOUSE_OFFSET_Y) * world.WORLD_HEIGHT / FullScreenWindow.IMAGE_HEIGHT) / world.getCellSize());
        if(mouseY < 0)
        {
            return 0;
        }
        else if(mouseY >= world.WORLD_HEIGHT)
        {
            return world.WORLD_HEIGHT - 1;
        }
        else
        {
            return mouseY;
        }
    }
    
    /**
     * Return the current x position of the mouse cursor on the screen.
     * The returned value is relative to the screensize and not to the worlds size.
     *
     * @return The x position on the screen.
     */
    public int getXOnScreen()
    {
        return (int)mousePoint.getX();
    }
    
    /**
     * Return the current y position of the mouse cursor on the screen.
     * The returned value is relative to the screensize and not to the worlds size.
     *
     * @return The y position on the screen.
     */
    public int getYOnScreen()
    {
        return (int)mousePoint.getY();
    }
    
    /**
     * Return the current mouse point consisting of the x and y coordinates of the mouse.
     * The returned value is relative to the worlds size and not to the screen size so this method can be used like in a normal greenfoot scenario.
     *
     * @return The current mouse point.
     */
    public Point getMousePoint()
    {
        return new Point(getX(), getY());
    }
    
    /**
     * The representing String for this fullscreen.FullScreenMouseInfo object.
     */
    public String toString()
    {
        return "fullscreen.FullScreenMouseInfo: (" + getX() + "|" + getY() + ") \t" + mouseEvent.toString();
    }
}