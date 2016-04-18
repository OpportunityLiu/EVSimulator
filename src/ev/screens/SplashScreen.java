package ev.screens;

import ev.DebugSettings;
import ev.Resources;
import loon.*;
import loon.canvas.LColor;
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

    private ArrayList<Splash> splashes = new ArrayList<>();

    private int current = -1;
    private int counter = -1;

    @Override
    public void draw(GLEx g)
    {
        if(!isOnLoadComplete())
            return;
        if(counter < 0)
            current++;
        if(DebugSettings.skipSplash)
        {
            if(Resources.loaded())
            {
                setScreen(new MainScreen());
                return;
            }
        }
        else if(current >= splashes.size())
        {
            setScreen(new MainScreen());
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
    }

    @Override
    public void alter(LTimerContext timer)
    {
        Resources.init();
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
