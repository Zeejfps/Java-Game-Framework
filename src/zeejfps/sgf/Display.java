package zeejfps.sgf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

/**
 * Created by Zeejfps on 6/3/17.
 */
public class Display {

    private static GraphicsDevice device = GraphicsEnvironment
            .getLocalGraphicsEnvironment().getScreenDevices()[0];


    public final Bitmap framebuffer;

    private final JFrame frame;
    private final Canvas canvas;
    private final BufferedImage img;
    private BufferStrategy bs;

    public Display(Config config) {

        // Create the canvas we are going to draw on
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(config.windowWidth, config.windowHeight));
        canvas.setBackground(Color.BLACK);
        canvas.setFocusable(true);

        // Create the frame to hold the canvas
        frame = new JFrame(config.gameTitle);
        frame.getContentPane().add(canvas);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        //frame.setUndecorated(true);
        //device.setFullScreenWindow(frame);

        // Create our framebuffer bitmap
        this.img = new BufferedImage(config.xRes, config.yRes, BufferedImage.TYPE_INT_RGB);
        this.framebuffer = Bitmap.attach(img, true);
    }

    void setKeyListener(KeyListener l) {
        canvas.addKeyListener(l);
    }

    public void clear(int color) {
        Arrays.fill(framebuffer.pixels, color);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public boolean isVisible() {
        return frame.isVisible();
    }

    public void updateFramebuffer() {
        if (bs == null) {
            canvas.createBufferStrategy(2);
            bs = canvas.getBufferStrategy();
        }

        Graphics2D g = (Graphics2D)bs.getDrawGraphics();
        g.drawImage(img, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
        bs.show();
    }

    public int getWidth() {
        return canvas.getWidth();
    }

    public int getHeight() {
        return canvas.getHeight();
    }


}
