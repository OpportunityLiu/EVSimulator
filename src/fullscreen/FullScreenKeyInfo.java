package fullscreen;

import java.awt.event.KeyEvent;

/**
 * Objects of this class represent KeyEvent objects.
 * This objects can be used to check which keys are currently pressed.
 * 
 * @author Gevater_Tod4711
 * @version 1.1
 */
class FullScreenKeyInfo
{
    private KeyEvent keyEvent;
    
    /**
     * Create a new fullscreen.FullScreenKeyInfo object with a KeyEvent object.
     * 
     * @throws IllegalArgumentException
     *      An IllegalArgumentException is thrown when the given KeyEvent object is null.
     */
    FullScreenKeyInfo(KeyEvent keyEvent) throws IllegalArgumentException {
        if (keyEvent == null) {
            throw new IllegalArgumentException("The given KeyEvent object mustn't be null");
        }
        this.keyEvent = keyEvent;
    }
    
    /**
     * Check whether a key is pressed for this object.
     * This method is not static and has not the same function as Greenfoot.isKeyDown(String).
     * The equivalent to Greenfoot.isKeyDown(String) is fullscreen.FullScreenWindow.isKeyDown(String).
     */
    boolean isKeyDown(String keyName) {
        keyName = keyName.toLowerCase();
        if (keyName.length() == 1) {
            return KeyEvent.getKeyText(keyEvent.getKeyCode()).toLowerCase().equals(keyName);// == KeyEvent.getExtendedKeyCodeForChar(keyName.charAt(0));
        }
        else {
            switch(keyName)
            {
                case "left":
                    return keyEvent.getKeyCode() == KeyEvent.VK_LEFT;
                case "right":
                    return keyEvent.getKeyCode() == KeyEvent.VK_RIGHT;
                case "up":
                    return keyEvent.getKeyCode() == KeyEvent.VK_UP;
                case "down":
                    return keyEvent.getKeyCode() == KeyEvent.VK_DOWN;
                case "enter":
                    return keyEvent.getKeyCode() == KeyEvent.VK_ENTER;
                case "space":
                    return keyEvent.getKeyCode() == KeyEvent.VK_SPACE;
                case "shift":
                    return keyEvent.isShiftDown();
                case "escape":
                    return keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE;
                case "backspace":
                    return keyEvent.getKeyCode() == KeyEvent.VK_BACK_SPACE;
                case "ctrl":
                case "control":
                case "strg":
                    return keyEvent.isControlDown();
                case "alt":
                    return keyEvent.isAltDown();
                case "altgr":
                    return keyEvent.isAltGraphDown();
                case "tab":
                case "tabulator":
                    return keyEvent.getKeyCode() == KeyEvent.VK_TAB;
                case "f1":
                    return keyEvent.getKeyCode() == KeyEvent.VK_F1;
                case "f2":
                    return keyEvent.getKeyCode() == KeyEvent.VK_F2;
                case "f3":
                    return keyEvent.getKeyCode() == KeyEvent.VK_F3;
                case "f4":
                    return keyEvent.getKeyCode() == KeyEvent.VK_F4;
                case "f5":
                    return keyEvent.getKeyCode() == KeyEvent.VK_F5;
                case "f6":
                    return keyEvent.getKeyCode() == KeyEvent.VK_F6;
                case "f7":
                    return keyEvent.getKeyCode() == KeyEvent.VK_F7;
                case "f8":
                    return keyEvent.getKeyCode() == KeyEvent.VK_F8;
                case "f9":
                    return keyEvent.getKeyCode() == KeyEvent.VK_F9;
                case "f10":
                    return keyEvent.getKeyCode() == KeyEvent.VK_F10;
                case "f11":
                    return keyEvent.getKeyCode() == KeyEvent.VK_F11;
                case "f12":
                    return keyEvent.getKeyCode() == KeyEvent.VK_F12;
            }
        }
        return false;
    }
    
    /**
     * Get the name of the key this KeyEvent represents.
     */
    String getKey() {
        return KeyEvent.getKeyText(keyEvent.getKeyCode());
    }
}