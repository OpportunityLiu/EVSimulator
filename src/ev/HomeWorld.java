/**
 * Write a description of class ev.HomeWorld here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
package ev;

import controls.Button;
import controls.MouseListener;
import fullscreen.*;

import java.awt.*;

class HomeWorld extends FullScreenWorld
{
    /**
     * Constructor for objects of class ev.HomeWorld
     */
    HomeWorld()
    {
        super(1.5, 1, false);
        setBackground("a.bmp");
        HomeWorld x=this;
        getBackground().setColor(Color.black);
        x.getBackground().clear();
        FullScreenWindow.setCursorVisibility(true);
        addObject(new Button().setListener(new MouseListener()
        {
            private int l=0;
            @Override
            public void mouseClicked(FullScreenMouseInfo e)
            {
                x.getBackground().drawString("Clicked",500,l+=20);
            }

            @Override
            public void mouseDragged(FullScreenMouseInfo e)
            {
                x.getBackground().drawString("Dragged",500,l+=20);
            }

            @Override
            public void mouseDragEnded(FullScreenMouseInfo e)
            {
                x.getBackground().drawString("DragEnded",500,l+=20);
            }

            @Override
            public void mouseMoved(FullScreenMouseInfo e)
            {
                x.getBackground().drawString("Moved",500,l+=20);
            }

            @Override
            public void mousePressed(FullScreenMouseInfo e)
            {
                x.getBackground().drawString("Pressed",500,l+=20);
            }
        }),100,100);
    }

    @Override
    public void started()
    {
    }
    
    @Override
    public void run()
    {
        i++;
        //if(i>500)
            //FullScreenWindow.setDisplayedWorld(new SplashWorld());
    }

    private  int i;
}
