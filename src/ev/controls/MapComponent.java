package ev.controls;

import loon.LTexture;
import loon.component.LComponent;
import loon.geom.Vector2f;
import loon.opengl.GLEx;
import map.controls.TileMap;

/**
 * Created by liuzh on 2016/5/4.
 * Component in the tile map
 */
public abstract class MapComponent extends LComponent
{

    protected static TileMap map;
    public static TileMap getMap()
    {
        return map;
    }

    public static void setMap(TileMap map)
    {
        MapComponent.map = map;
    }
    /**
     * 构造可用组件
     *
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public MapComponent(int x, int y, int width, int height, LTexture texture)
    {
        super(x, y, width, height);
        setImageUI(new LTexture[]{texture}, true);
        setSize(width, height);
    }

    public MapComponent(int x, int y, LTexture texture)
    {
        this(x, y, texture.getWidth(), texture.getHeight(), texture);
    }

    @Override
    public void createUI(GLEx g, int x, int y, LComponent component, LTexture[] buttonImage)
    {
        g.draw(buttonImage[0], x, y, getWidth(), getHeight());
    }

    @Override
    public boolean intersects(float x1, float y1)
    {
        Vector2f offset = map.getOffset();
        return (this.visible)
                && (x1 >= this.screenX - offset.x
                && x1 <= this.screenX + this.getWidth() - offset.x
                && y1 >= this.screenY - offset.y && y1 <= this.screenY
                + this.getHeight() - offset.y);
    }
}
