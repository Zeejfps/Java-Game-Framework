package zeejfps.sgf;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

/**
 * Created by Zeejfps on 6/3/17.
 */
public class Bitmap {

    public final int width;
    public final int height;
    public final int[] pixels;
    public final boolean isOpaque;

    private Bitmap(int width, int height, int[] pixels, boolean isOpaque) {
        this.width = width;
        this.height = height;
        this.pixels = pixels;
        this.isOpaque = isOpaque;
        if (!isOpaque) {
            for (int i = 0; i < pixels.length; i++) {
                int src = pixels[i];

                int a = (src>>24) & 0xff;
                float alpha = a / 255f;

                int r = (int)(((src>>16) & 0xff) * alpha);
                int g = (int)(((src>>8) & 0xff) * alpha);
                int b = (int)(((src) & 0xff) * alpha);

                pixels[i] = a << 24 | r << 16 | g << 8 | b;
            }
        }
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

        if (bmp.isOpaque) {
            blitOpaque(sx, sy, ex, ey, srci, dsti, bmp);
        }
        else {
            blitTransparent(sx, sy, ex, ey, srci, dsti, bmp);
        }
    }

    /*
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
    */

    private void blitOpaque(
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

    private void blitTransparent(
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
                pixels[dsti] = blend(src, pixels[dsti]);
            }
        }
    }

    private int blend(int src, int dst) {
        int sa = (src >> 24) & 0xff;
        if (sa == 0)
            return dst;

        int da = (dst>>24) & 0xff;
        if (da == 0)
            return src;

        float srcA = sa / 255f;
        float dstA = da / 255f;
        float invA = 1f - srcA;
        float oa = sa + dstA * (1 - srcA);

        int sr = (src>>16) & 0xff;
        int sg = (src>>8) & 0xff;
        int sb = (src) & 0xff;

        int dr = (dst>>16) & 0xff;
        int dg = (dst>>8) & 0xff;
        int db = (dst) & 0xff;

        int a = (int) (oa * 255f);
        int r = (int) (sr + dr*invA);
        int g = (int) (sg + dg*invA) ;
        int b = (int) (sb + db*invA);

        return a << 24 | r << 16 | g << 8 | b;
    }

    /*
    private int blend(int src, int dst) {

        int sa = (src >> 24) & 0xff;
        if (sa == 0)
            return dst;

        int da = (dst>>24) & 0xff;
        if (da == 0)
            return src;

        float srcA = sa / 255f;
        float dstA = da / 255f;
        float oa = srcA + dstA * (1 - srcA);
        if (oa == 0)
            return 0;

        int sr = (src>>16) & 0xff;
        int sg = (src>>8) & 0xff;
        int sb = (src) & 0xff;

        int dr = (dst>>16) & 0xff;
        int dg = (dst>>8) & 0xff;
        int db = (dst) & 0xff;

        int A = (int) (oa * 255f);
        int R = (int) ((sr * srcA + dr*dstA*(1f-srcA)) / oa);
        int G = (int) ((sg * srcA + dg*dstA*(1f-srcA)) / oa) ;
        int B = (int) ((sb * srcA + db*dstA*(1f-srcA)) / oa);

        return A << 24 | R << 16 | G << 8 | B;
    }
    */

    /*
    private int blend(int src, int dst, float alpha) {
        if (alpha == 0)
            return dst;

        int sr = (src>>16) & 0xff;
        int sg = (src>>8) & 0xff;
        int sb = (src) & 0xff;

        int dr = (dst>>16) & 0xff;
        int dg = (dst>>8) & 0xff;
        int db = (dst) & 0xff;

        int rr = (int) (sr * alpha + dr*(1f-alpha));
        int gg = (int) (sg * alpha + dg*(1f-alpha));
        int bb = (int) (sb * alpha + db*(1f-alpha));

        return rr << 16 | gg << 8 | bb;
    }
    */

    public static Bitmap load(String path, boolean isOpaque) {
        try {
            BufferedImage img = ImageIO.read(Bitmap.class.getClassLoader().getResourceAsStream(path));
            int[] pixels = img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, img.getWidth());
            return new Bitmap(img.getWidth(), img.getHeight(), pixels, isOpaque);
        } catch (IOException e) {
            System.err.println("Failed to load Bitmap " + path);
            return new Bitmap(0, 0, new int[0], isOpaque);
        }
    }

    public static Bitmap create(int width, int height, boolean isOpaque) {
        return new Bitmap(width, height, new int[width*height], isOpaque);
    }

    public static Bitmap attach(BufferedImage img, boolean isOpaque) {
        int[] pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
        return new Bitmap(img.getWidth(), img.getHeight(), pixels, isOpaque);
    }

}
