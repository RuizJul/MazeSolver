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
public class Agent {

    private MiniMax minimax;

    public Agent(MiniMax minimax) {
        this.minimax = minimax;
    }

    public Node chooseBestMove(Node root) {
        minimax.minimax(root, true);

        Node bestChild = null;
        int bestValue = Integer.MIN_VALUE;

        for (Node child : root.getChildren()) {
            if (child.getValue() > bestValue) {
                bestValue = child.getValue();
                bestChild = child;
            }
        }
        return bestChild;
    }
}
