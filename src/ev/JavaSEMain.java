package ev;

import ev.screens.SplashScreen;
import loon.*;
import loon.Display;
import loon.canvas.*;
import loon.canvas.Image;
import map.*;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.*;
import org.lwjgl.input.Cursor;

import loon.javase.Loon;

import java.awt.*;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class JavaSEMain
{
    public static void main(String[] args)
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        LSetting setting = new LSetting();
        setting.isFPS = true;
        setting.isLogo = false;
        setting.logoPath = "loon_logo.png";
        // 原始大小
        setting.width = 1280;
        setting.height = 960;
        if(setting.fullscreen = DebugSettings.fullscreen)
        {
            double scale = Math.min(screenSize.getWidth() / setting.width, screenSize.getHeight() / setting.height);
            setting.width_zoom = (int)(setting.width * scale);
            setting.height_zoom = (int)(setting.height * scale);
        }
        setting.fontName = "Tsentsiu HG";
        setting.appName = "EV Simulator";
        setting.gameType = GameType.SLG;
        Loon.register(setting, new LazyLoading.Data()
        {
            private Image pic;

            @Override
            public Screen onScreen()
            {
                pic = Display.loadImage("icon.png");
                ByteBuffer buffers[] = new ByteBuffer[2];
                buffers[0] = getBuffer(16);
                buffers[1] = getBuffer(32);
                org.lwjgl.opengl.Display.setIcon(buffers);
                try
                {
                    Image cursorImage = Display.loadImage("cursors/normal.png");
                    IntBuffer buffer = IntBuffer.wrap(cursorImage.getPixels());
                    Cursor cursor = new Cursor(cursorImage.pixelWidth(), cursorImage.pixelHeight(), 1, cursorImage.pixelHeight() - 1, 1, buffer, null);
                    Mouse.setNativeCursor(cursor);
                }
                catch(LWJGLException e)
                {
                    e.printStackTrace();
                }
                return new SplashScreen();
            }

            @NotNull
            private ByteBuffer getBuffer(int size)
            {
                Image picR = Image.getResize(pic, size, size);
                return ByteBuffer.wrap(LColor.argbToRGBA(picR.getPixels()));
            }
        });
        // Can't reach!!
    }
}
