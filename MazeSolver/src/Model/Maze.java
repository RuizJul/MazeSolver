/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

public class Maze {

    private int[][] grid;
    private int rows;
    private int cols;

    public Maze(int[][] grid) {
        this.grid = grid;
        this.rows = grid.length;
        this.cols = grid[0].length;
    }

    public boolean isInside(int x, int y) {
        return x >= 0 && y >= 0 && x < rows && y < cols;
    }

    public boolean isWall(int x, int y) {
        return grid[x][y] == -1;
    }

    public int cellCost(int x, int y) {
        return grid[x][y];
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}

