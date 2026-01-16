/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mazesolver;
/**
 *
 * @author Julian
 */

import Model.*;
import solver.*;
import BranchAndBound.BranchAndBoundSolver;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        int[][] grid = {
                { 0,  0,  0, -1,  0 },
                { -1, -1, 0, -1,  0 },
                { 0, 10, 0,  0,  0 }, // trampa mortal
                { 0, -1, -1, -1,  0 },
                { 0,  0,  0,  0,  0 }
        };

        Maze maze = new Maze(grid);
        Node start = new Node(0, 0, 0, 5, null); // poca vida

        int goalX = 4;
        int goalY = 4;

        System.out.println("===== BFS =====");
        BFS bfs = new BFS(maze);
        Node bfsSolution = bfs.solve(start, goalX, goalY);
        printPath(bfs.getPath(bfsSolution));

        System.out.println("\n===== DFS =====");
        DFS dfs = new DFS(maze, goalX, goalY);
        Node dfsSolution = dfs.solve(start);
        printPath(dfs.getPath(dfsSolution));

        System.out.println("\n===== Branch and Bound =====");
        BranchAndBoundSolver bb =
                new BranchAndBoundSolver(maze, goalX, goalY);
        Node bbSolution = bb.solve(start);
        printPath(bbSolution);
    }

    private static void printPath(List<Node> path) {
        if (path == null || path.isEmpty()) {
            System.out.println("No hay solución");
            return;
        }

        for (Node n : path) {
            System.out.println("(" + n.x + "," + n.y + ") vida=" + n.life);
        }
    }

    private static void printPath(Node node) {
        if (node == null) {
            System.out.println("No hay solución");
            return;
        }

        if (node.parent != null) {
            printPath(node.parent);
        }

        System.out.println("(" + node.x + "," + node.y + ") vida=" + node.life);
    }
}
