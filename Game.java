import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;
public class Game extends JFrame implements KeyListener, ActionListener {
    private Bird bird;
    private GameState gameState;
    private Timer gameTimer;
    private GamePanel gamePanel;
    private Ground ground;
    private List<Pipes> pipes;
    private boolean isSpacePressed = false;
    private int score = 0;
    public Game() {
        bird = new Bird(100, 300);
        gameState = GameState.MENU;
        ground = new Ground(500, 10);
        pipes = new ArrayList<>();
        setTitle("Flappy Bird Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        addKeyListener(this);
        generatePipes();
        gamePanel = new GamePanel();
        add(gamePanel);
        gameTimer = new Timer(10, this);
        gameTimer.start();
        setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        if (gameState == GameState.PLAYING) {
            bird.fall();
            updatePipes();
            checkPipesCollisions();
            // Collision with top of window
            if (bird.getY() < 0) {
                gameState = GameState.GAME_OVER;
            }
            // Collision with ground
            if (bird.getY() + bird.size >= ground.getY()) {
                gameState = GameState.GAME_OVER;
            }
            repaint(); // Repaint the game screen through the custom panel
        }
    }
    private void checkPipesCollisions() {
        Rectangle birdRect = new Rectangle(bird.getX(), bird.getY(), bird.size, bird.size);
        for (Pipes pipe : pipes) {
            Rectangle pipeRect = new Rectangle(pipe.getX(), 0, pipe.getWidth(), pipe.getGapY());
            Rectangle lowerPipeRect = new Rectangle(pipe.getX(), pipe.getGapY() + pipe.getGapHeight(), pipe.getWidth(), pipe.getHeight() - pipe.getGapY() - pipe.getGapHeight());
            if (birdRect.intersects(pipeRect) || birdRect.intersects(lowerPipeRect)) {
                gameState = GameState.GAME_OVER;
            }
        }
    }
    private void updatePipes() {
        // Go through the list in reverse to remove pipes
        for (int i = pipes.size() - 1; i >= 0; i--) {
            Pipes pipe = pipes.get(i);
            pipe.move();
            if (pipe.getX() + pipe.getWidth() < 0) {
                pipes.remove(i); // Remove off-screen pipes
                generatePipe(1600);
                score++;
            }
        }
    }
    private void generatePipe(int x) {
        int gapY = new Random().nextInt(300) + 50; //Gap position
        int gapHeight = 125;
        int pipeSpeed = 4;
        int pipeWidth = 80;
        pipes.add(new Pipes(x, gapY, gapHeight, pipeWidth, 600, pipeSpeed));
    }
    private void generatePipes() {
        if (pipes.isEmpty()) {
            int x = 800;
            for (int i = 0; i < 3; i++) {
                int gapY = new Random().nextInt(300) + 50; //Gap position
                generatePipe(x);
                int pipeSpacing = 500;
                x += pipeSpacing;
            }
        }
    }
    private void drawPipes(Graphics g) {
        for (Pipes pipe : pipes) {
            pipe.draw(g);
        }
    }
    @Override
    public void keyTyped(KeyEvent e){}
    @Override
    public void keyPressed(KeyEvent e) {
        if (gameState == GameState.MENU && e.getKeyCode() == KeyEvent.VK_SPACE) {
            gameState = GameState.PLAYING;
            gameTimer.start();
        } else if (gameState == GameState.GAME_OVER && e.getKeyCode() == KeyEvent.VK_SPACE) {
            gameState = GameState.PLAYING;
            resetGame();
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE && !isSpacePressed) {
            bird.jump();
            isSpacePressed = true;
        } else if ((gameState == GameState.MENU && e.getKeyCode() == KeyEvent.VK_ESCAPE) || (gameState == GameState.GAME_OVER && e.getKeyCode() == KeyEvent.VK_ESCAPE)) {
            System.exit(0);
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            isSpacePressed = false;
        }
    }
    private class GamePanel extends JPanel {
        public GamePanel() {
            ground = new Ground(500, 20);
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (gameState == GameState.MENU) {
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.BOLD, 36));
                g.drawString("Flappy Bird", 300, 100);
                g.setFont(new Font("Arial", Font.PLAIN, 24));
                g.drawString("Press SPACE to Start", 280, 200);
                g.drawString("Press ESC to Quit", 290, 250);
            } else if (gameState == GameState.PLAYING) {
                bird.draw(g);
                drawPipes(g);
                ground.draw(g);
            } else if (gameState == GameState.GAME_OVER) {
                g.setColor(Color.RED);
                g.setFont(new Font("Arial", Font.BOLD, 36));
                g.drawString("Game Over", 300, 100);
                g.setFont(new Font("Arial", Font.PLAIN, 24));
                g.drawString("Press SPACE to Restart", 270, 200);
                g.drawString("Press ESC to Quit", 290, 250);
            }
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Score: " + score, 20, 20);
        }
    }
    private void resetGame(){
        bird = new Bird(100, 300);
        pipes.clear();
        generatePipes();
        gameState = GameState.PLAYING;
    }
}
