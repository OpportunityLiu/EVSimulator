package controls.loader;

/**
 * Created by liuzh on 2016/4/15.
 * My viewer.
 */

import bluej.Config;
import bluej.utility.Debug;
import controls.ScenarioHelper;
import greenfoot.World;
import greenfoot.core.ProjectProperties;
import greenfoot.core.Simulation;
import greenfoot.core.WorldHandler;
import greenfoot.export.GreenfootScenarioMain;
import greenfoot.gui.WorldCanvas;
import greenfoot.gui.input.mouse.LocationTracker;
import greenfoot.platforms.standalone.ActorDelegateStandAlone;
import greenfoot.platforms.standalone.GreenfootUtilDelegateStandAlone;
import greenfoot.platforms.standalone.SimulationDelegateStandAlone;
import greenfoot.sound.SoundFactory;
import greenfoot.util.AskHandler;
import greenfoot.util.GreenfootUtil;
import org.jetbrains.annotations.Contract;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.swing.JApplet;
import javax.swing.JRootPane;
import javax.swing.border.EmptyBorder;

public class ScenarioViewer extends JApplet
{
    private static final int EMPTY_BORDER_SIZE = 5;
    private static String scenarioName;
    private boolean isApplet;
    private boolean showControls;
    private ProjectProperties properties;
    private Simulation sim;
    private WorldCanvas canvas;
    private FullScreenFrame rootFrame;
    private Constructor<?> worldConstructor;
    private AskHandler askHandler;

    ScenarioViewer(FullScreenFrame rootFrame)
    {
        this.rootFrame = rootFrame;
        this.isApplet = false;
    }

    @Contract(" -> !null")
    public static Dimension getControlsBorderSize()
    {
        return new Dimension(0, 0);
    }

    @Contract(" -> !null")
    public static Dimension getWorldBorderSize()
    {
        return new Dimension(0, 0);
    }

    private void buildGUI()
    {
        canvas.setBorder(new EmptyBorder(0, 0, 0, 0));
        canvas.setLayout(null);
        rootFrame.setContentPane(canvas);
//        canvas.addComponentListener(new ComponentAdapter()
//        {
//            @Override
//            public void componentResized(ComponentEvent e)
//            {
//                canvas.setLocation((ScenarioHelper.getFrameWidth() - canvas.getWidth()) / 2, (ScenarioHelper.getFrameHeight() - canvas.getHeight()) / 2);
//            }
//        });
//        this.rootFrame.setContentPane(canvas);
//        canvas.setLocation((ScenarioHelper.getFrameWidth() - canvas.getWidth()) / 2, (ScenarioHelper.getFrameHeight() - canvas.getHeight()) / 2);
     }

    public String getParameter(String name)
    {
        return this.isApplet ? super.getParameter(name) : null;
    }

    public void init()
    {
        GreenfootScenarioMain.initProperties();
        boolean storageStandalone = this.getParameter("storage.standalone") != null;
        String storageHost = this.getParameter("storage.server");
        String storagePort = this.getParameter("storage.serverPort");
        String storagePasscode = this.getParameter("storage.passcode");
        String storageScenarioId = this.getParameter("storage.scenarioId");
        String storageUserId = this.getParameter("storage.userId");
        String storageUserName = this.getParameter("storage.userName");
        JRootPane rootPane = this.getRootPane();
        rootPane.putClientProperty("defeatSystemEventQueueCheck", Boolean.TRUE);
        String worldClassName = Config.getPropString("main.class");

        try
        {
            GreenfootUtil.initialise(new GreenfootUtilDelegateStandAlone(storageStandalone, storageHost, storagePort, storagePasscode, storageScenarioId, storageUserId, storageUserName));
            this.properties = new ProjectProperties();
            ActorDelegateStandAlone.setupAsActorDelegate();
            ActorDelegateStandAlone.initProperties(this.properties);
            Simulation.initialize(new SimulationDelegateStandAlone());
            EventQueue.invokeAndWait(this::guiSetup);
            WorldHandler e = WorldHandler.getInstance();
            Class worldClass = Class.forName(worldClassName);
            this.worldConstructor = worldClass.getConstructor();
            World world = this.instantiateNewWorld();
            if(!e.checkWorldSet())
            {
                e.setWorld(world);
            }

            EventQueue.invokeAndWait(this::buildGUI);
        }
        catch(IllegalArgumentException | InvocationTargetException | InterruptedException | ClassNotFoundException | NoSuchMethodException | SecurityException e)
        {
            e.printStackTrace();
        }

    }

    private void guiSetup()
    {
        this.canvas = new FullScreenBufferPanel(null);
        this.canvas.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                ScenarioViewer.this.canvas.requestFocusInWindow();
                ScenarioViewer.this.canvas.requestFocus();
            }
        });
        WorldHandler.initialise(this.canvas, new WorldHandlerDelegateFullScreen(this));
        WorldHandler worldHandler = WorldHandler.getInstance();
        this.sim = Simulation.getInstance();
        this.sim.attachWorldHandler(worldHandler);
        LocationTracker.initialize();
        this.sim.addSimulationListener(SoundFactory.getInstance().getSoundCollection());
        this.sim.addSimulationListener((e) -> {
            if(e.getType() == 0)
            {
                this.canvas.requestFocusInWindow();
                this.canvas.requestFocus();
            }
        });

        try
        {
            int initialSpeed = this.properties.getInt("simulation.speed");
            this.sim.setSpeed(initialSpeed);
        }
        catch(NumberFormatException ex)
        {
            ex.printStackTrace();
        }

    }

    public void start()
    {
    }

    public void stop()
    {
        this.sim.setPaused(true);
    }

    public void destroy()
    {
        this.sim.abort();
    }

    public String getAppletInfo()
    {
        return Config.getString("scenario.viewer.appletInfo") + " " + scenarioName;
    }

    public String[][] getParameterInfo()
    {
        String[][] paramInfo = new String[0][];
        return paramInfo;
    }

    World instantiateNewWorld()
    {
        try
        {
            return (World)this.worldConstructor.newInstance();
        }
        catch(InstantiationException | IllegalAccessException | IllegalArgumentException ex)
        {
            ex.printStackTrace();
        }
        catch(InvocationTargetException ex)
        {
            ex.getCause().printStackTrace();
        }

        return null;
    }

    public World getWorld()
    {
        return WorldHandler.getInstance().getWorld();
    }

    public ReentrantReadWriteLock getWorldLock(World world)
    {
        return WorldHandler.getInstance().getWorldLock();
    }

    public String ask(final String prompt)
    {
        final AtomicReference c = new AtomicReference();

        try
        {
            EventQueue.invokeAndWait(new Runnable()
            {
                public void run()
                {
                    c.set(ScenarioViewer.this.askHandler.ask(prompt, ScenarioViewer.this.canvas.getPreferredSize().width));
                }
            });
        }
        catch(InterruptedException | InvocationTargetException var5)
        {
            Debug.reportError(var5);
        }

        try
        {
            return (String)((Callable)c.get()).call();
        }
        catch(Exception var4)
        {
            Debug.reportError(var4);
            return null;
        }
    }
}
