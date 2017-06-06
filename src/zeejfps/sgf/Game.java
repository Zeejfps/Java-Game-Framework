package zeejfps.sgf;

import java.util.logging.Logger;

/**
 * Created by Zeejfps on 6/3/17.
 */
public abstract class Game {

    public static final Logger log = Logger.getLogger("GAME");

    private static final double NS_PER_SECOND = 1000000000.0;
    private static final int MAX_FRAMES_SKIP = 5;

    private double nsPerUpdate = 30;
    private boolean running;

    public final void launch() {
        if (running) return;
        running = true;
        loop();
    }

    public final void terminate() {
        if (!running) return;
        running = false;
    }

    public final void setFixedUpdateInterval(double interval) {
        if (interval < 1.0) interval = 1.0;
        nsPerUpdate = NS_PER_SECOND / interval;
    }

    private void loop() {
        int skippedFrames = 0;

        double lag = 0, current, elapsed;
        double previous = System.nanoTime();

        init();
        while(running) {
            current = System.nanoTime();
            elapsed = current - previous;

            previous = current;
            lag += elapsed;

            while (lag >= nsPerUpdate && skippedFrames < MAX_FRAMES_SKIP) {
                fixedUpdate();
                lag -= nsPerUpdate;
                skippedFrames++;
            }
            skippedFrames = 0;

            update();
            render();
        }
    }

    protected abstract void init();

    protected abstract void update();

    protected abstract void fixedUpdate();

    protected abstract void render();

}
