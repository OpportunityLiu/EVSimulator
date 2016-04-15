package ev.screens;

import loon.LTexture;
import loon.LTransition;
import loon.Screen;
import loon.canvas.LColor;
import loon.event.GameTouch;
import loon.opengl.GLEx;
import loon.utils.timer.LTimerContext;
import java.util.ArrayList;

/**
 * Created by liuzh on 2016/4/15.
 */
public class SplashScreen extends Screen
{
    private ArrayList<LTexture> splashes = new ArrayList<>();

    private int current = -1;
    private int counter = -1;

    @Override
    public void draw(GLEx g)
    {
        if(counter < 0)
            current++;
        if(current >= splashes.size())
        {
            return;
        }
        int alpha;
        counter++;
        if(counter <= 100)
            alpha = (int)(counter * 2.55);
        else if(counter < 200)
            alpha = 255;
        else
            alpha = (int)((300 - counter) * 2.55);
        g.draw(splashes.get(current), 0, 0, getWidth(), getHeight(), new LColor(255, 255, 255, alpha));
        if(counter == 300)
            counter = -1;
    }

    /**
     * 初始化时加载的数据
     */
    @Override
    public void onLoad()
    {
        splashes.add(LTexture.createTexture("images/Splashes/1.png"));
        splashes.add(LTexture.createTexture("images/Splashes/2.png"));
    }

    @Override
    public void alter(LTimerContext timer)
    {

    }

    @Override
    public void resize(int width, int height)
    {

    }

    @Override
    public void touchDown(GameTouch e)
    {

    }

    @Override
    public void touchUp(GameTouch e)
    {

    }

    @Override
    public void touchMove(GameTouch e)
    {

    }

    @Override
    public void touchDrag(GameTouch e)
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void pause()
    {

    }

    /**
     * 释放函数内资源
     */
    @Override
    public void close()
    {

    }

    @Override
    public LTransition onTransition()
    {
        return LTransition.newEmpty();
    }
}
