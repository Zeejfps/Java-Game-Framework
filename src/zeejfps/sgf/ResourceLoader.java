package zeejfps.sgf;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Zeejfps on 6/3/17.
 */
public class ResourceLoader {

    private ResourceLoader() {}

    public static Bitmap loadBitmap(String path, boolean hasAlpha) {
        try {
            BufferedImage img = ImageIO.read(ResourceLoader.class.getClassLoader().getResourceAsStream(path));
            return loadBitmap(img, hasAlpha);
        } catch (IOException e) {
            System.err.println("Failed to load Bitmap " + path);
            return new Bitmap(0, 0, new int[0], hasAlpha);
        }
    }

    public static Bitmap loadBitmap(BufferedImage img, boolean hasAlpha) {
        int[] pixels = img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, img.getWidth());
        return new Bitmap(img.getWidth(), img.getHeight(), pixels, hasAlpha);
    }

}
