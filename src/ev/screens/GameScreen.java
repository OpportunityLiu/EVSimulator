package ev.screens;

import loon.Screen;
import loon.canvas.LColor;
import loon.event.GameTouch;
import loon.opengl.GLEx;
import loon.utils.timer.LTimerContext;

/**
 * Created by liuzh on 2016/4/20.
 * Main gaming screen
 */
public class GameScreen extends Screen
{
    @Override
    public void draw(GLEx g)
    {
        g.drawString("Game", 100, 100, LColor.white);
    }

    /**
     * 初始化时加载的数据
     */
    @Override
    public void onLoad()
    {

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
}
