/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package towerofhanoi;
/**
 * Represents a tower in the Tower of Hanoi game.
 * Stores and manages disks placed on the tower.
 */

/**
 *
 * @author Infinix
 */

import java.util.Stack;

public class Tower {

    private Stack<Disk> disks;

    public Tower() {
        disks = new Stack<>();
    }

    public void addDisk(Disk disk) {
        disks.push(disk);
    }

    public Disk removeDisk() {
        if (disks.isEmpty()) {
            return null;
        }

        return disks.pop();
    }

    public Disk topDisk() {
        if (disks.isEmpty()) {
            return null;
        }

        return disks.peek();
    }

    public boolean isEmpty() {
        return disks.isEmpty();
    }

    public int getDiskCount() {
        return disks.size();
    }

    public Disk getDisk(int index) {
        return disks.get(index);
    }

    public void clear() {
        disks.clear();
    }
}
