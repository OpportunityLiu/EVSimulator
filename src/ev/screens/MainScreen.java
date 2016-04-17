package ev.screens;

import ev.Resources;
import loon.LTexture;
import loon.Screen;
import loon.event.GameTouch;
import loon.opengl.GLEx;
import loon.utils.timer.LTimerContext;

/**
 * Created by liuzh on 2016/4/16.
 * Main menu of ev sim.
 */
public class MainScreen extends Screen
{
    @Override
    public void draw(GLEx g)
    {
        LTexture cpy = Resources.textures("blue_circle");
        g.draw(cpy,0,0);
    }

    @Override
    public void onCreate(int width, int height)
    {
        super.onCreate(width, height);
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
