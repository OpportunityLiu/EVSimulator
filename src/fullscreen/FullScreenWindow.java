package fullscreen;

import com.sun.istack.internal.NotNull;
import greenfoot.Actor;
import greenfoot.World;
import greenfoot.Greenfoot;
import greenfoot.core.WorldHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

/**
 * This class is used to create the fullscreen frame which displays the world image.
 * The image that is send to this frame is passed on to the fullscreen.FullScreenBufferPanel where the image is printed.
 * <p>
 * Mouse and key listeners are included into this frame.
 * Because the greenfoot frame is not the active frame when you playing in fullscreen mode the methods Greenfoot.isKeyDown(String), Greenfoot.getMouseInfo(), (...) will not work.
 * Instead you will have to use fullscreen.FullScreenWindow.isKeyDown(String) or fullscreen.FullScreenWindow.getMouseInfo() which work the same way like the greenfoot methods.
 * <p>
 * All changed methods are:
 * - Greenfoot.isKeyDown(String)
 * - Greenfoot.getKey()
 * - Greenfoot.getMouseInfo()
 * - Greenfoot.mouseClicked(Object obj)
 * - Greenfoot.mouseDragged(Object obj)
 * - Greenfoot.mouseDragEnded(Object obj)
 * - Greenfoot.mouseMoved(Object obj)
 * - Greenfoot.mousePressed(Object obj)
 * <p>
 * All these methods need to be changed to fullscreen.FullScreenWindow.methodname to make the fullscreen mode work.
 * These changes can be done by my Greenfoot Full Screen Rewriter scenario which does all the changes with only a fiew mouse clicks.
 *
 * @author Gevater_Tod4711
 * @version 1.1
 */
public class FullScreenWindow extends JFrame
{
    
    static int FRAME_WIDTH;
    static int FRAME_HEIGHT;

    public static int getFrameWidth()
    {
        return FRAME_WIDTH;
    }

    public static int getFrameHeight()
    {
        return FRAME_HEIGHT;
    }
    
    static int MOUSE_OFFSET_X;
    static int MOUSE_OFFSET_Y;
    static int IMAGE_WIDTH;
    static int IMAGE_HEIGHT;

    private static boolean cursorVisibility;

    private static int FPS = 60;
    private static int drawingRate = 1000 / FPS;

    private static FullScreenWindow instance;
    
    private int mouseClickCounter = 0;
    
    private static boolean isMouseClicked;
    private static boolean isMouseDragged;
    private static boolean isMouseDragEnded;
    private static boolean isMousePressed;
    
    private static FullScreenWorld displayedWorld;
    
    private static List<FullScreenKeyInfo> keyInfo;
    
    private static MouseEvent lastMouseEvent;
    
    private static FullScreenMouseInfo lastMouseInfo;
    private static FullScreenMouseInfo mouseInfo;
    
    private static FullScreenBufferPanel panel;
    
    private static Thread windowControllThread;

    static
    {
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        FRAME_WIDTH = (int)screenSize.getWidth();
        FRAME_HEIGHT = (int)screenSize.getHeight();
        keyInfo = new java.util.ArrayList<>();
    }

    /**
     * Create a new fullscreen.FullScreenWindow with reference to the calling World object.
     */
    FullScreenWindow()
    {
        instance = this;
        Image image= null;
        try
        {
            image = ImageIO.read(getClass().getResource("/images/Icon.png"));
            setIconImage(image);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        //创建图片对象

        this.setIconImage(image);

        setUndecorated(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
        addKeyListener(new GreenfootKeyListener(this));
        addMouseListener(new GreenfootMouseListener(this));
        addMouseMotionListener(new GreenfootMouseListener(this));
        panel = new FullScreenBufferPanel();
        panel.setBorder(new EmptyBorder(0, 0, 0, 0));
        panel.setLayout(null);
        setContentPane(panel);
        if(getGraphicsConfiguration().getDevice().isFullScreenSupported())
        {
            getGraphicsConfiguration().getDevice().setFullScreenWindow(this);
        }
        if(!GreenfootToolkit.getToolkit().playingOnline())
        {
            for(Window window : getWindows())
            {
                if(!(window instanceof greenfoot.gui.GreenfootFrame) && !window.equals(this))
                {
                    window.dispose();
                }
            }
        }
        displayWorld();
        cursor = panel.getCursor();
        Greenfoot.start();
    }
    
    /**
     * Check whether a given key is currently pressed down.
     *
     * @param keyName The name of the key to check.
     * @return Returns true if the key is currently pressed down.
     */
    public static boolean isKeyDown(String keyName)
    {
        if(displayedWorld != null)
        {
            if(keyInfo != null && !keyInfo.isEmpty())
            {
                try
                {
                    for(FullScreenKeyInfo info : keyInfo)
                    {
                        if(info.isKeyDown(keyName))
                        {
                            return true;
                        }
                    }
                }
                catch(java.util.ConcurrentModificationException cme)
                {
                    //cme.printStackTrace();
                    return false;
                }
                catch(java.util.NoSuchElementException nsee)
                {
                    //nsee.printStackTrace();
                    return false;
                }
                catch(NullPointerException npe)
                {
                    //npe.printStackTrace();
                    return false;
                }
            }
            return false;
        }
        else
        {
            return Greenfoot.isKeyDown(keyName);
        }
    }

    /**
     * Get the most recently pressed key, since the last time this method was called.
     * If no key was pressed since this method was last called, it will return null.
     * If more than one key was pressed, this returns only the most recently pressed key.
     *
     * @return The name of the most recently pressed key.
     */
    public static String getKey()
    {
        if(displayedWorld != null)
        {
            if(keyInfo != null && !keyInfo.isEmpty())
            {
                String key = keyInfo.get(keyInfo.size() - 1).getKey();
                keyInfo.remove(keyInfo.size() - 1);
                return key;
            }
            return null;
        }
        else
        {
            return Greenfoot.getKey();
        }
    }
    
    /**
     * Return a fullscreen.FullScreenMouseInfo object with information about the mouse state.
     *
     * @return The info about the current state of the mouse, or null if the mouse cursor is outside the world boundary (unless being dragged).
     */
    public static FullScreenMouseInfo getMouseInfo()
    {
        return mouseInfo;
    }
    
    /**
     * Get the reference to the world that is currently displayed.
     *
     * @return The reference to the world that is displayed.
     */
    public static FullScreenWorld getDisplayedWorld()
    {
        return displayedWorld;
    }

    public static void setDisplayedWorld(@NotNull FullScreenWorld displayedWorld)
    {
        FullScreenWindow.displayedWorld = displayedWorld;
        double scaleFactor = Math.min((double)FRAME_WIDTH / displayedWorld.WORLD_WIDTH, (double)FRAME_HEIGHT / displayedWorld.WORLD_HEIGHT);
        MOUSE_OFFSET_X = (int)((FRAME_WIDTH - (scaleFactor * displayedWorld.WORLD_WIDTH)) / 2);
        MOUSE_OFFSET_Y = (int)((FRAME_HEIGHT - (scaleFactor * displayedWorld.WORLD_HEIGHT)) / 2);
        IMAGE_WIDTH = (int)(scaleFactor * displayedWorld.WORLD_WIDTH);
        IMAGE_HEIGHT = (int)(scaleFactor * displayedWorld.WORLD_HEIGHT);
        Greenfoot.setWorld(displayedWorld);
    }

    private static Cursor cursor;

    /**
     * Set the mouse cursor for this frame.
     *
     * @param image       The image of the new mouse cursor.
     * @param cursorPoint The click point of the new cursor.
     */
    public static void setMouseCursor(BufferedImage image, Point cursorPoint)
    {
        cursor = Toolkit.getDefaultToolkit().createCustomCursor(image, cursorPoint, "cursor");
        panel.setMouseCursor(cursor);
    }

    /**
     * Set the mouse cursor for this frame.
     *
     * @param cursor The new cursor.
     */
    public static void setMouseCursor(Cursor cursor)
    {
        FullScreenWindow.cursor = cursor;
        panel.setCursor(cursor);
    }

    /**
     * Tell the fullscreenwindow to create a new fullscreen.FullScreenMouseInfo object.
     * This object is stored for two acts to check whether the mouse is moved, clicked, ...
     */
    void setMouseInfo()
    {
        lastMouseInfo = mouseInfo;
        if(lastMouseEvent != null)
        {
            mouseInfo = new FullScreenMouseInfo(lastMouseEvent, this);
        }
        else
        {
            mouseInfo = null;
        }
        if(isMouseClicked)
        {
            mouseClickCounter++;
            if(mouseClickCounter >= 2)
            {
                isMouseClicked = false;
            }
        }
    }
    
    /**
     * Set the last occouring mouse event.
     *
     * @param mouseEvent The latest occouring MouseEvent object passed on by the mouse listener.
     */
    void setLastMouseEvent(MouseEvent mouseEvent)
    {
        FullScreenWindow.lastMouseEvent = mouseEvent;
    }
    
    /**
     * Set the currently displayed image.
     *
     * @param img The next displayed image.
     */
    private void setImage(BufferedImage img)
    {
        panel.setImage(img);
        panel.repaint();
    }
    
    /**
     * Add a new fullscreen.FullScreenKeyInfo object to the key info queue when a key was pressed.
     */
    void addKeyEvent(FullScreenKeyInfo keyInfo)
    {
        FullScreenWindow.keyInfo.add(keyInfo);
    }

    /**
     * Delete a fullscreen.FullScreenKeyInfo object from the key info queue when the key was released.
     */
    void deleteKeyEvent(FullScreenKeyInfo keyInfo)
    {
        String deletingKey = keyInfo.getKey();
        for(int i = 0; i < FullScreenWindow.keyInfo.size(); i++)
        {
            if(FullScreenWindow.keyInfo.get(i).getKey().equals(deletingKey))
            {
                FullScreenWindow.keyInfo.remove(i);
                i--;
            }
        }
    }

    /**
     * Clear the whole key info queue.
     */
    void deleteAllKeyEvents()
    {
        keyInfo = new java.util.ArrayList<>();
    }
    
    /**
     * True if the mouse has been clicked (pressed and released) on the given object.
     * If the parameter is an Actor the method will only return true if the mouse has been clicked on the given actor.
     * If there are several actors at the same place, only the top most actor will receive the click.
     * If the parameter is a World then true will be returned if the mouse was clicked on the world background.
     * If the parameter is null, then true will be returned for any click, independent of the target clicked on.
     *
     * @param obj Typically one of Actor, World or null.
     * @return Returns true if the mouse has been clicked as explained above.
     */
    public static boolean mouseClicked(java.lang.Object obj)
    {
        if(displayedWorld != null)
        {
            if(isMouseClicked)
            {
                if(obj != null)
                {
                    if((obj instanceof Actor && displayedWorld.getObjectsAt(mouseInfo.getX(), mouseInfo.getY(), obj.getClass()).contains(obj)) || (obj instanceof World && obj.equals(displayedWorld)))
                    {
                        return true;
                    }
                    return false;
                }
                else
                {
                    return true;
                }
            }
            return false;
        }
        else
        {
            return Greenfoot.mouseClicked(obj);
        }
    }

    /**
     * Set the current mouseClicked state.
     * This method is called from the fullscreen.GreenfootKeyListener object when the mouse has been clicked.
     *
     * @param isMouseClicked The new mouse state.
     */
    void setMouseClicked(boolean isMouseClicked)
    {
        FullScreenWindow.isMouseClicked = isMouseClicked;
        mouseClickCounter = 0;
    }
    
    /**
     * True if the mouse is currently being dragged on the given object.
     * The mouse is considered to be dragged on an object if the drag started on that object - even if the mouse has since been moved outside of that object.
     * If the parameter is an Actor the method will only return true if the drag started on the given actor.
     * If there are several actors at the same place, only the top most actor will receive the drag.
     * If the parameter is a World then true will be returned if the drag action was started on the world background.
     * If the parameter is null, then true will be returned for any drag action, independent of the target clicked on.
     *
     * @param obj Typically one of Actor, World or null.
     * @return Returns true if the mouse has been dragged as explained above.
     */
    public static boolean mouseDragged(java.lang.Object obj)
    {
        if(displayedWorld != null)
        {
            if(isMouseDragged)
            {
                if(obj != null)
                {
                    if((obj instanceof Actor && displayedWorld.getObjectsAt(mouseInfo.getX(), mouseInfo.getY(), obj.getClass()).contains(obj)) || (obj instanceof World && obj.equals(displayedWorld)))
                    {
                        return true;
                    }
                    return false;
                }
                return true;
            }
            return false;
        }
        else
        {
            return Greenfoot.mouseDragged(obj);
        }
    }

    /**
     * Set the current mouseDragged state.
     * This method is called from the fullscreen.GreenfootKeyListener object when the mouse has been dragged.
     *
     * @param isMouseDragged The new mouse state.
     */
    void setMouseDragged(boolean isMouseDragged)
    {
        FullScreenWindow.isMouseDragged = isMouseDragged;
    }
    
    /**
     * True if a mouse drag has ended.
     * This happens when the mouse has been dragged and the mouse button released.
     * If the parameter is an Actor the method will only return true if the drag started on the given actor.
     * If there are several actors at the same place, only the top most actor will receive the drag.
     * If the parameter is a World then true will be returned if the drag action was started on the world background.
     * If the parameter is null, then true will be returned for any drag action, independent of the target clicked on.
     *
     * @param obj Typically one of Actor, World or null.
     * @return Returns true if the mouse has been dragged as explained above.
     */
    public static boolean mouseDragEnded(java.lang.Object obj)
    {
        if(displayedWorld != null)
        {
            if(isMouseDragEnded)
            {
                if(obj != null)
                {
                    if((obj instanceof Actor && displayedWorld.getObjectsAt(mouseInfo.getX(), mouseInfo.getY(), obj.getClass()).contains(obj)) || (obj instanceof World && obj.equals(displayedWorld)))
                    {
                        return true;
                    }
                    return false;
                }
                return true;
            }
            return false;
        }
        else
        {
            return Greenfoot.mouseDragEnded(obj);
        }
    }

    /**
     * Set the current mouseDragEnded state.
     * This method is called from the fullscreen.GreenfootKeyListener object when the mouse dragg has ended.
     *
     * @param isMouseDragEnded The new mouse state.
     */
    void setMouseDragEnded(boolean isMouseDragEnded)
    {
        FullScreenWindow.isMouseDragEnded = isMouseDragEnded;
    }
    
    /**
     * True if the mouse has been moved on the given object.
     * The mouse is considered to be moved on an object if the mouse pointer is above that object.
     * If the parameter is an Actor the method will only return true if the move is on the given actor.
     * If there are several actors at the same place, only the top most actor will receive the move.
     * If the parameter is a World then true will be returned if the move was on the world background.
     * If the parameter is null, then true will be returned for any move, independent of the target under the move location.
     *
     * @param obj Typically one of Actor, World or null.
     * @return True if the mouse has been moved as explained above.
     */
    public static boolean mouseMoved(java.lang.Object obj)
    {
        if(displayedWorld != null)
        {
            if(lastMouseInfo != null && mouseInfo != null && !lastMouseInfo.getMousePoint().equals(mouseInfo.getMousePoint()))
            {
                if(obj != null)
                {
                    if((obj instanceof Actor && displayedWorld.getObjectsAt(mouseInfo.getX(), mouseInfo.getY(), obj.getClass()).contains(obj)) || (obj instanceof World && obj.equals(displayedWorld)))
                    {
                        return true;
                    }
                    return false;
                }
                return true;
            }
            return false;
        }
        else
        {
            return Greenfoot.mouseMoved(obj);
        }
    }
    
    /**
     * True if the mouse has been pressed (changed from a non-pressed state to being pressed) on the given object.
     * If the parameter is an Actor the method will only return true if the mouse has been pressed on the given actor.
     * If there are several actors at the same place, only the top most actor will receive the press.
     * If the parameter is a World then true will be returned if the mouse was pressed on the world background.
     * If the parameter is null, then true will be returned for any mouse press, independent of the target pressed on.
     *
     * @param obj Typically one of Actor, World or null.
     * @return True if the mouse has been pressed as explained above.
     */
    public static boolean mousePressed(java.lang.Object obj)
    {
        if(displayedWorld != null)
        {
            if(isMousePressed)
            {
                if(obj != null)
                {
                    if((obj instanceof Actor && displayedWorld.getObjectsAt(mouseInfo.getX(), mouseInfo.getY(), obj.getClass()).contains(obj)) || (obj instanceof World && obj.equals(displayedWorld)))
                    {
                        return true;
                    }
                    return false;
                }
                return true;
            }
            return false;
        }
        else
        {
            return Greenfoot.mousePressed(obj);
        }
    }

    /**
     * Set the current mousePressed state.
     * This method is called from the fullscreen.GreenfootKeyListener object when the mouse has been pressed.
     *
     * @param isMousePressed The new mouse state.
     */
    void setMousePressed(boolean isMousePressed)
    {
        FullScreenWindow.isMousePressed = isMousePressed;
    }


    /**
     * Create a WorldDisplayThread and start it to draw the images on the fullscreenwindow.
     */
    private void displayWorld()
    {
        class WorldDisplayer implements Runnable
        {

            public void run()
            {
                while(true)
                {
                    drawWorldImage();
                    try
                    {
                        Thread.currentThread().sleep(FullScreenWindow.drawingRate);
                    }
                    catch(InterruptedException ie)
                    {
                        //ie.printStackTrace();
                    }
                }
            }

            /**
             * Resize the world image to a new size (screensize).
             *
             * @param originalImage The image that is resized.
             * @param width         The new width of the resized image.
             * @param height        The new height of the resized image.
             * @param type          The imagetype of the resizing image.
             * @return The given originalImage resized to the given width and height.
             */
            private BufferedImage resizeImage(BufferedImage originalImage, int width, int height, int type)
            {
                if(originalImage.getWidth() == width && originalImage.getHeight() == height)
                {
                    return originalImage;
                }
                System.out.println("Warning! Image resizing. Current world: " + getDisplayedWorld());
                BufferedImage resizedImage = new BufferedImage(width, height, type);
                Graphics2D g = resizedImage.createGraphics();
                g.drawImage(originalImage, 0, 0, width, height, null);
                g.dispose();
                return resizedImage;
            }

            private void drawWorldImage()
            {
                try
                {
                    BufferedImage bgImage = WorldHandler.getInstance().getSnapShot();
                    if(bgImage != null)
                    {
                        int imageType = bgImage.getType();
                        instance.setImage(resizeImage(bgImage, FullScreenWindow.IMAGE_WIDTH, FullScreenWindow.IMAGE_HEIGHT, imageType == 0 ? BufferedImage.TYPE_INT_ARGB : imageType));
                    }
                }
                catch(NullPointerException npe)
                {
                    //npe.printStackTrace();
                }
            }
        }
        Thread drawingThread = new Thread(new WorldDisplayer(), "World Display Thread");
        drawingThread.start();
    }

    /**
     * Check whether the cursor is disappearing.
     *
     * @return Return whether the cursor is disappearing or not.
     */
    public static boolean getCuesorVisibility()
    {
        return cursorVisibility;
    }

    private static final Cursor emptyCursor = Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(1, 1, 2), new Point(0, 0), "cursor");

    /**
     * Set the visibility of the cursor.
     */
    public static void setCursorVisibility(boolean visible)
    {
        cursorVisibility = visible;
        if(visible)
        {
            panel.setCursor(cursor);
        }
        else
        {
            panel.setMouseCursor(emptyCursor);
        }
    }

    /**
     * Get the current used FPS rate.
     *
     * @return The current FPS rate.
     */
    public static int getFPS()
    {
        return FPS;
    }

    /**
     * Set the FPS rate of the fullscreenwindow
     *
     * @param fps The new FPS rate.
     * @throws IllegalArgumentException An IllegalArgumentException is thrown if the given FPS number is less or equal 0.
     */
    public static void setFPS(int fps) throws IllegalArgumentException
    {
        if(fps <= 0)
        {
            throw new IllegalArgumentException("fps(" + fps + ") must be greater than 0 (and should be less than 75).");
        }
        FPS = fps;
        drawingRate = 1000 / FPS;
    }
}