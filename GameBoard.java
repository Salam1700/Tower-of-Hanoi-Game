/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package towerofhanoi;
/**
 * Represents the game board.
 * Displays towers and disks during game play.
 */

/**
 *
 * @author Infinix
 */

import javax.swing.*;
import java.awt.*;

public class GameBoard extends JFrame {

    private GameController controller;

    private JLabel towerALabel;
    private JLabel towerBLabel;
    private JLabel towerCLabel;

    private JLabel moveLabel;

    private JComboBox<String> fromBox;
    private JComboBox<String> toBox;

    public GameBoard() {

        controller = new GameController();

        setTitle("Tower of Hanoi - Game");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createGUI();

        updateBoard();

        setVisible(true);
    }

    private void createGUI() {

        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("TOWER OF HANOI");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 25));

        add(title, BorderLayout.NORTH);

        // Tower panel
        JPanel towerPanel = new JPanel(new GridLayout(1, 3));

        towerALabel = new JLabel();
        towerBLabel = new JLabel();
        towerCLabel = new JLabel();

        towerALabel.setHorizontalAlignment(SwingConstants.CENTER);
        towerBLabel.setHorizontalAlignment(SwingConstants.CENTER);
        towerCLabel.setHorizontalAlignment(SwingConstants.CENTER);

        towerPanel.add(towerALabel);
        towerPanel.add(towerBLabel);
        towerPanel.add(towerCLabel);

        add(towerPanel, BorderLayout.CENTER);

        // Bottom panel
        JPanel bottomPanel = new JPanel();

        fromBox = new JComboBox<>(
                new String[]{"Tower A", "Tower B", "Tower C"}
        );

        toBox = new JComboBox<>(
                new String[]{"Tower A", "Tower B", "Tower C"}
        );

        JButton moveButton = new JButton("Move");

        JButton restartButton = new JButton("Restart");

        moveLabel = new JLabel("Moves: 0");

        bottomPanel.add(new JLabel("From:"));
        bottomPanel.add(fromBox);

        bottomPanel.add(new JLabel("To:"));
        bottomPanel.add(toBox);

        bottomPanel.add(moveButton);
        bottomPanel.add(restartButton);

        bottomPanel.add(moveLabel);

        add(bottomPanel, BorderLayout.SOUTH);

        // Move button
        moveButton.addActionListener(e -> performMove());

        // Restart button
        restartButton.addActionListener(e -> restartGame());
    }

    private void performMove() {

        int from = fromBox.getSelectedIndex();
        int to = toBox.getSelectedIndex();

        boolean success = controller.moveDisk(from, to);

        if (success) {

            updateBoard();

            if (controller.isSolved()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Congratulations! Puzzle Solved!"
                );
            }

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Move!"
            );
        }
    }

    private void updateBoard() {

        towerALabel.setText(
                createTowerText(
                        "Tower A",
                        controller.getTower(0)
                )
        );

        towerBLabel.setText(
                createTowerText(
                        "Tower B",
                        controller.getTower(1)
                )
        );

        towerCLabel.setText(
                createTowerText(
                        "Tower C",
                        controller.getTower(2)
                )
        );

        moveLabel.setText(
                "Moves: " + controller.getMoveCount()
        );
    }

    private String createTowerText(
            String towerName,
            Tower tower) {

        StringBuilder text = new StringBuilder();

        text.append("<html>");
        text.append("<center>");

        text.append("<b>");
        text.append(towerName);
        text.append("</b>");

        text.append("<br><br>");

        if (tower.isEmpty()) {

            text.append("Empty");

        } else {

            for (int i = tower.getDiskCount() - 1; i >= 0; i--) {

                text.append(
                        "Disk " +
                        tower.getDisk(i).getSize()
                );

                text.append("<br>");
            }
        }

        text.append("</center>");
        text.append("</html>");

        return text.toString();
    }

    private void restartGame() {

        controller.resetGame();

        updateBoard();
    }
}
