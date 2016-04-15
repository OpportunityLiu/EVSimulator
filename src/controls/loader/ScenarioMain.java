package controls.loader;

/**
 * Created by liuzh on 2016/4/15.
 * Main
 */

import bluej.Config;
import greenfoot.Greenfoot;
import greenfoot.util.StandalonePropStringManager;

import java.awt.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class ScenarioMain
{
    public static String scenarioName;
    public static String[] args;

    public ScenarioMain()
    {
    }

    public static void main(String[] args)
    {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        if(args.length != 3 && args.length != 0)
        {
            System.err.println("Wrong number of arguments");
        }

        ScenarioMain.args = args;
        initProperties();
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", scenarioName);
        final ScenarioViewer[] scenarioViewer = new ScenarioViewer[1];
        final FullScreenFrame[] frame = new FullScreenFrame[1];

        try
        {
            EventQueue.invokeAndWait(() -> {
                frame[0] = new FullScreenFrame(ScenarioMain.scenarioName);
                scenarioViewer[0] = new ScenarioViewer(frame[0]);
            });
            scenarioViewer[0].init();
            EventQueue.invokeAndWait(() -> {
                frame[0].showFullScreen();
                if(Config.getPropBoolean("scenario.hideControls", false))
                    Greenfoot.start();
            });
        }
        catch(InvocationTargetException | InterruptedException e)
        {
            e.printStackTrace();
        }

    }

    public static void initProperties()
    {
        if(scenarioName == null)
        {
            Properties p = new Properties();

            try
            {
                ClassLoader e = ScenarioMain.class.getClassLoader();
                Object is = e.getResourceAsStream("standalone.properties");
                if(is == null && (args.length == 3 || args.length == 4))
                {
                    p.put("project.name", args[0]);
                    p.put("main.class", args[1]);
                    p.put("scenario.lock", "true");
                    File f = new File(args[2]);
                    if(f.canRead())
                    {
                        is = new FileInputStream(f);
                    }

                    if(args.length == 4)
                    {
                        p.put("scenario.hideControls", args[3]);
                    }
                    else
                    {
                        p.put("scenario.hideControls", false);
                    }
                }

                if(is != null)
                {
                    p.load((InputStream)is);
                }

                scenarioName = p.getProperty("project.name");
                Config.initializeStandalone(new StandalonePropStringManager(p));
                if(is != null)
                {
                    ((InputStream)is).close();
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }

        }
    }
}
