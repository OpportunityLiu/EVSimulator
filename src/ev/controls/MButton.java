package ev.controls;

import com.sun.org.apache.xpath.internal.operations.Lt;
import loon.LSystem;
import loon.LTexture;
import loon.LTrans;
import loon.action.sprite.DisplayObject;
import loon.canvas.LColor;
import loon.component.LButton;
import loon.component.LComponent;
import loon.font.IFont;
import loon.geom.PointF;
import loon.geom.RectBox;
import loon.opengl.GLEx;
import loon.utils.res.Texture;
import loon.utils.res.TextureData;

/**
 * Created by liuzh on 2016/4/17.
 * My own button supports x offset and y offset
 */
public class MButton extends LButton
{
    public MButton(Texture img, String text, int x, int y, int w, int h)
    {
        super(text, x, y, w, h);
        setImages(img);
    }

    public MButton(Texture[] img, String text, int x, int y, int w, int h)
    {
        super(text, x, y, w, h);
        setImages(img);
    }

    protected TextureData[] textureData;
    protected int myType;

    @Override
    public void createUI(GLEx g, int x, int y, LComponent component, LTexture[] buttonImage)
    {
        float destX = x;
        float destY = y;
        LButton button = (LButton)component;
        int current;
        if(!button.isEnabled())
        {
            current = 3;
        }
        else if(button.isTouchPressed())
        {
            current = 2;
        }
        else if(button.isTouchOver())
        {
            current = 1;
        }
        else
        {
            current = 0;
        }
        if(buttonImage != null)
        {
            TextureData data = textureData[current];
            LTexture texture = buttonImage[current];
            LColor color;
            if(myType == 1 && current == 0)
                color = LColor.gray;
            else
                color = LColor.white;
            destX = x + data.offX();
            destY = y + data.offY();
            g.draw(texture, destX, destY,data.w(), data.h(), data.x(), data.y(), data.w(), data.h(), color);

        }
        if(getText() != null)
        {
            int tmp = g.color();
            g.setColor(getFontColor());
            IFont f = getFont();
            f.drawString(g, getText(), destX + button.getOffsetLeft() + (button.getWidth() - f.stringWidth(getText())) / 2, destY + button.getOffsetTop() + (button.getHeight() - f.getHeight() - f.getAscent()) / 2);
            g.setColor(tmp);
        }
    }

    public void setImages(Texture... images)
    {
        int size = images.length;
        this.myType = size;
        textureData = new TextureData[4];
        LTexture[] img = new LTexture[size];
        for(int i = 0; i < size; i++)
        {
            img[i] = images[i].img();
        }
        switch(size)
        {
            case 1:
                textureData[0] = images[0].data();
                textureData[1] = images[0].data();
                textureData[2] = images[0].data();
                textureData[3] = images[0].data();
                break;
            case 2:
                textureData[0] = images[0].data();
                textureData[1] = images[1].data();
                textureData[2] = images[0].data();
                textureData[3] = images[0].data();
                break;
            case 3:
                textureData[0] = images[0].data();
                textureData[1] = images[1].data();
                textureData[2] = images[2].data();
                textureData[3] = images[0].data();
                break;
            case 4:
                textureData[0] = images[0].data();
                textureData[1] = images[1].data();
                textureData[2] = images[2].data();
                textureData[3] = images[3].data();
                break;
        }
        super.setImages(img);
        setWidth(textureData[0].w());
        setHeight(textureData[0].h());
    }

}

