package controls.loader;

import controls.ScenarioHelper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by liuzh on 2016/4/15.
 * Custom panel for full screen.
 */
public class FullScreenFrame extends JFrame
{

    public FullScreenFrame(String title)
    {
        super(title);
        ScenarioHelper.init(this);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
    }



}
