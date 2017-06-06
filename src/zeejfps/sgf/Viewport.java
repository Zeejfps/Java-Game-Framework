package zeejfps.sgf;

import java.util.Arrays;

/**
 * Created by Zeejfps on 6/5/17.
 */
public class Viewport extends Bitmap {

    public final int x, y;

    Viewport(int x, int y, int width, int height, int[] pixels, boolean hasAlpha) {
        super(width, height, pixels, hasAlpha);
        this.x = x;
        this.y = y;
    }

    public void clear(int color) {
        Arrays.fill(pixels, color);
    }

}
