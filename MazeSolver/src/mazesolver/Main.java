/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mazesolver;
import Model.*;
import MiniMax.*;
/**
 *
 * @author Julian
 */

public class Main {

    public static void main(String[] args) {

        char[][] grid = {
            {'S', '.', '#', '.', '.', '.', '#'},
            {'#', '.', '#', '.', '#', '.', '#'},
            {'#', '.', '.', '.', '#', '.', '.'},
            {'#', '#', '#', '.', '#', '#', '.'},
            {'.', '.', '.', '.', '.', '.', '#'},
            {'#', '.', '#', '#', '#', '.', '#'},
            {'#', '.', '.', '.', '#', '.', 'G'}
        };

        Maze maze = new Maze(grid);

        // Nodo raíz (posición inicial)
        Node root = new Node(0, 0, 0, null);

        // Resolver usando DFS
        DFS solver = new DFS(maze);
        Node goalNode = solver.solve(root);

        if (goalNode != null) {
            System.out.println("Path to goal:");
            printPath(goalNode);
        } else {
            System.out.println("No path found.");
        }
    }

    private static void printPath(Node node) {
        if (node == null) return;
        printPath(node.getParent());
        System.out.println("(" + node.getX() + ", " + node.getY() + ")");
    }
}