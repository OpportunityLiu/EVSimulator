package fullscreen;

import java.awt.event.MouseEvent;

/**
 * This MouseListener is used to react on MouseEvents.
 * All incoming MouseEvents are passed on to the fullscreenwindow.
 * 
 * @author Gevater_Tod4711
 * @version 1.1
 */
class GreenfootMouseListener implements java.awt.event.MouseListener, java.awt.event.MouseMotionListener {

    private FullScreenWindow window;
    
    /**
     * Create a new fullscreen.GreenfootMouseListener with a reference to the calling fullscreen.FullScreenWindow object.
     */
    public GreenfootMouseListener(FullScreenWindow window) {
        this.window = window;
    }
    
    /**
     * React when the mouse is clicked.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        window.setMouseClicked(true);
        window.setLastMouseEvent(e);
    }
    
	/**
	 * React when the mouse is entered.
	 */
    @Override
    public void mouseEntered(MouseEvent e) {
        window.setMouseDragged(true);
        window.setLastMouseEvent(e);
    }
    
	/**
	 * React when the mouse is exited.
	 */
    @Override
    public void mouseExited(MouseEvent e) {
        window.setMouseDragged(false);
        window.setMouseDragEnded(true);
        window.setLastMouseEvent(e);
    }
    
	/**
	 * React when the mouse is pressed.
	 */
    @Override
    public void mousePressed(MouseEvent e) {
        window.setMousePressed(true);
        window.setLastMouseEvent(e);
    }
    
	/**
	 * React when the mouse is released.
	 */
    @Override
    public void mouseReleased(MouseEvent e) {
        window.setMousePressed(false);
        window.setLastMouseEvent(e);
    }
    
    /**
     * React when the mouse is dragged.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        window.setMouseDragged(true);
        window.setLastMouseEvent(e);
    }
    
    /**
     * React when the mouse is moved.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        window.setLastMouseEvent(e);
    }
}