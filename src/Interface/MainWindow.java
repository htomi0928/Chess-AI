package Interface;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainWindow extends JFrame {
    private final Timer timer;
    private final GameLogic gl;
    private final Board board;

    public MainWindow(boolean isBlackAi) {
        setTitle("Chess");
        setIconImage(new ImageIcon("src/images/king_black.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        gl = new GameLogic(isBlackAi);
        board = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR", gl);

        JPanel whitePanel = new JPanel(new BorderLayout());
        whitePanel.setBackground(Color.gray);
        JLabel whiteClockLabel = new JLabel(gl.getWhiteClock().toString());
        whiteClockLabel.setBorder(new EmptyBorder(10, 10, 5, 10));
        whiteClockLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        whiteClockLabel.setOpaque(true);
        whiteClockLabel.setBackground(Color.white);
        whitePanel.add(whiteClockLabel, BorderLayout.EAST);

        JPanel blackPanel = new JPanel(new BorderLayout());
        blackPanel.setBackground(Color.gray);
        JLabel blackClockLabel = new JLabel(gl.getBlackClock().toString());
        blackClockLabel.setBorder(new EmptyBorder(10, 10, 5, 10));
        blackClockLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        blackClockLabel.setOpaque(true);
        blackClockLabel.setBackground(Color.black);
        blackClockLabel.setForeground(Color.white);
        blackPanel.add(blackClockLabel, BorderLayout.EAST);
        add(whitePanel, BorderLayout.SOUTH);
        add(blackPanel, BorderLayout.NORTH);

        JLabel aiLabel = new JLabel("AI");
        aiLabel.setBorder(new EmptyBorder(10, 10, 5, 10));
        aiLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        if (isBlackAi) {
            blackPanel.add(aiLabel);
        }

        timer = new Timer(1000, null);
        timer.addActionListener(e -> {
            if (gl.whiteToMove()) {
                gl.getWhiteClock().takeTime();
                whiteClockLabel.setText(gl.getWhiteClock().toString());
            } else {
                gl.getBlackClock().takeTime();
                blackClockLabel.setText(gl.getBlackClock().toString());
            }
            if (gl.getBlackClock().isOutOfTime() || gl.getWhiteClock().isOutOfTime()) {
                timer.stop();
                board.setGameOver(true);
                board.setWhiteWon(gl.getBlackClock().isOutOfTime());
            }
            if (board.gameOver()) {
                timer.stop();
                String msg;
                if (board.isStaleMate()) {
                    msg = "Stalemate!";
                } else {
                    if (board.whiteWon()) {
                        msg = "White won!";
                    } else {
                        msg = "Black won!";
                    }
                }
                int n = JOptionPane.showConfirmDialog(this, msg + "\nPlay a new game?", msg, JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    gl.restartGame();
                    board.restartGame();
                    board.repaint();
                    whiteClockLabel.setText(gl.getWhiteClock().toString());
                    blackClockLabel.setText(gl.getBlackClock().toString());
                    timer.start();
                } else this.dispose();
            }
        });
        timer.start();

        JMenuBar menuBar = new JMenuBar();
        JMenu options = new JMenu("Options");
        JMenuItem restartGame = new JMenuItem("Restart");
        options.add(restartGame);
        menuBar.add(options);

        restartGame.addActionListener(e -> {
            gl.restartGame();
            board.restartGame();
            board.repaint();
            whiteClockLabel.setText(gl.getWhiteClock().toString());
            blackClockLabel.setText(gl.getBlackClock().toString());
            timer.start();
        });
        setJMenuBar(menuBar);

        add(board, BorderLayout.CENTER);
        pack();
        setResizable(false);
        setVisible(true);
    }
}
