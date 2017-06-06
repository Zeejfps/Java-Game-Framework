package zeejfps.sgf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zeejfps on 6/3/17.
 */
public class Display {

    private final JFrame frame;
    private final Canvas canvas;

    private final BufferedImage img;
    private final Bitmap framebuffer;

    private BufferStrategy bs;
    private List<Viewport> viewports;

    public Display(int xRes, int yRes, int width, int height, String title) {

        // Create the canvas we are going to draw on
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setBackground(Color.BLACK);
        canvas.setFocusable(true);

        // Create the frame to hold the canvas
        frame = new JFrame(title);
        frame.getContentPane().add(canvas);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);

        // Create our framebuffer bitmap
        this.img = new BufferedImage(xRes, yRes, BufferedImage.TYPE_INT_RGB);
        int[] pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
        this.framebuffer = new Bitmap(xRes, yRes, pixels, false);

         viewports = new ArrayList<>();
    }

    void setKeyListener(KeyListener l) {
        canvas.addKeyListener(l);
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
        for (Viewport vp : viewports) {
            framebuffer.blit(vp, vp.x, vp.y);
        }
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

    public Viewport createViewport(float x, float y, float w, float h) {

        int sx = (int) (x * framebuffer.width);
        int ex = (int) (w * framebuffer.width);
        int sy = (int) (y * framebuffer.height);
        int ey = (int) (h * framebuffer.height);
        int width = ex - sx;
        int height = ey - sy;

        Viewport vp = new Viewport(sx, sy, width, height, new int[width*height], false);
        viewports.add(vp);
        return vp;
    }

}
