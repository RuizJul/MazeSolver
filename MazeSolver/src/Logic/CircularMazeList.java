/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logic;

/**
 *
 * @author Julian
 */
public class CircularMazeList {

    private MazeNode current;

    public void add(MazeData data) {
        MazeNode newNode = new MazeNode(data);

        if (current == null) {
            current = newNode;
        } else {
            MazeNode last = current.prev;

            last.next = newNode;
            newNode.prev = last;

            newNode.next = current;
            current.prev = newNode;
        }
    }

    public MazeData getCurrent() {
        return current != null ? current.data : null;
    }

    public MazeData next() {
        if (current == null) {
            return null;
        }
        current = current.next;
        return current.data;
    }

    public MazeData prev() {
        if (current == null) {
            return null;
        }
        current = current.prev;
        return current.data;
    }

    public boolean isEmpty() {
        return current == null;
    }
}
