package controls.loader;

/**
 * Created by liuzh on 2016/4/15.
 * My world handler delegate.
 */

import greenfoot.Actor;
import greenfoot.World;
import greenfoot.core.WorldHandler;
import greenfoot.gui.input.InputManager;
import greenfoot.platforms.WorldHandlerDelegate;

import java.awt.event.MouseEvent;

class WorldHandlerDelegateFullScreen implements WorldHandlerDelegate
{
    private WorldHandler worldHandler;
    private ScenarioViewer viewer;

    WorldHandlerDelegateFullScreen(ScenarioViewer viewer)
    {
        this.viewer = viewer;
    }

    public boolean maybeShowPopup(MouseEvent e)
    {
        return false;
    }

    public void mouseClicked(MouseEvent e)
    {
    }

    public void mouseMoved(MouseEvent e)
    {
    }

    public void setWorld(World oldWorld, World newWorld)
    {
    }

    public void setWorldHandler(WorldHandler handler)
    {
        this.worldHandler = handler;
    }

    public void instantiateNewWorld()
    {
        WorldHandler.getInstance().clearWorldSet();
        World newWorld = this.viewer.instantiateNewWorld();
        if(!WorldHandler.getInstance().checkWorldSet())
        {
            WorldHandler.getInstance().setWorld(newWorld);
        }

    }

    public InputManager getInputManager()
    {
        InputManager inputManager = new InputManager();
        inputManager.setDragListeners(null, null, null);
        inputManager.setIdleListeners(null, null, null);
        inputManager.setMoveListeners(null, null, null);
        return inputManager;
    }

    public void discardWorld(World world)
    {
    }

    public void addActor(Actor actor, int x, int y)
    {
    }

    public void actorDragged(Actor actor, int xCell, int yCell)
    {
    }

    public void objectAddedToWorld(Actor actor)
    {
    }

    public String ask(String prompt)
    {
        return this.viewer.ask(prompt);
    }
}
