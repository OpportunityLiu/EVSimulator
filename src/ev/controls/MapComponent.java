package ev.controls;

import loon.LTexture;
import loon.component.LComponent;
import loon.opengl.GLEx;

/**
 * Created by liuzh on 2016/5/4.
 * Component in the tile map
 */
public abstract class MapComponent extends LComponent
{
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

    private EVController controller;

    public EVController getController()
    {
        return controller;
    }

    void setController(EVController controller)
    {
        this.controller = controller;
    }
}
