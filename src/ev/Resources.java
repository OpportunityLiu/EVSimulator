package ev;

import loon.*;
import loon.font.BMFont;
import loon.font.IFont;
import loon.utils.res.Texture;
import loon.utils.res.TextureAtlas;
import loon.utils.res.TextureData;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * Created by liuzh on 2016/4/16.
 * Class for visiting resources.
 */
public abstract class Resources
{

    private static LGame game;
    private static Assets assets;
    private static Platform platform;
    private static TextureAtlas texBlue;

    public enum FontInfo
    {
        MENU("menu", "fonts/menu.fnt", "fonts/menu_0.png");

        private final String fontName;
        private IFont font;

        FontInfo(String name, String infoFile, String imageFile)
        {
            fontName = name;
            try
            {
                font = new BMFont(infoFile, imageFile);
            }
            catch(Exception e)
            {
                font = null;
                e.printStackTrace();
            }
        }

        @Contract(pure = true)
        public String fontName()
        {
            return fontName;
        }

        public IFont font()
        {
            return font;
        }
    }

    @Nullable
    public static Texture textures(@NotNull String name)
    {
        if(name.startsWith("blue"))
        {
            return texBlue.getTexture(name);
        }
        return null;
    }

    private static int state = 0;

    public static void init()
    {
        switch(state)
        {
            case 0:
                game = LSystem.base();
                platform = LSystem.platform();
                assets = game.assets();
                break;
            case 1:
                try
                {
                    String json = Resources.assets().getTextSync("controls/blue.json");
                    texBlue = new TextureAtlas(LTexture.createTexture("controls/blue.png"), LSystem.json().parse(json));
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
        state++;
    }

    @Contract(pure = true)
    public static LGame game()
    {
        return game;
    }

    @Contract(pure = true)
    public static Assets assets()
    {
        return assets;
    }

    @Contract(pure = true)
    public static Platform platform()
    {
        return platform;
    }
}
