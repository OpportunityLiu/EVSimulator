package ev;

import loon.*;
import loon.font.BMFont;
import loon.font.IFont;
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
    private static HashMap<String, IFont> fontDictionary = new HashMap<>();
    private static TextureAtlas texBlue;

    @Nullable
    public static LTexture textures(@NotNull String name)
    {
        if(name.startsWith("blue"))
        {
            TextureData info = texBlue.getFrame(name);
            if(info == null)
                return null;
            return texBlue.img().copy(info.x(), info.y(), info.w(), info.h());
        }
        return null;
    }

    public static void init()
    {
        game = LSystem.base();
        platform = LSystem.platform();
        assets = game.assets();
        try
        {
            String json = Resources.assets().getTextSync("controls/blue.json");
            fontDictionary.put("menu", new BMFont("fonts/menu.fnt", "fonts/menu.png"));
            texBlue = new TextureAtlas(LTexture.createTexture("controls/blue.png"), LSystem.json().parse(json));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static IFont getFont(String name)
    {
        return fontDictionary.get(name);
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
