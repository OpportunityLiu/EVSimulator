package controls.loader;

import controls.ScenarioHelper;
import greenfoot.World;
import greenfoot.gui.WorldCanvas;

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
class FullScreenBufferPanel extends WorldCanvas
{
    private BufferedImage contentImage;
    
    /**
     * Create a new fullscreen.FullScreenBufferPanel.
     */
    FullScreenBufferPanel(World world)
    {
        super(world);
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
        repaint();
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
            redrawFlag = false;
            g.setColor(Color.black);
            g.fillRect(0, 0, ScenarioHelper.getFrameWidth(), ScenarioHelper.getFrameHeight());
        }
        if(contentImage != null)
        {
            //g.drawImage(contentImage, (ScenarioHelper.getFrameWidth() - contentImage.getWidth()) / 2, (ScenarioHelper.getFrameHeight() - contentImage.getHeight()) / 2, null);
            g.drawImage(contentImage, 0, 0, null);
        }
    }

    private boolean redrawFlag = true;

    void notifyRedraw()
    {
        redrawFlag = true;
    }
}