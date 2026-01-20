/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logic;

import Logic.MazeData;

/**
 *
 * @author Julian
 */
public class MazeNode {

    public MazeData data;
    public MazeNode next;
    public MazeNode prev;

    public MazeNode(MazeData data) {
        this.data = data;
        this.next = this;
        this.prev = this;
    }
}
