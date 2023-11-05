import java.awt.*;
public class Ground {
    private int y;
    private int height;
    public Ground(int y, int height) {
        this.y = y;
        this.height = height;
    }
    public int getY() {
        return y;
    }
    public void draw(Graphics g){
        g.setColor(Color.ORANGE);
        g.fillRect(0, y, 800, height);
    }
}