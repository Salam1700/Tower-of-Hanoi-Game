/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package towerofhanoi;
/**
 * Controls the overall game logic.
 * Manages moves, rules, and game progress.
 */
/**
 *
 * @author Infinix
 */

public class GameController {

    private Tower[] towers;
    private int moveCount;

    public GameController() {

        towers = new Tower[3];

        towers[0] = new Tower();
        towers[1] = new Tower();
        towers[2] = new Tower();

        moveCount = 0;

        resetGame();
    }

    public void resetGame() {

        towers[0].clear();
        towers[1].clear();
        towers[2].clear();

        towers[0].addDisk(new Disk(3));
        towers[0].addDisk(new Disk(2));
        towers[0].addDisk(new Disk(1));

        moveCount = 0;
    }

    public boolean moveDisk(int from, int to) {

        if (from == to) {
            return false;
        }

        if (towers[from].isEmpty()) {
            return false;
        }

        Disk movingDisk = towers[from].topDisk();
        Disk destinationDisk = towers[to].topDisk();

        // Check whether the move is illegal
        if (destinationDisk != null &&
                movingDisk.getSize() > destinationDisk.getSize()) {

            return false;
        }

        // Perform valid move
        towers[from].removeDisk();
        towers[to].addDisk(movingDisk);

        moveCount++;

        return true;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public Tower getTower(int index) {
        return towers[index];
    }

    public boolean isSolved() {

        return towers[2].getDiskCount() == 3;
    }
}
