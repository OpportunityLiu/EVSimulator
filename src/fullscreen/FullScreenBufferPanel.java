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
    FullScreenBufferPanel() {
        setDoubleBuffered(true);
    }
    
    /**
     * Set the next drawn image of the panel.
     * 
     * @param img
     *      The next drawn image of the panel.
     */
    void setImage(BufferedImage img) {
        this.contentImage = img;
    }

    public BufferedImage getImage()
    {
        return contentImage;
    }

    /**
     * Draw the worlds image onto the fullscreen panel.
     * 
     * @param g
     *      The Graphics object to draw the image.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (contentImage != null) {
            Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            if (contentImage.getWidth() < screenSize.getWidth() || contentImage.getHeight() < screenSize.getHeight()) {
                BufferedImage bg = new BufferedImage((int) screenSize.getWidth(), (int) screenSize.getHeight(), BufferedImage.TYPE_INT_ARGB);
                bg.getGraphics().clearRect(0, 0, (int) screenSize.getWidth(), (int) screenSize.getHeight());
                g.drawImage(bg, 0, 0, null);
            }
            g.drawImage(contentImage, (int) (screenSize.getWidth()/2 - contentImage.getWidth()/2), (int) (screenSize.getHeight()/2 - contentImage.getHeight()/2), null);
        }
    }
    
    /**
     * Set the mouse cursor for this panel.
     * 
     * @param image
     *      The image of the new mouse cursor.
     * 
     * @param cursorPoint
     *      The click point of the new cursor.
     */
    void setMouseCursor(BufferedImage image, Point cursorPoint) {
        setMouseCursor(Toolkit.getDefaultToolkit().createCustomCursor(image, cursorPoint, "cursor"));
    }
    /**
     * Set the mouse cursor for this panel.
     * 
     * @param cursor
     *      The new cursor.
     */
    void setMouseCursor(Cursor cursor) {
        setCursor(cursor);
    }
}