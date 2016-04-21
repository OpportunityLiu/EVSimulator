package ev.effects;

import ev.screens.GameScreen;
import loon.action.sprite.effect.FadeEffect;
import loon.canvas.LColor;

/**
 * Created by liuzh on 2016/4/21.
 * My FadeEffect.
 */
public abstract class MFadeEffect extends FadeEffect
{
    protected MFadeEffect(int type, LColor c)
    {
        super(type, c);
    }

    protected MFadeEffect(LColor c, int delay, int type, int w, int h)
    {
        super(c, delay, type, w, h);
    }

    @Override
    public void update(long timer)
    {
        if(type == TYPE_FADE_IN)
        {
            currentFrame--;
            if(currentFrame == 0)
            {
                setOpacity(0);
                setStop(true);
            }
        }
        else
        {
            currentFrame++;
            if(currentFrame == time)
            {
                setOpacity(0);
                setStop(true);
            }
        }
    }

    @Override
    public void setStop(boolean finished)
    {
        super.setStop(finished);
        if(finished)
            onFinished();
    }

    public abstract void onFinished();
}
