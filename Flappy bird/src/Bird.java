import java.awt.Color;
import java.awt.Graphics;
public class Bird {
    private int x;
    private int y;
    int size = 20;
    private double velocityY;
    private final double gravity;
    public Bird(int x, int y) {
        this.x = x;
        this.y = y;
        this.velocityY = 0;
        this.gravity = 0.4;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void jump() {
        velocityY = -8;
    }
    public void fall() {
        velocityY += gravity; // Apply gravity to the bird's velocity
        y += velocityY; // Update the bird's vertical position
    }
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x, y, size, size);
    }
}