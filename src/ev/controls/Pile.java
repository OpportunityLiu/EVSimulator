package ev.controls;

import loon.LTexture;
import loon.opengl.GLEx;

/**
 * Created by liuzh on 2016/5/4.
 * Charging-pile
 */
public class Pile extends MapComponent
{
    private float power = 1;

    private LTexture image;

    @Override
    public boolean intersects(float x1, float y1)
    {
        return super.intersects(x1 + getWidth() / 2, y1 + getHeight() / 2);
    }

    public Pile(int x, int y, int width, int height, LTexture texture)
    {
        super(x, y, width, height, texture);
        image = texture;
        setSize(width, height);
    }

    public Pile(int x, int y, LTexture texture)
    {
        super(x, y, texture);
        image = texture;
    }

    public float getPower()
    {
        return power;
    }

    public void setPower(float power)
    {
        this.power = power;
    }

    @Override
    public void update(long elapsedTime)
    {
        super.update(elapsedTime);
    }

    @Override
    public String getUIName()
    {
        return "Pile";
    }

    @Override
    public void createUI(GLEx g)
    {
        g.draw(image, screenX - getWidth() / 2, screenY - getHeight() / 2, getWidth(), getHeight(), getRotation());
    }
}
