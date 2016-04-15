package controls;

import greenfoot.MouseInfo;

import java.util.EventListener;

/**
 * Created by liuzh on 2016/4/15.
 * Custom mouse listener for custom controls.
 */
public interface MouseListener extends EventListener
{
    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     */
    public void mouseClicked(MouseInfo e);

    /**
     * Invoked when the mouse is currently being dragged on a component.
     */
    public void mouseDragged(MouseInfo e);

    /**
     * Invoked when a mouse drag has ended on a component.
     */
    public void mouseDragEnded(MouseInfo e);

    /**
     * Invoked when the mouse has been moved on a component.
     */
    public void mouseMoved(MouseInfo e);

    /**
     * Invoked when the mouse has been pressed (changed from a non-pressed state to being pressed) on a component.
     */
    public void mousePressed(MouseInfo e);
}
