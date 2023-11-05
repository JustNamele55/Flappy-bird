import java.awt.*;
public class Pipes {
    private int x;
    private int gapY;
    private int gapHeight;
    private int width;
    private int height;
    private int speed;
    public Pipes(int x, int gapY, int gapHeight, int width, int height, int speed) {
        this.x = x;
        this.gapY = gapY;
        this.gapHeight = gapHeight;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }
    public void move() {
        x -= speed;
    }
    public int getX() {
        return x;
    }
    public int getWidth() {
        return width;
    }
    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x, 0, width, gapY);
        g.fillRect(x, gapY + gapHeight, width, height - gapY - gapHeight);
    }
    public int getGapY() {
        return gapY;
    }
    public int getGapHeight() {
        return gapHeight;
    }
    public int getHeight() {
        return height;
    }
}
