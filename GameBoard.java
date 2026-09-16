package towerofhanoi;

import javax.swing.*;
import java.awt.*;

public class GameBoard extends JFrame {

    private GameController controller;

    private JLabel moveLabel;
    private JLabel minimumMoveLabel;
    private JLabel statusLabel;
    private JLabel timerLabel;

    private JComboBox<String> fromBox;
    private JComboBox<String> toBox;

    private JButton moveButton;
    private JButton restartButton;
    private JButton autoSolveButton;
    private JButton aboutButton;

    private GameTimer gameTimer;

    private GamePanel gamePanel;

    private boolean autoSolving = false;

    public GameBoard() {

        controller = new GameController();

        setTitle("Tower of Hanoi - Game");
        setSize(850, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createGUI();

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

        gameTimer.start();

        setVisible(true);
    }

    private void createGUI() {

        setLayout(new BorderLayout(10, 10));

        // =========================
        // TOP TITLE
        // =========================

        JPanel titlePanel = new JPanel();

        JLabel title = new JLabel("TOWER OF HANOI");

        title.setFont(
                new Font("Arial", Font.BOLD, 28)
        );

        titlePanel.add(title);

        add(titlePanel, BorderLayout.NORTH);

        // =========================
        // GAME PANEL
        // =========================

        gamePanel = new GamePanel(controller);

        add(
                gamePanel,
                BorderLayout.CENTER
        );

        // =========================
        // CONTROL PANEL
        // =========================

        JPanel controlPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                8,
                                8
                        )
                );

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

        moveButton =
                new JButton("Move");

        restartButton =
                new JButton("Restart");

        autoSolveButton =
                new JButton("Auto Solve");

        aboutButton =
                new JButton("About");

        controlPanel.add(
                new JLabel("From:")
        );

        controlPanel.add(fromBox);

        controlPanel.add(
                new JLabel("To:")
        );

        controlPanel.add(toBox);

        controlPanel.add(moveButton);
        controlPanel.add(restartButton);
        controlPanel.add(autoSolveButton);
        controlPanel.add(aboutButton);

        // =========================
        // INFORMATION PANEL
        // =========================

        JPanel infoPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                25,
                                5
                        )
                );

        moveLabel =
                new JLabel("Moves: 0");

        minimumMoveLabel =
                new JLabel(
                        "Minimum Moves: "
                        + controller.getMinimumMoves()
                );

        timerLabel =
                new JLabel("Time: 00:00");

        statusLabel =
                new JLabel("Status: Playing");

        infoPanel.add(moveLabel);
        infoPanel.add(minimumMoveLabel);
        infoPanel.add(timerLabel);
        infoPanel.add(statusLabel);

        // =========================
        // BOTTOM PANEL
        // =========================

        JPanel bottomPanel =
                new JPanel(
                        new BorderLayout()
                );

        bottomPanel.add(
                controlPanel,
                BorderLayout.CENTER
        );

        bottomPanel.add(
                infoPanel,
                BorderLayout.SOUTH
        );

        add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        // =========================
        // BUTTON ACTIONS
        // =========================

        moveButton.addActionListener(
                e -> performMove()
        );

        restartButton.addActionListener(
                e -> restartGame()
        );

        autoSolveButton.addActionListener(
                e -> startAutoSolve()
        );

        aboutButton.addActionListener(
                e -> showAbout()
        );
    }

    // =========================
    // MANUAL MOVE
    // =========================

    private void performMove() {

        if (autoSolving) {
            return;
        }

        int from =
                fromBox.getSelectedIndex();

        int to =
                toBox.getSelectedIndex();

        boolean success =
                controller.moveDisk(
                        from,
                        to
                );

        if (success) {

            updateBoard();

            if (controller.isSolved()) {

                gameTimer.stop();

                statusLabel.setText(
                        "Status: Completed"
                );

                showSuccessMessage();
            }

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Move!\n\n"
                    + "You cannot place a larger disk "
                    + "on a smaller disk.",
                    "Invalid Move",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // =========================
    // UPDATE BOARD
    // =========================

    public void updateBoard() {

        gamePanel.refresh();

        moveLabel.setText(
                "Moves: "
                + controller.getMoveCount()
        );

        minimumMoveLabel.setText(
                "Minimum Moves: "
                + controller.getMinimumMoves()
        );

        if (controller.isGameCompleted()) {

            statusLabel.setText(
                    "Status: Completed"
            );

        } else if (autoSolving) {

            statusLabel.setText(
                    "Status: Auto Solving..."
            );

        } else {

            statusLabel.setText(
                    "Status: Playing"
            );
        }
    }

    // =========================
    // RESTART
    // =========================

    private void restartGame() {

        if (autoSolving) {
            return;
        }

        controller.resetGame();

        gameTimer.reset();

        updateBoard();

        statusLabel.setText(
                "Status: Playing"
        );

        gameTimer.start();
    }

    // =========================
    // AUTO SOLVE
    // =========================

    private void startAutoSolve() {

        if (autoSolving) {
            return;
        }

        if (controller.isSolved()) {

            JOptionPane.showMessageDialog(
                    this,
                    "The puzzle is already solved.",
                    "Auto Solve",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Do you want to automatically solve "
                        + "the puzzle?",
                        "Auto Solve",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        autoSolving = true;

        moveButton.setEnabled(false);
        restartButton.setEnabled(false);
        autoSolveButton.setEnabled(false);
        fromBox.setEnabled(false);
        toBox.setEnabled(false);

        gameTimer.stop();

        statusLabel.setText(
                "Status: Auto Solving..."
        );

        AutoSolver solver =
                new AutoSolver(
                        controller,
                        this
                );

        solver.solve();
    }

    // =========================
    // AUTO SOLVE FINISHED
    // =========================

    public void autoSolveFinished() {

        SwingUtilities.invokeLater(() -> {

            autoSolving = false;

            moveButton.setEnabled(true);
            restartButton.setEnabled(true);
            autoSolveButton.setEnabled(true);
            fromBox.setEnabled(true);
            toBox.setEnabled(true);

            updateBoard();

            gameTimer.stop();

            if (controller.isSolved()) {

                statusLabel.setText(
                        "Status: Completed"
                );

                showSuccessMessage();
            }
        });
    }

    // =========================
    // SUCCESS MESSAGE
    // =========================

    private void showSuccessMessage() {

        JOptionPane.showMessageDialog(
                this,
                "Congratulations!\n\n"
                + "Puzzle Solved Successfully!\n\n"
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

    // =========================
    // ABOUT
    // =========================

    private void showAbout() {

        JOptionPane.showMessageDialog(
                this,
                "Tower of Hanoi Game\n\n"
                + "Developed using Java Swing\n"
                + "Algorithm: Recursive Tower of Hanoi\n\n"
                + "Features:\n"
                + "- Manual Disk Movement\n"
                + "- Move Validation\n"
                + "- Move Counter\n"
                + "- Timer\n"
                + "- Auto Solver\n"
                + "- Win Detection",
                "About",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}