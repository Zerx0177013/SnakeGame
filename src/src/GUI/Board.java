package GUI;
import Entity.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Board extends JPanel implements KeyListener {
    int cellSize = 20;
    int rows = 20;
    int cols = 30;
    private Timer timer;
    Snake petitSerpent;
    Point food;
    ScorePanel scorePanel;
    private boolean gameOver = false;
    private boolean paused = false;
    int delay = 100;

    Sound eatSound = new Sound("/sounds/eat.wav");
    Sound gameOverSound = new Sound("/sounds/gameover.wav");
    Sound music = new Sound("/sounds/background.wav");


    public Board(ScorePanel scorePanel) {
        this.scorePanel = scorePanel;
        petitSerpent = new Snake();
        spawnFood();
        setFocusable(true);
        addKeyListener(this);
        requestFocusInWindow();
        music.play();
        music.loop();

        timer = new Timer(delay, e -> {
            updateGame();
            repaint();
        });
        timer.start();
    }

    public int getCellSize() {
        return cellSize;
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if ((key == KeyEvent.VK_UP && petitSerpent.getDirection() != Snake.Direction.DOWN)
                || (key == KeyEvent.VK_W && petitSerpent.getDirection() != Snake.Direction.DOWN)) {
            petitSerpent.setDirection(Snake.Direction.UP);
        } else if ((key == KeyEvent.VK_DOWN && petitSerpent.getDirection() != Snake.Direction.UP)
                || (key == KeyEvent.VK_S && petitSerpent.getDirection() != Snake.Direction.UP)) {
            petitSerpent.setDirection(Snake.Direction.DOWN);
        } else if ((key == KeyEvent.VK_LEFT && petitSerpent.getDirection() != Snake.Direction.RIGHT)
                || (key == KeyEvent.VK_A && petitSerpent.getDirection() != Snake.Direction.RIGHT)) {
            petitSerpent.setDirection(Snake.Direction.LEFT);
        } else if ((key == KeyEvent.VK_RIGHT && petitSerpent.getDirection() != Snake.Direction.LEFT)
                || (key == KeyEvent.VK_D && petitSerpent.getDirection() != Snake.Direction.LEFT)) {
            petitSerpent.setDirection(Snake.Direction.RIGHT);
        }
        if (gameOver) {
            if (key == KeyEvent.VK_ENTER) {
                restartGame();
            }
            return;
        }
        if (key == KeyEvent.VK_P || key == KeyEvent.VK_ENTER) {
            if (paused) {
                continueGame();
                eatSound.resume();
                music.resume();
                music.loop();
            } else {
                pauseGame();
                music.pause();
                eatSound.pause();
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public void updateGame(){
        if (gameOver) return;

        petitSerpent.move(cols, rows);
        if (petitSerpent.getBody().firstElement().equals(food)){
            petitSerpent.grow(cols, rows);
            spawnFood();
            scorePanel.incrementScore();
            eatSound.play();
            delay = delay - 5;
        }
        if (checkCollision(rows, cols)) {
            gameOver();
            music.stop();
            gameOverSound.play();

        }
    }

    public void spawnFood(){
        boolean onSnake = true;

        while(onSnake){
            onSnake = false;
            int foodX = (int)(Math.random() * cols);
            int foodY = (int)(Math.random() * rows);
            food = new Point(foodX,foodY);

            for (Point p : petitSerpent.getBody()){
                if (p.equals(food)){
                    onSnake = true;
                    break;
                }
            }
        }
    }

    public boolean checkCollision(int rows,int cols){
        for (int i = 1; i < petitSerpent.getBody().size(); i++){
            if (this.petitSerpent.getHead().equals(petitSerpent.getBody().get(i))){
                return true;
            }
        }
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.white);
        g.fillRect(0, 0, cols * cellSize, rows * cellSize);

        g.setColor(Color.GREEN);
        for (Point p : petitSerpent.getBody()){
            g.fillRect(p.x * cellSize, p.y * cellSize, cellSize, cellSize);
        }

        g.setColor(Color.RED);
        g.fillOval(food.x * cellSize, food.y * cellSize, cellSize, cellSize);

        if (gameOver) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setColor(new Color(222, 79, 79, 150));
            g2d.fillRect(0, 0, getWidth(), getHeight());

            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 36));
            String gameOverText = "GAME OVER";
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(gameOverText);
            g2d.drawString(gameOverText, getWidth()/2 - textWidth/2, getHeight()/2 - 30);

            g2d.setFont(new Font("Arial", Font.PLAIN, 16));
            String restartText = "Press ENTER to restart";
            int restartWidth = fm.stringWidth(restartText);
            g2d.drawString(restartText, getWidth()/2 - restartWidth/2, getHeight()/2 + 10);
        }

        if (paused) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setColor(new Color(0, 0, 0, 150));
            g2d.fillRect(0, 0, getWidth(), getHeight());

            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 36));
            String gameOverText = "PAUSED";
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(gameOverText);
            g2d.drawString(gameOverText, getWidth()/2 - textWidth/2, getHeight()/2 - 30);

            g2d.setFont(new Font("Arial", Font.PLAIN, 16));
            String restartText = "Press ENTER or P to continue";
            int restartWidth = fm.stringWidth(restartText);
            g2d.drawString(restartText, getWidth()/2 - restartWidth/2, getHeight()/2 + 10);
        }
    }

    private void restartGame() {
        petitSerpent = new Snake();
        spawnFood();
        gameOver = false;
        scorePanel.resetScore();
        music.play();
        music.loop();
        timer.start();
        requestFocusInWindow();
        repaint();
    }

    private void gameOver() {
        timer.stop();
        gameOver = true;
        System.out.println("GAME OVER");
        repaint();
    }

    private void pauseGame(){
        timer.stop();
        paused = true;
        System.out.println("|||| GAME PAUSED ||||");
        repaint();
    }

    private void continueGame(){
        timer.start();
        paused = false;
        System.out.println("|||| GAME UNPAUSED ||||");
        repaint();
    }

    private void showGameOverDialog() {
        JDialog gameOverDialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Game Over", true);
        gameOverDialog.setLayout(new BorderLayout());
        gameOverDialog.setSize(300, 200);
        gameOverDialog.setLocationRelativeTo(this);
        gameOverDialog.setResizable(false);

        JLabel messageLabel = new JLabel("GAME OVER", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel scoreLabel = new JLabel("Final Score: " + scorePanel.getScore(), SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        // Restart button
        JButton restartButton = new JButton("Restart Game");
        restartButton.setFont(new Font("Arial", Font.BOLD, 16));
        restartButton.addActionListener(e -> {
            gameOverDialog.dispose();
            restartGame();
        });

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(messageLabel);
        panel.add(scoreLabel);
        panel.add(restartButton);

        gameOverDialog.add(panel);
        gameOverDialog.setVisible(true);
    }
}