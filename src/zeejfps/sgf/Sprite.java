package zeejfps.sgf;

/**
 * Created by Zeejfps on 6/5/17.
 */
public class Sprite {

    public final Vec2f pos;
    public final Vec2f pivot;

    private Bitmap bitmap;
    private Bitmap cached;

    public final int pixelsPerUnit;
    private final Vec2f size;

    private int unitSize;
    private boolean isDirty;

    public Sprite(Bitmap bitmap, int pixelsPerUnit) {
        this.bitmap = bitmap;
        this.pos = new Vec2f();
        this.pivot = new Vec2f();
        this.size = new Vec2f((float)bitmap.width / pixelsPerUnit, (float)bitmap.height / pixelsPerUnit);
        this.pixelsPerUnit = pixelsPerUnit;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        isDirty = true;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    protected Bitmap onRender(int unitSize) {

        if (cached != null && !isDirty && this.unitSize == unitSize) {
            return cached;
        }

        System.out.printf("Not Cached!");

        int w = (int) (size.x * unitSize);
        int h = (int) (size.y * unitSize);

        float dx = (float)bitmap.width / w;
        float dy = (float)bitmap.height / h;

        int index = 0;
        float x, y = 0;
        int[] pixels = new int[w*h];
        for (int i = 0; i < h; i++, y += dy) {
            x = 0;
            for (int j = 0; j < w; j++, x += dx, index++) {
                pixels[index] = bitmap.pixels[(int)(y)*bitmap.width + (int)x];
            }
        }

        cached = new Bitmap(w, h, pixels, bitmap.hasAlpha);
        this.unitSize = unitSize;
        isDirty = false;

        return cached;
    }
}
