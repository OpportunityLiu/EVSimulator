package ev.screens;

import ev.Resources;
import ev.controls.MButton;
import ev.controls.MapComponent;
import ev.controls.Pile;
import ev.controls.Vehicle;
import ev.event.ClickAdapter;
import loon.Screen;
import loon.canvas.LColor;
import loon.component.*;
import loon.event.GameTouch;
import loon.font.LFont;
import loon.geom.Vector2f;
import loon.opengl.GLEx;
import loon.utils.res.Texture;
import loon.utils.timer.LTimerContext;
import map.controls.TileMap;

import java.util.ArrayList;

/**
 * Created by liuzh on 2016/4/20.
 * Main gaming screen
 */
public class GameScreen extends Screen
{
    private TileMap map;
    private LProgress progress;
    private LPaper controls;
    private LTextList statics;
    private MButton addVehicle, addPile, delete;
    private LLabels hint;

    private ArrayList<Vehicle> vehicles = new ArrayList<>();
    private ArrayList<Pile> piles = new ArrayList<>();

    private LComponent selected;

    private int positionX, positionY, scale, size;

    private int state;

    private String region;

    public void init(int positionX, int positionY, int scale, int size, String region)
    {
        this.positionX = positionX;
        this.positionY = positionY;
        this.scale = scale;
        this.size = size;
        this.region = region;
    }

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
        map = new TileMap(getWidth(), getHeight(), positionX, positionX + size, positionY, positionY + size, scale, region);
        add(map);

        progress = new LProgress(LProgress.ProgressType.UI, LColor.blue, getHalfWidth() - 300, 800, 600, 20);
        add(progress);

        controls = new LPaper(0, 0, 200, 500);
        controls.setLocked(false);
        controls.setBackground(LColor.white);
        controls.setTransparency(200);

        statics = new LTextList(5, 35, 190, 215);
        statics.setFocusable(false);

        Texture[] btn = new Texture[]{
                Resources.controls("yellow_button02"),
                Resources.controls("yellow_button04"),
                Resources.controls("yellow_button03")
        };
        addVehicle = new MButton(btn, "Add Vehi.", 5, 260, 190, 49);
        addVehicle.setFont(Resources.fonts("menu"));
        addVehicle.setOffsetTop(2);
        addVehicle.addClickListener(new ClickAdapter()
        {
            @Override
            public void DoClick(LComponent comp)
            {
                state = 200;
                hint.labels.clear();
                hint.addLabel("Set departure", LColor.black);
            }
        });
        addPile = new MButton(btn, "Add Pile", 5, 320, 190, 49);
        addPile.setFont(Resources.fonts("menu"));
        addPile.setOffsetTop(2);
        addPile.addClickListener(new ClickAdapter()
        {
            @Override
            public void DoClick(LComponent comp)
            {
                state = 100;
                hint.labels.clear();
                hint.addLabel("Click to add", LColor.black);
            }
        });
        delete = new MButton(btn, "Delete", 5, 380, 190, 49);
        delete.setFont(Resources.fonts("menu"));
        delete.setOffsetTop(2);
        delete.setVisible(false);
        delete.addClickListener(new ClickAdapter()
        {
            @Override
            public void DoClick(LComponent comp)
            {
                if(selected == null)
                    return;
                state = 0;
                hint.labels.clear();
                map.remove(selected);
                if(selected instanceof Vehicle)
                    vehicles.remove(selected);
                else if(selected instanceof Pile)
                    piles.remove(selected);
                selected = null;
                delete.setVisible(false);
            }
        });

        hint = new LLabels(5, 440, 190, 30);

        map.addClickListener(new ClickAdapter()
        {
            private Vehicle initializingVehicle;

            private boolean dragging;

            @Override
            public void DragClick(LComponent comp, float x, float y)
            {
                dragging = true;
            }

            @Override
            public void DownClick(LComponent comp, float x, float y)
            {
                Vector2f offset = map.getOffset();
                this.x = x + offset.x;
                this.y = y + offset.y;
            }

            private float x, y;

            @Override
            public void DoClick(LComponent comp)
            {
                if(dragging)
                {
                    dragging = false;
                    return;
                }
                switch(state)
                {
                case 100:
                    Pile p = new Pile((int)x, (int)y, 30, 30, Resources.spirits("pile.png"));
                    p.addClickListener(clickAdapter);
                    piles.add(p);
                    map.add(p);
                    state = 0;
                    break;
                case 200:
                    initializingVehicle = new Vehicle((int)x, (int)y, 30, 30, Resources.spirits("cars/blue_1.png"), 5000);
                    initializingVehicle.setDeparture(new Vector2f(x, y));
                    initializingVehicle.addClickListener(clickAdapter);
                    hint.labels.clear();
                    hint.addLabel("Set destination", LColor.black);
                    state = 201;
                    break;
                case 201:
                    initializingVehicle.setDestination(new Vector2f(x, y));
                    vehicles.add(initializingVehicle);
                    map.add(initializingVehicle);
                    initializingVehicle.start();
                    initializingVehicle = null;
                    state = 0;
                    break;
                case 300:
                    selected = null;
                    delete.setVisible(false);
                    state = 0;
                }
                if(state == 0)
                {
                    hint.labels.clear();

                }
            }
        });

        controls.add(statics);
        controls.add(addPile);
        controls.add(addVehicle);
        controls.add(delete);
        controls.add(hint);
        controls.setVisible(false);

        add(controls);

        Vehicle.setPiles(piles);
        MapComponent.setMap(map);
    }

    private ClickAdapter clickAdapter = new ClickAdapter()
    {
        @Override
        public void DoClick(LComponent comp)
        {
            selected = comp;
            delete.setVisible(true);
            state = 300;
        }
    };

    @Override
    public void alter(LTimerContext timer)
    {
        if(!isOnLoadComplete())
            return;
        if(progress != null)
        {
            progress.setPercentage(map.getProgress());
            if(progress.getPercentage() >= 1)
            {
                remove(progress);
                progress = null;

                controls.setVisible(true);
            }
        }

        statics.reset(10);
        statics.add("Vehicles: " + vehicles.size());
        statics.add("Piles: " + piles.size());
        double power = 0;
        for(Vehicle v : vehicles)
        {
            Pile p = v.getCurrentPile();
            if(p != null)
                power += p.getPower();
        }
        statics.add("Power: " + power);
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
