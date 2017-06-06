package zeejfps.sgf;

/**
 * Created by Zeejfps on 6/3/17.
 */
public class Bitmap {

    public final int width;
    public final int height;
    public final int[] pixels;
    public final boolean hasAlpha;

    public Bitmap(int width, int height, int[] pixels, boolean hasAlpha) {
        this.width = width;
        this.height = height;
        this.pixels = pixels;
        this.hasAlpha = hasAlpha;
    }

    public int get(int index) {
        return pixels[index];
    }

    public int get(int x, int y) {
        return get(y* width + x);
    }

    public void set(int index, int color) {
        pixels[index] = color;
    }

    public void set(int x, int y, int color) {
        set(y* width + x, color);
    }

    public void blit(Bitmap bmp, int x, int y) {

        int xe = x+bmp.width;
        int ye = y+bmp.height;

        int sx = x < 0 ? 0 : x;
        int sy = y < 0 ? 0 : y;

        int ex = xe > width ? width : xe;
        int ey = ye > height ? height : ye;

        if (ex < sx || sx > ex) return;
        if (ey < sy || sy > ey) return;

        int srci = (sy-y) * bmp.width + (sx-x);
        int dsti = sy*width + sx;

        if (bmp.hasAlpha) {
            blitLowTransparency(sx, sy, ex, ey, srci, dsti, bmp);
        }
        else {
            blitNoTransparency(sx, sy, ex, ey, srci, dsti, bmp);
        }
    }

    private void blitLowTransparency(
            int sx, int sy,
            int ex, int ey,
            int srci, int dsti,
            Bitmap bmp
    ){
        int dd = width - (ex - sx);
        int ds = bmp.width - (ex - sx);

        int src;

        for (int i = sy; i < ey; i++, dsti += dd, srci += ds) {
            for (int j = sx; j < ex; j++, dsti++, srci++) {
                src = bmp.pixels[srci];
                if ((src & 0xff000000) != 0) pixels[dsti] = src;
            }
        }
    }

    private void blitNoTransparency(
            int sx, int sy,
            int ex, int ey,
            int srci, int dsti,
            Bitmap bmp
    ){
        int length = ex - sx;

        for (; sy < ey; sy++) {
            System.arraycopy(bmp.pixels, srci, pixels, dsti, length);
            srci += bmp.width;
            dsti += width;
        }
    }

}
