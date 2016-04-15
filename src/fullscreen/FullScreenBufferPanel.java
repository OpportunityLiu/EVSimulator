package fullscreen;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Point;
import java.awt.Cursor;
import java.awt.Toolkit;
import javax.swing.JPanel;

/**
 * This panel is used to draw the image of the world onto it.
 *
 * @author Gevater_Tod4711
 * @version 1.1
 */
class FullScreenBufferPanel extends JPanel
{
    private BufferedImage contentImage;
    
    /**
     * Create a new fullscreen.FullScreenBufferPanel.
     */
    FullScreenBufferPanel()
    {
        setDoubleBuffered(true);
    }
    
    /**
     * Set the next drawn image of the panel.
     *
     * @param img The next drawn image of the panel.
     */
    void setImage(BufferedImage img)
    {
        this.contentImage = img;
    }

    /**
     * Draw the worlds image onto the fullscreen panel.
     *
     * @param g The Graphics object to draw the image.
     */
    @Override
    public void paintComponent(Graphics g)
    {
        if(redrawFlag)
        {
            redrawFlag=false;
            g.setColor(Color.black);
            g.fillRect(0, 0, FullScreenWindow.FRAME_WIDTH, FullScreenWindow.FRAME_HEIGHT);
        }
        if(contentImage != null)
        {
            g.drawImage(contentImage, (FullScreenWindow.FRAME_WIDTH - contentImage.getWidth()) / 2, (FullScreenWindow.FRAME_HEIGHT - contentImage.getHeight()) / 2, null);
        }
    }

    private boolean redrawFlag=true;

    void notifyRedraw()
    {
        redrawFlag=true;
    }
    
    /**
     * Set the mouse cursor for this panel.
     *
     * @param image       The image of the new mouse cursor.
     * @param cursorPoint The click point of the new cursor.
     */
    void setMouseCursor(BufferedImage image, Point cursorPoint)
    {
        setMouseCursor(Toolkit.getDefaultToolkit().createCustomCursor(image, cursorPoint, "cursor"));
    }

    /**
     * Set the mouse cursor for this panel.
     *
     * @param cursor The new cursor.
     */
    void setMouseCursor(Cursor cursor)
    {
        setCursor(cursor);
    }
}