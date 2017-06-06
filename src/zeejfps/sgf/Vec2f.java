package zeejfps.sgf;

/**
 * Created by Zeejfps on 6/5/17.
 */
public class Vec2f {

    public float x, y;

    public Vec2f() {
        this(0, 0);
    }

    public Vec2f(float x, float y) {
        this.x = x;
        this.y = y ;
    }

    public Vec2f(Vec2f copy) {
        this.x = copy.x;
        this.y = copy.y;
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Vec2f other) {
        return x == other.x && y == other.y;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Vec2f) {
            Vec2f v = (Vec2f)other;
            return equals(v);
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vec2f[").append(x).append(",").append(y).append("]");
        return sb.toString();
    }

}
