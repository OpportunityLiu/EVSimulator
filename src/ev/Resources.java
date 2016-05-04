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

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by liuzh on 2016/4/16.
 * Class for visiting resources.
 */
public abstract class Resources
{

    private static LGame game;
    private static Assets assets;
    private static Platform platform;

    private static TextureAtlas textureAtlasUI;

    private static final Hashtable<String, IFont> fontsHashtable = new Hashtable<>();
    private static final Hashtable<String, LTexture> imagesHashtable = new Hashtable<>();
    private static final Hashtable<String, LTexture> spiritsHashtable = new Hashtable<>();
    private static final Hashtable<String, Sound> soundsHashtable = new Hashtable<>();

    @Nullable
    public static Texture controls(@NotNull String name)
    {
        return textureAtlasUI.getTexture(name);
    }

    @Nullable
    public static LTexture controlsTexture(@NotNull String name)
    {
        Texture t = controls(name);
        if(t == null)
            return null;
        TextureData data = t.getTextureData();
        return t.img().copy(data.x(), data.y(), data.w(), data.h());
    }

    public static LTexture images(@NotNull String name)
    {
        final LTexture texture = imagesHashtable.get(name.toLowerCase());
        if(texture.isClose())
        {
            return Initailizer.addImage(name, texture.getSource());
        }
        else
            return texture;
    }

    public static LTexture spirits(@NotNull String name)
    {
        final LTexture texture = spiritsHashtable.get(name.toLowerCase());
        if(texture.isClose())
        {
            return Initailizer.addSpirit(name, texture.getSource());
        }
        else
            return texture;
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
        private static Json.Array imageList, soundList, bmfontList, spiritList;
        private static int counter;

        static void init()
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
                    String json = Resources.assets().getTextSync("controls/ui.json");
                    textureAtlasUI = new TextureAtlas(LTexture.createTexture("controls/ui.png"), LSystem.json()
                                                                                                        .parse(json));
                    state++;
                    break;
                case 2:
                    resourceList = game().json().parse(assets().getTextSync("resources.json"));
                    imageList = resourceList.getArray("images");
                    soundList = resourceList.getArray("sounds");
                    bmfontList = resourceList.getArray("bmfonts");
                    spiritList = resourceList.getArray("spirits");
                    overall = imageList.length() + soundList.length() + bmfontList.length() + spiritList.length();
                    state++;
                    break;
                case 3:
                    if(counter < imageList.length())
                    {
                        String resourceName = imageList.getString(counter);
                        addImage(resourceName, resourceName);
                        current++;
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
                        current++;
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
                        current++;
                        counter++;
                    }
                    else
                    {
                        counter = 0;
                        state++;
                    }
                    break;
                case 6:
                    if(counter < spiritList.length())
                    {
                        String resourceName = spiritList.getString(counter);
                        addSpirit(resourceName, resourceName);
                        current++;
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
                        game.log().info("Resource loaded.");
                    state = -1;
                    break;
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        private static int overall, current;

        @Contract(pure = true)
        static int current()
        {
            if(state < 3)
                return 0;
            return current;
        }

        @Contract(pure = true)
        static int overall()
        {
            if(state < 3)
                return 1;
            return overall;
        }

        @NotNull
        @Contract(pure = true)
        static Boolean loaded()
        {
            return state == -1;
        }

        private static BMFont addBMFont(String name, String infoFile, String imageFile) throws Exception
        {
            final String prefix = "fonts/";

            BMFont font = new BMFont(prefix + infoFile, prefix + imageFile);
            fontsHashtable.put(name.toLowerCase(), font);
            return font;
        }

        private static LTexture addImage(String name, String file)
        {
            final String prefix = "images/";
            final String source = file.startsWith(prefix) ? file : (prefix + file);
            imagesHashtable.remove(name);
            final LTexture texture = LTexture.createTexture(source);
            imagesHashtable.put(name.toLowerCase(), texture);
            return texture;
        }

        private static LTexture addSpirit(String name, String file)
        {
            final String prefix = "spirits/";
            final String source = file.startsWith(prefix) ? file : (prefix + file);
            spiritsHashtable.remove(name);
            final LTexture texture = LTexture.createTexture(source);
            spiritsHashtable.put(name.toLowerCase(), texture);
            return texture;
        }

        private static Sound addSound(String name, String file)
        {
            final String prefix = "sounds/";
            final Sound sound = assets().getSound(prefix + file);
            soundsHashtable.put(name.toLowerCase(), sound);
            return sound;
        }
    }

    public static void init()
    {
        if(loaded())
        {
            listeners.forEach(LoadingListener::onLoaded);
            listeners.clear();
        }
        else
        {
            Initailizer.init();
            if(!listeners.isEmpty())
            {
                final int current = Initailizer.current(), overall = Initailizer.overall();
                listeners.forEach(loadingListener -> loadingListener.onLoading(current, overall));
            }
        }
    }

    @Contract(pure = true)
    public static boolean loaded()
    {
        return Initailizer.loaded();
    }

    private static final ArrayList<LoadingListener> listeners = new ArrayList<>();

    public static void addLoadingListener(@NotNull LoadingListener listener)
    {
        if(loaded())
        {
            listener.onLoaded();
        }
        else
        {
            listeners.add(listener);
        }
    }

    public interface LoadingListener
    {
        void onLoaded();

        void onLoading(int currentStep, int overallStep);
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
