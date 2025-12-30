/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MiniMax;

import Model.*;

/**
 *
 * @author Julian
 */
public class MiniMax {

    private Maze maze;

    public MiniMax(Maze maze) {
        this.maze = maze;
    }

    public int minimax(Node node, boolean isMax) {

        // Nodo hoja
        if (node.isLeaf()) {
            int value = evaluate(node);
            node.setValue(value);
            return value;
        }

        int bestValue = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (Node child : node.getChildren()) {
            int childValue = minimax(child, !isMax);

            if (isMax) {
                bestValue = Math.max(bestValue, childValue);
            } else {
                bestValue = Math.min(bestValue, childValue);
            }
        }

        node.setValue(bestValue);
        return bestValue;
    }

    // Función de evaluación (heurística)
    private int evaluate(Node node) {
        if (maze.isGoal(node.getX(), node.getY())) {
            return 100 - node.getDepth();
        }
        return -node.getDepth();
    }
}

