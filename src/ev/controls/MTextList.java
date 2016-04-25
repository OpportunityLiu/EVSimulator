package ev.controls;

import loon.LTexture;
import loon.canvas.LColor;
import loon.component.DefUI;
import loon.component.LComponent;
import loon.component.LScrollContainer;
import loon.event.SysTouch;
import loon.font.IFont;
import loon.font.LFont;
import loon.opengl.GLEx;

/**
 * 列表
 */
public class MTextList<T> extends LComponent
{

    public final int LIST_SPACE_TOP = 5;

    public final int LIST_SPACE_LEFT = 5;

    public final int LIST_SPACE_BOTTOM = 5;

    private LTexture bgTexture, choiceTexture, scrollTexture, scrollFlagATexture, scrollFlagBTexture;

    private boolean visualized;

    private int capacity;
    private Object[] items;

    private int num;
    private LColor defaultStringColor = LColor.white;

    private LColor choiceStringColor = LColor.black;
    private LColor choiceStringBoxColor = LColor.cyan;

    private IFont font = LFont.getDefaultFont();
    private int selectedIndex;

    public static final int defaultWidth = 150;
    public static final int defaultHeight = 300;

    private LColor listColor = LColor.black;
    private int drawNum;
    private int loop;
    private int drawX;
    private int drawY;
    private int scrollList;

    private int scrollBarX;
    private int scrollBarY;
    private int scrollBarHeight;

    private int scrollBarHeight_max;
    private boolean scrollBarDrag;

    private int scrollButtonWidth = 15;
    private int scrollButtonHeight = 15;
    private int scrollButtonX;
    private int scrollButtonY;
    private boolean scrollUpButtonON;
    private boolean scrollDownButtonON;
    private float[] px = new float[3];
    private float[] py = new float[3];
    private boolean useHold;
    private int hold;

    public MTextList(int x, int y)
    {
        this(128, x, y, defaultWidth, defaultHeight, 30);
    }

    public MTextList(int x, int y, int w, int h)
    {
        this(128, x, y, w, h, 30);
    }

    public MTextList(int capacity, int x, int y, int width, int height, int scrollButtonWidth)
    {
        this(capacity, x, y, width, height, scrollButtonWidth, DefUI.getDefaultTextures(2), DefUI.getDefaultTextures(11), DefUI.getDefaultTextures(3), null, null);
    }

    /**
     * @param capacity          允许插入的最大行数
     * @param x                 显示用坐标x
     * @param y                 显示用坐标y
     * @param width             文本列表宽
     * @param height            文本列表高
     * @param scrollButtonWidth 滚轴按钮触发范围
     * @param bg                背景图
     * @param choice            选中单独栏用图
     * @param scroll            滚轴用图
     * @param scrollFlagA       滚轴上下标识用图(A)
     * @param scrollFlagB       滚轴上下标识用图(B)
     */
    public MTextList(int capacity, int x, int y, int width, int height, int scrollButtonWidth, LTexture bg, LTexture choice, LTexture scroll, LTexture scrollFlagA, LTexture scrollFlagB)
    {
        super(x, y, (width - scrollButtonWidth), height);
        this.reset(capacity);
        this.bgTexture = bg;
        this.choiceTexture = choice;
        this.scrollTexture = scroll;
        this.scrollFlagATexture = scrollFlagA;
        this.scrollFlagBTexture = scrollFlagB;
    }

    public void reset(int capacity)
    {
        this.capacity = capacity;
        this.items = new Object[this.capacity];

        this.selectedIndex = 0;
        this.num = 0;
        this.scrollList = 0;
    }

    public void delete()
    {
        this.capacity = 0;
    }

    public void setFont(IFont newFont)
    {
        this.font = newFont;
    }

    public void set(int index, T value)
    {
        this.items[index] = value;
    }

    private void removeNames(int index, int flag)
    {
        int size = flag - index - 1;
        if(size > 0)
        {
            System.arraycopy(this.items, index + 1, this.items, index, size);
        }
        this.items[--flag] = null;
        if(size == 0)
        {
            items = new Object[0];
        }
    }

    public void remove(T value)
    {
        for(int i = 0; i < num; )
        {

            if(this.items[i] != null && this.items[i].equals(value))
            {
                remove(i);
                break;
            }
            i++;
        }
    }

    public void remove(int index)
    {
        if(index > -1 && index < num)
        {
            this.removeNames(index, this.num);
            this.num -= 1;
        }
    }

    public void add(T value)
    {
        this.items[this.num] = value;
        this.num++;
    }

    public void setDefaultStringColor(LColor stringNewColor)
    {
        this.defaultStringColor = stringNewColor;
    }

    public void setDefaultStringColor(LColor newStringColor, LColor newChoiceStringColor, LColor newChoiceStringBoxColor)
    {
        this.defaultStringColor = newStringColor;
        this.choiceStringColor = newChoiceStringColor;
        this.choiceStringBoxColor = newChoiceStringBoxColor;
    }

    public void setListColor(LColor newColor)
    {
        this.listColor = newColor;
    }

    public void setUseHold(boolean bool)
    {
        this.useHold = bool;
    }

    public void setHold(int num)
    {
        this.hold = num;
    }

    public void setBoundsScrollButton(int width, int height)
    {
        this.scrollButtonWidth = width;
        this.scrollButtonHeight = height;
    }

    public int getSelectedIndex()
    {
        return this.selectedIndex;
    }

    @SuppressWarnings("unchecked")
    public T getSelectedValue()
    {
        if(this.selectedIndex >= 0)
        {
            return (T)this.items[this.selectedIndex];
        }
        return null;
    }

    public int getCapacity()
    {
        return this.capacity;
    }

    public void setScrollList(int scroll)
    {
        this.scrollList = (scroll - this.drawNum);
        if(this.scrollList < 0)
        {
            this.scrollList = 0;
        }
    }

    private void drawString(GLEx g, String str, int x, int y, LColor color)
    {
        font.drawString(g, str, x, y, color);
    }

    public void draw(GLEx g, int x, int y, float mouseX, float mouseY)
    {
        if(this.capacity > 0)
        {

            int oldColor = g.color();

            int fontSize = font.getSize();

            // 如果没有设置背景，则绘制
            if(bgTexture == null)
            {
                g.setColor(this.listColor);
                g.fillRect(x, y, getWidth(), getHeight());
                g.setColor(255, 255, 255);
                g.drawRect(x, y, getWidth(), getHeight());
            }
            else
            {
                g.draw(bgTexture, x, y, getWidth(), getHeight());
            }

            this.drawNum = (int)((getHeight() - 10) / fontSize);
            this.loop = 0;
            this.selectedIndex = -1;

            for(int i = this.scrollList; i < this.drawNum + this.scrollList; i++)
            {
                if(i >= this.num)
                    break;
                this.drawX = (x + 5);
                this.drawY = (y + 5 + this.loop * fontSize);

                if(!this.scrollBarDrag)
                {
                    if((mouseY > this.drawY) && (mouseY <= this.drawY + fontSize) && (mouseX > this.drawX) && (mouseX < this.drawX + getWidth()))
                    {
                        this.selectedIndex = i;
                    }

                }

                // 计算是否选中当前行

                if((this.selectedIndex == i) || ((this.useHold) && (this.hold == i)))
                {
                    if((this.useHold) && (this.hold == i))
                    {
                        g.setColor(255, 255, 0);
                        g.fillRect(x + 1, this.drawY, getWidth() - 1, fontSize);
                        drawString(g, this.items[i].toString(), this.drawX, this.drawY, LColor.black);
                        this.hold = -1;
                    }
                    // 选中指定列时
                    if(this.selectedIndex == i)
                    {
                        if(choiceTexture == null)
                        {
                            g.setColor(this.choiceStringBoxColor);
                            g.fillRect(x + 1, this.drawY, getWidth() - 2, fontSize + 2);
                        }
                        else
                        {
                            g.draw(this.choiceTexture, x + 2, this.drawY, getWidth() - 2, fontSize + 2);
                        }
                        drawString(g, this.items[i].toString(), this.drawX, this.drawY, this.choiceStringColor);
                    }
                }
                else
                {
                    drawString(g, this.items[i].toString(), this.drawX, this.drawY, defaultStringColor);
                }

                this.loop += 1;
            }

            this.scrollBarX = (int)(x + getWidth() + 1);

            this.scrollBarHeight_max = (int)(getHeight() - this.scrollButtonHeight * 2);

            if((this.drawNum < this.num) && (this.drawNum > 0))
            {
                this.scrollBarHeight = (this.scrollBarHeight_max / this.num / this.drawNum);
                this.scrollBarHeight = (this.scrollBarHeight_max * this.drawNum / this.num);
                if(this.scrollBarHeight < 8)
                    this.scrollBarHeight = 8;

                this.scrollBarY = (y + this.scrollButtonHeight + 1);
                this.scrollBarY += (this.scrollBarHeight_max - this.scrollBarHeight) * this.scrollList / (this.num - this.drawNum);
            }
            else
            {
                this.scrollBarHeight = this.scrollBarHeight_max;
                this.scrollBarY = (y + this.scrollButtonHeight + 1);
            }

            if(this.scrollBarDrag)
            {
                if(mouseY < this.scrollBarY + this.scrollBarHeight / 3)
                {
                    if(this.scrollList > 0)
                        this.scrollList -= 1;
                }

                if(mouseY > this.scrollBarY + this.scrollBarHeight * 2 / 3)
                {
                    if(this.scrollList < this.num - this.drawNum)
                        this.scrollList += 1;
                }
            }

            if(SysTouch.isDrag())
            {
                if((mouseX > this.scrollBarX) && (mouseX <= this.scrollBarX + this.scrollButtonWidth) && (mouseY > y + this.scrollButtonHeight) && (mouseY < y + getHeight() - this.scrollButtonHeight))
                {
                    this.scrollBarDrag = true;
                }
            }
            else
            {
                this.scrollBarDrag = false;
            }

            if(scrollTexture == null)
            {
                if(this.scrollBarDrag)
                {
                    g.setColor(0, 255, 255);
                }
                else
                {
                    g.setColor(255, 255, 255);
                }
                g.fillRect(this.scrollBarX, this.scrollBarY, this.scrollButtonWidth, this.scrollBarHeight);
            }
            else
            {
                g.draw(this.scrollTexture, this.scrollBarX, this.scrollBarY, this.scrollButtonWidth, this.scrollBarHeight);

            }

            this.scrollButtonX = (int)(x + getWidth());
            this.scrollButtonY = y;

            if(scrollFlagATexture == null)
            {
                if(this.scrollUpButtonON)
                {
                    g.setColor(LColor.gray);
                }
                else
                {
                    g.setColor(LColor.black);
                }
                g.fillRect(this.scrollButtonX + 1, this.scrollButtonY + 1, this.scrollButtonWidth, this.scrollButtonHeight);
                g.setColor(255, 255, 255);
                this.px[0] = (this.scrollButtonX + 1 + this.scrollButtonWidth / 6);
                this.px[1] = (this.scrollButtonX + 1 + this.scrollButtonWidth / 2);
                this.px[2] = (this.scrollButtonX + 1 + this.scrollButtonWidth * 5 / 6);
                this.py[0] = (this.scrollButtonY + 1 + this.scrollButtonHeight * 5 / 6);
                this.py[1] = (this.scrollButtonY + 1 + this.scrollButtonHeight / 6);
                this.py[2] = (this.scrollButtonY + 1 + this.scrollButtonHeight * 5 / 6);
                g.fillPolygon(this.px, this.py, 3);
            }
            else
            {
                g.draw(this.scrollFlagATexture, this.scrollButtonX + 1, this.scrollButtonY + 1, this.scrollButtonWidth - 1, this.scrollButtonHeight - 1);
            }

            this.scrollUpButtonON = false;
            if((!this.scrollBarDrag) && isFocusable() && (mouseX > this.scrollButtonX) && (mouseX <= this.scrollButtonX + this.scrollButtonWidth) && (mouseY > this.scrollButtonY) && (mouseY < this.scrollButtonY + this.scrollButtonHeight))
            {
                if(this.scrollList > 0)
                {
                    this.scrollList -= 1;
                }
                this.scrollUpButtonON = true;
            }
            this.scrollButtonX = (int)(x + getWidth());
            this.scrollButtonY = (int)(y + getHeight() - this.scrollButtonHeight);
            this.scrollDownButtonON = false;
            if((!this.scrollBarDrag) && isFocusable() && (mouseX > this.scrollButtonX) && (mouseX <= this.scrollButtonX + this.scrollButtonWidth) && (mouseY > this.scrollButtonY) && (mouseY < this.scrollButtonY + this.scrollButtonHeight))
            {
                if(this.scrollList < this.num - this.drawNum)
                {
                    this.scrollList += 1;
                }
                this.scrollDownButtonON = true;
            }
            if(scrollFlagBTexture == null)
            {
                if(this.scrollDownButtonON)
                {
                    g.setColor(LColor.gray);
                }
                else
                {
                    g.setColor(LColor.black);
                }
                g.fillRect(this.scrollButtonX + 1, this.scrollButtonY - 1, this.scrollButtonWidth, this.scrollButtonHeight);
                g.setColor(LColor.white);
                this.px[0] = (this.scrollButtonX + 1 + this.scrollButtonWidth / 6);
                this.px[1] = (this.scrollButtonX + 1 + this.scrollButtonWidth / 2);
                this.px[2] = (this.scrollButtonX + 1 + this.scrollButtonWidth * 5 / 6);
                this.py[0] = (this.scrollButtonY - 1 + this.scrollButtonHeight / 6);
                this.py[1] = (this.scrollButtonY - 1 + this.scrollButtonHeight * 5 / 6);
                this.py[2] = (this.scrollButtonY - 1 + this.scrollButtonHeight / 6);
                g.fillPolygon(this.px, this.py, 3);
            }
            else
            {
                g.draw(this.scrollFlagBTexture, this.scrollButtonX + 1, this.scrollButtonY + 1, this.scrollButtonWidth - 1, this.scrollButtonHeight - 1);
            }
            g.setColor(oldColor);
        }
    }

    public void setChoiceStringColor(LColor choiceStringColor)
    {
        this.choiceStringColor = choiceStringColor;
    }

    public void setChoiceStringBoxColor(LColor choiceStringBoxColor)
    {
        this.choiceStringBoxColor = choiceStringBoxColor;
    }

    @Override
    public void createUI(GLEx g, int x, int y, LComponent component, LTexture[] buttonImage)
    {
        if(getContainer() == null || !(getContainer() instanceof LScrollContainer))
        {
            draw(g, x, y, SysTouch.getX(), SysTouch.getY());
        }
        else
        {
            draw(g, x, y, ((LScrollContainer)getContainer()).getScrollX() + SysTouch.getX(), ((LScrollContainer)getContainer()).getScrollY() + SysTouch.getY());
        }
    }

    @Override
    public String getUIName()
    {
        return "MyTextList";
    }

    public boolean isVisualized()
    {
        return visualized;
    }

    public void setVisualized(boolean visualized)
    {
        this.visualized = visualized;
    }

    public void setItemSource(T[] items)
    {
        this.items = items;
        this.visualized = true;
        this.num = items.length;
        this.capacity = items.length;
    }
}
