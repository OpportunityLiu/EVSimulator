package ev.screens;

import ev.Resources;
import ev.controls.ActionHelper;
import ev.controls.MButton;
import ev.controls.MTextList;
import map.MapTile;
import map.controls.MiniMap;
import ev.effects.MFadeEffect;
import ev.event.ActionAdapter;
import ev.event.ClickAdapter;
import loon.*;
import loon.action.*;
import loon.action.sprite.effect.FadeEffect;
import loon.canvas.LColor;
import loon.component.*;
import loon.event.GameTouch;
import loon.opengl.GLEx;
import loon.utils.MathUtils;
import loon.utils.res.Texture;
import loon.utils.timer.LTimerContext;
import map.CityInfo;

/**
 * Created by liuzh on 2016/4/16.
 * Main menu of ev sim.
 */
class MainScreen extends Screen
{
    private float scale;

    private boolean animating = true;
    
    //for background
    private LTexture p1, p2, p3, p4;
    private float r1, r2, r3, r4;
    private LPanel c1, c2, c3, c4;
    private LTexture backgroundTexture;
    
    private LPanel logo;
    
    //for main menu
    private LPanel mainMenu;
    private MButton mButtonStart, mButtonExit;
    
    //for select menu
    private LPanel selectMenu, sPanel;
    private MButton sButtonStart, sButtonBack;
    private MTextList<CityInfo.Province> provinceList;
    private MTextList<CityInfo.City> cityList;
    private MiniMap mapPanel;
    
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
    }
    
    /**
     * 初始化时加载的数据
     */
    @Override
    public void onLoad()
    {
        {
            setFristOrder(DRAW_USER_PAINT());
            setSecondOrder(DRAW_DESKTOP_PAINT());
            setLastOrder(DRAW_SPRITE_PAINT());
        }

        initBackground();
        initMain();
        initSelect();
        
        logo = new LPanel(0, 50, 1, 1);
        logo.setBackground(Resources.images("home/logo.png"));
        logo.setSize(500, 120);
        logo.setX(getHalfWidth() - logo.getWidth() / 2);
        logo.setAlpha(0);
        add(logo);
        
        add(new MFadeEffect(MFadeEffect.TYPE_FADE_IN, LColor.black)
        {
            @Override
            public void onFinished()
            {
                addAction(new MoveBy(logo.getX(), 150, 10), logo);
                addAction(new FadeTo(FadeEffect.TYPE_FADE_OUT, 10f), logo);
                showMain();
                remove(this);
            }
        });
    }
    
    private void initBackground()
    {
        backgroundTexture = Resources.images("home/background.png");
        scale = backgroundTexture.width() / getWidth();
        setBackground(backgroundTexture);

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
    
    private void initMain()
    {
        Texture[] btn1 = new Texture[]{Resources.controls("blue_button02"), Resources.controls("blue_button04"), Resources.controls("blue_button03")};
        Texture[] btn2 = new Texture[]{Resources.controls("red_button02"), Resources.controls("red_button04"), Resources.controls("red_button03")};

        mainMenu = new LPanel(getHalfWidth() - 125, getHalfHeight() - 150, 250, 178);
        LColor c = new LColor(1f, 1f, 1f, 0.5f);
        mainMenu.setBackground(c);
        mainMenu.setAlpha(0);
        
        mButtonStart = new MButton(btn1, "Start", (mainMenu.width() - 190) / 2, 30, 190, 49);
        mButtonStart.setFont(Resources.fonts("menu"));
        mButtonStart.setOffsetTop(2);
        mButtonStart.setAlpha(0);
        mButtonStart.addClickListener(new ClickAdapter()
        {
            @Override
            public void DoClick(LComponent comp)
            {
                if(comp.getAlpha() < 0.2 || animating)
                    return;
                toSelect();
            }
        });
        
        mButtonExit = new MButton(btn2, "Exit", (mainMenu.width() - 190) / 2, 100, 190, 49);
        mButtonExit.setFont(Resources.fonts("menu"));
        mButtonExit.setOffsetTop(2);
        mButtonExit.setAlpha(0);
        mButtonExit.addClickListener(new ClickAdapter()
        {
            MFadeEffect fade = new MFadeEffect(FadeEffect.TYPE_FADE_OUT, LColor.black)
            {
                @Override
                public void onFinished()
                {
                    getGame().status.emit(LGame.Status.EXIT);
                    System.exit(0);
                }
            };
            
            @Override
            public void DoClick(LComponent comp)
            {
                if(comp.getAlpha() < 0.2 || animating)
                    return;
                animating = true;
                if(!contains(fade))
                    add(fade);
            }
        });
        
        mainMenu.add(mButtonStart);
        mainMenu.add(mButtonExit);

        mainMenu.setVisible(false);
        add(mainMenu);
    }
    
    private void showMain()
    {
        mainMenu.setVisible(true);

        ActionEvent a1 = new FadeTo(FadeEffect.TYPE_FADE_OUT, 20f);
        ActionEvent a2 = a1.cpy();
        addAction(ActionHelper.runAfter(0.5f, a1), mButtonStart);
        addAction(ActionHelper.runAfter(1f, a2), mButtonExit);
        addAction(new FadeTo(FadeEffect.TYPE_FADE_OUT, 40f), mainMenu);

        animating = false;
    }
    
    private void toMain()
    {
        animating = true;
        removeAllActions(selectMenu);
        removeAllActions(sPanel);
        removeAllActions(sButtonStart);
        removeAllActions(sButtonBack);
        removeAllActions(provinceList);
        removeAllActions(cityList);
        removeAllActions(mapPanel);

        ActionEvent fadeB1 = new FadeTo(FadeEffect.TYPE_FADE_IN, 5f);
        ActionEvent fadeB2 = fadeB1.cpy();
        ActionEvent fadeLP = fadeB1.cpy();
        ActionEvent fadeLC = fadeB1.cpy();
        ActionEvent fadeP = fadeB1.cpy();
        ActionEvent fadeMap = fadeB1.cpy();
        ActionEvent fadeM = fadeB1.cpy();
        fadeM.setActionListener(new ActionAdapter()
        {
            @Override
            public void stop(ActionBind o)
            {
                selectMenu.setVisible(false);
                showMain();
            }
        });
        ActionEvent moveLogo = new MoveBy(logo.getX(), 150, 10);

        addAction(fadeB1, sButtonStart);
        addAction(fadeB2, sButtonBack);
        addAction(fadeP, sPanel);
        addAction(moveLogo, logo);
        addAction(fadeLP, provinceList);
        addAction(fadeLC, cityList);
        addAction(fadeMap, mapPanel);
        addAction(fadeM, selectMenu);
    }
    
    private void initSelect()
    {
        selectMenu = new LPanel(0, 0, getWidth(), getHeight());
        selectMenu.setBackground(new LColor(0, 0, 0, 0.6f));
        selectMenu.setAlpha(0);

        sPanel = new LPanel(915, 681, 250, 178);
        LColor c = new LColor(1f, 1f, 1f, 0.7f);
        sPanel.setBackground(c);
        sPanel.setAlpha(0);
        selectMenu.add(sPanel);

        Texture[] btn1 = new Texture[]{Resources.controls("green_button02"), Resources.controls("green_button04"), Resources.controls("green_button03")};
        Texture[] btn2 = new Texture[]{Resources.controls("blue_button02"), Resources.controls("blue_button04"), Resources.controls("blue_button03")};

        sButtonStart = new MButton(btn1, "Start", (sPanel.width() - 190) / 2, 30, 190, 49);
        sButtonStart.setFont(Resources.fonts("menu"));
        sButtonStart.setOffsetTop(2);
        sButtonStart.setAlpha(0);
        sButtonStart.addClickListener(new ClickAdapter()
        {
            MFadeEffect fade = new MFadeEffect(FadeEffect.TYPE_FADE_OUT, LColor.black)
            {
                @Override
                public void onFinished()
                {
                    remove(this);
                    GameScreen s = (GameScreen)getScreen("game");
                    if(s == null)
                    {
                        s = new GameScreen();
                        addScreen("game", s);
                    }
                    MapTile tile = city.getTile();
                    s.init(tile.getX() * 16, tile.getY() * 16, 16, 16);
                    runScreen("game");
                    animating = false;
                }
            };

            CityInfo.City city;

            @Override
            public void DoClick(LComponent comp)
            {
                if(comp.getAlpha() < 0.2 || animating)
                    return;
                if(currentCity == null)
                    return;
                city = currentCity;
                animating = true;
                if(!contains(fade))
                {
                    add(fade);
                }
            }
        });

        sButtonBack = new MButton(btn2, "Back", (sPanel.width() - 190) / 2, 100, 190, 49);
        sButtonBack.setFont(Resources.fonts("menu"));
        sButtonBack.setOffsetTop(2);
        sButtonBack.setAlpha(0);
        sButtonBack.addClickListener(new ClickAdapter()
        {
            @Override
            public void DoClick(LComponent comp)
            {
                if(comp.getAlpha() < 0.2 || animating)
                    return;
                toMain();
            }
        });

        sPanel.add(sButtonStart);
        sPanel.add(sButtonBack);

        mapPanel = new MiniMap(850, 210, 380, 380);
        mapPanel.setAlpha(0);
        selectMenu.add(mapPanel);

        provinceList = new MTextList<>(35, 50, 210, 350, 700, 0, null, null, null, null, null);
        provinceList.setAlpha(0);

        cityList = new MTextList<>(35, 450, 210, 350, 700, 0, null, null, null, null, null);
        cityList.setAlpha(0);

        selectMenu.add(provinceList);
        selectMenu.add(cityList);

        selectMenu.setVisible(false);
        add(selectMenu);

        //Load data
        final CityInfo.Province[] provinces = CityInfo.data();
        provinceList.setItemSource(provinces);
        provinceList.addClickListener(new ClickAdapter()
        {
            int index = -1;
            boolean down = false;

            @Override
            public void UpClick(LComponent comp, float x, float y)
            {
                int newIndex = provinceList.getSelectedIndex();
                if(newIndex == -1)
                    return;
                if(index == newIndex)
                {
                    needRefreshCity = true;
                }
                down = false;
            }

            @Override
            public void DownClick(LComponent comp, float x, float y)
            {
                if(down)
                    return;
                down = true;
                int newIndex = provinceList.getSelectedIndex();
                if(newIndex == -1)
                    return;
                index = newIndex;
            }
        });
        cityList.addClickListener(new ClickAdapter()
        {
            int index = -1;
            boolean down = false;

            @Override
            public void UpClick(LComponent comp, float x, float y)
            {
                int newIndex = cityList.getSelectedIndex();
                if(newIndex == -1)
                    return;
                if(index == newIndex)
                {
                    needRefreshImage = true;
                }
                down = false;
            }

            @Override
            public void DownClick(LComponent comp, float x, float y)
            {
                if(down)
                    return;
                down = true;
                int newIndex = cityList.getSelectedIndex();
                if(newIndex == -1)
                    return;
                index = newIndex;
            }
        });
    }

    private void showSelect()
    {
        selectMenu.setVisible(true);

        addAction(new FadeTo(FadeEffect.TYPE_FADE_OUT, 5f), selectMenu);

        addAction(new FadeTo(FadeEffect.TYPE_FADE_OUT, 5f), provinceList);

        addAction(new FadeTo(FadeEffect.TYPE_FADE_OUT, 5f), cityList);

        ActionEvent a1 = new FadeTo(FadeEffect.TYPE_FADE_OUT, 10f);
        ActionEvent a2 = a1.cpy();
        ActionEvent aMap = a1.cpy();
        addAction(ActionHelper.runAfter(0.6f, a1), sButtonStart);
        addAction(ActionHelper.runAfter(1f, a2), sButtonBack);
        addAction(new FadeTo(FadeEffect.TYPE_FADE_OUT, 40f), sPanel);
        addAction(ActionHelper.runAfter(0.3f, aMap), mapPanel);

        animating = false;
    }
    
    private void toSelect()
    {
        animating = true;

        removeAllActions(mainMenu);
        removeAllActions(mButtonStart);
        removeAllActions(mButtonExit);

        ActionEvent fadeB1 = new FadeTo(FadeEffect.TYPE_FADE_IN, 5f);
        ActionEvent fadeB2 = fadeB1.cpy();
        ActionEvent fadeP = fadeB1.cpy();
        fadeP.setActionListener(new ActionAdapter()
        {
            @Override
            public void stop(ActionBind o)
            {
                mainMenu.setVisible(false);
                showSelect();
            }
        });
        ActionEvent moveLogo = new MoveBy(logo.getX(), 50, 10);

        addAction(fadeB1, mButtonStart);
        addAction(fadeB2, mButtonExit);
        addAction(fadeP, mainMenu);
        addAction(moveLogo, logo);
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

    private boolean needRefreshCity = true, needRefreshImage;

    private CityInfo.City currentCity;

    public void alter(LTimerContext timer)
    {
        if(!isOnLoadComplete())
            return;
        if(needRefreshCity)
        {
            cityList.setItemSource(provinceList.getSelectedValue().cities);
            needRefreshCity = false;
        }
        if(needRefreshImage)
        {
            CityInfo.City old = currentCity;
            currentCity = cityList.getSelectedValue();
            if(currentCity != old)
            {
                if(currentCity != null)
                    mapPanel.setCenter(currentCity.coordinate, 12);
                else
                    currentCity = old;
            }
            needRefreshImage = false;
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
        needRefreshCity = true;
    }
}
