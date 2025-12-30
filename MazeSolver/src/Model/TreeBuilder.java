/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Julian
 */
public class TreeBuilder {

    private Maze maze;
    private int maxDepth;

    // Movimientos: arriba, abajo, izquierda, derecha
    private static final int[][] MOVES = {
        {-1, 0}, //up
        {1, 0}, //down
        {0, -1}, //left
        {0, 1} //rigth
    };
    

    public TreeBuilder(Maze maze, int maxDepth) {
        this.maze = maze;
        this.maxDepth = maxDepth;
    }

    public void buildTree(Node node) {
        if (node.getDepth() >= maxDepth) {
            return;
        }
        if (maze.isGoal(node.getX(), node.getY())) {
            return;
        }

        for (int[] move : MOVES) {
            int newX = node.getX() + move[0];
            int newY = node.getY() + move[1];

            if (maze.isValidMove(newX, newY)) {
                Node child = new Node(
                        newX,
                        newY,
                        node.getDepth() + 1,
                        node
                );
                node.addChild(child);
                buildTree(child); // expansión recursiva
            }
        }
    }
}
