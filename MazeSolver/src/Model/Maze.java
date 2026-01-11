/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

public class Maze {

    private char[][] grid;
    private int rows;
    private int cols;

    public Maze(char[][] grid) {
        this.grid = grid;
        this.rows = grid.length;
        this.cols = grid[0].length;
    }

    // ---------- Getters ----------
    public int getRows() { return rows; }
    public int getCols() { return cols; }

    // ---------- Validaciones básicas ----------
    public boolean isInside(int x, int y) {
        return x >= 0 && x < rows && y >= 0 && y < cols;
    }

    public boolean isWall(int x, int y) {
        return grid[x][y] == '#';
    }

    public boolean isValidMove(int x, int y) {
        return isInside(x, y) && !isWall(x, y);
    }

    public boolean isGoal(int x, int y) {
        return grid[x][y] == 'G';
    }

    // ---------- Tipo de celda ----------
    public char getCell(int x, int y) {
        return grid[x][y];
    }

    public boolean isTrap(int x, int y) {
        return grid[x][y] == 'T';
    }

    public boolean isHealing(int x, int y) {
        return grid[x][y] == 'H';
    }
}
