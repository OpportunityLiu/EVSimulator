package ev.event;

import loon.component.LComponent;
import loon.event.ClickListener;

/**
 * Created by liuzh on 2016/4/17.
 * Empty ClickListener
 */
public abstract class ClickAdapter implements ClickListener
{
    @Override
    public void DoClick(LComponent comp)
    {

    }

    @Override
    public void DownClick(LComponent comp, float x, float y)
    {

    }

    @Override
    public void UpClick(LComponent comp, float x, float y)
    {

    }

    @Override
    public void DragClick(LComponent comp, float x, float y)
    {

    }
}
