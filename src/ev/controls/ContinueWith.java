package ev.controls;

import loon.action.ActionBind;
import loon.action.ActionControl;
import loon.action.ActionEvent;
import loon.action.ActionListener;

/**
 * Created by liuzh on 2016/4/18.
 * Continue to play next action
 */
public class ContinueWith implements ActionListener
{
    private final ActionEvent next;
    private boolean finished;

    public ContinueWith(ActionEvent next)
    {
        this.next = next;
    }

    @Override
    public void start(ActionBind o)
    {

    }

    @Override
    public void process(ActionBind o)
    {

    }

    @Override
    public void stop(ActionBind o)
    {
        if(finished)
            return;
        ActionControl.get().addAction(next, o);
        finished = true;
    }
}
