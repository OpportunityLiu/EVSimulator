package ev.screens;

import ev.DebugSettings;
import ev.Resources;
import ev.effects.MFadeEffect;
import loon.*;
import loon.action.FadeTo;
import loon.action.sprite.effect.FadeEffect;
import loon.canvas.LColor;
import loon.component.LTextBar;
import loon.event.GameTouch;
import loon.font.BMFont;
import loon.opengl.GLEx;
import loon.opengl.LTextureImage;
import loon.utils.processes.GameProcess;
import loon.utils.processes.RealtimeProcessManager;
import loon.utils.processes.WaitProcess;
import loon.utils.timer.LTimerContext;
import map.GeoConverter;
import map.LongLat;
import map.MeterXY;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

import static map.GeoConverter.*;

/**
 * Created by liuzh on 2016/4/15.
 * Splash screen
 */
public class SplashScreen extends Screen
{
    private class Splash
    {
        LTexture image;
        int background;
        int x, y, width, height;
    }

    private LTextBar textBarSkip;

    private boolean skipping = false;

    private ArrayList<Splash> splashes = new ArrayList<>();

    private int current = -1;
    private int counter = -1;

    @Override
    public void draw(GLEx g)
    {
        if(!isOnLoadComplete())
            return;
        int alpha;
        if(counter <= 100)
            alpha = (int)(counter * 2.55);
        else if(counter < 200)
            alpha = 255;
        else
            alpha = (int)((300 - counter) * 2.55);
        Splash splash = splashes.get(current);
        if(alpha != 0)
        {
            int background = LColor.withAlpha(splash.background, alpha);
            g.fillRect(0, 0, getWidth(), getHeight(), new LColor(background));
        }
        g.draw(splash.image, splash.x, splash.y, splash.width, splash.height, new LColor(255, 255, 255, alpha));
        if(counter == 300)
            counter = -1;
    }

    /**
     * 初始化时加载的数据
     */
    @Override
    public void onLoad()
    {
        fristUserDraw();
        Json json = LSystem.json();
        String jsonText = null;
        try
        {
            jsonText = LSystem.base().assets().getTextSync("images/splashes/splash.json");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        Json.Array splashInfo = json.parseArray(jsonText);
        for(int i = 0; i < splashInfo.length(); i++)
        {
            Json.Object obj = splashInfo.getObject(i);
            Splash splash = new Splash();
            splash.background = new LColor(obj.getString("background")).getARGB();
            double rawScale = obj.getDouble("scale");
            splash.image = LTexture.createTexture("images/splashes/" + obj.getString("name"));
            double scale = Math.min((double)getWidth() / splash.image.getWidth(), (double)getHeight() / splash.image.getHeight()) * rawScale;
            splash.width = (int)(splash.image.getWidth() * scale);
            splash.height = (int)(splash.image.getHeight() * scale);
            splash.x = getHalfWidth() - splash.width / 2;
            splash.y = getHalfHeight() - splash.height / 2;
            splashes.add(splash);
        }

        addScreen(new MainScreen());
        addScreen(new GameScreen());

        textBarSkip = new LTextBar("点击屏幕跳过...", 0, 0);
        textBarSkip.setHideBackground(true);
        textBarSkip.setX(getHalfWidth() - textBarSkip.width() / 2);
        textBarSkip.setY(getHeight() * 0.8f - textBarSkip.height() / 2);
        textBarSkip.setBackground(new LColor(0, 0, 0, 0.7f));
        textBarSkip.setAlpha(0);
        add(textBarSkip);
        Resources.addLoadingListener(new Resources.LoadingListener()
        {
            @Override
            public void onLoaded()
            {
                if(DebugSettings.skipSplash)
                    touchUp(null);
                else
                    addAction(new FadeTo(FadeEffect.TYPE_FADE_OUT, 10f), textBarSkip);
            }

            @Override
            public void onLoading(int currentStep, int overallStep)
            {
            }
        });
    }

    @Override
    public void alter(LTimerContext timer)
    {
        if(!isOnLoadComplete())
            return;
        Resources.init();
        if(!skipping)
        {
            if(counter < 0)
                current++;
            if(current >= splashes.size())
            {
                this.runNextScreen();
                return;
            }
            counter++;
        }
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
        if(isOnLoadComplete() && Resources.loaded() && !skipping)
        {
            skipping = true;
            addAction(new FadeTo(FadeEffect.TYPE_FADE_IN, 10f), textBarSkip);
            add(new MFadeEffect(FadeEffect.TYPE_FADE_OUT, LColor.black)
            {
                @Override
                public void onFinished()
                {
                    remove(this);
                    runNextScreen();
                }
            });
        }
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
        for(Splash s : splashes)
        {
            s.image.close();
        }
        splashes = null;
    }

    @Override
    public LTransition onTransition()
    {
        return LTransition.newEmpty();
    }
}
