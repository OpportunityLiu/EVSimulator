package map.controls;

import loon.LRelease;
import loon.LTexture;
import loon.canvas.LColor;
import loon.component.LComponent;
import loon.component.LContainer;
import loon.component.LLayer;
import loon.geom.RectBox;
import loon.geom.Vector2f;
import loon.opengl.GLEx;
import loon.utils.MathUtils;
import loon.utils.StringUtils;
import map.MapTile;
import map.MeterXY;

/**
 * Created by liuzh on 2016/4/22.
 * My tile map.
 */
public class TileMap extends LContainer implements LRelease
{
    public interface DrawListener
    {

        void update(long elapsedTime);

        void draw(GLEx g, float x, float y);

    }

    private int tileWidth = 256, tileHeight = 256;

    /**
     * Not include.
     */
    private int maxRawX;

    /**
     * Include.
     */
    private int minRawX;

    /**
     * Not include.
     */
    private int maxRawY;

    /**
     * Include.
     */
    private int minRawY;
    private int scale;

    private DrawListener listener;

    /**
     * arrays[x][y]
     */
    private MapTile[][] arrays;

    private Vector2f offset, maxOffset;

    private String region;

    public String getRegion()
    {
        return region;
    }

    public TileMap(int width, int height, int minRawX, int maxRawX, int minRawY, int maxRawY, int scale, String region)
    {
        super(0, 0, width, height);
        setBackground(new LColor("#f5f3f0"));
        this.maxRawX = maxRawX;
        this.minRawX = minRawX;
        this.maxRawY = maxRawY;
        this.minRawY = minRawY;
        this.scale = scale;
        this.scaler = (float)(1 << (26 - scale));
        this.offset = new Vector2f(0, 0);
        this.maxOffset = new Vector2f(0, 0);
        this.visible = true;
        arrays = new MapTile[getCol()][];
        for(int i = 0; i < getCol(); i++)
        {
            arrays[i] = new MapTile[getRow()];
            for(int j = 0; j < getRow(); j++)
            {
                arrays[i][j] = MapTile.getTile(minRawX + i, maxRawY - 1 - j, scale)
                                      .onComplete(event -> loadingProgress++);
            }
        }
        recalculateMaxOffset();
        this.region = region;
    }

    protected void recalculateMaxOffset()
    {
        float maxX = getMapWidth() - getWidth(), maxY = getMapHeight() - getHeight();
        maxX = maxX > 0 ? maxX : 0;
        maxY = maxY > 0 ? maxY : 0;
        this.maxOffset.set(maxX, maxY);
        this.setOffset(offset);
    }

    private void draw(GLEx g)
    {
        final float offsetX = offset.getX(), offsetY = offset.getY();

        int firstTileX = pixelsToTilesWidth(offsetX);
        int firstTileY = pixelsToTilesHeight(offsetY);

        int lastTileX = firstTileX + pixelsToTilesWidth(getWidth()) + 2;
        lastTileX = MathUtils.min(lastTileX, getCol());

        int lastTileY = firstTileY + pixelsToTilesHeight(getHeight()) + 2;
        lastTileY = MathUtils.min(lastTileY, getRow());

        firstTileX = MathUtils.max(0, firstTileX);
        firstTileY = MathUtils.max(0, firstTileY);

        for(int i = firstTileX; i < lastTileX; i++)
        {
            for(int j = firstTileY; j < lastTileY; j++)
            {
                MapTile tile = arrays[i][j];
                if(tile.isLoaded())
                    g.draw(tile.texture(), tilesToPixelsX(i) - offsetX, tilesToPixelsY(j) - offsetY, tileWidth, tileHeight);
            }
        }

        if(listener != null)
        {
            listener.draw(g, offsetX, offsetY);
        }
    }

    @Override
    public void setWidth(float width)
    {
        super.setWidth(width);
        recalculateMaxOffset();
    }

    @Override
    public void setHeight(float height)
    {
        super.setHeight(height);
        recalculateMaxOffset();
    }

    private float tilesToPixelsX(float x)
    {
        return (x * tileWidth);
    }

    private float tilesToPixelsY(float y)
    {
        return (y * tileHeight);
    }

    private int pixelsToTilesWidth(float x)
    {
        return MathUtils.floor(x / tileWidth);
    }

    private int pixelsToTilesHeight(float y)
    {
        return MathUtils.floor(y / tileHeight);
    }

    /**
     * 转换坐标为像素坐标
     *
     * @param x 距整个地图左边的距离
     * @param y 距整个地图上边的距离
     * @return 坐标
     */
    public Vector2f tilesToScreenPixels(float x, float y)
    {
        float xprime = tilesToPixelsX(x) - offset.x;
        float yprime = tilesToPixelsY(y) - offset.y;
        return new Vector2f(xprime, yprime);
    }

    /**
     * 转换坐标为像素坐标
     *
     * @param x 距整个地图左边的距离
     * @param y 距整个地图上边的距离
     * @return 坐标
     */
    public Vector2f tilesToPixels(float x, float y)
    {
        float xprime = tilesToPixelsX(x);
        float yprime = tilesToPixelsY(y);
        return new Vector2f(xprime, yprime);
    }

    /**
     * 转换像素坐标至坐标
     *
     * @param x 距控件左边的距离
     * @param y 距控件上边的距离
     * @return 坐标
     */
    public Vector2f screenPixelsToTiles(float x, float y)
    {
        float xprime = (x + offset.x) / tileWidth;
        float yprime = (y + offset.y) / tileHeight;
        return new Vector2f(xprime, yprime);
    }

    /**
     * 转换像素坐标至坐标
     *
     * @param x 距地图左边的距离
     * @param y 距地图上边的距离
     * @return 坐标
     */
    public Vector2f pixelsToTiles(float x, float y)
    {
        float xprime = x / tileWidth;
        float yprime = y / tileHeight;
        return new Vector2f(xprime, yprime);
    }

    private float scaler;

    public MeterXY tilesToCoordinates(float x, float y)
    {
        double xx = (minRawX + x) * scaler;
        double yy = (maxRawY - y) * scaler;
        return new MeterXY(xx, yy);
    }

    public Vector2f coordinatesToTiles(MeterXY coordinate)
    {
        double x = coordinate.x() / scaler - minRawX;
        double y = maxRawY - coordinate.y() / scaler;
        return new Vector2f((float)x, (float)y);
    }

    public void setPosition(MeterXY center)
    {
        final Vector2f centerInTile = this.coordinatesToTiles(center);
        centerInTile.x -= getWidth() / 2 / tileWidth;
        centerInTile.y -= getHeight() / 2 / tileHeight;
        this.setOffset(tilesToPixelsX(centerInTile.x), tilesToPixelsY(centerInTile.y));
    }

    /**
     * 设定偏移量
     *
     * @param x x 偏移
     * @param y y 偏移
     */
    public void setOffset(float x, float y)
    {
        x = x < 0 ? 0 : x;
        y = y < 0 ? 0 : y;
        x = MathUtils.min(x, maxOffset.x);
        y = MathUtils.min(y, maxOffset.y);
        this.offset.set(x, y);
    }

    /**
     * 设定偏移量
     *
     * @param offset 偏移量
     */
    public void setOffset(Vector2f offset)
    {
        this.setOffset(offset.getX(), offset.getY());
    }

    /**
     * 获得偏移量
     *
     * @return 偏移量
     */
    public Vector2f getOffset()
    {
        return offset;
    }

    public int getTileWidth()
    {
        return tileWidth;
    }

    public void setTileWidth(int tileWidth)
    {
        this.tileWidth = tileWidth;
        recalculateMaxOffset();
    }

    public int getTileHeight()
    {
        return tileHeight;
    }

    public void setTileHeight(int tileHeight)
    {
        this.tileHeight = tileHeight;
        recalculateMaxOffset();
    }

    public int getMapWidth()
    {
        return getCol() * tileWidth;
    }

    public int getMapHeight()
    {
        return getRow() * tileHeight;
    }

    public int getCol()
    {
        return maxRawX - minRawX;
    }

    public int getRow()
    {
        return maxRawY - minRawY;
    }

    public DrawListener getListener()
    {
        return listener;
    }

    public void setListener(DrawListener listener)
    {
        this.listener = listener;
    }

    public RectBox getCollisionBox()
    {
        return getRect(x() - offset.x, y() - offset.y, getMapWidth(), getMapHeight());
    }

    @Override
    public String getUIName()
    {
        return "TileMap";
    }

    public void update(long elapsedTime)
    {
        super.update(elapsedTime);
        if(listener != null)
        {
            listener.update(elapsedTime);
        }
    }

    @Override
    public void createUI(GLEx g)
    {
        if(isClose)
            return;
        if(!this.isVisible())
            return;

        float x = getX(), y = getY();
        g.startClipped(x, y, getWidth(), getHeight());
        if(x != 0 || y != 0)
            g.translate(x, y);
        if(bk != null && getProgress() < 1)
            g.draw(bk, 0, 0);
        draw(g);
        boolean needTranslate = offset.x != 0 || offset.y != 0;
        try
        {
            if(needTranslate)
            {
                g.saveTx();
                g.translate(-offset.x, -offset.y);
            }
            super.createUI(g);
            if(needTranslate)
                g.translate(offset.x, offset.y);
        }
        finally
        {
            if(needTranslate)
                g.restoreTx();
        }

        if(x != 0 || y != 0)
            g.translate(-x, -y);
        g.endClipped();
    }

    @Override
    public void createUI(GLEx g, int x, int y, LComponent component, LTexture[] buttonImage)
    {
    }

    private boolean canDrag = true;

    public boolean canDrag()
    {
        return canDrag;
    }

    public void setCanDrag(boolean canDrag)
    {
        this.canDrag = canDrag;
    }

    @Override
    protected void processTouchDragged()
    {
        super.processTouchDragged();
        if(canDrag)
        {
            setOffset(offset.x - input.getTouchDX(), offset.y - input.getTouchDY());
        }
    }

    public void close()
    {
        visible = false;
        for(int i = 0; i < getCol(); i++)
        {
            for(int j = 0; j < getRow(); j++)
            {
                arrays[i][j].close();
            }
        }
        arrays = null;
    }

    private int loadingProgress;

    public float getProgress()
    {
        return (float)loadingProgress / (getCol() * getRow());
    }

    protected LTexture bk;

    @Override
    public void setBackground(LTexture background)
    {
        if(background == null)
        {
            return;
        }
        LTexture oldImage = this.bk;
        if(oldImage != background && oldImage != null)
        {
            oldImage.close();
        }
        this.bk = background;
    }

    @Override
    public LTexture getBackground()
    {
        return bk;
    }
}
