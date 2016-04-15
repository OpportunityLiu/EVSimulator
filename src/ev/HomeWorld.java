/**
 * Write a description of class ev.HomeWorld here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
package ev;

import controls.Button;
import controls.MouseListener;
import controls.ScenarioHelper;
import greenfoot.*;
import greenfoot.MouseInfo;
import java.awt.*;

class HomeWorld extends World
{
    /**
     * Constructor for objects of class ev.HomeWorld
     */
    HomeWorld()
    {
        super(1280,900,1);
        setBackground("a.bmp");
        getBackground().setColor(Color.white);
        getBackground().fill();
        getBackground().setColor(Color.black);
        ScenarioHelper.setCursorVisible(true);
        addObject(new Button().setListener(new MouseListener()
        {
            private int l=0;
            @Override
            public void mouseClicked(MouseInfo e)
            {
                HomeWorld.this.getBackground().drawString("Clicked",500,l+=20);
            }

            @Override
            public void mouseDragged(MouseInfo e)
            {
                HomeWorld.this.getBackground().drawString("Dragged",500,l+=20);
            }

            @Override
            public void mouseDragEnded(MouseInfo e)
            {
                HomeWorld.this.getBackground().drawString("DragEnded",500,l+=20);
            }

            @Override
            public void mouseMoved(MouseInfo e)
            {
                HomeWorld.this.getBackground().drawString("Moved",500,l+=20);
            }

            @Override
            public void mousePressed(MouseInfo e)
            {
                HomeWorld.this.getBackground().drawString("Pressed",500,l+=20);
            }
        }),100,100);
    }

    @Override
    public void started()
    {
    }
    
    @Override
    public void act()
    {
    }

    private  int i;
}
