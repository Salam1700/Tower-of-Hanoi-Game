/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package towerofhanoi;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the game board.
 * Displays towers and disks during game play.
 *
 * @author Infinix
 */
public class GameBoard extends JFrame {

    private GameController controller;

    private JLabel towerALabel;
    private JLabel towerBLabel;
    private JLabel towerCLabel;

    private JLabel moveLabel;
    private JLabel minimumMoveLabel;
    private JLabel statusLabel;
    private JLabel timerLabel;

    private JComboBox<String> fromBox;
    private JComboBox<String> toBox;

    private GameTimer gameTimer;

    public GameBoard() {

        controller = new GameController();

        setTitle("Tower of Hanoi - Game");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createGUI();

        // Create game timer
        gameTimer = new GameTimer(seconds -> {

            int minutes = seconds / 60;
            int remainingSeconds = seconds % 60;

            timerLabel.setText(
                    String.format(
                            "Time: %02d:%02d",
                            minutes,
                            remainingSeconds
                    )
            );
        });

        updateBoard();

        // Start timer when game starts
        gameTimer.start();

        setVisible(true);
    }

    private void createGUI() {

        setLayout(new BorderLayout());

        // =========================
        // Title
        // =========================

        JLabel title = new JLabel("TOWER OF HANOI");

        title.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        title.setFont(
                new Font("Arial", Font.BOLD, 25)
        );

        add(title, BorderLayout.NORTH);

        // =========================
        // Tower Panel
        // =========================

        JPanel towerPanel =
                new JPanel(new GridLayout(1, 3));

        towerALabel = new JLabel();
        towerBLabel = new JLabel();
        towerCLabel = new JLabel();

        towerALabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        towerBLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        towerCLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        towerPanel.add(towerALabel);
        towerPanel.add(towerBLabel);
        towerPanel.add(towerCLabel);

        add(towerPanel, BorderLayout.CENTER);

        // =========================
        // Bottom Panel
        // =========================

        JPanel bottomPanel = new JPanel();

        fromBox = new JComboBox<>(
                new String[]{
                    "Tower A",
                    "Tower B",
                    "Tower C"
                }
        );

        toBox = new JComboBox<>(
                new String[]{
                    "Tower A",
                    "Tower B",
                    "Tower C"
                }
        );

        JButton moveButton =
                new JButton("Move");

        JButton restartButton =
                new JButton("Restart");

        moveLabel =
                new JLabel("Moves: 0");

        minimumMoveLabel =
                new JLabel(
                        "Minimum Moves: "
                        + controller.getMinimumMoves()
                );

        statusLabel =
                new JLabel("Status: Playing");

        timerLabel =
                new JLabel("Time: 00:00");

        bottomPanel.add(
                new JLabel("From:")
        );

        bottomPanel.add(fromBox);

        bottomPanel.add(
                new JLabel("To:")
        );

        bottomPanel.add(toBox);

        bottomPanel.add(moveButton);

        bottomPanel.add(restartButton);

        bottomPanel.add(moveLabel);

        bottomPanel.add(minimumMoveLabel);

        bottomPanel.add(timerLabel);

        bottomPanel.add(statusLabel);

        add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        // =========================
        // Button Actions
        // =========================

        moveButton.addActionListener(
                e -> performMove()
        );

        restartButton.addActionListener(
                e -> restartGame()
        );
    }

    // =========================
    // Perform Move
    // =========================

    private void performMove() {

        int from =
                fromBox.getSelectedIndex();

        int to =
                toBox.getSelectedIndex();

        boolean success =
                controller.moveDisk(from, to);

        if (success) {

            updateBoard();

            if (controller.isSolved()) {

                // Stop timer
                gameTimer.stop();

                statusLabel.setText(
                        "Status: Completed"
                );

                JOptionPane.showMessageDialog(
                        this,

                        "Congratulations! "
                        + "Puzzle Solved!\n\n"

                        + "Your Moves: "
                        + controller.getMoveCount()
                        + "\n"

                        + "Minimum Moves: "
                        + controller.getMinimumMoves()
                        + "\n"

                        + "Your Time: "
                        + timerLabel.getText().substring(6),

                        "Game Completed",

                        JOptionPane.INFORMATION_MESSAGE
                );
            }

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Move!",
                    "Invalid Move",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // =========================
    // Update Board
    // =========================

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

        // Update move count
        moveLabel.setText(
                "Moves: "
                + controller.getMoveCount()
        );

        // Update minimum moves
        minimumMoveLabel.setText(
                "Minimum Moves: "
                + controller.getMinimumMoves()
        );

        // Update game status
        if (controller.isGameCompleted()) {

            statusLabel.setText(
                    "Status: Completed"
            );

        } else {

            statusLabel.setText(
                    "Status: Playing"
            );
        }
    }

    // =========================
    // Create Tower Display
    // =========================

    private String createTowerText(
            String towerName,
            Tower tower) {

        StringBuilder text =
                new StringBuilder();

        text.append("<html>");
        text.append("<center>");

        text.append("<b>");
        text.append(towerName);
        text.append("</b>");

        text.append("<br><br>");

        if (tower.isEmpty()) {

            text.append("Empty");

        } else {

            for (
                    int i = tower.getDiskCount() - 1;
                    i >= 0;
                    i--
            ) {

                text.append(
                        "Disk "
                        + tower.getDisk(i).getSize()
                );

                text.append("<br>");
            }
        }

        text.append("</center>");
        text.append("</html>");

        return text.toString();
    }

    // =========================
    // Restart Game
    // =========================

    private void restartGame() {

        controller.resetGame();

        // Reset timer
        gameTimer.reset();

        // Update board
        updateBoard();

        // Update status
        statusLabel.setText(
                "Status: Playing"
        );

        // Start timer again
        gameTimer.start();
    }
}