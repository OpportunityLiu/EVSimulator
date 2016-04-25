package map;

import ev.Resources;
import loon.LRelease;
import loon.LTexture;
import loon.canvas.Image;
import loon.utils.MathUtils;
import loon.utils.reply.ActView;
import loon.utils.reply.Try;

import javax.swing.plaf.PanelUI;
import java.awt.image.BufferedImage;

/**
 * Created by liuzh on 2016/4/22.
 * Represents a tile of map
 */
public class MapTile implements LRelease
{
    private static final String uri = "http://online" +
            "%d" +
            ".map.bdimg.com/tile/?qt=tile" +
            "&x=%d" +
            "&y=%d" +
            "&z=%d" +
            "&styles=pl&scaler=2";

    public static MapTile getTile(int x, int y, int scale)
    {
        String url = String.format(uri, MathUtils.random(0, 9), x, y, scale);
        Image image = Resources.assets().getRemoteImage(url, 512, 512);
        return new MapTile(image, x, y, scale);
    }

    private MapTile(Image image, int x, int y, int scale)
    {
        this.image = image;
        image.state.onSuccess(this::init);
        this.x = x;
        this.y = y;
        this.scale = scale;
        scaler = (double)(1 << (26 - scale));
    }

    private Image image;
    private final int x, y, scale;
    private final double scaler;
    private LTexture texture;
    private boolean loaded;

    public LTexture texture()
    {
        return texture;
    }

    public boolean isLoaded()
    {
        return loaded;
    }

    /**
     * Get the coordinate of the given point in the tile.
     *
     * @param x x of the point.
     * @param y y of the point.
     * @return the coordinate of the point.
     */
    public MeterXY getCoordinate(float x, float y)
    {
        return new MeterXY(this.x * scaler + x, (this.y + 1) * scaler + y);
    }

    private void init(Image img)
    {
        texture = img.texture();
        img.destroy();
        image = null;
        loaded = true;
    }

    public MapTile onSuccess(ActView.Listener<MapTile> slot)
    {
        if(loaded)
            slot.onEmit(this);
        else
            image.state.onSuccess(event -> slot.onEmit(MapTile.this));
        return this;
    }

    public MapTile onFailure(ActView.Listener<Throwable> slot)
    {
        if(!loaded)
            image.state.onFailure(slot);
        return this;
    }

    public MapTile onComplete(ActView.Listener<Try<MapTile>> slot)
    {
        if(loaded)
            slot.onEmit(Try.success(this));
        else
            image.state.onComplete(event -> slot.onEmit(event.map(input -> MapTile.this)));
        return this;
    }

    @Override
    public void close()
    {
        if(isLoaded())
        {
            texture.close();
            texture = null;
        }
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }
}
