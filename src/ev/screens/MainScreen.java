package ev.screens;

import ev.Resources;
import ev.controls.MButton;
import ev.event.ClickAdapter;
import loon.LSystem;
import loon.LTexture;
import loon.Screen;
import loon.component.LButton;
import loon.component.LComponent;
import loon.component.LPanel;
import loon.event.ClickListener;
import loon.event.GameTouch;
import loon.opengl.GLEx;
import loon.utils.res.Texture;
import loon.utils.res.TextureData;
import loon.utils.timer.LTimerContext;

/**
 * Created by liuzh on 2016/4/16.
 * Main menu of ev sim.
 */
public class MainScreen extends Screen
{
    private LButton buttonStart, buttonExit;
    private LPanel panelMenu;

    @Override
    public void draw(GLEx g)
    {
        //LTexture cpy = Resources.textures("blue_circle");
        // g.draw(cpy,0,0);
    }

    /**
     * 初始化时加载的数据
     */
    @Override
    public void onLoad()
    {
        Texture[] btn = new Texture[]{Resources.textures("blue_button02"), Resources.textures("blue_button04"), Resources.textures("blue_button03")};

        buttonStart = new MButton(btn, "Start", 0, 0, 190, 49);
        buttonStart.setFont(Resources.FontInfo.MENU.font());
        buttonStart.setOffsetTop(2);
        buttonExit = new MButton(btn, "Exit", 0, 70, 190, 49);
        buttonExit.setFont(Resources.FontInfo.MENU.font());
        buttonExit.setOffsetTop(2);
        buttonExit.addClickListener(new ClickAdapter()
        {
            @Override
            public void DoClick(LComponent comp)
            {
                LSystem.exit();
            }
        });
        panelMenu = new LPanel(getHalfWidth() - 95, getHalfHeight(), 190, getHalfHeight());
        panelMenu.add(buttonStart);
        panelMenu.add(buttonExit);
        add(panelMenu);
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
