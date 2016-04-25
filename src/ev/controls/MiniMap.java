package ev.controls;

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
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public MiniMap(int x, int y, int width, int height)
    {
        super(x, y, width, height);
        setBackground(LTexture.createTexture("controls/map_panel.png"));
    }

    private MapTile tile;

    public void setTile(MapTile tile)
    {
        this.tile = tile;
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
        if(tile != null && tile.isLoaded())
            g.draw(tile.texture(), x + padding, y + padding, getWidth() - 2 * padding, getHeight() - 2 * padding);
    }

    @Override
    public String getUIName()
    {
        return null;
    }
}
