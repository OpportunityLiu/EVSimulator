package map.controls;

import loon.LTexture;
import loon.component.LComponent;
import loon.geom.Vector2f;
import loon.opengl.GLEx;
import map.MapTile;
import map.MeterXY;

/**
 * Created by liuzh on 2016/4/25.
 * Mini map control
 */
public class MiniMap extends LComponent
{
    /**
     * 构造可用组件
     *
     * @param x      position x
     * @param y      position y
     * @param width  width of the control
     * @param height height of the control
     */
    public MiniMap(int x, int y, int width, int height)
    {
        super(x, y, width, height);
        setBackground(LTexture.createTexture("controls/map_panel.png"));
        setLoadingPlaceholder(LTexture.createTexture("controls/loading.png"));
    }

    private TileMap mapPresenter;

    private LTexture loadingPlaceholder;

    public void setLoadingPlaceholder(LTexture loadingPlaceholder)
    {
        this.loadingPlaceholder = loadingPlaceholder;
    }

    public void setCenter(MeterXY center, int scale)
    {
        rotate = 0;
        if(mapPresenter != null)
            mapPresenter.close();
        final MeterXY.MapTileInfo tileInfo = center.getTileOfThis(scale);
        mapPresenter = new TileMap((int)(getWidth() - 2 * padding), (int)(getHeight() - 2 * padding), tileInfo.x - 1, tileInfo.x + 2, tileInfo.y - 1, tileInfo.y + 2, scale);
        mapPresenter.setCanDrag(false);
        mapPresenter.setTileHeight(mapPresenter.height());
        mapPresenter.setTileWidth(mapPresenter.width());
        mapPresenter.setLocation(getX() + padding, getY() + padding);
        mapPresenter.setPosition(center);
    }

    private float padding = 5;

    public float getPadding()
    {
        return padding;
    }

    public void setPadding(float padding)
    {
        this.padding = padding;
    }

    @Override
    public void createUI(GLEx g, int x, int y, LComponent component, LTexture[] buttonImage)
    {
        if(mapPresenter == null)
            return;
        if(mapPresenter.getProgress() == 1)
            mapPresenter.createUI(g);
        else if(loadingPlaceholder != null)
            g.draw(loadingPlaceholder, x + getWidth() / 4, y + getHeight() / 4, getWidth() / 2, getHeight() / 2, rotate);
    }

    private float rotate;

    @Override
    public void update(long elapsedTime)
    {
        super.update(elapsedTime);
        rotate += elapsedTime / 5;
    }

    @Override
    public String getUIName()
    {
        return "MiniMap";
    }
}
