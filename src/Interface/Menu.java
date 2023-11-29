package Interface;

import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame {

    public Menu() {
        setTitle("Chess");
        setIconImage((new ImageIcon("src/images/king_black.png")).getImage());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 130);

        JPanel gameModePanel = new JPanel();
        gameModePanel.setLayout(new BorderLayout());
        add(new JLabel("GAME MODE: ", SwingConstants.CENTER), BorderLayout.NORTH);
        JRadioButton singleplayer = new JRadioButton("Single-player");
        JRadioButton multiplayer = new JRadioButton("Multiplayer");
        singleplayer.setSelected(true);
        ButtonGroup bg = new ButtonGroup();
        bg.add(singleplayer);
        bg.add(multiplayer);

        gameModePanel.add(singleplayer, BorderLayout.NORTH);
        gameModePanel.add(multiplayer, BorderLayout.SOUTH);
        add(gameModePanel);

        JButton startButton = new JButton("START");
        this.getRootPane().setDefaultButton(startButton);
        startButton.requestFocus();
        startButton.setBackground(Color.BLUE);
        startButton.setOpaque(true);
        startButton.setForeground(Color.white);
        startButton.addActionListener(e -> {
            boolean isBlackAI = singleplayer.isSelected();
            new MainWindow(isBlackAI);
            Menu.super.dispose();
        });
        add(startButton, BorderLayout.SOUTH);
        setResizable(false);
        setVisible(true);
    }
}
