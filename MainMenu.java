/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package towerofhanoi;
/**
 * Handles the main menu of the application.
 * Provides options such as Start Game, Help, and Exit.
 */

/**
 *
 * @author Infinix
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JFrame {

    JButton startButton;
    JButton helpButton;
    JButton exitButton;

    public MainMenu() {

        setTitle("Tower Of Hanoi");
        setSize(500,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel title =
                new JLabel("TOWER OF HANOI");

        title.setHorizontalAlignment(
                SwingConstants.CENTER);

        title.setFont(
                new Font("Arial", Font.BOLD, 24));

        startButton =
                new JButton("Start Game");

        helpButton =
                new JButton("Help");

        exitButton =
                new JButton("Exit");

        JPanel panel = new JPanel();

        panel.setLayout(
                new GridLayout(3,1,10,10));

        panel.add(startButton);
        panel.add(helpButton);
        panel.add(exitButton);

        add(title, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        startButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(
                            ActionEvent e) {

                        JOptionPane.showMessageDialog(
                                null,
                                "Game Started");
                    }
                });

        helpButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(
                            ActionEvent e) {

                        JOptionPane.showMessageDialog(
                                null,
                                "Move all disks from Tower A to Tower C");
                    }
                });

        exitButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(
                            ActionEvent e) {

                        System.exit(0);
                    }
                });

        setVisible(true);
    }
}

