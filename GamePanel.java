/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package towerofhanoi;

/**
 *
 * @author Infinix
 */

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private GameController controller;

    public GamePanel(GameController controller) {
        this.controller = controller;

        setPreferredSize(new Dimension(800, 400));
        setBackground(new Color(245, 248, 252));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        int width = getWidth();
        int height = getHeight();

        int[] towerX = {
            width / 6,
            width / 2,
            width * 5 / 6
        };

        int baseY = height - 65;
        int poleTop = 55;
        int diskHeight = 32;

        // Title
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        g2.setColor(new Color(40, 40, 40));
        g2.drawString("Tower of Hanoi", width / 2 - 100, 30);

        // Base
        g2.setColor(new Color(80, 80, 80));
        g2.fillRoundRect(
                width / 12,
                baseY,
                width * 5 / 6,
                15,
                10,
                10
        );

        // Draw towers
        for (int i = 0; i < 3; i++) {

            int x = towerX[i];

            // Vertical pole
            g2.setColor(new Color(100, 100, 100));
            g2.fillRoundRect(
                    x - 5,
                    poleTop,
                    10,
                    baseY - poleTop,
                    8,
                    8
            );

            // Tower label
            g2.setFont(new Font("Arial", Font.BOLD, 18));
            g2.setColor(new Color(40, 40, 40));

            String towerName = "Tower " + (char) ('A' + i);

            int labelWidth =
                    g2.getFontMetrics().stringWidth(towerName);

            g2.drawString(
                    towerName,
                    x - labelWidth / 2,
                    baseY + 42
            );

            // Draw disks
            Tower tower = controller.getTower(i);

            for (int j = 0; j < tower.getDiskCount(); j++) {

                Disk disk = tower.getDisk(j);

                int size = disk.getSize();

                int diskWidth;

                if (size == 1) {
                    diskWidth = 85;
                } else if (size == 2) {
                    diskWidth = 135;
                } else {
                    diskWidth = 185;
                }

                int diskY =
                        baseY
                        - diskHeight * (j + 1);

                int diskX =
                        x - diskWidth / 2;

                // Disk
                if (size == 1) {
                    g2.setColor(new Color(52, 152, 219));
                } else if (size == 2) {
                    g2.setColor(new Color(46, 204, 113));
                } else {
                    g2.setColor(new Color(231, 76, 60));
                }

                g2.fillRoundRect(
                        diskX,
                        diskY,
                        diskWidth,
                        diskHeight - 3,
                        18,
                        18
                );

                // Disk border
                g2.setColor(new Color(50, 50, 50));
                g2.drawRoundRect(
                        diskX,
                        diskY,
                        diskWidth,
                        diskHeight - 3,
                        18,
                        18
                );

                // Disk number
                g2.setFont(
                        new Font("Arial", Font.BOLD, 14)
                );

                g2.setColor(Color.WHITE);

                String number = String.valueOf(size);

                int textWidth =
                        g2.getFontMetrics()
                                .stringWidth(number);

                g2.drawString(
                        number,
                        x - textWidth / 2,
                        diskY + 20
                );
            }
        }

        g2.dispose();
    }

    public void refresh() {
        repaint();
    }
}