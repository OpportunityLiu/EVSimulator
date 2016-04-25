package ev.screens;

import ev.event.ClickAdapter;
import loon.Screen;
import loon.canvas.LColor;
import loon.component.*;
import loon.event.GameTouch;
import loon.geom.Vector2f;
import loon.opengl.GLEx;
import loon.utils.timer.LTimerContext;
import map.MeterXY;
import map.TileMap;

/**
 * Created by liuzh on 2016/4/20.
 * Main gaming screen
 */
public class GameScreen extends Screen
{
    private TileMap map;
    private LProgress progress;

    @Override
    public void draw(GLEx g)
    {
        if(!isOnLoadComplete())
            return;
    }

    /**
     * 初始化时加载的数据
     */
    @Override
    public void onLoad()
    {
        map = new TileMap(getWidth(), getHeight(), 12644, 12644 + 8, 4703, 4703 + 8, 16);
        map.addClickListener(new ClickAdapter()
        {
            @Override
            public void UpClick(LComponent comp, float x, float y)
            {
                x = x - comp.getX();
                y = y - comp.getY();
                Vector2f p = map.pixelsToTiles(x, y);
                MeterXY pp = map.tilesToCoordinates(p.x, p.y);
                getGame().log().debug(pp.toString());
            }
        });
        add(map);

        progress = new LProgress(LProgress.ProgressType.UI, LColor.blue, getHalfWidth() - 300, 800, 600, 20);
        add(progress);
        map.add(new LTextBar("hello", 2000, 2000, LColor.black));
        //add(progress);
    }

    @Override
    public void alter(LTimerContext timer)
    {
        if(!isOnLoadComplete())
            return;
        progress.setPercentage(map.getProgress());
        if(map.getProgress() > 0.1)
            runPreviousScreen();
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
