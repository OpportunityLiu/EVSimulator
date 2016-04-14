package fullscreen;

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.core.WorldHandler;
import org.jetbrains.annotations.Contract;

import java.awt.image.BufferedImage;
import java.awt.Cursor;
import java.awt.Graphics2D;

/**
 * This abstract world method must be used as the superclass of all fullscreen worldclasses.
 * To create a fullscreen world you have to use the constructor fullscreen.FullScreenWorld(int, int, int, boolean, boolean). All others will not create a fullscreen world.
 * <p>
 * The act method of this class is declared final because some methods need to be called every act cycle to make the fullscreenwindow work.
 * So you can't use a act method in any subclass of this world but instead of the act method the abstract void run is called every act cycle so you can use public void run() as a kind of new act method.
 * <p>
 * The FPS rate of the fullscreen window is set to 35 by default. If you want to change it you can use the method setFPS(int) but the higher the FPS rate is the more memory and CPU it'll take.
 * So you shouldn't have a too high FPS rate.
 *
 * @author Gevater_Tod4711
 * @version 1.1
 */
public abstract class FullScreenWorld extends World
{
    private static FullScreenWindow fullScreenWindow;
    
    int WORLD_WIDTH;
    int WORLD_HEIGHT;

    public FullScreenWorld()
    {
        this(FullScreenWindow.FRAME_WIDTH, FullScreenWindow.FRAME_HEIGHT, 1, true);
    }
    
    /**
     * Create a new world with a given width and hight.
     *
     * @param width  The width of the created world.
     * @param height The height of the created world.
     */
    public FullScreenWorld(int width, int height)
    {
        this(width, height, 1, true);
    }

    /**
     * Create a new world with a given width and hight.
     *
     * @param width    The width of the created world.
     * @param height   The height of the created world.
     * @param cellSize The cell size of the created world.
     */
    public FullScreenWorld(int width, int height, int cellSize)
    {
        this(width, height, cellSize, true);
    }

    /**
     * Create a new world with a given width and hight.
     *
     * @param ratio    The screen ratio of the created world, width / height.
     * @param cellSize The cell size of the created world.
     * @param bounded  Decide whether the actors should be able to move out of the world.
     */
    public FullScreenWorld(double ratio, int cellSize, boolean bounded)
    {
        this(getWidth(ratio), getHeight(ratio), cellSize, bounded);
    }

    private static double FRAME_RATIO = (double)FullScreenWindow.FRAME_WIDTH / FullScreenWindow.FRAME_HEIGHT;

    @Contract(pure = true)
    private static int getHeight(double ratio)
    {
        if(ratio<=FRAME_RATIO)
            return FullScreenWindow.FRAME_HEIGHT;
        else
            return (int)(FullScreenWindow.FRAME_WIDTH/ratio);
    }

    @Contract(pure = true)
    private static int getWidth(double ratio)
    {
        if(ratio>FRAME_RATIO)
            return FullScreenWindow.FRAME_WIDTH;
        else
            return (int)(FullScreenWindow.FRAME_HEIGHT*ratio);
    }

    /**
     * Create a new world with a given width and hight.
     *
     * @param width    The width of the created world.
     * @param height   The height of the created world.
     * @param cellSize The cell size of the created world.
     * @param bounded  Decide whether the actors should be able to move out of the world.
     */
    public FullScreenWorld(int width, int height, int cellSize, boolean bounded)
    {
        super(width, height, cellSize, bounded);
        WORLD_WIDTH = width;
        WORLD_HEIGHT = height;
        if(fullScreenWindow == null)
        {
            createFullScreenWindow();
        }
    }
    
    /**
     * The act mehtod is declared final to make shure that the neccessary fullscreen methods are executed every act.
     * Instead of an act method you'll have to use the run method in subclasses of this class.
     */
    public final void act()
    {
        fullScreenWindow.setMouseInfo();
        run();
    }
    
    /**
     * The run mehtod is called every act cycle and works like a new act method because the act method in this class is declared final.
     */
    public abstract void run();
    
    /**
     * Create the fullscreenwindow.
     */
    private void createFullScreenWindow()
    {
        FullScreenWindow.setDisplayedWorld(this);
        fullScreenWindow = new FullScreenWindow();
    }

    
    /**
     * Get the reference to the current used fullscreen.FullScreenWindow object.
     *
     * @return The currend used fullscreen.FullScreenWindow object.
     */
    public final FullScreenWindow getFullScreenWindow()
    {
        return fullScreenWindow;
    }


}