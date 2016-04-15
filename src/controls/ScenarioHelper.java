package controls;

import com.sun.istack.internal.NotNull;
import controls.loader.FullScreenFrame;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by liuzh on 2016/4/15.
 * Helper class as an addition of
 *
 * @see greenfoot.Greenfoot
 */
public class ScenarioHelper
{
    private static FullScreenFrame rootFrame;
    private static boolean cursorVisible;
    private static Cursor cursor;

    private static final int FRAME_WIDTH;
    private static final int FRAME_HEIGHT;

    public static int getFrameHeight()
    {
        return FRAME_HEIGHT;
    }

    public static int getFrameWidth()
    {
        return FRAME_WIDTH;
    }

    static
    {
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        FRAME_WIDTH = (int)screenSize.getWidth();
        FRAME_HEIGHT = (int)screenSize.getHeight();
    }

    public static void init(@NotNull FullScreenFrame rootFrame)
    {
        ScenarioHelper.rootFrame=rootFrame;
        cursorVisible=true;
    }

    /**
     * Check whether the cursor is visible.
     *
     * @return Return whether the cursor is visible or not.
     */
    public static boolean IsCursorVisible()
    {
        return cursorVisible;
    }

    private static final Cursor emptyCursor = Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(1, 1, 2), new Point(0, 0), "cursor");

    /**
     * Set the visibility of the cursor.
     */
    public static void setCursorVisible(boolean visible)
    {
        if(cursorVisible != visible)
        {
            cursorVisible = visible;
            if(visible)
            {
                rootFrame.setCursor(cursor);
            }
            else
            {
                cursor = rootFrame.getCursor();
                rootFrame.setCursor(emptyCursor);
            }
        }
    }


    /**
     * Set the mouse cursor for the root frame.
     *
     * @param image       The image of the new mouse cursor.
     * @param cursorPoint The click point of the new cursor.
     */
    public static void setCursor(Image image, Point cursorPoint)
    {
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(image, cursorPoint, "cursor"));
    }

    /**
     * Set the mouse cursor for the root frame.
     *
     * @param cursor The new cursor.
     */
    public static void setCursor(Cursor cursor)
    {
        ScenarioHelper.cursor = cursor;
        if(cursorVisible)
            rootFrame.setCursor(cursor);
    }
}
