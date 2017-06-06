package zeejfps.sgf;

/**
 * Created by Zeejfps on 6/5/17.
 */
public class Sprite {

    public final Vec2f pos;
    public final Vec2f pivot;

    public Bitmap bitmap;

    public Sprite(Bitmap bitmap) {
        this.pos = new Vec2f();
        this.pivot = new Vec2f();
        this.bitmap = bitmap;
    }

}
