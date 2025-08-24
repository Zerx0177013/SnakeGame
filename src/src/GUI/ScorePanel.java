package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class ScorePanel extends JPanel {
    private int score;
    private final JLabel scoreLabel;
    private final JLabel highScoreLabel;
    private int highScore = 0;

    public ScorePanel() {
        setLayout(new GridLayout(1, 2, 20, 0));
        setOpaque(false);

        scoreLabel = createStyledLabel("Score: 0");
        highScoreLabel = createStyledLabel("High Score: 0");

        add(scoreLabel);
        add(highScoreLabel);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setForeground(new Color(50, 50, 50));
        label.setOpaque(false);
        return label;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        GradientPaint gradient = new GradientPaint(
                0, 0, new Color(220, 220, 220),
                0, getHeight(), new Color(180, 180, 180)
        );
        g2d.setPaint(gradient);
        g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15));

        g2d.dispose();
    }

    public void incrementScore() {
        score++;
        updateScoreDisplay();
    }

    public void resetScore() {
        score = 0;
        updateScoreDisplay();
    }

    private void updateScoreDisplay() {
        if(score > highScore) {
            highScore = score;
        }

        scoreLabel.setText("Score: " + score);
        highScoreLabel.setText("High Score: " + highScore);
    }

    public int getScore() {
        return score;
    }

    public void setLabelColors(Color textColor, Color backgroundColor) {
        scoreLabel.setForeground(textColor);
        highScoreLabel.setForeground(textColor);
        setBackground(backgroundColor);
    }
}
