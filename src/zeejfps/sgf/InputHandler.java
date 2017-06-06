package zeejfps.sgf;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

/**
 * Created by Zeejfps on 6/3/17.
 */
public class InputHandler {

    public static final int MAX_KEYS = 512;

    private final boolean[] keysNow = new boolean[MAX_KEYS];
    private final boolean[] keysPrev = new boolean[MAX_KEYS];

    public InputHandler(Display display) {
        display.setKeyListener(new KeyHandler());
    }

    public boolean isKeyDown(int code) {
        return keysNow[code];
    }

    public boolean wasKeyPressed(int code) {
        return keysNow[code] && !keysPrev[code];
    }

    public boolean wasKeyReleased(int code) {
        return !keysNow[code] && keysPrev[code];
    }

    public void update() {
        System.arraycopy(keysNow, 0, keysPrev, 0, MAX_KEYS);
    }

    private class KeyHandler implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            keysNow[e.getKeyCode()] = true;
        }

        @Override
        public void keyReleased(KeyEvent e) {
            keysNow[e.getKeyCode()] = false;
        }

    }

}
