package GUI;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
    public MainFrame(){
        JFrame frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        ScorePanel scorePanel = new ScorePanel();

        Board board = new Board(scorePanel);

        frame.setLayout(new BorderLayout());
        frame.add(scorePanel, BorderLayout.NORTH);
        frame.add(board, BorderLayout.CENTER);

        int boardWidth = board.getCols() * board.getCellSize();
        int boardHeight = board.getRows() * board.getCellSize();
        int scorePanelHeight = 40;

        frame.setSize(boardWidth + 17, boardHeight + scorePanelHeight + 40);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(true);
    }
}
