package map.controls;

import loon.LTexture;
import loon.action.ActionBind;
import loon.component.LComponent;
import loon.opengl.GLEx;
import map.MapTile;

/**
 * Created by liuzh on 2016/4/25.
 * Mini map control
 */
public class MiniMap extends LComponent
{
    /**
     * 构造可用组件
     *
     * @param x position x
     * @param y position y
     * @param width width of the control
     * @param height height of the control
     */
    public MiniMap(int x, int y, int width, int height)
    {
        super(x, y, width, height);
        setBackground(LTexture.createTexture("controls/map_panel.png"));
        setLoadingPlaceholder(LTexture.createTexture("controls/loading.png"));
    }

    private LTexture loadingPlaceholder;

    public void setLoadingPlaceholder(LTexture loadingPlaceholder)
    {
        this.loadingPlaceholder = loadingPlaceholder;
    }

    private MapTile tile;

    public void setTile(MapTile tile)
    {
        this.tile = tile;
        rotate = 0;
    }

    public MapTile getTile()
    {
        return tile;
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
        if(tile == null)
            return;
        if(tile.isLoaded())
            g.draw(tile.texture(), x + padding, y + padding, getWidth() - 2 * padding, getHeight() - 2 * padding);
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
        return null;
    }
}
