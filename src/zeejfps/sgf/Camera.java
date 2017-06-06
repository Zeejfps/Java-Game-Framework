package zeejfps.sgf;

/**
 * Created by Zeejfps on 6/5/17.
 */
public class Camera {

    public final Vec2f pos;

    private final Framebuffer fb;
    private final float numUnitsX, numUnitsY;
    private final int pixelsPerUnit;

    public Camera(int xRes, int yRes, int pixelsPerUnit) {
        this.fb = Framebuffer.create(xRes, yRes);
        this.pixelsPerUnit = pixelsPerUnit;
        this.pos = new Vec2f(0,0);
        this.numUnitsX = xRes / (float) pixelsPerUnit / 2f;
        this.numUnitsY = yRes / (float) pixelsPerUnit / 2f;
    }

    public Framebuffer getFramebuffer() {
        return fb;
    }

    public void render(Renderable r) {
        Sprite sprite = r.onRender();

        float viewX = sprite.pos.x + pos.x + numUnitsX;
        float viewY = sprite.pos.y + pos.y + numUnitsY;

        int screenX = (int)(viewX * pixelsPerUnit - sprite.pivot.x * sprite.bitmap.width);
        int screenY = (int)(viewY * pixelsPerUnit - sprite.pivot.y * sprite.bitmap.height);

        fb.blit(sprite.bitmap, screenX, screenY);
    }

}
