package zeejfps.sgf;

/**
 * Created by zeejfps on 4/21/17.
 */
public class Clock {

    public static final double MS_PER_SC = 1000.0;
    public static final double NS_PER_MS = 1000000.0;

    private float scale;
    private double prevTime;
    private boolean running;

    private float deltaTimeMS; // delta time in Milliseconds
    private float deltaTimeNS; // delta time in Nanoseconds
    private float deltaTime; // delta time in Seconds

    public Clock() {
        this(1.0f);
    }

    public Clock(float scale) {
        setScale(scale);
    }

    public void start() {
        if (running) return;
        running = true;

        prevTime = System.nanoTime();
    }

    public void stop() {
        running = false;
    }

    public void tick() {
        if (!running) return;
        deltaTimeNS = (float) ((System.nanoTime() - prevTime) * scale);
        deltaTimeMS = (float) (deltaTimeNS / NS_PER_MS);
        deltaTime = (float) (deltaTimeMS / MS_PER_SC);
        prevTime = System.nanoTime();
    }

    public void reset() {
        deltaTimeNS = 0;
        deltaTimeMS = 0;
        prevTime = System.nanoTime();
    }

    public float getDeltaTimeMS() {
        return deltaTimeMS;
    }

    public float getDeltaTimeNS() {
        return deltaTimeNS;
    }

    public float getDeltaTime() {
        return deltaTime;
    }

    public void setScale(float scale) {
        if (scale <= 0.0) scale = 0.1f;
        this.scale = scale;
    }

}