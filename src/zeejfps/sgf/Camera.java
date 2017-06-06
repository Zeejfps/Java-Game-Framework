package zeejfps.sgf;

/**
 * Created by Zeejfps on 6/5/17.
 */
public class Camera {

    public final Vec2f pos;

    private final Bitmap fb;

    private Vec2f size;
    private int unitSize;
    private float aspect;

    public Camera(Bitmap framebuffer, int size) {
        this.fb = framebuffer;
        this.pos = new Vec2f(0,0);
        this.aspect = framebuffer.width / (float)framebuffer.height;
        this.size = new Vec2f();
        setSize(size);
    }

    public void setSize(int size) {
        if (size < 1) size = 1;
        unitSize = fb.height / size;
        this.size.y = size / 2f;
        this.size.x = this.size.y * aspect;
    }

    public void render(Sprite sprite) {

        Bitmap bitmap = sprite.onRender(unitSize);

        float viewX = sprite.pos.x + pos.x + size.x;
        float viewY = sprite.pos.y + pos.y + size.y;

        int screenX = (int)(viewX * unitSize - sprite.pivot.x * bitmap.width);
        int screenY = (int)(viewY * unitSize - sprite.pivot.y * bitmap.height);

        fb.blit(bitmap, screenX, screenY);
    }

}
