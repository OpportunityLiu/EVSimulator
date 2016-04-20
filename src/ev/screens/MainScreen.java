package ev.screens;

import ev.Resources;
import ev.controls.ActionHelper;
import ev.controls.MButton;
import ev.event.ActionAdapter;
import ev.event.ClickAdapter;
import javafx.animation.FadeTransition;
import loon.LSystem;
import loon.LTexture;
import loon.LTransition;
import loon.Screen;
import loon.action.*;
import loon.action.sprite.*;
import loon.action.sprite.effect.FadeEffect;
import loon.canvas.LColor;
import loon.component.*;
import loon.event.GameTouch;
import loon.opengl.GLEx;
import loon.utils.MathUtils;
import loon.utils.res.Texture;
import loon.utils.timer.LTimerContext;

/**
 * Created by liuzh on 2016/4/16.
 * Main menu of ev sim.
 */
public class MainScreen extends Screen
{
    private LButton buttonStart, buttonExit;
    private LPanel panelMenu, logo;
    private LTexture p1, p2, p3, p4;
    private float scale;
    private LPanel c1, c2, c3, c4;
    private float r1, r2, r3, r4, a;
    
    public MainScreen()
    {
        centerUserDraw();
    }
    
    
    @Override
    public void draw(GLEx g)
    {
        if(!isOnLoadComplete())
            return;
        r1 += elapsedTime / 33.0;
        r2 += elapsedTime / 44.0;
        r3 += elapsedTime / 28.0;
        r4 += elapsedTime / 20.0;
        g.draw(p1, 28.4f, 700.2f, p1.width() / scale, p1.height() / scale, r1);
        g.draw(p2, 75.44f, 672.48f, p2.width() / scale, p2.height() / scale, r2);
        g.draw(p3, 155.68f, 750.56f, p3.width() / scale, p3.height() / scale, r3);
        g.draw(p4, 1077.68f, 446.4f, p4.width() / scale, p4.height() / scale, r4);
        if(a > 1)
        {
            a = -1;
            ActionEvent a1 = new FadeTo(ISprite.TYPE_FADE_OUT, 20f);
            ActionEvent a2 = a1.cpy();
            addAction(ActionHelper.runAfter(0.5f, a1), buttonStart);
            addAction(ActionHelper.runAfter(1f, a2), buttonExit);
            addAction(new FadeTo(ISprite.TYPE_FADE_OUT, 40f), panelMenu);
            addAction(new FadeTo(ISprite.TYPE_FADE_OUT, 10f), logo);
            addAction(new MoveBy(logo.getX(), 300, 10), logo);
        }
        else if(a >= 0)
        {
            a += elapsedTime * 0.001;
            g.setAlpha(1 - a);
            g.fillRect(0, 0, getWidth(), getHeight(), LColor.black);
        }
    }
    
    /**
     * 初始化时加载的数据
     */
    @Override
    public void onLoad()
    {
        logo = new LPanel(0, 200, 1, 1);
        logo.setBackground(Resources.images("home/logo.png"));
        logo.setSize(500, 120);
        logo.setX(getHalfWidth() - logo.getWidth() / 2);
        logo.setAlpha(0);
        add(logo);
        
        Texture[] btn = new Texture[]{Resources.controls("blue_button02"), Resources.controls("blue_button04"), Resources.controls("blue_button03")};
        LTexture backgroundTexture = Resources.images("home/background.png");
        setBackground(backgroundTexture);
        scale = backgroundTexture.width() / getWidth();
        
        panelMenu = new LPanel(getHalfWidth() - 125, getHalfHeight(), 250, 178);
        LColor c = new LColor(1f, 1f, 1f, 0.5f);
        panelMenu.setBackground(c);
        panelMenu.setAlpha(0);
        add(panelMenu);
        
        buttonStart = new MButton(btn, "Start", (panelMenu.width() - 190) / 2, 30, 190, 49);
        buttonStart.setFont(Resources.fonts("menu"));
        buttonStart.setOffsetTop(2);
        buttonStart.setAlpha(0);
        buttonStart.addClickListener(new ClickAdapter()
        {
            @Override
            public void DoClick(LComponent comp)
            {
                setScreen(new GameScreen());
            }
        });
        
        buttonExit = new MButton(btn, "Exit", (panelMenu.width() - 190) / 2, 100, 190, 49);
        buttonExit.setFont(Resources.fonts("menu"));
        buttonExit.setOffsetTop(2);
        buttonExit.setAlpha(0);
        buttonExit.addClickListener(new ClickAdapter()
        {
            @Override
            public void DoClick(LComponent comp)
            {
                LSystem.exit();
            }
        });
        
        panelMenu.add(buttonStart);
        panelMenu.add(buttonExit);
        
        
        p1 = Resources.images("home/p1.png");
        p2 = Resources.images("home/p2.png");
        p3 = Resources.images("home/p3.png");
        p4 = Resources.images("home/p4.png");
        
        c3 = new LPanel(-500, 0, 0, 0);
        c3.setBackground(Resources.images("home/car3.png"));
        c3.setScale(0.8f);
        add(c3);
        addAction(repeatMoveActionDelay(1300, 830, -300, 830, 20), c3);
        c1 = new LPanel(-500, 0, 0, 0);
        c1.setBackground(Resources.images("home/car1.png"));
        c1.setScale(0.8f);
        add(c1);
        addAction(repeatMoveActionDelay(-300, 820, 1300, 820, 17), c1);
        c2 = new LPanel(-500, 0, 0, 0);
        c2.setBackground(Resources.images("home/car2.png"));
        c2.setScale(0.8f);
        add(c2);
        addAction(repeatMoveActionDelay(-300, 820, 1300, 820, 14), c2);
        c4 = new LPanel(-500, 0, 0, 0);
        c4.setBackground(Resources.images("home/car4.png"));
        c4.setScale(0.8f);
        add(c4);
        addAction(repeatMoveActionDelay(-300, 800, 1300, 800, 11), c4);
    }
    
    private ActionEvent repeatMoveActionDelay(float x1, float y1, float x2, float y2, int speed)
    {
        return ActionHelper.runAfter(MathUtils.random(10f), repeatMoveAction(x1, y1, x2, y2, speed));
    }
    
    private ActionEvent repeatMoveAction(float x1, float y1, float x2, float y2, int speed)
    {
        MoveBy r = new MoveBy(x2, y2, MathUtils.random(speed - 1, speed + 2));
        r.setActionListener(new ActionAdapter()
        {
            @Override
            public void start(ActionBind o)
            {
                o.setLocation(x1, y1);
            }
            
            @Override
            public void stop(ActionBind o)
            {
                addAction(ActionHelper.runAfter(MathUtils.random(0.5f, 2f), repeatMoveAction(x1, y1, x2, y2, speed)), o);
            }
        });
        return r;
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
