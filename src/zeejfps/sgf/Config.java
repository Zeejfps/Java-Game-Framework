package zeejfps.sgf;

/**
 * Created by Zeejfps on 6/6/17.
 */
public class Config {

    int xRes = 320;
    int yRes = 180;
    int windowWidth = 1280;
    int windowHeight = 720;
    String gameTitle = "Untitled game";

    public void setGameTitle(String title) {
        this.gameTitle = gameTitle;
    }

    public void setWindowSize(int width, int height) {
        this.windowWidth = width;
        this.windowHeight = height;
    }

    public void setFramebufferRes(int xRes, int yRes) {
        this.xRes = xRes;
        this.yRes = yRes;
    }

}
