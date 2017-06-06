package zeejfps.sgf;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * Created by Zeejfps on 6/3/17.
 */
public class Framebuffer extends Bitmap {

    private final BufferedImage img;

    private Framebuffer(int width, int height, int[] pixels, BufferedImage img) {
        super(width, height, pixels, false);
        this.img = img;
    }

    BufferedImage getImage() {
        return img;
    }

    public static Framebuffer create(int xRes, int yRes) {
        BufferedImage img = new BufferedImage(xRes, yRes, BufferedImage.TYPE_INT_RGB);
        int[] pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
        return new Framebuffer(xRes, yRes, pixels, img);
    }

}
