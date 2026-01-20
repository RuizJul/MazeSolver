/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logic;

/**
 *
 * @author Julian
 */
public class MazeData {

    private String name;
    private int[][] matrix;

    public MazeData(String name, int[][] matrix) {
        this.name = name;
        this.matrix = matrix;
    }

    public String getName() {
        return name;
    }

    public int[][] getMatrix() {
        return matrix;
    }
}
