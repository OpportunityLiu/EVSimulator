package ev.controls;

import loon.action.ActionEvent;
import loon.action.DelayTo;

/**
 * Created by liuzh on 2016/4/18.
 * Helper class for actions
 */
public abstract class ActionHelper
{
    public static ActionEvent runAfter(float delay, ActionEvent action)
    {
        DelayTo d = new DelayTo(delay);
        d.setActionListener(new ContinueWith(action));
        return d;
    }
}
