package controls.loader;

import controls.ScenarioHelper;
import greenfoot.Greenfoot;
import greenfoot.core.WorldHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


/**
 * Created by liuzh on 2016/4/15.
 * Custom panel for full screen.
 */
public class FullScreenFrame extends JFrame
{

    private static int drawingRate=30;
    private FullScreenBufferPanel panel;

    public FullScreenFrame(String title)
    {
        super(title);
        ScenarioHelper.init(this);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setBackground(Color.black);
        this.setUndecorated(true);
        this.setLayout(null);
        this.setBounds(0, 0, ScenarioHelper.getFrameWidth(), ScenarioHelper.getFrameHeight());
        try
        {
            Image cursorImage = ImageIO.read(getClass().getResource("/images/Cursors/Normal.png"));
            Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(2, 2), "cursor");
            this.setCursor(cursor);
            Image icon = ImageIO.read(getClass().getResource("/images/Icon.png"));
            this.setIconImage(icon);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void showFullScreen()
    {
        if(isVisible())
            return;
        setVisible(true);
        GraphicsDevice dev = getGraphicsConfiguration().getDevice();
        if(dev.isFullScreenSupported())
        {
            dev.setFullScreenWindow(this);
        }
        panel= (FullScreenBufferPanel)getContentPane();
        displayWorld();
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
                        Thread.currentThread();
                        Thread.sleep(FullScreenFrame.drawingRate);
                    }
                    catch(InterruptedException ie)
                    {
                        //ie.printStackTrace();
                    }
                }
            }

            private void drawWorldImage()
            {
                try
                {
                    BufferedImage bgImage = WorldHandler.getInstance().getSnapShot();
                    if(bgImage != null)
                    {
                        panel.setImage(bgImage);
                    }
                    else {
                        panel.notifyRedraw();
                    }
                }
                catch(NullPointerException npe)
                {
                    npe.printStackTrace();
                }
            }
        }
        Thread drawingThread = new Thread(new WorldDisplayer(), "World Display Thread");
        drawingThread.start();
    }
}
