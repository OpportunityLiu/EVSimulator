package ev;

import loon.*;
import loon.font.BMFont;
import loon.font.IFont;
import loon.utils.res.Texture;
import loon.utils.res.TextureAtlas;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Hashtable;
import java.util.concurrent.ExecutionException;

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

    private static final Hashtable<String, IFont> fontsHashtable = new Hashtable<>();
    private static final Hashtable<String, LTexture> imagesHashtable = new Hashtable<>();
    private static final Hashtable<String, Sound> soundsHashtable = new Hashtable<>();

    @Nullable
    public static Texture controls(@NotNull String name)
    {
        if(name.startsWith("blue"))
        {
            return texBlue.getTexture(name);
        }
        return null;
    }

    public static LTexture images(@NotNull String name)
    {
        return imagesHashtable.get(name.toLowerCase());
    }

    public static IFont fonts(@NotNull String name)
    {
        return fontsHashtable.get(name.toLowerCase());
    }


    public static Sound sounds(@NotNull String name)
    {
        return soundsHashtable.get(name.toLowerCase());
    }

    private static class Initailizer
    {
        private static int state = 0;

        private static Json.Object resourceList;
        private static Json.Array imageList, soundList, bmfontList;
        private static int counter;

        public static void init()
        {
            try
            {
                switch(state)
                {
                    case 0:
                        game = LSystem.base();
                        platform = LSystem.platform();
                        assets = game.assets();
                        state++;
                        break;
                    case 1:
                        String json = Resources.assets().getTextSync("controls/blue.json");
                        texBlue = new TextureAtlas(LTexture.createTexture("controls/blue.png"), LSystem.json().parse(json));
                        state++;
                        break;
                    case 2:
                        resourceList = game().json().parse(assets().getTextSync("resources.json"));
                        imageList = resourceList.getArray("images");
                        soundList = resourceList.getArray("sounds");
                        bmfontList = resourceList.getArray("bmfonts");
                        state++;
                        break;
                    case 3:
                        if(counter < imageList.length())
                        {
                            String resourceName = imageList.getString(counter);
                            addImage(resourceName, resourceName);
                            counter++;
                        }
                        else
                        {
                            counter = 0;
                            state++;
                        }
                        break;
                    case 4:
                        if(counter < bmfontList.length())
                        {
                            Json.Object resource = bmfontList.getObject(counter);
                            addBMFont(resource.getString("name"), resource.getString("info"), resource.getString("image"));
                            counter++;
                        }
                        else
                        {
                            counter = 0;
                            state++;
                        }
                        break;
                    case 5:
                        if(counter < soundList.length())
                        {
                            String resource = soundList.getString(counter);
                            addSound(resource, resource);
                            counter++;
                        }
                        else
                        {
                            counter = 0;
                            state++;
                        }
                        break;
                    default:
                        if(state != -1)
                            game.log().debug("Resource loaded.");
                        state = -1;
                        break;
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        @NotNull
        @Contract(pure = true)
        public static Boolean loaded()
        {
            return state == -1;
        }

        private static void addBMFont(String name, String infoFile, String imageFile) throws Exception
        {
            final String prefix = "fonts/";

            BMFont font = new BMFont(prefix + infoFile, prefix + imageFile);
            fontsHashtable.put(name.toLowerCase(), font);
        }

        private static void addImage(String name, String file)
        {
            final String prefix = "images/";

            imagesHashtable.put(name.toLowerCase(), LTexture.createTexture(prefix + file));
        }

        private static void addSound(String name, String file)
        {
            final String prefix = "sounds/";

            soundsHashtable.put(name.toLowerCase(), assets().getSound(prefix + file));
        }
    }

    public static void init()
    {
        Initailizer.init();
    }


    @Contract(pure = true)
    public static boolean loaded()
    {
        return Initailizer.loaded();
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
